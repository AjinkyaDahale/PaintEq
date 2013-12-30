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
import com.example.PaintEq.R;

import java.io.InputStream;
import java.util.ArrayList;

public class SymbolsGridAdapter extends BaseAdapter {

    private String groupName = "";
    private ArrayList<String> paths;
    private int pageNumber;
    private Context mContext;

    private KeyClickListener mListener;

    /**
     * For recent, the path after assets/mathsymbols is encoded in its entirety.
     */
    public SymbolsGridAdapter(Context context, ArrayList<String> paths, int pageNumber, KeyClickListener listener) {
        this.mContext = context;
        this.paths = paths;
        this.pageNumber = pageNumber;
        this.mListener = listener;
        this.groupName = "";
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

        image.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.keyClickedIndex(path);
            }
        });

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

    public int getPageNumber() {
        return pageNumber;
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
