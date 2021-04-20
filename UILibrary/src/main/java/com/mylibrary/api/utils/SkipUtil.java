package com.mylibrary.api.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SkipUtil {

    public static Intent getIntent(Context activity, Class classs) {
        return getIntent(activity, classs, null, null, null);
    }

    public static Intent getIntent(Context activity, Class classs, ArrayMap<String, Object> map) {
        return getIntent(activity, classs, map, null, null);
    }

    public static Intent getIntent(Context activity, Class classs, ArrayMap<String, Object> map, Bundle bundle) {
        return getIntent(activity, classs, map, bundle, null);
    }

    public static Intent getIntent(Context activity, Class classs, ArrayMap<String, Object> map, Bundle bundle, Intent intent1) {
        Intent intent = new Intent(activity, classs);
        if (map != null && map.size() > 0) {
            for (int i = 0; i < map.size(); i++) {
                String key = map.keyAt(i);
                Object o = map.valueAt(i);
                if (o instanceof Boolean) {
                    intent.putExtra(key, (Boolean) o);
                } else if (o instanceof boolean[]) {
                    intent.putExtra(key, (boolean[]) o);
                } else if (o instanceof Bundle) {
                    intent.putExtra(key, (Bundle) o);
                } else if (o instanceof Byte) {
                    intent.putExtra(key, (Byte) o);
                } else if (o instanceof Byte[]) {
                    intent.putExtra(key, (Byte[]) o);
                } else if (o instanceof char[]) {
                    intent.putExtra(key, (char[]) o);
                } else if (o instanceof CharSequence) {
                    intent.putExtra(key, (CharSequence) o);
                } else if (o instanceof CharSequence[]) {
                    intent.putExtra(key, (CharSequence[]) o);
                } else if (o instanceof Double) {
                    intent.putExtra(key, (Double) o);
                } else if (o instanceof Double[]) {
                    intent.putExtra(key, (Double[]) o);
                } else if (o instanceof Float) {
                    intent.putExtra(key, (Float) o);
                } else if (o instanceof Float[]) {
                    intent.putExtra(key, (Float[]) o);
                } else if (o instanceof Integer) {
                    intent.putExtra(key, (Integer) o);
                } else if (o instanceof Integer[]) {
                    intent.putExtra(key, (Integer[]) o);
                } else if (o instanceof Long) {
                    intent.putExtra(key, (Long) o);
                } else if (o instanceof Long[]) {
                    intent.putExtra(key, (Long[]) o);
                } else if (o instanceof Parcelable) {
                    intent.putExtra(key, (Parcelable) o);
                } else if (o instanceof Parcelable[]) {
                    intent.putExtra(key, (Parcelable[]) o);
                } else if (o instanceof Serializable) {
                    intent.putExtra(key, (Serializable) o);
                } else if (o instanceof Short) {
                    intent.putExtra(key, (Short) o);
                } else if (o instanceof Short[]) {
                    intent.putExtra(key, (Short[]) o);
                } else if (o instanceof String) {
                    intent.putExtra(key, (String) o);
                } else if (o instanceof String[]) {
                    intent.putExtra(key, (String[]) o);
                } else if (o instanceof ArrayMap) {
                    intent.putExtra(key, new JSONObject((ArrayMap) o).toString());
                } else if (o instanceof ArrayList) {
                    if (o != null && ((ArrayList) o).size() > 0) {
                        Object o1 = ((ArrayList) o).get(0);
                        if (o1 instanceof Parcelable) {
                            intent.putParcelableArrayListExtra(key, (ArrayList) o);
                        } else if (o1 instanceof Integer) {
                            intent.putIntegerArrayListExtra(key, (ArrayList) o);
                        } else if (o1 instanceof String) {
                            intent.putStringArrayListExtra(key, (ArrayList) o);
                        } else if (o1 instanceof CharSequence) {
                            intent.putCharSequenceArrayListExtra(key, (ArrayList) o);
                        }
                    }
                }

            }
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (intent1 != null) {
            intent.putExtras(intent1);
        }
        return intent;
    }


    public static void skipActivity(Context activity, Class classs) {
        skipActivity(activity, classs, null, null, -1, null, true);
    }

    public static void skipActivity(Context activity, Class classs, int requestCode) {
        skipActivity(activity, classs, null, null, requestCode, null, true);
    }

    public static void skipActivity(Context activity, Class classs, ArrayMap<String, Object> map) {
        skipActivity(activity, classs, map, null, -1, null, true);
    }

    public static void skipActivity(Context activity, Class classs, ArrayMap<String, Object> map, int requestCode) {
        skipActivity(activity, classs, map, null, requestCode, null, true);
    }

    public static void skipActivity(Context activity, Class classs, ArrayMap<String, Object> map, boolean isFinish) {
        skipActivity(activity, classs, map, null, -1, null, isFinish);
    }
    public static void skipActivity(Context activity, Class classs, ArrayMap<String, Object> map,int requsetCode, boolean isFinish) {
        skipActivity(activity, classs, map, null, requsetCode, null, isFinish);
    }
    public static void skipActivity(Context activity, Class classs, Bundle bundle) {
        skipActivity(activity, classs, null, bundle, -1, null, true);
    }

    public static void skipActivity(Context activity, Class classs, Bundle bundle, int requestCode) {
        skipActivity(activity, classs, null, bundle, requestCode, null, true);
    }

    public static void skipActivity(Context activity, Class classs, Bundle bundle, boolean isFinish) {
        skipActivity(activity, classs, null, bundle, -1, null, isFinish);
    }

    public static void skipActivity(Context activity, Class classs, Intent intent1) {
        skipActivity(activity, classs, null, null, -1, intent1, true);
    }

    public static void skipActivity(Context activity, Class classs, ArrayMap<String, Object> map, Bundle bundle, int requestCode) {
        skipActivity(activity, classs, map, bundle, requestCode, null, true);
    }

    public static void skipActivity(Context activity, Class classs, ArrayMap<String, Object> map, Bundle bundle, int requestCode, Intent intent1, boolean isFinish) {
        if (ActivityManager.getInstance() != null && isFinish) {
            ActivityManager.getInstance().finishActivity(classs);
        }
        Intent intent = getIntent(activity, classs, map, bundle, intent1);
        if (requestCode != -1) {
            ((Activity) activity).startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }
    }


    public static void skipActivity(Fragment fragment, Class classs) {
        skipActivity(fragment, classs, null, null, -1, null, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, int requestCode) {
        skipActivity(fragment, classs, null, null, requestCode, null, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, ArrayMap<String, Object> map) {
        skipActivity(fragment, classs, map, null, -1, null, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, ArrayMap<String, Object> map, int requestCode) {
        skipActivity(fragment, classs, map, null, requestCode, null, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, ArrayMap<String, Object> map, boolean isFinish) {
        skipActivity(fragment, classs, map, null, -1, null, isFinish);
    }

    public static void skipActivity(Fragment fragment, Class classs, Bundle bundle) {
        skipActivity(fragment, classs, null, bundle, -1, null, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, Bundle bundle, int requestCode) {
        skipActivity(fragment, classs, null, bundle, requestCode, null, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, Bundle bundle, boolean isFinish) {
        skipActivity(fragment, classs, null, bundle, -1, null, isFinish);
    }

    public static void skipActivity(Fragment fragment, Class classs, Intent intent1) {
        skipActivity(fragment, classs, null, null, -1, intent1, true);
    }

    public static void skipActivity(Fragment fragment, Class classs, ArrayMap<String, Object> map, Bundle bundle, int requestCode) {
        skipActivity(fragment, classs, map, bundle, requestCode, null, true);
    }
    public static void skipActivity(Fragment fragment, Class classs, ArrayMap<String, Object> map,int requsetCode, boolean isFinish) {
        skipActivity(fragment, classs, map, null, requsetCode, null, isFinish);
    }
    public static void skipActivity(Fragment fragment, Class classs, ArrayMap<String, Object> map, Bundle bundle, int requestCode, Intent intent1, boolean isFinish) {
        if (ActivityManager.getInstance() != null && isFinish) {
            ActivityManager.getInstance().finishActivity(classs);
        }
        Intent intent = getIntent(fragment.getActivity(), classs, map, bundle, intent1);
        if (requestCode != -1) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            fragment.startActivity(intent);
        }
    }


}
