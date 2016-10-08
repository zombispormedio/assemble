package com.zombispormedio.assemble.models.resources;

import com.zombispormedio.assemble.handlers.IServiceHandler;
import com.zombispormedio.assemble.handlers.ServiceHandler;
import com.zombispormedio.assemble.models.FriendRequestProfile;
import com.zombispormedio.assemble.models.subscriptions.FriendRequestSubscription;
import com.zombispormedio.assemble.net.Error;
import com.zombispormedio.assemble.models.services.interfaces.IFriendService;
import com.zombispormedio.assemble.models.services.storage.IStorageService;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Xavier Serrano on 13/09/2016.
 */
public class FriendRequestResource extends ConceptResource<FriendRequestProfile> {

    private IFriendService apiService;


    private FriendRequestSubscription friendRequestSubscription;

    @Inject
    public FriendRequestResource(IFriendService apiService,
            IStorageService<FriendRequestProfile> storage) {
        super(storage);
        this.apiService = apiService;
    }


    public void rejectRequest(int id, final IServiceHandler<ArrayList<FriendRequestProfile>, Error> handler){
        apiService.rejectRequest(id, new ServiceHandler<ArrayList<FriendRequestProfile>, Error>(){
            @Override
            public void onError(Error error) {
                handler.onError(error);
            }

            @Override
            public void onSuccess(ArrayList<FriendRequestProfile> result) {
                storage.createOrUpdateOrDeleteAll(result);
                haveChanged();
                handler.onSuccess(result);
            }
        });
    }

    public void acceptRequest(int id, final IServiceHandler<ArrayList<FriendRequestProfile>, Error> handler){
        apiService.acceptRequest(id, new ServiceHandler<ArrayList<FriendRequestProfile>, Error>(){
            @Override
            public void onError(Error error) {
                handler.onError(error);
            }

            @Override
            public void onSuccess(ArrayList<FriendRequestProfile> result) {
                storage.createOrUpdateOrDeleteAll(result);
                haveChanged();
                handler.onSuccess(result);
            }
        });
    }


    public void setFriendRequestSubscription(
            FriendRequestSubscription friendRequestSubscription) {
        this.friendRequestSubscription = friendRequestSubscription;
    }

    private void haveChanged(){
        if (friendRequestSubscription!=null){
            friendRequestSubscription.haveChanged();
        }
    }

}
