package com.zombispormedio.assemble.adapters;

import com.zombispormedio.assemble.R;
import com.zombispormedio.assemble.handlers.IOnClickItemListHandler;
import com.zombispormedio.assemble.models.Meeting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 06/09/2016.
 */
public class MeetingsRecyclerViewAdapter extends BaseRecyclerViewAdapter<Meeting, MeetingViewHolder> {

    public MeetingsRecyclerViewAdapter(ArrayList<Meeting> data) {
        super(data);
    }

    @Override
    public MeetingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_meetings, parent, false);

        MeetingViewHolder holder = new MeetingViewHolder(itemView);
        if (listener != null) {
            holder.setOnClickListener(listener);
        }
        return holder;
    }

    public static class Factory {

        private IOnClickItemListHandler<Meeting> listener;

        public void setOnClickListener(IOnClickItemListHandler<Meeting> listener) {
            this.listener = listener;

        }

        public MeetingsRecyclerViewAdapter make(ArrayList<Meeting> data) {
            MeetingsRecyclerViewAdapter adapter = new MeetingsRecyclerViewAdapter(data);
            if (listener != null) {
                adapter.setOnClickListener(listener);
            }
            return adapter;
        }
    }
}