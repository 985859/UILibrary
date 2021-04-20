package com.mylibrary.api.utils.file;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.mylibrary.api.utils.LogUtil;
import com.mylibrary.api.utils.StringUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/06/22
 *     desc  : utils about file io
 *     writeFileFromIS            : 将输入流写入文件
 * writeFileFromBytesByStream : 将字节数组写入文件
 * writeFileFromBytesByChannel: 将字节数组写入文件
 * writeFileFromBytesByMap    : 将字节数组写入文件
 * writeFileFromString        : 将字符串写入文件
 * readFile2List              : 读取文件到字符串链表中
 * readFile2String            : 读取文件到字符串中
 * readFile2BytesByStream     : 读取文件到字节数组中
 * readFile2BytesByChannel    : 读取文件到字节数组中
 * readFile2BytesByMap        : 读取文件到字节数组中
 * setBufferSize              : 设置缓冲区尺寸
 * writeTxtToFile             : 将字符串写入到文本文件中
 * getFileTxtContent          : 读取指定目录下的所有TXT文件的文件内容
 * </pre>
 */
public final class FileIOUtils {

    private static int sBufferSize = 524288;

    private FileIOUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 将path换成Base64编码的字符串
     */
    public static String pathToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    /**
     * Write file from input stream.
     * 将输入流写入文件
     *
     * @param filePath The path of file.
     * @param is       The input stream.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return writeFileFromIS(FileUtils.getFileByPath(filePath), is, false, null);
    }

    /**
     * Write file from input stream.
     *
     * @param filePath The path of file.
     * @param is       The input stream.
     * @param append   True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(FileUtils.getFileByPath(filePath), is, append, null);
    }

    /**
     * Write file from input stream.
     *
     * @param file The file.
     * @param is   The input stream.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final File file, final InputStream is) {
        return writeFileFromIS(file, is, false, null);
    }

    /**
     * Write file from input stream.
     *
     * @param file   The file.
     * @param is     The input stream.
     * @param append True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(file, is, append, null);
    }


    ///////////////////////////////////////////////////////////////////////////
    // writeFileFromIS with progress
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write file from input stream.
     *
     * @param filePath The path of file.
     * @param is       The input stream.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final OnProgressUpdateListener listener) {
        return writeFileFromIS(FileUtils.getFileByPath(filePath), is, false, listener);
    }

    /**
     * Write file from input stream.
     *
     * @param filePath The path of file.
     * @param is       The input stream.
     * @param append   True to append, false otherwise.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append,
                                          final OnProgressUpdateListener listener) {
        return writeFileFromIS(FileUtils.getFileByPath(filePath), is, append, listener);
    }

    /**
     * Write file from input stream.
     *
     * @param file     The file.
     * @param is       The input stream.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final OnProgressUpdateListener listener) {
        return writeFileFromIS(file, is, false, listener);
    }

    /**
     * Write file from input stream.
     *
     * @param file     The file.
     * @param is       The input stream.
     * @param append   True to append, false otherwise.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append,
                                          final OnProgressUpdateListener listener) {
        if (is == null || !FileUtils.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append), sBufferSize);
            if (listener == null) {
                byte[] data = new byte[sBufferSize];
                for (int len; (len = is.read(data)) != -1; ) {
                    os.write(data, 0, len);
                }
            } else {
                double totalSize = is.available();
                int curSize = 0;
                listener.onProgressUpdate(0);
                byte[] data = new byte[sBufferSize];
                for (int len; (len = is.read(data)) != -1; ) {
                    os.write(data, 0, len);
                    curSize += len;
                    listener.onProgressUpdate(curSize / totalSize);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // writeFileFromBytesByStream without progress
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write file from bytes by stream.
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(FileUtils.getFileByPath(filePath), bytes, false, null);
    }

    /**
     * Write file from bytes by stream.
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final String filePath,
                                                     final byte[] bytes,
                                                     final boolean append) {
        return writeFileFromBytesByStream(FileUtils.getFileByPath(filePath), bytes, append, null);
    }

    /**
     * Write file from bytes by stream.
     *
     * @param file  The file.
     * @param bytes The bytes.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false, null);
    }

    /**
     * Write file from bytes by stream.
     *
     * @param file   The file.
     * @param bytes  The bytes.
     * @param append True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final File file,
                                                     final byte[] bytes,
                                                     final boolean append) {
        return writeFileFromBytesByStream(file, bytes, append, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // writeFileFromBytesByStream with progress
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Write file from bytes by stream.
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final String filePath,
                                                     final byte[] bytes,
                                                     final OnProgressUpdateListener listener) {
        return writeFileFromBytesByStream(FileUtils.getFileByPath(filePath), bytes, false, listener);
    }

    /**
     * Write file from bytes by stream.
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final String filePath,
                                                     final byte[] bytes,
                                                     final boolean append,
                                                     final OnProgressUpdateListener listener) {
        return writeFileFromBytesByStream(FileUtils.getFileByPath(filePath), bytes, append, listener);
    }

    /**
     * Write file from bytes by stream.
     *
     * @param file     The file.
     * @param bytes    The bytes.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final File file,
                                                     final byte[] bytes,
                                                     final OnProgressUpdateListener listener) {
        return writeFileFromBytesByStream(file, bytes, false, listener);
    }

    /**
     * Write file from bytes by stream.
     *
     * @param file     The file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param listener The progress update listener.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByStream(final File file,
                                                     final byte[] bytes,
                                                     final boolean append,
                                                     final OnProgressUpdateListener listener) {
        if (bytes == null) return false;
        return writeFileFromIS(file, new ByteArrayInputStream(bytes), append, listener);
    }

    /**
     * Write file from bytes by channel.
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param isForce  是否写入文件
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(FileUtils.getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * Write file from bytes by channel.
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param isForce  True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(FileUtils.getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * Write file from bytes by channel.
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param isForce True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * Write file from bytes by channel.
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param append  True to append, false otherwise.
     * @param isForce True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        if (bytes == null) {
            Log.e("FileIOUtils", "bytes is null.");
            return false;
        }
        if (!FileUtils.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            if (fc == null) {
                Log.e("FileIOUtils", "fc is null.");
                return false;
            }
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write file from bytes by map.
     * 将字节数组写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param isForce  True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(filePath, bytes, false, isForce);
    }

    /**
     * Write file from bytes by map.
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param isForce  True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByMap(final String filePath,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(FileUtils.getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * Write file from bytes by map.
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param isForce True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * Write file from bytes by map.
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param append  True to append, false otherwise.
     * @param isForce True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByMap(final File file,
                                                  final byte[] bytes,
                                                  final boolean append,
                                                  final boolean isForce) {
        if (bytes == null || !FileUtils.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            if (fc == null) {
                Log.e("FileIOUtils", "fc is null.");
                return false;
            }
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) mbb.force();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Write file from string.
     *
     * @param filePath The path of file.
     * @param content  The string of content.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(FileUtils.getFileByPath(filePath), content, false);
    }

    /**
     * Write file from string.
     *
     * @param filePath The path of file.
     * @param content  The string of content.
     * @param append   True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromString(final String filePath,
                                              final String content,
                                              final boolean append) {
        return writeFileFromString(FileUtils.getFileByPath(filePath), content, append);
    }

    /**
     * Write file from string.
     *
     * @param file    The file.
     * @param content The string of content.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    /**
     * Write file from string.
     *
     * @param file    The file.
     * @param content The string of content.
     * @param append  True to append, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromString(final File file,
                                              final String content,
                                              final boolean append) {
        if (file == null || content == null) return false;
        if (!FileUtils.createOrExistsFile(file)) {
            Log.e("FileIOUtils", "create file <" + file + "> failed.");
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("writeFileFromString ", e.toString());
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // the divide line of write and read
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the lines in file.
     * 读取文件到字符串链表中
     *
     * @param filePath The path of file.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final String filePath) {
        return readFile2List(context, FileUtils.getFileByPath(filePath), null);
    }

    /**
     * Return the lines in file.
     *
     * @param filePath    The path of file.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final String filePath, final String charsetName) {
        return readFile2List(context, FileUtils.getFileByPath(filePath), charsetName);
    }

    /**
     * Return the lines in file.
     *
     * @param file The file.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final File file) {
        return readFile2List(context, file, 0, 0x7FFFFFFF, null);
    }

    /**
     * Return the lines in file.
     *
     * @param file        The file.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final File file, final String charsetName) {
        return readFile2List(context, file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * Return the lines in file.
     *
     * @param filePath The path of file.
     * @param st       The line's index of start.
     * @param end      The line's index of end.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final String filePath, final int st, final int end) {
        return readFile2List(context, FileUtils.getFileByPath(filePath), st, end, null);
    }

    /**
     * Return the lines in file.
     *
     * @param filePath    The path of file.
     * @param st          The line's index of start.
     * @param end         The line's index of end.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final String filePath,
                                             final int st,
                                             final int end,
                                             final String charsetName) {
        return readFile2List(context, FileUtils.getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * Return the lines in file.
     *
     * @param file The file.
     * @param st   The line's index of start.
     * @param end  The line's index of end.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final File file, final int st, final int end) {
        return readFile2List(context, file, st, end, null);
    }

    /**
     * Return the lines in file.
     *
     * @param file        The file.
     * @param st          The line's index of start.
     * @param end         The line's index of end.
     * @param charsetName The name of charset.
     * @return the lines in file
     */
    public static List<String> readFile2List(Context context, final File file,
                                             final int st,
                                             final int end,
                                             final String charsetName) {
        if (!FileUtils.isFileExists(context, file)) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> list = new ArrayList<>();
            if (StringUtil.isSpace(charsetName)) {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(file), charsetName)
                );
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) list.add(line);
                ++curLine;
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the string in file.
     *
     * @param filePath The path of file.
     * @return the string in file
     */
    public static String readFile2String(Context context, final String filePath) {
        return readFile2String(context, FileUtils.getFileByPath(filePath), null);
    }

    /**
     * Return the string in file.
     *
     * @param filePath    The path of file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    public static String readFile2String(Context context, final String filePath, final String charsetName) {
        return readFile2String(context, FileUtils.getFileByPath(filePath), charsetName);
    }

    /**
     * Return the string in file.
     *
     * @param file The file.
     * @return the string in file
     */
    public static String readFile2String(Context context, final File file) {
        return readFile2String(context, file, null);
    }

    /**
     * Return the string in file.
     *
     * @param file        The file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    public static String readFile2String(Context context, final File file, final String charsetName) {
        byte[] bytes = readFile2BytesByStream(context, file);
        if (bytes == null) return null;
        if (StringUtil.isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // readFile2BytesByStream without progress
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the bytes in file by stream.
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(Context context, final String filePath) {
        return readFile2BytesByStream(context, FileUtils.getFileByPath(filePath), null);
    }

    /**
     * Return the bytes in file by stream.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(Context context, final File file) {
        return readFile2BytesByStream(context, file, null);
    }

    ///////////////////////////////////////////////////////////////////////////
    // readFile2BytesByStream with progress
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Return the bytes in file by stream.
     *
     * @param filePath The path of file.
     * @param listener The progress update listener.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(Context context, final String filePath,
                                                final OnProgressUpdateListener listener) {
        return readFile2BytesByStream(context, FileUtils.getFileByPath(filePath), listener);
    }

    /**
     * Return the bytes in file by stream.
     *
     * @param file     The file.
     * @param listener The progress update listener.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(Context context, final File file,
                                                final OnProgressUpdateListener listener) {
        if (!FileUtils.isFileExists(context, file)) return null;
        try {
            ByteArrayOutputStream os = null;
            InputStream is = new BufferedInputStream(new FileInputStream(file), sBufferSize);
            try {
                os = new ByteArrayOutputStream();
                byte[] b = new byte[sBufferSize];
                int len;
                if (listener == null) {
                    while ((len = is.read(b, 0, sBufferSize)) != -1) {
                        os.write(b, 0, len);
                    }
                } else {
                    double totalSize = is.available();
                    int curSize = 0;
                    listener.onProgressUpdate(0);
                    while ((len = is.read(b, 0, sBufferSize)) != -1) {
                        os.write(b, 0, len);
                        curSize += len;
                        listener.onProgressUpdate(curSize / totalSize);
                    }
                }
                return os.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the bytes in file by channel.
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByChannel(Context context, final String filePath) {
        return readFile2BytesByChannel(context, FileUtils.getFileByPath(filePath));
    }

    /**
     * Return the bytes in file by channel.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByChannel(Context context, final File file) {
        if (!FileUtils.isFileExists(context, file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            if (fc == null) {
                Log.e("FileIOUtils", "fc is null.");
                return new byte[0];
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the bytes in file by map.
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByMap(Context context, final String filePath) {
        return readFile2BytesByMap(context, FileUtils.getFileByPath(filePath));
    }

    /**
     * Return the bytes in file by map.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByMap(Context context, final File file) {
        if (!FileUtils.isFileExists(context, file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            if (fc == null) {
                Log.e("FileIOUtils", "fc is null.");
                return new byte[0];
            }
            int size = (int) fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] result = new byte[size];
            mbb.get(result, 0, size);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Set the buffer's size.
     * <p>Default size equals 8192 bytes.</p>
     * 设置缓冲区尺寸
     *
     * @param bufferSize The buffer's size.
     */
    public static void setBufferSize(final int bufferSize) {
        sBufferSize = bufferSize;
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(double progress);
    }


    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            // 清空之前数据  重新写入  如不需要可注释
            raf.setLength(0);
            raf.write(strContent.getBytes());
            raf.close();
            LogUtil.e("文件写入  " + strContent + "  成功");
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }

    }

    //生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    //生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    //读取指定目录下的所有TXT文件的文件内容
    public static String getFileTxtContent(File file) {
        String content = "";
        if (!file.isDirectory()) {  //检查此路径名的文件是否是一个目录(文件夹)
            if (file.getName().endsWith("txt")) {//文件格式为""文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader
                                = new InputStreamReader(instream, "UTF-8");
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line = "";
                        //分行读取
                        while ((line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();//关闭输入流
                    }
                } catch (FileNotFoundException e) {
                    Log.d("TestFile", "The File doesn't not exist.");
                } catch (IOException e) {
                    Log.d("TestFile", e.getMessage());
                }
            }
        }
        return content;
    }


}
