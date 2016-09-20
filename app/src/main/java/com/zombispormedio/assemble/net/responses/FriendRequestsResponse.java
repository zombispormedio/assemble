package com.zombispormedio.assemble.net.responses;


import com.zombispormedio.assemble.models.FriendRequestProfile;
import com.zombispormedio.assemble.net.Error;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Xavier Serrano on 26/08/2016.
 */
public class FriendRequestsResponse extends ArrayResponse<FriendRequestProfile> {

    public FriendRequestsResponse(boolean success, Error error,
            FriendRequestProfile[] result) {
        super(success, error, result);
    }


}
