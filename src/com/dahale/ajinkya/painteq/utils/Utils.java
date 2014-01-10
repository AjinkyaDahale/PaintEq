package com.dahale.ajinkya.painteq.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class Utils {

    private static final String TAG = "PaintEq Utils";

    public final static int MAX_NO_RECENT_SYMBOLS = 40;

    private Context mContext;

    public static ArrayList<String> recentSymbolPaths, recentSymbolCodes;
    public static ArrayList<String> customSymbolCodes;

    private Utils(Context context) {
        this.mContext = context;
    }

    public static Utils getUtilsInstance(Context context) {
        return new Utils(context);
    }

    // TODO: Complete this here or insert it somewhere else.
    /**
     * Inserts a symbol in group "Recent" if not already present. Also removes older symbols.
     * @param path Path to the image file past the mathsymbols directory
     * @param code Code for the said symbol
     */
    public static void insertRecent(String path, String code) {
        if (!recentSymbolPaths.contains(path)) {
            recentSymbolPaths.add(path);
            recentSymbolCodes.add(code);
            if (recentSymbolCodes.size() > MAX_NO_RECENT_SYMBOLS) {
                recentSymbolPaths.remove(0);
                recentSymbolCodes.remove(0);
            }
        }
    }

    /**
     * Inserts a symbol in group "Custom".
     * @param code Code for the custom symbol
     */
    public static void insertCustom(String code) {
        customSymbolCodes.add(code);
    }

    /**
     * Removes a symbol from the group "Custom".
     * @param index index of symbol to be removed
     */
    public static void removeCustom(int index) {
        customSymbolCodes.remove(index);
    }

}
