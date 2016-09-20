package com.zombispormedio.assemble.services.api;

import com.zombispormedio.assemble.handlers.IServiceHandler;
import com.zombispormedio.assemble.handlers.PromiseHandler;
import com.zombispormedio.assemble.models.BaseModel;
import com.zombispormedio.assemble.models.Chat;
import com.zombispormedio.assemble.models.FriendProfile;
import com.zombispormedio.assemble.models.FriendRequestProfile;
import com.zombispormedio.assemble.models.Meeting;
import com.zombispormedio.assemble.models.Team;
import com.zombispormedio.assemble.models.UserProfile;
import com.zombispormedio.assemble.net.Error;
import com.zombispormedio.assemble.net.JsonBinder;
import com.zombispormedio.assemble.net.Result;
import com.zombispormedio.assemble.net.responses.ChatsResponse;
import com.zombispormedio.assemble.net.responses.DefaultResponse;
import com.zombispormedio.assemble.net.responses.FriendRequestsResponse;
import com.zombispormedio.assemble.net.responses.FriendsResponse;
import com.zombispormedio.assemble.net.responses.ArrayResponse;
import com.zombispormedio.assemble.net.responses.MeetingsResponse;
import com.zombispormedio.assemble.net.responses.ProfileResponse;
import com.zombispormedio.assemble.net.responses.TeamsResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 20/09/2016.
 */

class DeferUtils {

    static PromiseHandler defer(IServiceHandler<Result, Error> handler) {
        return new PromiseHandler<DefaultResponse, Result>(handler);
    }


    static PromiseHandler deferProfile(IServiceHandler<UserProfile, Error> handler) {
        return new PromiseHandler<ProfileResponse, UserProfile>(handler) {
            @Override
            protected ProfileResponse getResponse(String arg) throws IOException {
                return JsonBinder.toProfileResponse(arg);
            }
        };
    }

    static PromiseHandler deferFriends(IServiceHandler<ArrayList<FriendProfile>, Error> handler) {
        return new ArrayPromiseHandler<FriendsResponse, FriendProfile>(handler) {
            @Override
            protected FriendsResponse getResponse(String arg) throws IOException {
                return JsonBinder.toFriendsResponse(arg);
            }
        };
    }

    static PromiseHandler deferFriendRequests(IServiceHandler<ArrayList<FriendRequestProfile>, Error> handler) {

        return new ArrayPromiseHandler<FriendRequestsResponse, FriendRequestProfile>(handler) {
            @Override
            protected FriendRequestsResponse getResponse(String arg) throws IOException {
                return JsonBinder.toFriendRequestsResponse(arg);
            }
        };
    }


    static PromiseHandler deferChats(IServiceHandler<ArrayList<Chat>, Error> handler) {
        return new ArrayPromiseHandler<ChatsResponse, Chat>(handler) {
            @Override
            protected ChatsResponse getResponse(String arg) throws IOException {
                return JsonBinder.toChatsResponse(arg);
            }
        };
    }

    static PromiseHandler deferMeetings(IServiceHandler<ArrayList<Meeting>, Error> handler) {
        return new ArrayPromiseHandler<MeetingsResponse, Meeting>(handler) {
            @Override
            protected MeetingsResponse getResponse(String arg) throws IOException {
                return JsonBinder.toMeetingsResponse(arg);
            }

        };
    }

    static PromiseHandler deferTeams(IServiceHandler<ArrayList<Team>, Error> handler) {
        return new ArrayPromiseHandler<TeamsResponse, Team>(handler) {

            @Override
            protected TeamsResponse getResponse(String arg) throws IOException {
                return JsonBinder.toTeamsResponse(arg);
            }
        };
    }


    private static class ArrayPromiseHandler<R extends ArrayResponse<M>, M extends BaseModel>
            extends PromiseHandler<R, ArrayList<M>> {

        ArrayPromiseHandler(
                IServiceHandler<ArrayList<M>, Error> handler) {
            super(handler);
        }

        @Override
        protected ArrayList<M> getResult(R res) {
            return res.getResult();
        }
    }

}