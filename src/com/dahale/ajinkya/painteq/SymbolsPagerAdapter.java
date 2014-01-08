package com.dahale.ajinkya.painteq;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import com.dahale.ajinkya.painteq.SymbolsGridAdapter.KeyClickListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SymbolsPagerAdapter extends PagerAdapter {

    private String[] groupPaths;
    ArrayList<String> paths, recent;
    private static final int MAX_NO_OF_RECENT_SYMBOLS = 40;
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
            layout = mActivity.getLayoutInflater().inflate(
                    R.layout.placeholder, null);
        } else if (position == 1) {
            layout = mActivity.getLayoutInflater().inflate(
                    R.layout.placeholder, null);
        } else {
            layout = mActivity.getLayoutInflater().inflate(
                    R.layout.symbols_grid, null);


            new AsyncTask<Void, Void, SymbolsGridAdapter>() {

                /**
                 * Override this method to perform a computation on a background thread. The
                 * specified parameters are the parameters passed to {@link #execute}
                 * by the caller of this task.
                 * <p/>
                 * This method can call {@link #publishProgress} to publish updates
                 * on the UI thread.
                 *
                 * @param params The parameters of the task.
                 * @return A result, defined by the subclass of this task.
                 * @see #onPreExecute()
                 * @see #onPostExecute
                 * @see #publishProgress
                 */
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

    private void addRecent(String path) {
        if (!recent.contains(path)) {
            recent.add(path);
            if (recent.size() > MAX_NO_OF_RECENT_SYMBOLS) recent.remove(0);
        }
    }
}