package com.dahale.ajinkya.painteq;

import android.content.Context;

import java.util.ArrayList;

public class RecentSymbolsGridAdapter extends SymbolsGridAdapter{

    /**
     * For recent, the path after assets/mathsymbols is encoded in its entirety.
     * May change. Let's see.
     */
    public RecentSymbolsGridAdapter(Context context, ArrayList<String> paths, int pageNumber, KeyClickListener listener) {
        super(context, paths, pageNumber, listener);
    }


}
