package com.dahale.ajinkya.painteq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.dahale.ajinkya.painteq.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class RecentSymbolsGridAdapter extends SymbolsGridAdapter {
    private static final String TAG = "RecentSymbolsGridAdapter";

    /**
     * For recent, the path after assets/mathsymbols is encoded in its entirety.
     * May change. Let's see.
     */
    public RecentSymbolsGridAdapter(Context context, KeyClickListener listener) {
        super(context, listener);
        Utils utils = Utils.getUtilsInstance(context);
        paths = Utils.recentSymbolPaths;
        Log.d(TAG, "" + paths.size());
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.symbols_item, null);
        }

        final String path = paths.get(position);

        ImageView image = (ImageView) v.findViewById(R.id.item);
        image.setImageBitmap(getImage(path));

        final String code = Utils.recentSymbolCodes.get(position);
        v.findViewById(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.keyClickedIndex(code);
//                Utils.insertRecent(path,code);
            }
        });
        return v;
    }

}
