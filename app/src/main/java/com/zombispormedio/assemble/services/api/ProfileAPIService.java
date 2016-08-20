package com.zombispormedio.assemble.services.api;

import com.orhanobut.logger.Logger;
import com.zombispormedio.assemble.handlers.IPromiseHandler;
import com.zombispormedio.assemble.handlers.IServiceHandler;
import com.zombispormedio.assemble.models.Profile;
import com.zombispormedio.assemble.models.UserProfile;
import com.zombispormedio.assemble.rest.Error;
import com.zombispormedio.assemble.rest.FileBody;
import com.zombispormedio.assemble.rest.JsonBinder;
import com.zombispormedio.assemble.rest.Result;
import com.zombispormedio.assemble.rest.responses.DefaultResponse;
import com.zombispormedio.assemble.rest.responses.ProfileResponse;
import com.zombispormedio.assemble.services.IProfileService;

import android.support.v7.widget.DefaultItemAnimator;

import java.io.File;
import java.io.IOException;

/**
 * Created by Xavier Serrano on 27/07/2016.
 */
public class ProfileAPIService implements IProfileService {
    private APIConfiguration api;

    public ProfileAPIService() {
        api=APIConfiguration.getInstance();
    }

    @Override
    public void retrieve(final IServiceHandler<UserProfile, Error> handler) {
        api.RestWithAuth("/profile")
                .handler(new IPromiseHandler() {
                    @Override
                    public void onSuccess(String... args) {
                        try {
                            ProfileResponse res= JsonBinder.toProfileResponse(args[0]);
                            if(res.success){
                                handler.onSuccess(res.result);
                            }else{
                                handler.onError(res.error);
                            }

                        } catch (IOException e) {
                            handler.onError(new Error(e.getMessage()));
                        }

                    }
                })
                .get();
    }

    @Override
    public void changeAvatar(File file, final IServiceHandler<UserProfile, Error> handler) {
        api.RestWithAuth("/profile/avatar")
                .handler(new IPromiseHandler() {
                    @Override
                    public void onSuccess(String... args) {
                        try {
                            ProfileResponse res= JsonBinder.toProfileResponse(args[0]);
                            if(res.success){
                                handler.onSuccess(res.result);
                            }else{
                                handler.onError(res.error);
                            }
                        } catch (IOException e) {
                            handler.onError(new Error(e.getMessage()));
                        }

                    }
                })
                .patch(new FileBody(file, "image/*", "avatar", file.getName()));
    }
}