package com.drprog.happystory.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.drprog.happystory.R;
import com.drprog.happystory.adapter.TrackAdapter;
import com.drprog.happystory.db.model.TrackPoint;

import java.util.List;

/**
 * Screen where user set their value
 */
public class TrackerFragment extends BaseFragment implements TrackAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private TrackAdapter mAdapter;
    private List<TrackPoint> mTrackPointList;

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

    private void initListAdapter() {
        if(mAdapter == null) {
            mAdapter = new TrackAdapter();
            mAdapter.setOnItemClickListener(this);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    protected void refreshListData(List<TrackPoint> _listData) {
        mTrackPointList = _listData;
        mAdapter.setList(mTrackPointList);
    }

    @Override
    public void onItemClicked(int position, TrackPoint item) {

    }
}
