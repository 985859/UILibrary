package com.mylibrary.api.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatabaseUtil {
    public static MyDBOpenHelper helper;
    public static SQLiteDatabase db;
    private volatile static DatabaseUtil databaseUtil;
    private DatabaseUtil(Context context) {
        super();
        helper = new MyDBOpenHelper(context);
    }


    public static DatabaseUtil getInterface(Context context) {
        if (databaseUtil == null) {
            synchronized (DatabaseUtil.class) {
                if (databaseUtil == null) {
                    databaseUtil = new DatabaseUtil(context);
                }
            }
        }
        return databaseUtil;
    }

    /****
     * 建立表格
     * ***/
    public static void craedTable(String execSQL) {
        db = helper.getWritableDatabase();//此this是继承SQLiteOpenHelper类得到的
        db.execSQL(execSQL);
        close();
    }

    /**
     * 判断数据库中某张表是否存在
     */
    public static boolean sqlTableIsExist(String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();//此this是继承SQLiteOpenHelper类得到的
            String sql = "select count(*) as c from sqlite_master where type ='table' and name ='" + tableName.trim() + "'";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            close();
            // TODO: handle exception
        }
        close();
        return result;
    }

    /**
     * 根据条件插入单条数据
     */
    public static void insertCondition(String table, Map<String, Object> map) {
        db = helper.getWritableDatabase();
        // 创建ContentValues对象
        ContentValues values = new ContentValues();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            values.put(key, value);
        }
        db.insert(table, null, values);
        close();

    }

    /**
     * 根据条件更新单条数据
     */
    public static  void updatabse(String table, Map<String, Object> map, String[] columns, String selection, String[] selectionArgs) {
        db = helper.getWritableDatabase();
        Cursor cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        ContentValues values = new ContentValues();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            values.put(key, value);
        }
        if (cursor.moveToFirst()) {
            // 创建ContentValues对象
            int i = db.update(table, values, selection, selectionArgs);
        } else {
            long i = db.insert(table, null, values);
        }
        close();
    }

    /**
     * 根据ID 删除数据
     * <p/>
     * param int id
     */
    public static  boolean delete(String table, String column, String condition) {
        boolean flag = false;
        db = helper.getWritableDatabase();
        try {
            if (db.isOpen()) {
                db.execSQL("delete from " + table + " where " + column + " = ?", new Object[]{condition});
            }
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        close();
        return flag;
    }


    /**
     * 根据ID  查询单个
     */
    public   static String queryID(String table, String[] columns, String selection, String[] selectionArgs) {
        db = helper.getReadableDatabase();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Cursor cursor;
        cursor = db.query(table, columns, selection, selectionArgs, null, null, null);
        String str = cursor.getString(cursor.getColumnIndex(columns[0]));
        cursor.close();
        close();
        return str;
    }


    /**
     * 根据条件查询
     */
    public  static List<Map<String, Object>> queryCondition(String table, String[] columns, String selection, String[] selectionArgs) {

        return queryCondition(table, columns, selection, selectionArgs, null, null, null, null);
    }

    /**
     * 根据条件查询
     */
    public static List<Map<String, Object>> queryCondition(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                                                    String orderBy, String limit) {
        db = helper.getReadableDatabase();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Cursor cursor;
        cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        if (columns == null) {
            columns = cursor.getColumnNames();
        }
        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < columns.length; i++) {
                map.put(columns[i], cursor.getString(cursor.getColumnIndex(columns[i])));
            }
            list.add(map);
        }
        cursor.close();
        close();
        return list;
    }

    //关闭数据库
    public static void close() {
        if (db != null) {
            db.close();
        }
    }

    /***
     * 插入多条数据
     ***/
    public static  boolean insertList(String table, List<ContentValues> list) {
        boolean result = true;
        if (null == list || list.size() <= 0) {
            return true;
        }
        try {
            db = helper.getWritableDatabase();
            db.beginTransaction();
            for (ContentValues remoteAppInfo : list) {
                ContentValues values = remoteAppInfo;
                if (db.insert(table, null, values) < 0) {
                    result = false;
                    break;
                }
            }
            if (result) {
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();
                    close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    /*****
     * 清空表中内容
     */

    public static  void empty(String table) {
        db = helper.getWritableDatabase();
        db.delete(table, null, null);
        db.execSQL("update sqlite_sequence set seq=0 where name='" + table + "'");
        close();
    }

}