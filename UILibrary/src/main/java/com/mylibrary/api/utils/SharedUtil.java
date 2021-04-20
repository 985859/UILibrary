package com.mylibrary.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mylibrary.api.utils.json.JsonUtil;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/7/17 17:07
 */
public class SharedUtil {
    public static final String TAG = "SharedUtil";
    // 创建一个写入器
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedUtil sharedUtil;

    private SharedUtil(Context context) {
        mPreferences = context.getSharedPreferences(context.getPackageName() + TAG, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static SharedUtil getInstance(Context context) {
        if (sharedUtil == null) {
            synchronized (SharedUtil.class) {
                if (sharedUtil == null) {
                    sharedUtil = new SharedUtil(context);
                }
            }
        }
        return sharedUtil;
    }

    // 存入String 数据
    public static void putSP(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    // 获取String数据
    public static String getSP(String key) {
        return mPreferences.getString(key, "");
    }

    public static String getSP(String key, String value) {
        return mPreferences.getString(key, value);
    }

    // 存入String 数据
    public static void putSetP(String key, Set<String> value) {
        mEditor.putStringSet(key, value);
        mEditor.commit();
    }

    // Set<String>
    public static Set<String> getSetP(String key, Set<String> value) {
        return mPreferences.getStringSet(key, value);
    }

    // 存入boolean 数据
    public static void putBP(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    // 获取 boolean 数据
    public static boolean getBP(String key) {
        return getBP(key, false);
    }

    public static boolean getBP(String key, boolean value) {
        return mPreferences.getBoolean(key, value);
    }

    // 存入int 数据
    public static void putIP(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    // 获取 int 数据
    public static int getIP(String key) {
        return getIP(key, 0);
    }

    public static int getIP(String key, int value) {
        return mPreferences.getInt(key, value);
    }

    // Float 数据
    public static void putFP(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    // 获取 Float 数据
    public static float getFP(String key) {
        return getFP(key, 0);
    }

    public static float getFP(String key, float value) {
        return mPreferences.getFloat(key, value);
    }

    // long 数据
    public static void putLP(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    // 获取 long 数据
    public static long getLP(String key) {
        return getLP(key, 0);
    }

    public static long getLP(String key, long value) {
        return mPreferences.getLong(key, value);
    }







    //以 String 形式存入 一个Map数组
    public static <T, K> void putMap(String key, Map<T, K> map) {
        if (map != null && map.size() > 0) {
            putSP(key, JsonUtil.formatJson(map));
        }
    }

    //获取Map数组
    public static <T, K> Map<T, K> getMap(String key) {
        String value = getSP(key);
        if (StringUtil.isNotEmpty(value)) {
            Map<T, K> map = JsonUtil.jsonToMap(value);
            return map;
        } else {
            return null;
        }
    }

    //以 String 形式存入 一个 Bean
    public static void putObjToStr(String key, Object vlaue) {
        if (vlaue != null) {
            putSP(key, JsonUtil.formatJson(vlaue));
        }
    }

    public static <T> T getObjToBean(String key, Class<T> type) {
        String value = getSP(key);
        if (StringUtil.isNotEmpty(value)) {
            return JsonUtil.fromJson(value, type);
        } else {
            return null;
        }
    }
    public static <T> T getObjToBean(String key, Type type) {
        String value = getSP(key);
        if (StringUtil.isNotEmpty(value)) {
            return JsonUtil.fromJson(value, type);
        } else {
            return null;
        }
    }
    // 移除数据
    public static void removeSP(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    public static void    clear(){
        mEditor.clear();
        mEditor.commit();
    }

}
