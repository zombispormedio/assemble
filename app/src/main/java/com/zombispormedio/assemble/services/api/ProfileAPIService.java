package com.zombispormedio.assemble.services.api;


import com.zombispormedio.assemble.handlers.IServiceHandler;
import com.zombispormedio.assemble.handlers.PromiseHandler;
import com.zombispormedio.assemble.models.EditProfile;


import com.zombispormedio.assemble.models.UserProfile;
import com.zombispormedio.assemble.net.Error;
import com.zombispormedio.assemble.net.FileBody;
import com.zombispormedio.assemble.net.JsonBinder;


import com.zombispormedio.assemble.net.responses.ProfileResponse;
import com.zombispormedio.assemble.services.interfaces.IProfileService;


import java.io.File;
import java.io.IOException;

/**
 * Created by Xavier Serrano on 27/07/2016.
 */
public class ProfileAPIService implements IProfileService {

    private APIConfiguration api;

    public ProfileAPIService() {
        api = APIConfiguration.getInstance();
    }

    @Override
    public void retrieve(final IServiceHandler<UserProfile, Error> handler) {
        api.RestWithAuth("/profile")
                .handler(DeferUtils.deferProfile(handler))
                .get();
    }

    @Override
    public void changeAvatar(File file, final IServiceHandler<UserProfile, Error> handler) {
        api.RestWithAuth("/profile/avatar")
                .handler(DeferUtils.deferProfile(handler))
                .patch(new FileBody(file, "image/*", "avatar", file.getName()));
    }


    @Override
    public void update(EditProfile profile, final IServiceHandler<UserProfile, Error> handler) {
        api.RestWithAuth("/profile")
                .handler(DeferUtils.deferProfile(handler))
                .put(JsonBinder.fromEditProfile(profile));
    }




}
