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

    // TODO: Decide on a more appropriate data structure for passing key-code relation.
    // TODO: Make ways for recent and custom codes.

    /**
     * Returns a hashmap describing which image uses which symbol.
     * To use this, save a page "keysdata.csv" in the folder giving the
     * image name and the symbol code separated by a semicolon at every line.
     */
    public static HashMap<String, String> CSV2Map(Context context, String groupName) {
        HashMap<String, String> map = new HashMap<String, String>();
        AssetManager manager = context.getAssets();
        InputStream is;
        InputStreamReader isr;
        BufferedReader br;
        try {
            is = manager.open("mathsymbols/" + groupName + "keysdata.csv");

            // create new input stream reader
            isr = new InputStreamReader(is);

            // create new buffered reader
            br = new BufferedReader(isr);

            String value;

            // reads to the end of the stream
            while ((value = br.readLine()) != null) {
                Log.d(TAG, value);
                String[] s = value.split(";");
                map.put(s[0], s[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
