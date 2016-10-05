package com.zombispormedio.assemble.views.activities;

import com.zombispormedio.assemble.views.activities.IBaseView;

/**
 * Created by Xavier Serrano on 09/07/2016.
 */
public interface IRegisterView extends IBaseView {

    void showProgressBar();

    void hideProgressBar();

    void showForm();

    void hideForm();

    String getEmail();

    String getPassword();

    String getRepPassword();

    void goToLogin();

    void showEmptyEmail();

    void showEmptyPassword();

    void showEmptyRepPassword();

    void showNotEqualsBothPassword();

    void showSuccessfulRegister();

    void showUnknowError();
}