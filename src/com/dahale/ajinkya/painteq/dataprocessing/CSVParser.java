package com.dahale.ajinkya.painteq.dataprocessing;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class CSVParser {
    private static final String TAG = "CSVParser";

    public static HashMap<String, String> CSV2Map(Context context) {
        HashMap<String, String> map = new HashMap<String, String>();
        AssetManager manager = context.getAssets();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = manager.open("mathsymbols/Greek/keysdata.csv");

            // create new input stream reader
            isr = new InputStreamReader(is);

            // create new buffered reader
            br = new BufferedReader(isr);

            String value = "";

            // reads to the end of the stream
            while ((value = br.readLine()) != null) {
                Log.d(TAG, value);
                for (String s : value.split(";")) {
                    Log.d(TAG, s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
