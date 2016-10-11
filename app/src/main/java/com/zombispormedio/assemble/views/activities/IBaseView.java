package com.zombispormedio.assemble.views.activities;

import com.zombispormedio.assemble.views.IApplicationView;

/**
 * Created by Xavier Serrano on 25/07/2016.
 */
public interface IBaseView extends IApplicationView {

    String getAuthToken();

    void setAuthToken(String token);

    void clearAuthToken();

    void showAlert(String msg);

    String getMessagingId();

    void removeMessagingId();


}
