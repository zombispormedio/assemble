package com.zombispormedio.assemble.controllers;

/**
 * Created by Xavier Serrano on 08/07/2016.
 */
public interface IAbstractController {

    void onDestroy();

    void onStart();

    void onStop();

    void onCreate();

}
