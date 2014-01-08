package com.dahale.ajinkya.painteq;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.dahale.ajinkya.painteq.dataprocessing.CSVParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SymbolsGridAdapter extends BaseAdapter {

    private String groupName;
    private ArrayList<String> paths;
    private int pageNumber;
    private Context mContext;

    private KeyClickListener mListener;
    private HashMap<String, String> mapOfSymbols;

    /**
     * Try to load the Map if the "keysdata.csv" file is present in the active folder
     */
    private void tryLoadingMap() {
        this.mapOfSymbols = CSVParser.CSV2Map(mContext, groupName);
    }

    /**
     * For recent, the path after assets/mathsymbols is encoded in its entirety.
     * May change. Let's see.
     */
    public SymbolsGridAdapter(Context context, ArrayList<String> paths, int pageNumber, KeyClickListener listener) {
        this.mContext = context;
        this.paths = paths;
        this.pageNumber = pageNumber;
        this.mListener = listener;
        this.groupName = "";
        tryLoadingMap();
    }

    /**
     * The constructor to call for static Symbol Groups.
     */
    public SymbolsGridAdapter(Context context, ArrayList<String> paths, String groupName, int pageNumber, KeyClickListener listener) {
        this.mContext = context;
        this.paths = paths;
        this.groupName = groupName;
        this.pageNumber = pageNumber;
        this.mListener = listener;
        tryLoadingMap();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.symbols_item, null);
        }

        final String path = paths.get(position);

        ImageView image = (ImageView) v.findViewById(R.id.item);
        image.setImageBitmap(getImage(path));

        if (mapOfSymbols == null) {
            image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.keyClickedIndex(path);
                }
            });
        } else {
            image.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mListener.keyClickedIndex(mapOfSymbols.get(path));
                }
            });
        }

        return v;
    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public String getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Bitmap getImage(String path) {
        AssetManager mngr = mContext.getAssets();
        InputStream in = null;

        try {
            in = mngr.open("mathsymbols/" + groupName + path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = chunks;

        Bitmap temp = BitmapFactory.decodeStream(in, null, null);
        return temp;
    }

    /**
     * A custom key press instruction for our custom keyboard.
     */
    public interface KeyClickListener {

        public void keyClickedIndex(String index);
    }
}
