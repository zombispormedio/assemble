package com.zombispormedio.assemble.models.singletons;

import com.zombispormedio.assemble.models.FriendProfile;
import com.zombispormedio.assemble.models.UserProfile;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 30/07/2016.
 */
public class CurrentUser {
    private static CurrentUser ourInstance = new CurrentUser();

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    private UserProfile profile;

    private ArrayList<FriendProfile> friends;

    private CurrentUser() {
        profile=new UserProfile();
        friends=null;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public ArrayList<FriendProfile> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<FriendProfile> friends) {
        this.friends = friends;
    }

    public int getFriendsCount(){
        return friends.size();
    }
}
