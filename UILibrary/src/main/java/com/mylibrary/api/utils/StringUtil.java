package com.mylibrary.api.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
    private static Pattern numericStringPattern = Pattern.compile("^[0-9\\-\\-]+$");
    private static Pattern floatNumericPattern = Pattern
            .compile("^[0-9\\-\\.]+$");
    private static Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
    public static final String splitStrPattern = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";
    private static String nums[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private static String pos_units[] = {"", "十", "百", "千"};
    private static String weight_units[] = {"", "万", "亿"};

    /**
     * 判断 字符串是否为空
     **/
    public static boolean isEmpty(CharSequence str) {
        if (str == null || "".equals(str) || "null".equals(str) || "NULL".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断 字符串不为空
     **/
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否为汉字
     *
     * @param str
     * @return
     */

    public static boolean isChinese(String str) {
        if (isEmpty(str)) {
            return false;
        }
        int n = 0;
        for (int i = 0; i < str.length(); i++) {
            n = (int) str.charAt(i);
            if (!(19968 <= n && n < 40869)) {
                return false;
            }

        }
        return true;
    }

    /**
     * 判断是否纯字母组合
     *
     * @param src 源字符串
     * @return 是否纯字母组合的标志
     */
    public static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * 判断字符串是否仅为数字:
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (isEmpty(str)) {
            return false;
        }
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;

    }

    /*
     * 是否为浮点数double或float类型。
     * @param str 传入的字符串。
     * @return 是浮点数返回true,否则返回false。
     */
    public static boolean isDoubleOrFloat(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断邮箱是否合法
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isPhone(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、   159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][3456789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }


    /**
     * 转换Int
     **/
    public static int toInt(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 转换Long
     **/
    public static long toLong(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Long.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param str 转换的数据
     * @return
     * @author: hukui
     * @date: 2019/8/30
     * @description 描述一下方法的作用
     */
    public static double toDouble(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Double.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * @param dStr   转换的数据
     * @param format 保留小数点转换的个式:0 占位 不足补0  # 不占为 如： 0.00 0.##  详细了解DecimalFormat的用法
     * @return
     * @author: hukui
     * @date: 2019/8/30
     * @description 描述一下方法的作用
     */
    public static String toDouble(String dStr, String format) {
        if (isEmpty(format)) {
            return "0.00";
        }
        double d = toDouble(dStr);
        try {
            return doubleToString(d, format);
        } catch (Exception e) {
            return "0.00";
        }
    }


    /**
     * /**
     *
     * @param d 转换的数据
     * @return 默认 保留两位小数
     * @author: hukui
     * @date: 2019/8/30
     */
    public static String doubleToString(double d) {
        return doubleToString(d, "0.00");
    }

    /**
     * @param d      转换的数据
     * @param format 保留小数点转换的个式:0 占位 不足补0  # 不占为 如： 0.00 0.##  详细了解DecimalFormat的用法
     * @return
     * @author: hukui
     * @date: 2019/8/30
     * @description 描述一下方法的作用
     */
    public static String doubleToString(double d, String format) {
        if (isEmpty(format)) {
            return "";
        }
        try {
            DecimalFormat df = new DecimalFormat(format);
            return df.format(d);
        } catch (Exception e) {
            return "";
        }
    }


    /**
     * @param str 转换的数据
     * @return
     * @author: hukui
     * @date: 2019/8/30
     * @description 描述一下方法的作用
     */
    public static float toFloat(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        try {
            return Float.valueOf(str);
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 复制到剪切板
     *
     * @param szContent
     */
    @SuppressLint({"NewApi", "ServiceCast"})
    public static void copyToClipboard(Context context, String szContent) {
        String sourceText = szContent;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(sourceText);
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", sourceText);
            clipboard.setPrimaryClip(clip);
        }
        ToastUtil.showShort("复制成功");
    }

    /**
     * 数字转汉字
     *
     * @param num
     * @return
     */
    public static String arabToChinese(int num) {
        if (num == 0) {
            return "零";
        }
        int weigth = 0;//节权位
        String chinese = "";
        String chinese_section = "";
        boolean setZero = false;//下一小节是否需要零，第一次没有上一小节所以为false
        while (num > 0) {
            int section = num % 10000;//得到最后面的小节
            if (setZero) {//判断上一小节的千位是否为零，是就设置零
                chinese = nums[0] + chinese;
            }
            chinese_section = sectionTrans(section);
            if (section != 0) {//判断是都加节权位
                chinese_section = chinese_section + weight_units[weigth];
            }
            chinese = chinese_section + chinese;
            chinese_section = "";
            setZero = (section < 1000) && (section > 0);
            num = num / 10000;
            weigth++;
        }
        if ((chinese.length() == 2 || (chinese.length() == 3)) && chinese.contains("一十")) {
            chinese = chinese.substring(1, chinese.length());
        }
        if (chinese.indexOf("一十") == 0) {
            chinese = chinese.replaceFirst("一十", "十");
        }
        return chinese;
    }

    /**
     * 将每段数字转汉字
     *
     * @param section
     * @return
     */
    public static String sectionTrans(int section) {
        StringBuilder section_chinese = new StringBuilder();
        int pos = 0;//小节内部权位的计数器
        boolean zero = true;//小节内部的置零判断，每一个小节只能有一个零。
        while (section > 0) {
            int v = section % 10;//得到最后一个数
            if (v == 0) {
                if (!zero) {
                    zero = true;//需要补零的操作，确保对连续多个零只是输出一个
                    section_chinese.insert(0, nums[0]);
                }
            } else {
                zero = false;//有非零数字就把置零打开
                section_chinese.insert(0, pos_units[pos]);
                section_chinese.insert(0, nums[v]);
            }
            pos++;
            section = section / 10;
        }

        return section_chinese.toString();
    }

    // 传入阿拉伯数字返回罗马数字
    public static String arabToRoman(int Arab) {
        String Roman = "";
        String[][] list = {
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"},
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"},
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"},
                {"", "M", "MM", "MMM", "", "", "", "", "", ""}
        };
        Roman += list[3][Arab / 1000 % 10];
        Roman += list[2][Arab / 100 % 10];
        Roman += list[1][Arab / 10 % 10];
        Roman += list[0][Arab % 10];
        return Roman;
    }

    /**
     * 罗马数字转阿拉伯数字
     **/
    public static int romanToInt(String roman) {
        int res = 0;
        try {
            while (roman.charAt(0) == 'M') {
                res += 1000;
                roman = roman.substring(1);
            }
            if (roman.charAt(0) == 'D') {
                res += 500;
                roman = roman.substring(1);
            }
            while (roman.charAt(0) == 'C') {
                res += 100;
                roman = roman.substring(1);
            }
            if (roman.charAt(0) == 'D') {
                res += 300;
                roman = roman.substring(1);
            } else if (roman.charAt(0) == 'M') {
                res += 800;
                roman = roman.substring(1);
            }
            if (roman.charAt(0) == 'L') {
                res += 50;
                roman = roman.substring(1);
            }
            while (roman.charAt(0) == 'X') {
                res += 10;
                roman = roman.substring(1);
            }
            if (roman.charAt(0) == 'L') {
                res += 30;
                roman = roman.substring(1);
            } else if (roman.charAt(0) == 'C') {
                res += 80;
                roman = roman.substring(1);
            }
            if (roman.charAt(0) == 'V') {
                res += 5;
                roman = roman.substring(1);
            }
            while (roman.charAt(0) == 'I') {
                res += 1;
                roman = roman.substring(1);
            }
            if (roman.charAt(0) == 'V') {
                res += 3;
                roman = roman.substring(1);
            } else if (roman.charAt(0) == 'X') {
                res += 8;
                roman = roman.substring(1);
            }
        } catch (
                StringIndexOutOfBoundsException e) {
            return res;
        }
        return res;
    }


    /**
     * 获取从start开始
     * 替换len个长度后的字符串
     *
     * @param str   替换的字符 如*
     * @param start 开始位置
     * @param len   长度
     * @return 替换后的字符串
     */
    public static String getMaskStr(String str, int start, int len) {
        return getMaskStr(str, "*", start, len);
    }

    /**
     * 获取从start开始
     * 替换len个长度后的字符串
     *
     * @param str   要替换的字符串
     * @param s     替换的字符 如*
     * @param start 开始位置
     * @param len   长度
     * @return 替换后的字符串
     */
    public static String getMaskStr(String str, String s, int start, int len) {
        if (isEmpty(str)) {
            return "";
        }
        if (str.length() < start) {
            return str;
        }

        // 获取*之前的字符串
        String ret = str.substring(0, start);

        // 获取最多能打的*个数
        int strLen = str.length();
        if (strLen < start + len) {
            len = strLen - start;
        }

        // 替换成*
        for (int i = 0; i < len; i++) {
            ret += s;
        }

        // 加上*之后的字符串
        if (strLen > start + len) {
            ret += str.substring(start + len);
        }
        return ret;
    }

    /**
     * 截取字符串
     *
     * @param str  原始字符串
     * @param len  要截取的长度
     * @param tail 结束加上的后缀
     * @return 截取后的字符串
     */
    public static String getHtmlSubString(String str, int len, String tail) {
        if (str == null || str.length() <= len) {
            return str;
        }
        int length = str.length();
        char c = ' ';
        String tag = null;
        String name = null;
        int size = 0;
        String result = "";
        boolean isTag = false;
        List<String> tags = new ArrayList<String>();
        int i = 0;
        for (int end = 0, spanEnd = 0; i < length && len > 0; i++) {
            c = str.charAt(i);
            if (c == '<') {
                end = str.indexOf('>', i);
            }

            if (end > 0) {
                // 截取标签
                tag = str.substring(i, end + 1);
                int n = tag.length();
                if (tag.endsWith("/>")) {
                    isTag = true;
                } else if (tag.startsWith("</")) { // 结束符
                    name = tag.substring(2, end - i);
                    size = tags.size() - 1;
                    // 堆栈取出html开始标签
                    if (size >= 0 && name.equals(tags.get(size))) {
                        isTag = true;
                        tags.remove(size);
                    }
                } else { // 开始符
                    spanEnd = tag.indexOf(' ', 0);
                    spanEnd = spanEnd > 0 ? spanEnd : n;
                    name = tag.substring(1, spanEnd);
                    if (name.trim().length() > 0) {
                        // 如果有结束符则为html标签
                        spanEnd = str.indexOf("</" + name + ">", end);
                        if (spanEnd > 0) {
                            isTag = true;
                            tags.add(name);
                        }
                    }
                }
                // 非html标签字符
                if (!isTag) {
                    if (n >= len) {
                        result += tag.substring(0, len);
                        break;
                    } else {
                        len -= n;
                    }
                }

                result += tag;
                isTag = false;
                i = end;
                end = 0;
            } else { // 非html标签字符
                len--;
                result += c;
            }
        }
        // 添加未结束的html标签
        for (String endTag : tags) {
            result += "</" + endTag + ">";
        }
        if (i < length) {
            result += tail;
        }
        return result;
    }


    /**
     * 截取字符串
     *
     * @param s   源字符串
     * @param jmp 跳过jmp
     * @param sb  取在sb
     * @param se  于se
     * @return 之间的字符串
     */
    public static String subStringExe(String s, String jmp, String sb, String se) {
        if (isEmpty(s)) {
            return "";
        }
        int i = s.indexOf(jmp);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        i = s.indexOf(sb);
        if (i >= 0 && i < s.length()) {
            s = s.substring(i + 1);
        }
        if (se == "") {
            return s;
        } else {
            i = s.indexOf(se);
            if (i >= 0 && i < s.length()) {
                s = s.substring(i + 1);
            }
            return s;
        }
    }


    /**
     * 截取字符串　超出的字符用...代替
     *
     * @param subject 　字符串
     * @param size    字符串长度
     * @param size    要代替的字符串
     * @return
     */
    public static String subString(String subject, int size, String str) {
        subject = TextUtils.htmlEncode(subject);
        if (subject.length() > size) {
            subject = subject.substring(0, size) + str;
        }
        return subject;
    }

    /**
     * 按照字节长度截取字符串
     *
     * @param str     要截取的字符串
     * @param subLeng 要截取的字节长度
     * @return 截取后的字符串
     **/
    public static String subString(String str, int subLeng) {
        try {
            if (str == null || "".equals(str)) {
                return "";
            } else {
                int leng = subLeng;

                String subStr = str.substring(0, subLeng > str.length() ? str.length() : subLeng);
                int byteLeng = subStr.getBytes("GBK").length;//截取后字符串的字节长度
                //如果byteLeng>leng 说明截取的字节中包含汉字
                while (byteLeng > leng) {
                    int minusLeng = --subLeng;
                    subStr = str.substring(0, minusLeng > str.length() ? str.length() : minusLeng);
                    byteLeng = subStr.getBytes("GBK").length;
                }
                return subStr;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 得到第一个b,e之间的字符串,并返回e后的子串
     *
     * @param s 源字符串
     * @param b 标志开始
     * @param e 标志结束
     * @return b, e之间的字符串
     */

    public static String[] midString(String s, String b, String e) {
        int i = s.indexOf(b) + b.length();
        int j = s.indexOf(e, i);
        String[] sa = new String[2];
        if (i < b.length() || j < i + 1 || i > j) {
            sa[1] = s;
            sa[0] = null;
            return sa;
        } else {
            sa[0] = s.substring(i, j);
            sa[1] = s.substring(j);
            return sa;
        }
    }

    /**
     * 带有前一次替代序列的正则表达式替代
     *
     * @param s
     * @param pf
     * @param pb
     * @param start
     * @return
     */
    public static String stringReplace(String s, String pf, String pb, int start) {
        Pattern pattern_hand = Pattern.compile(pf);
        Matcher matcher_hand = pattern_hand.matcher(s);
        int gc = matcher_hand.groupCount();
        int pos = start;
        String sf1 = "";
        String sf2 = "";
        String sf3 = "";
        int if1 = 0;
        String strr = "";
        while (matcher_hand.find(pos)) {
            sf1 = matcher_hand.group();
            if1 = s.indexOf(sf1, pos);
            if (if1 >= pos) {
                strr += s.substring(pos, if1);
                pos = if1 + sf1.length();
                sf2 = pb;
                for (int i = 1; i <= gc; i++) {
                    sf3 = "\\" + i;
                    sf2 = replaceAll(sf2, sf3, matcher_hand.group(i));
                }
                strr += sf2;
            } else {
                return s;
            }
        }
        strr = s.substring(0, start) + strr;
        return strr;
    }

    /**
     * 存文本替换
     *
     * @param s  源字符串
     * @param sf 子字符串
     * @param sb 替换字符串
     * @return 替换后的字符串
     */
    public static String replaceAll(String s, String sf, String sb) {
        int i = 0, j = 0;
        int l = sf.length();
        boolean b = true;
        boolean o = true;
        String str = "";
        do {
            j = i;
            i = s.indexOf(sf, j);
            if (i > j) {
                str += s.substring(j, i);
                str += sb;
                i += l;
                o = false;
            } else {
                str += s.substring(j);
                b = false;
            }
        } while (b);
        if (o) {
            str = s;
        }
        return str;
    }

    /**
     * 截取字符串　超出的字符用symbol代替
     *
     * @param len    字符串
     * @param str    字符串长度长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param symbol
     * @return
     */
    public static String getLimitLengthString(String str, int len, String symbol) {
        int iLen = len * 2;
        int counterOfDoubleByte = 0;
        String strRet = "";
        try {
            if (str != null) {
                byte[] b = str.getBytes("GBK");
                if (b.length <= iLen) {
                    return str;
                }
                for (int i = 0; i < iLen; i++) {
                    if (b[i] < 0) {
                        counterOfDoubleByte++;
                    }
                }
                if (counterOfDoubleByte % 2 == 0) {
                    strRet = new String(b, 0, iLen, "GBK") + symbol;
                    return strRet;
                } else {
                    strRet = new String(b, 0, iLen - 1, "GBK") + symbol;
                    return strRet;
                }
            } else {
                return "";
            }
        } catch (Exception ex) {
            return str.substring(0, len);
        } finally {
            strRet = null;
        }
    }

    /**
     * 截取字符串　超出的字符用symbol代替
     *
     * @param len 　字符串长度　长度计量单位为一个GBK汉字　　两个英文字母计算为一个单位长度
     * @param str
     * @return12
     */
    public static String getLimitLengthString(String str, int len) {
        return getLimitLengthString(str, len, "...");
    }


    /**
     * 把字节码转换成16进制
     */
    public static String byte2hex(byte bytes[]) {
        StringBuffer retString = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            retString.append(Integer.toHexString(0x0100 + (bytes[i] & 0x00FF))
                    .substring(1).toUpperCase());
        }
        return retString.toString();
    }

    /**
     * 把16进制转换成字节码
     *
     * @param hex
     * @return
     */
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }

    /**
     * 页面中去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     * @author shazao
     * @date 2007-08-17
     */
    public static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            str = m.replaceAll("");
        }
        return str;
    }

    /**
     * 根据传入的分割符号,把传入的字符串分割为List字符串
     *
     * @param slipStr 分隔的字符串
     * @param src     字符串
     * @return 列表
     */
    public static List<String> slipStringToList(String src, String slipStr) {
        List<String> list = new ArrayList<String>();
        if (isEmpty(src)) {
            return list;
        }
        String[] result = src.split(slipStr);
        for (int i = 0; i < result.length; i++) {
            list.add(result[i]);
        }
        return list;
    }

    /**
     * 根据传入的分割符号,把传入的List<String >合并为字符串
     *
     * @param slipStr 分隔符
     * @param list    字符串数组
     * @return 列表
     */
    public static String slipListToString(List<String> list, String slipStr) {
        String str = "";
        if (list == null || list.size() == 0) {
            return str;
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            buffer.append(list.get(i));
            if (i < list.size() - 1) {
                if (StringUtil.isNotEmpty(slipStr)) {
                    buffer.append(slipStr);
                }
            }
        }
        str = buffer.toString();
        return str;
    }


    /**
     * 取从指定搜索项开始的字符串，返回的值不包含搜索项
     *
     * @param captions 例如:"www.koubei.com"
     * @param regex    分隔符，例如"."
     * @return 结果字符串，如：koubei.com，如未找到返回空串
     */
    public static String getStrAfterRegex(String captions, String regex) {
        if (!isEmpty(captions) && !isEmpty(regex)) {
            int pos = captions.indexOf(regex);
            if (pos != -1 && pos < captions.length() - 1) {
                return captions.substring(pos + 1);
            }
        }
        return "";
    }

    /*
     * getHideEmailPrefix - 隐藏邮件地址前缀。
     *
     * @param email
     *            - EMail邮箱地址 例如: linwenguo@koubei.com 等等...
     * @return 返回已隐藏前缀邮件地址, 如 *********@koubei.com.
     * @version 1.0 (2006.11.27) Wilson Lin
     */
    public static String getHideEmailPrefix(String email) {
        if (isEmpty(email)) {
            return "";
        }
        int index = email.lastIndexOf('@');
        if (index > 0) {
            email = getMaskStr(email, 0, index);
        }
        return email;
    }

    /**
     * 用要通过URL传输的内容进行编码
     *
     * @param src 源字符串
     * @return 经过编码的内容
     */
    public static String URLEncode(String src) {
        String return_value = "";
        try {
            if (src != null) {
                return_value = URLEncoder.encode(src, "GBK");

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return_value = src;
        }

        return return_value;
    }

    /**
     * *************************************************************************
     *
     * @param str 传入 &#31119;test&#29031;&#27004;&#65288;&#21271;&#22823;&#38376;&#24635
     *            ;&#24215;&#65289;&#31119;
     * @return 经过解码的内容
     * ************************************************************************
     */
    public static String getGBK(String str) {

        return transfer(str);
    }

    public static String transfer(String str) {
        Pattern p = Pattern.compile("&#\\d+;");
        Matcher m = p.matcher(str);
        while (m.find()) {
            String old = m.group();
            str = str.replaceAll(old, getChar(old));
        }
        return str;
    }

    public static String getChar(String str) {
        String dest = str.substring(2, str.length() - 1);
        char ch = (char) Integer.parseInt(dest);
        return "" + ch;
    }

    /**
     * 取得字符串的实际长度（考虑了汉字的情况）
     *
     * @param SrcStr 源字符串
     * @return 字符串的实际长度
     */
    public static int getStringLen(String SrcStr) {
        int return_value = 0;
        if (SrcStr != null) {
            char[] theChars = SrcStr.toCharArray();
            for (int i = 0; i < theChars.length; i++) {
                return_value += (theChars[i] <= 255) ? 1 : 2;
            }
        }
        return return_value;
    }

    /**
     * 转换编码
     *
     * @param s       源字符串
     * @param fencode 源编码格式
     * @param bencode 目标编码格式
     * @return 目标编码
     */
    public static String changCoding(String s, String fencode, String bencode) {
        try {
            String str = new String(s.getBytes(fencode), bencode);
            return str;
        } catch (UnsupportedEncodingException e) {
            return s;
        }
    }


    /**
     * 全角生成半角
     *
     * @param QJstr
     * @return
     * @author bailong
     * @date 2007-08-29
     */
    @SuppressLint("LongLogTag")
    public static String Q2B(String QJstr) {
        String outStr = "";
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < QJstr.length(); i++) {
            try {
                Tstr = QJstr.substring(i, i + 1);
                b = Tstr.getBytes("unicode");
            } catch (UnsupportedEncodingException e) {
                Log.e("UnsupportedEncodingException", e.toString());
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (UnsupportedEncodingException ex) {
                    Log.e("UnsupportedEncodingException", ex.toString());
                }
            } else {
                outStr = outStr + Tstr;
            }
        }
        return outStr;
    }

    /**
     * 全角字符变半角字符
     *
     * @param str
     * @return
     * @author shazao
     * @date 2008-04-03
     */
    public static String full2Half(String str) {
        if (str == null || "".equals(str))
            return "";
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            if (c >= 65281 && c < 65373)
                sb.append((char) (c - 65248));
            else
                sb.append(str.charAt(i));
        }

        return sb.toString();

    }

    /**
     * 全角括号转为半角
     *
     * @param str
     * @return
     * @author shazao
     * @date 2007-11-29
     */
    public static String replaceBracketStr(String str) {
        if (str != null && str.length() > 0) {
            str = str.replaceAll("（", "(");
            str = str.replaceAll("）", ")");
        }
        return str;
    }


    /**
     * 解析字符串返回 名称=值的参数表 (a=1&b=2 => a=1,b=2)
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unchecked")
    public static LinkedHashMap<String, String> toLinkedHashMap(String str) {
        if (str != null && !str.equals("") && str.indexOf("=") > 0) {
            LinkedHashMap result = new LinkedHashMap();

            String name = null;
            String value = null;
            int i = 0;
            while (i < str.length()) {
                char c = str.charAt(i);
                switch (c) {
                    case 61: // =
                        value = "";
                        break;
                    case 38: // &
                        if (name != null && value != null && !name.equals("")) {
                            result.put(name, value);
                        }
                        name = null;
                        value = null;
                        break;
                    default:
                        if (value != null) {
                            value = (value != null) ? (value + c) : "" + c;
                        } else {
                            name = (name != null) ? (name + c) : "" + c;
                        }
                }
                i++;

            }

            if (name != null && value != null && !name.equals("")) {
                result.put(name, value);
            }

            return result;

        }
        return null;
    }


    /**
     * Wap页面的非法字符检查
     *
     * @param str
     * @return
     * @author hugh115
     * @date 2007-06-29
     */
    public static String replaceWapStr(String str) {
        if (str != null) {
            str = str.replaceAll("<span class=\"keyword\">", "");
            str = str.replaceAll("</span>", "");
            str = str.replaceAll("<strong class=\"keyword\">", "");
            str = str.replaceAll("<strong>", "");
            str = str.replaceAll("</strong>", "");

            str = str.replace('$', '＄');

            str = str.replaceAll("&amp;", "＆");
            str = str.replace('&', '＆');

            str = str.replace('<', '＜');

            str = str.replace('>', '＞');

        }
        return str;
    }

    /**
     * 页面的非法字符检查
     *
     * @param str
     * @return
     * @author shazao
     * @date 2007-11-29
     */
    public static String replaceStr(String str) {
        if (str != null && str.length() > 0) {
            str = str.replaceAll("~", ",");
            str = str.replaceAll(" ", ",");
            str = str.replaceAll("　", ",");
            str = str.replaceAll(" ", ",");
            str = str.replaceAll("`", ",");
            str = str.replaceAll("!", ",");
            str = str.replaceAll("@", ",");
            str = str.replaceAll("#", ",");
            str = str.replaceAll("\\$", ",");
            str = str.replaceAll("%", ",");
            str = str.replaceAll("\\^", ",");
            str = str.replaceAll("&", ",");
            str = str.replaceAll("\\*", ",");
            str = str.replaceAll("\\(", ",");
            str = str.replaceAll("\\)", ",");
            str = str.replaceAll("-", ",");
            str = str.replaceAll("_", ",");
            str = str.replaceAll("=", ",");
            str = str.replaceAll("\\+", ",");
            str = str.replaceAll("\\{", ",");
            str = str.replaceAll("\\[", ",");
            str = str.replaceAll("\\}", ",");
            str = str.replaceAll("\\]", ",");
            str = str.replaceAll("\\|", ",");
            str = str.replaceAll("\\\\", ",");
            str = str.replaceAll(";", ",");
            str = str.replaceAll(":", ",");
            str = str.replaceAll("'", ",");
            str = str.replaceAll("\\\"", ",");
            str = str.replaceAll("<", ",");
            str = str.replaceAll(">", ",");
            str = str.replaceAll("\\.", ",");
            str = str.replaceAll("\\?", ",");
            str = str.replaceAll("/", ",");
            str = str.replaceAll("～", ",");
            str = str.replaceAll("`", ",");
            str = str.replaceAll("！", ",");
            str = str.replaceAll("＠", ",");
            str = str.replaceAll("＃", ",");
            str = str.replaceAll("＄", ",");
            str = str.replaceAll("％", ",");
            str = str.replaceAll("︿", ",");
            str = str.replaceAll("＆", ",");
            str = str.replaceAll("×", ",");
            str = str.replaceAll("（", ",");
            str = str.replaceAll("）", ",");
            str = str.replaceAll("－", ",");
            str = str.replaceAll("＿", ",");
            str = str.replaceAll("＋", ",");
            str = str.replaceAll("＝", ",");
            str = str.replaceAll("｛", ",");
            str = str.replaceAll("［", ",");
            str = str.replaceAll("｝", ",");
            str = str.replaceAll("］", ",");
            str = str.replaceAll("｜", ",");
            str = str.replaceAll("＼", ",");
            str = str.replaceAll("：", ",");
            str = str.replaceAll("；", ",");
            str = str.replaceAll("＂", ",");
            str = str.replaceAll("＇", ",");
            str = str.replaceAll("＜", ",");
            str = str.replaceAll("，", ",");
            str = str.replaceAll("＞", ",");
            str = str.replaceAll("．", ",");
            str = str.replaceAll("？", ",");
            str = str.replaceAll("／", ",");
            str = str.replaceAll("·", ",");
            str = str.replaceAll("￥", ",");
            str = str.replaceAll("……", ",");
            str = str.replaceAll("（", ",");
            str = str.replaceAll("）", ",");
            str = str.replaceAll("——", ",");
            str = str.replaceAll("-", ",");
            str = str.replaceAll("【", ",");
            str = str.replaceAll("】", ",");
            str = str.replaceAll("、", ",");
            str = str.replaceAll("”", ",");
            str = str.replaceAll("’", ",");
            str = str.replaceAll("《", ",");
            str = str.replaceAll("》", ",");
            str = str.replaceAll("“", ",");
            str = str.replaceAll("。", ",");
        }
        return str;
    }


    /**
     * 将html的省略写法替换成非省略写法
     *
     * @param str html字符串
     * @param pt  标签如table
     * @return 结果串
     */
    public static String fomateToFullForm(String str, String pt) {
        String regEx = "<" + pt + "\\s+([\\S&&[^<>]]*)/>";
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        String[] sa = null;
        String sf = "";
        String sf2 = "";
        String sf3 = "";
        for (; m.find(); ) {
            sa = p.split(str);
            if (sa == null) {
                break;
            }
            sf = str.substring(sa[0].length(), str
                    .indexOf("/>", sa[0].length()));
            sf2 = sf + "></" + pt + ">";
            sf3 = str.substring(sa[0].length() + sf.length() + 2);
            str = sa[0] + sf2 + sf3;
            sa = null;
        }
        return str;
    }


    /**
     * 将带有htmlcode代码的字符转换成<>&'"
     *
     * @param str
     * @return
     */
    public static String htmlcodeToSpecialchars(String str) {
        str = str.replaceAll("&amp;", "&");
        str = str.replaceAll("&quot;", "\"");
        str = str.replaceAll("&#039;", "'");
        str = str.replaceAll("&lt;", "<");
        str = str.replaceAll("&gt;", ">");
        return str;
    }

    /**
     * 移除html标签
     *
     * @param str
     * @return
     */
    public static String removeHtmlTag(String str) {
        if (isEmpty(str)) {
            return "";
        }
        str = stringReplace(str, "\\s", "");// 去掉页面上看不到的字符
        str = stringReplace(str, "<br ?/?>", "\n");// 去<br><br />
        str = stringReplace(str, "<([^<>]+)>", "");// 去掉<>内的字符
        str = stringReplace(str, "&nbsp;", " ");// 替换空格
        str = stringReplace(str, "&(\\S)(\\S?)(\\S?)(\\S?);", "");// 去<br><br />
        return str;
    }


    public static String removeHTMLLableExe(String str) {
        str = stringReplace(str, ">\\s*<", "><");
        str = stringReplace(str, "&nbsp;", " ");// 替换空格
        str = stringReplace(str, "<br ?/?>", "\n");// 去<br><br />
        str = stringReplace(str, "<([^<>]+)>", "");// 去掉<>内的字符
        str = stringReplace(str, "\\s\\s\\s*", " ");// 将多个空白变成一个空格
        str = stringReplace(str, "^\\s*", "");// 去掉头的空白
        str = stringReplace(str, "\\s*$", "");// 去掉尾的空白
        str = stringReplace(str, " +", " ");
        return str;
    }


    /**
     * 去掉HTML标签之外的字符串
     *
     * @param str 源字符串
     * @return 目标字符串
     */
    public static String removeOutHTMLLable(String str) {
        str = stringReplace(str, ">([^<>]+)<", "><");
        str = stringReplace(str, "^([^<>]+)<", "<");
        str = stringReplace(str, ">([^<>]+)$", ">");
        return str;
    }

    /**
     * 字符串替换
     *
     * @param str 源字符串
     * @param sr  正则表达式样式
     * @param sd  替换文本
     * @return 结果串
     */
    public static String stringReplace(String str, String sr, String sd) {
        if (str == null || sd == null || sr == null) {
            return "";
        }
        String regEx = sr;
        Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        str = m.replaceAll(sd);
        return str;
    }


    /**
     * 将一个字节数组转换成base64的字符数组
     *
     * @param data 字节数组
     * @return base64字符数组
     */
    public static char[] encode(byte[] data) {
        char[] out = new char[0];
        if (data != null && data.length > 0) {
            out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }
        }
        return out;
    }

    /**
     * 将一个base64字符数组解码成一个字节数组
     *
     * @param data base64字符数组
     * @return 返回解码以后的字节数组
     */
    public static byte[] decode(char[] data) {
        byte[] out = new byte[0];
        if (data != null && data.length > 0) {
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '=')
                --len;
            if (data.length > 1 && data[data.length - 2] == '=')
                --len;
            out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < data.length; ix++) {
                int value = codes[data[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length)
                throw new Error("miscalculated data length!");
        }
        return out;
    }

    /**
     * base64字符集 0.63
     */
    static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();

    /**
     * 初始化base64字符集表
     */
    static private byte[] codes = new byte[256];

    static {
        for (int i = 0; i < 256; i++)
            codes[i] = -1;
        for (int i = 'A'; i <= 'Z'; i++)
            codes[i] = (byte) (i - 'A');
        for (int i = 'a'; i <= 'z'; i++)
            codes[i] = (byte) (26 + i - 'a');
        for (int i = '0'; i <= '9'; i++)
            codes[i] = (byte) (52 + i - '0');
        codes['+'] = 62;
        codes['/'] = 63;
    }

    /**
     * 将字符串通过base64转码
     *
     * @param str 要转码的字符串
     * @return 返回转码后的字符串
     */
    public static String strToBase64Str(String str) {
        return new String(encode(str.getBytes()));
    }

    /**
     * 将base64码反转成字符串
     *
     * @param base64Str base64码
     * @return 返回转码后的字符串
     */
    public static String base64StrToStr(String base64Str) {
        char[] dataArr = new char[0];
        if (StringUtil.isNotEmpty(base64Str)) {
            dataArr = new char[base64Str.length()];
            base64Str.getChars(0, base64Str.length(), dataArr, 0);
        }
        return new String(decode(dataArr));
    }


    /**
     * 将字节数组通过base64转码
     *
     * @param byteArray 字节数组
     * @return 返回转码后的字符串
     */
    public static String byteArrayToBase64Str(byte byteArray[]) {
        return new String(encode(byteArray));
    }

    /**
     * 将base64码转换成字节数组
     *
     * @param base64Str base64码
     * @return 返回转换后的字节数组
     */
    public static byte[] base64StrToByteArray(String base64Str) {
        char[] dataArr = new char[0];
        if (StringUtil.isNotEmpty(base64Str)) {
            dataArr = new char[base64Str.length()];
            base64Str.getChars(0, base64Str.length(), dataArr, 0);
        }
        return decode(dataArr);
    }

    /**
     * 获得MD5 的密匙
     **/
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /***
     * 获得 Hash256 密匙
     **/
    public static String getHash256(String password) {
        try {
            MessageDigest sha_256 = MessageDigest.getInstance("SHA-256");
            sha_256.update(password.getBytes("UTF-8"));
            byte[] encryption = sha_256.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String getLast(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.substring(str.length() - 1, str.length());

    }
}
