package com.zombispormedio.assemble.models;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 07/09/2016.
 */
public class Chat extends BaseModel{

    public String created_at;

    public UserProfile sender;

    public FriendProfile recipient;

    public Message[] messages;

    public int owner_id;

    public int friend_id;

    public Message last_message;

    public Chat() {
        super(0);
    }

    public Chat(int id, String created_at, UserProfile sender, FriendProfile recipient,
            Message[] messages, Message last_message) {
        super(id);
        this.created_at = created_at;
        this.sender = sender;
        this.recipient = recipient;
        this.messages = messages;
        this.last_message = last_message;
    }
}
