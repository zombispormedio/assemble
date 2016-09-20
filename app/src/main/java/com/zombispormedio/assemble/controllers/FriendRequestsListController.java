package com.zombispormedio.assemble.controllers;

import com.orhanobut.logger.Logger;
import com.zombispormedio.assemble.handlers.IOnClickItemListHandler;
import com.zombispormedio.assemble.models.FriendRequestProfile;
import com.zombispormedio.assemble.models.resources.FriendRequestResource;
import com.zombispormedio.assemble.models.subscriptions.FriendRequestSubscription;
import com.zombispormedio.assemble.models.subscriptions.Subscriber;
import com.zombispormedio.assemble.views.IFriendRequestHolder;
import com.zombispormedio.assemble.views.IFriendRequestsListView;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 11/09/2016.
 */
public class FriendRequestsListController extends Controller {

    private IFriendRequestsListView ctx;

    private FriendRequestResource friendRequestResource;

    private FriendRequestSubscription friendRequestSubscription;

    private FriendRequestSubscriber friendRequestSubscriber;

    public FriendRequestsListController(IFriendRequestsListView ctx) {
        super(ctx.getParent());
        this.ctx = ctx;

        friendRequestResource = getResourceComponent().provideFriendRequestResource();
        friendRequestSubscription = getResourceComponent().provideFriendRequestSubscription();
        friendRequestSubscriber = new FriendRequestSubscriber();
        friendRequestSubscription.addSubscriber(friendRequestSubscriber);

        friendRequestResource.setFriendRequestSubscription(friendRequestSubscription);

        friendRequestResource.setFriendSubscription(getResourceComponent().provideFriendSubscription());
    }

    @Override
    public void onCreate() {
        bindRequests();
    }

    private void bindRequests() {
        ArrayList<FriendRequestProfile> friendRequests = friendRequestResource.getAll();

        if (friendRequests.size() > 0) {
            ctx.bindFriendRequests(friendRequests);
        }
    }

    public void  onClickRequestItem(int position, FriendRequestProfile data) {

    }

    public void onAcceptRequest(int position, FriendRequestProfile data, IFriendRequestHolder holder) {

    }

    public void onRejectRequest(int position, FriendRequestProfile data, IFriendRequestHolder holder) {

    }

    private class FriendRequestSubscriber extends Subscriber {

        @Override
        public void notifyChange() {
            bindRequests();
        }
    }

    @Override
    public void onDestroy() {
        ctx = null;
        friendRequestSubscription.removeSubscriber(friendRequestSubscriber);
    }
}
