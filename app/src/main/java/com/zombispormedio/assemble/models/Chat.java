package com.zombispormedio.assemble.models;


import com.zombispormedio.assemble.utils.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Xavier Serrano on 07/09/2016.
 */
public class Chat extends BaseModel implements Sorted<Chat> {

    public final String created_at;

    public final UserProfile sender;

    public final FriendProfile recipient;

    public final Message lastMessage;

    public int owner_id;

    public int friend_id;

    public Chat(int id, String created_at, UserProfile sender, FriendProfile recipient,
            Message lastMessage) {
        super(id);
        this.created_at = created_at;
        this.sender = sender;
        this.recipient = recipient;
        this.lastMessage = lastMessage;
    }


    @Override
    public int compareTo(Chat o) {
        int result;
        Message m2 = o.lastMessage;
        if (m2 == null && lastMessage == null) {
            result= DateUtils.compareISODateString(created_at, o.created_at);
        } else {
            if (m2 == null) {
                result = DateUtils.compareISODateString(lastMessage.created_at, o.created_at);
            } else if (lastMessage == null) {
                result = DateUtils.compareISODateString(created_at, m2.created_at);
            } else {
                result = lastMessage.compareTo(m2);
            }
        }
        return result;
    }

    @Override
    public boolean areTheSame(Chat o) {
        return id == o.id &&
                safeCompareLastMessage(o.lastMessage);
    }

    @Override
    public int getIdentity() {
        return id;
    }

    private boolean safeCompareLastMessage(Message last2) {
        boolean comp = lastMessage != null;
        if (comp) {
            comp = lastMessage.areTheSame(last2);
        }
        return comp;
    }
}
