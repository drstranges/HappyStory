package com.drprog.happystory.adapter;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.drprog.happystory.db.model.TrackPoint;
import com.drprog.happystory.fragment.TrackerFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.BaseViewHolder> {

    public interface OnItemClickListener {
        void onItemClicked(int position, TrackPoint item);
    }

    private TrackerFragment mOnItemClickListener;
    private List<TrackPoint> mItems;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdapterUtils.newViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return AdapterUtils.getItemCount(mItems == null ? 0 : mItems.size());
    }

    @Override
    public int getItemViewType(int position) {
        return AdapterUtils.getItemViewType(position);
    }

    public void setOnItemClickListener(TrackerFragment _onItemClickListener) {
        mOnItemClickListener = _onItemClickListener;
    }

    public void setList(List<TrackPoint> _list) {
        mItems = _list;
    }


    // ============================================================================================
    public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public BaseViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public abstract void onBind(int _position);

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null){
                final int position = getAdapterPosition();
                mOnItemClickListener.onItemClicked(position, mItems.get(position));
            }
        }
    }

    private static class AdapterUtils {
        @IntDef({HEADER, ITEM})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ItemViewType {}

        private static final int HEADER = 1;
        private static final int ITEM = 2;

        public static BaseViewHolder newViewHolder(ViewGroup parent, int viewType) {
            switch (viewType){

            }
            return null;
        }

        public static int getItemCount(int listItemCount) {
            return listItemCount + 1;
        }

        public static @ItemViewType int getItemViewType(int position) {
            return position == 0 ? HEADER : ITEM;
        }
    }
}
