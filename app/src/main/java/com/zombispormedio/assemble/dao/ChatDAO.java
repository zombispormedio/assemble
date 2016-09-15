package com.zombispormedio.assemble.dao;

import com.zombispormedio.assemble.models.Chat;
import com.zombispormedio.assemble.models.Recipient;
import com.zombispormedio.assemble.models.Sender;
import com.zombispormedio.assemble.utils.Utils;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Xavier Serrano on 12/09/2016.
 */
public class ChatDAO extends RealmObject implements IBaseDAO<Chat> {

    @PrimaryKey
    public int id;

    public String created_at;

    public SenderDAO sender;

    public RecipientDAO recipient;


    @Override
    public Chat toModel() {
        return new Chat(id, created_at, sender.toModel(), recipient.toModel());
    }

    @Override
    public ChatDAO fromModel(Chat model) {

        this.id=model.id;
        this.created_at=model.created_at;
        this.sender=(new SenderDAO()).fromModel(model.sender);
        this.recipient=(new RecipientDAO()).fromModel(model.recipient);

        return this;
    }

    @Override
    public int getId() {
        return id;
    }

    public static class Factory implements IDAOFactory<ChatDAO>{

        @Override
        public ChatDAO create() {
            return new ChatDAO();
        }
    }
}