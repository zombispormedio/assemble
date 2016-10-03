package com.zombispormedio.assemble.views.activities;

import com.zombispormedio.assemble.views.activities.IBaseView;

/**
 * Created by Xavier Serrano on 02/10/2016.
 */

public interface IChatView extends IBaseView {

    void bindTitle(String title);

    void setAvatar(String path, String letter);
}
