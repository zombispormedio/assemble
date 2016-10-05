package com.zombispormedio.assemble.adapters.lists;

import com.zombispormedio.assemble.R;
import com.zombispormedio.assemble.adapters.PreviewFriendHolder;
import com.zombispormedio.assemble.handlers.IOnClickItemListHandler;
import com.zombispormedio.assemble.models.FriendProfile;

import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 29/09/2016.
 */

public class PreviewFriendsListAdapter extends BaseListAdapter<FriendProfile, PreviewFriendHolder> {

    private IOnClickItemListHandler<FriendProfile> listener;

    @Override
    public PreviewFriendHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        PreviewFriendHolder holder = new PreviewFriendHolder(getView(parent, R.layout.list_item_preview_friends));

        holder.setOnClickListener(listener);

        return holder;
    }

    public void setOnClickListener(
            IOnClickItemListHandler<FriendProfile> listener) {
        this.listener = listener;
    }
}