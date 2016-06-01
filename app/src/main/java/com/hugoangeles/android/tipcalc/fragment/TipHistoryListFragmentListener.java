package com.hugoangeles.android.tipcalc.fragment;

import com.hugoangeles.android.tipcalc.model.TipRecord;

/**
 * Created by Hugo on 30/05/16.
 */
public interface TipHistoryListFragmentListener {

    void addToList(TipRecord record);
    void clearList();
}
