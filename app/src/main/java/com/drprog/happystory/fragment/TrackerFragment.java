package com.drprog.happystory.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.drprog.happystory.R;
import com.drprog.happystory.adapter.TrackAdapter;
import com.drprog.happystory.db.DatabaseHelper;
import com.drprog.happystory.db.model.TrackPoint;
import com.drprog.happystory.db.table.UserTrackTable;

import java.util.LinkedList;
import java.util.List;

/**
 * Screen where user set their value
 */
public class TrackerFragment extends BaseFragment implements TrackAdapter.OnActionListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_USER_TRACK_ID = 1;
    private static final long DATA_LOAD_LIMIT = 20;
    private RecyclerView mRecyclerView;
    private TrackAdapter mAdapter;
    private List<TrackPoint> mDataList;

    @Override
    protected Integer getLayoutResId() {
        return R.layout.fragment_tracker;
    }

    @Override
    protected Integer getActionBarTitleResId() {
        return null;
    }

    @Override
    protected void onCreateView(View _rootView, Bundle _savedInstanceState) {
        mRecyclerView = (RecyclerView) _rootView.findViewById(R.id.recyclerView_FT);
        final RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        initListAdapter();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getSupportLoaderManager().initLoader(LOADER_USER_TRACK_ID, null, this);
    }

    private void initListAdapter() {
        if(mAdapter == null) {
            mAdapter = new TrackAdapter(getContext(),mDataList, this);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void refreshListData(List<TrackPoint> _listData) {
        mDataList = _listData;
        mAdapter.setList(mDataList);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final long offset = mDataList == null ? 0 : mDataList.size();
        final long limit = DATA_LOAD_LIMIT;
        final Uri uri = UserTrackTable.buildUriForAllWithLimit(offset, limit);
        final String sortOrder = UserTrackTable.FIELD_TIMESTAMP + " DESC";
        return new CursorLoader(getActivity(),uri,null,null,null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data == null) return;
        if (mDataList == null) mDataList = new LinkedList<>();
        List<TrackPoint> newDataList =
                DatabaseHelper.getInstance(getContext())
                        .convertCursorToList(data, UserTrackTable.class);
        mDataList.addAll(newDataList);
        mAdapter.setList(mDataList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActionFired(int position, int _itemViewType) {
        switch (_itemViewType){
            case TrackAdapter.AdapterUtils.ITEM:

                break;
        }
    }
}
