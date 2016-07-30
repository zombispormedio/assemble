package com.zombispormedio.assemble.activities;



import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.zombispormedio.assemble.R;
import com.zombispormedio.assemble.fragments.SettingsFragment;
import com.zombispormedio.assemble.controllers.SettingsController;

import com.zombispormedio.assemble.handlers.ISuccessHandler;
import com.zombispormedio.assemble.utils.NavigationManager;
import com.zombispormedio.assemble.utils.AndroidUtils;
import com.zombispormedio.assemble.views.ISettingsView;


public class SettingsActivity extends BaseActivity implements ISettingsView {
    private SettingsController ctrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar bar = (Toolbar) findViewById(R.id.settings_bar);
        setSupportActionBar(bar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        ctrl= new SettingsController(this);

        SettingsFragment frag = new SettingsFragment();

        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, frag)
                .commit();



    }

    public SettingsController getController(){
        return ctrl;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ctrl.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ctrl.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ctrl.onStop();
    }

    public void showConfirmSignOutDialog( ISuccessHandler listener){
        String msg=getResources().getString(R.string.signout_dialog_msg);

        String positive=getResources().getString(R.string.yes_title);

        String negative=getResources().getString(R.string.no_title);

        AndroidUtils.createConfirmDialog(this, msg, positive, negative, AndroidUtils.createDialogClickListener(listener))
                .show();
    }

    @Override
    public void goToLogin() {
        NavigationManager.Login(this);
    }

}
