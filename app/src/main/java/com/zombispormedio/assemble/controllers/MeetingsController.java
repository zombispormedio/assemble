package com.zombispormedio.assemble.controllers;

import com.orhanobut.logger.Logger;
import com.zombispormedio.assemble.handlers.IOnClickItemListHandler;
import com.zombispormedio.assemble.handlers.IServiceHandler;
import com.zombispormedio.assemble.models.Meeting;
import com.zombispormedio.assemble.models.factories.ResourceFactory;
import com.zombispormedio.assemble.models.resources.MeetingResource;
import com.zombispormedio.assemble.models.singletons.CurrentUser;
import com.zombispormedio.assemble.models.subscriptions.MeetingSubscription;
import com.zombispormedio.assemble.models.subscriptions.Subscriber;
import com.zombispormedio.assemble.net.Error;
import com.zombispormedio.assemble.views.IMeetingsView;

import java.util.ArrayList;

/**
 * Created by Xavier Serrano on 06/09/2016.
 */
public class MeetingsController extends AbstractController {

    private IMeetingsView ctx;

    private MeetingResource meetingResource;

    private MeetingSubscription meetingSubscription;

    private MeetingSubscriber meetingSubscriber;


    private CurrentUser user;

    public MeetingsController(IMeetingsView ctx) {
        this.ctx = ctx;
        user=CurrentUser.getInstance();
        meetingResource= ResourceFactory.createMeetingResource();
        meetingSubscription= user.getMeetingSubscription();
        meetingSubscriber=new MeetingSubscriber();
        meetingSubscription.addSubscriber(meetingSubscriber);
    }

    @Override
    public void onCreate() {
        setupMeetings();
    }

    private void setupMeetings() {
        bindMeetings();

       meetingSubscription.load();

    }

    public void bindMeetings(){
        ArrayList<Meeting> meetings = meetingResource.getAll();

        if (meetings.size() > 0) {
            ctx.bindMeetings(meetings);
        }
    }


    public IOnClickItemListHandler<Meeting> getOnClickOneTeam() {
        return new IOnClickItemListHandler<Meeting>() {
            @Override
            public void onClick(int position, Meeting data) {
                Logger.d(position);
                Logger.d(data);
            }
        };
    }

    private class MeetingSubscriber extends Subscriber {

        @Override
        public void notifyChange() {
          bindMeetings();
        }
    }


}
