package com.zombispormedio.assemble.controllers;

import com.orhanobut.logger.Logger;
import com.zombispormedio.assemble.handlers.IOnClickItemListHandler;
import com.zombispormedio.assemble.handlers.IServiceHandler;
import com.zombispormedio.assemble.handlers.ServiceHandler;
import com.zombispormedio.assemble.models.FriendProfile;
import com.zombispormedio.assemble.models.FriendRequestProfile;
import com.zombispormedio.assemble.models.factories.ResourceFactory;
import com.zombispormedio.assemble.models.resources.FriendRequestResource;
import com.zombispormedio.assemble.models.resources.FriendResource;
import com.zombispormedio.assemble.models.resources.UserResource;
import com.zombispormedio.assemble.models.singletons.CurrentUser;
import com.zombispormedio.assemble.models.subscriptions.FriendRequestSubscription;
import com.zombispormedio.assemble.models.subscriptions.FriendSubscription;
import com.zombispormedio.assemble.models.subscriptions.Subscriber;
import com.zombispormedio.assemble.net.Error;
import com.zombispormedio.assemble.views.IFriendsView;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 24/08/2016.
 */
public class FriendsController extends AbstractController {

    private IFriendsView ctx;

    private CurrentUser user;

    private FriendResource friendResource;

    private FriendRequestResource friendRequestResource;

    private FriendSubscription friendSubscription;

    private FriendRequestSubscription friendRequestSubscription;

    private boolean isLoading;

    private boolean isFriendsReady;

    private boolean isRequestsReady;

    public FriendsController(IFriendsView ctx) {
        this.ctx = ctx;
        user = CurrentUser.getInstance();

        friendResource = ResourceFactory.createFriendResource();
        friendRequestResource = ResourceFactory.createFriendRequestResource();

        friendSubscription = user.getFriendSubscription();
        friendRequestSubscription = user.getFriendRequestSubscription();

        isFriendsReady = isRequestsReady = false;
        isLoading = false;

    }

    public void onNewFriend() {
        ctx.goToNewFriend();
    }

    @Override
    public void onCreate() {
        setupFriendsAndRequests();
    }

    private void setupFriendsAndRequests() {

        ArrayList<FriendProfile> friends = friendResource.getAll();
        ArrayList<FriendRequestProfile> friendsRequests = friendRequestResource.getAll();

        if (friends.size() < 0 || friendsRequests.size() < 0) {
            loadingTime();
            addSubscriptions();
        }

        friendSubscription.load();
        friendRequestSubscription.load();

    }


    private void addSubscriptions() {
        friendSubscription.addSubscriber(new Subscriber() {
            @Override
            public void notifyChange() {
                friendSubscription.removeSubscriber(this.getID());
                readyFriends();
            }
        });

        friendRequestSubscription.addSubscriber(new Subscriber() {
            @Override
            public void notifyChange() {
                friendRequestSubscription.removeSubscriber(this.getID());
                readyRequests();
            }
        });
    }


    private void noCheckAll() {
        isFriendsReady = isRequestsReady = false;
    }


    private void loadingTime() {
        if (!isLoading) {
            noCheckAll();
            ctx.loading();
            ctx.hideLists();
            isLoading = true;
        }
    }


    private void noLoadTime() {
        if (isLoading && isReady()) {
            ctx.unloading();
            ctx.showLists();
            isLoading = false;
        }
    }


    private void readyFriends() {
        isFriendsReady = true;
        noLoadTime();
    }

    private void readyRequests() {
        isRequestsReady = true;
        noLoadTime();
    }

    private boolean isReady() {
        return isFriendsReady && isRequestsReady;
    }

    @Override
    public void onDestroy() {
        ctx=null;
    }
}
