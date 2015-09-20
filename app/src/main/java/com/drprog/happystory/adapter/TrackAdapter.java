package com.drprog.happystory.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.drprog.happystory.R;
import com.drprog.happystory.db.model.TrackPoint;
import com.drprog.happystory.fragment.TrackerFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.BaseViewHolder<TrackPoint>> {

    private Context mContext;

    public TrackAdapter(Context _context, List<TrackPoint> _items, OnActionListener _actionListener) {
        mContext = _context;
        mItems = _items;
        mOnActionListener = _actionListener;
    }

    public interface OnActionListener {
        void onActionFired(TrackAction _action);
    }

    private OnActionListener mOnActionListener;
    private List<TrackPoint> mItems;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AdapterUtils
                .newViewHolder(LayoutInflater.from(mContext), parent, viewType, mOnActionListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position, mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return AdapterUtils.getItemCount(mItems == null ? 0 : mItems.size());
    }

    @Override
    public int getItemViewType(int position) {
        return AdapterUtils.getItemViewType(position);
    }

    public void setOnActionListener(TrackerFragment _onActionListener) {
        mOnActionListener = _onActionListener;
    }

    public void setList(List<TrackPoint> _list) {
        mItems = _list;
    }


    // ============================================================================================
    public static abstract class BaseViewHolder<I> extends RecyclerView.ViewHolder {

        private OnActionListener mOnActionListener;

        public BaseViewHolder(View itemView, OnActionListener _actionListener) {
            super(itemView);
            mOnActionListener = _actionListener;
        }

        public abstract void onBind(int _position, I _item);

        public void fireAction(TrackAction _action){
            if (mOnActionListener != null){
                mOnActionListener.onActionFired(_action);
            }
        }
    }

    public static class AdapterUtils {
        @IntDef({HEADER, ITEM})
        @Retention(RetentionPolicy.SOURCE)
        public @interface ItemViewType {}

        public static final int HEADER = 1;
        public static final int ITEM = 2;

        public static BaseViewHolder newViewHolder(LayoutInflater _inflater,
                                                   ViewGroup parent,
                                                   @ItemViewType int viewType,
                                                   OnActionListener _itemClickListener) {
            final View view;
            switch (viewType){
                case HEADER:
                    view = _inflater.inflate(R.layout.item_user_track_header, parent, false);
                    return new HeaderViewHolder(view, _itemClickListener);
                case ITEM:
                    view = _inflater.inflate(R.layout.item_user_track, parent, false);
                    return new ItemViewHolder(view, _itemClickListener);
            }
            return null;
        }

        public static int getItemCount(int listItemCount) {
            return listItemCount + 1;
        }

        public static @ItemViewType int getItemViewType(int position) {
            return position == 0 ? HEADER : ITEM;
        }

        public static class HeaderViewHolder extends BaseViewHolder<TrackPoint>{
            SeekBar seekBar;

            public HeaderViewHolder(View itemView, OnActionListener _itemClickListener) {
                super(itemView, _itemClickListener);
                seekBar = (SeekBar) itemView.findViewById(R.id.seekBar);

            }

            @Override
            public void onBind(int _position, TrackPoint _item) {}
        }

        public static class ItemViewHolder extends BaseViewHolder<TrackPoint>{
            TextView valueLabel;
            public ItemViewHolder(View itemView, OnActionListener _itemClickListener) {
                super(itemView, _itemClickListener);
                valueLabel = (TextView) itemView.findViewById(R.id.tvValue);
            }

            @Override
            public void onBind(int _position, TrackPoint _item) {
                valueLabel.setText(_item.value);
            }
        }

    }
}
