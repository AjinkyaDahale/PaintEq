package com.dahale.ajinkya.painteq.utils;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class Utils {

    private static final String TAG = "PaintEq Utils";

    public final static int MAX_NO_RECENT_SYMBOLS = 40;

    private Context mContext;

    public static ArrayList<String> recentSymbolPaths, recentSymbolCodes;

    public static ArrayList<String> getCustomSymbolCodes() {
        if (customSymbolCodes==null) customSymbolCodes = new ArrayList<String>();
        return customSymbolCodes;
    }

    public static void setCustomSymbolCodes(ArrayList<String> customSymbolCodes) {
        Utils.customSymbolCodes = customSymbolCodes;
    }

    public static ArrayList<String> customSymbolCodes;

    private Utils(Context context) {
        this.mContext = context;
    }

    public static Utils getUtilsInstance(Context context) {
        if (recentSymbolPaths == null) {
            recentSymbolPaths = new ArrayList<String>();
            recentSymbolCodes = new ArrayList<String>();
            // TODO: If no ArrayList, make one from the file.
        }
        return new Utils(context);
    }

    /**
     * Inserts a symbol in group "Recent" if not already present. Also removes older symbols.
     *
     * @param path Path to the image file past the mathsymbols directory
     * @param code Code for the said symbol
     */
    public static void insertRecent(String path, String code) {
        if (recentSymbolPaths == null) {
            recentSymbolPaths = new ArrayList<String>();
            recentSymbolCodes = new ArrayList<String>();
            // TODO: If no ArrayList, make one from the file.
        }
        if (!recentSymbolPaths.contains(path)) {
            recentSymbolPaths.add(path);
            recentSymbolCodes.add(code);
            if (recentSymbolCodes.size() > MAX_NO_RECENT_SYMBOLS) {
                recentSymbolPaths.remove(0);
                recentSymbolCodes.remove(0);
            }
        } else {
            // TODO: If it is already there, make it the most recent.
        }
    }

    /**
     * Inserts a symbol in group "Custom".
     *
     * @param code Code for the custom symbol
     */
    public static void insertCustom(String code) {
        getCustomSymbolCodes().add(code);
    }

    /**
     * Inserts/edits a symbol in group "Custom".
     *
     * @param code Code for the custom symbol
     * @param position Position if you want to edit,
     *                 any other value to insert.
     */
    public static void insertOrEditCustom(String code, int position) {
        Log.d(TAG, code + " " + position);
        if(0<=position && position<getCustomSymbolCodes().size())
            getCustomSymbolCodes().set(position,code);
        else
            getCustomSymbolCodes().add(code);
    }

    /**
     * Removes a symbol from the group "Custom".
     *
     * @param index index of symbol to be removed
     */
    public static void removeCustom(int index) {
        customSymbolCodes.remove(index);
    }

}
