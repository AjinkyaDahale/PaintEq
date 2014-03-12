package com.dahale.ajinkya.painteq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.dahale.ajinkya.painteq.utils.Utils;

import java.util.ArrayList;

public class CustomSymbolsAdapter extends BaseAdapter {

    protected Context mContext;
    private SymbolsGridAdapter.KeyClickListener mListener;
    private ArrayList<String> customSymbolCodes;

    public CustomSymbolsAdapter(Context mContext, SymbolsGridAdapter.KeyClickListener mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
        customSymbolCodes = Utils.getCustomSymbolCodes();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return customSymbolCodes.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    // TODO: Make a better looking layout.
    // TODO: Provide edit/delete support.

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.custom_symbols_layout_item, null);
            if (v != null) {
                TextView t = ((TextView) v.findViewById(R.id.custom_text));
                t.setText(customSymbolCodes.get(position));
                t.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mListener.keyClickedIndex(customSymbolCodes.get(position));
                    }
                });
                ImageButton discard = (ImageButton) v.findViewById(R.id.delete_custom);
                discard.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Utils.getCustomSymbolCodes().remove(position);
                        CustomSymbolsAdapter.this.notifyDataSetChanged();
                    }
                });
            }
        } else {
            ((TextView) v.findViewById(R.id.custom_text)).setText(Utils.getCustomSymbolCodes().get(position));
        }

        return v;
    }

}
