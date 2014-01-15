package com.dahale.ajinkya.painteq;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import com.dahale.ajinkya.painteq.SymbolsGridAdapter.KeyClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SymbolsPagerAdapter extends PagerAdapter {

    // TODO: behaves really jaggy. Sort it out.
    private String[] groupPaths;
    ArrayList<String> paths;
    FragmentActivity mActivity;
    KeyClickListener mListener;

    public SymbolsPagerAdapter(FragmentActivity activity,
                               ArrayList<String> paths, KeyClickListener listener) {
        this.paths = paths;
        this.mActivity = activity;
        this.mListener = listener;
        groupPaths = activity.getResources().getStringArray(R.array.symbolGroups_array);
    }

    @Override
    public int getCount() {
        // For now, it is to show Greek and 2 other (hopefully user-defined).
        return groupPaths.length;
    }

    @Override
    public Object instantiateItem(View collection, final int position) {

        final View layout;

        if (position == 0) {
            // TODO: Make the recent symbols update when this view comes on screen
            layout = mActivity.getLayoutInflater().inflate(
                    R.layout.symbols_grid, null);
            GridView grid = (GridView) layout.findViewById(R.id.symbols_grid);
            grid.setAdapter(new RecentSymbolsGridAdapter(mActivity, mListener));
        } else if (position == 1) {
            layout = mActivity.getLayoutInflater().inflate(
                    R.layout.custom_symbols_layout, null);

            ((ListView) layout.findViewById(R.id.custom_symbols_list)).setAdapter(new CustomSymbolsAdapter(mActivity, mListener));

        } else {
            layout = mActivity.getLayoutInflater().inflate(
                    R.layout.symbols_grid, null);


            new AsyncTask<Void, Void, SymbolsGridAdapter>() {

                @Override
                protected SymbolsGridAdapter doInBackground(Void... params) {
                    String[] a = new String[0];
                    try {
                        a = mActivity.getAssets().list("mathsymbols/" + groupPaths[position]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d("Tag", "mathsymbols/" + groupPaths[position] + " has " + a.length + " elements," +
                            "first of which is " + a[0] + ".");

                    return new SymbolsGridAdapter(
                            mActivity.getApplicationContext(), new ArrayList<String>(Arrays.asList(a)), groupPaths[position] + "/", position,
                            mListener);
                }

                @Override
                protected void onPostExecute(SymbolsGridAdapter symbolsGridAdapter) {
                    GridView grid = (GridView) layout.findViewById(R.id.symbols_grid);
                    grid.setAdapter(symbolsGridAdapter);
                }
            }.execute();

        }


        ((ViewPager) collection).addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}