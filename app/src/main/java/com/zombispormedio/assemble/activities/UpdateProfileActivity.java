package com.zombispormedio.assemble.activities;

import com.orhanobut.logger.Logger;
import com.zombispormedio.assemble.R;
import com.zombispormedio.assemble.controllers.UpdateProfileController;
import com.zombispormedio.assemble.utils.NavigationManager;
import com.zombispormedio.assemble.views.IUpdateProfileView;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.security.auth.login.LoginException;

public class UpdateProfileActivity extends BaseActivity implements IUpdateProfileView {

    private UpdateProfileController ctrl;

    private EditText _usernameInput;

    private EditText _bioInput;

    private EditText _locationInput;

    private EditText _birthdateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        Toolbar bar = (Toolbar) findViewById(R.id.update_profile_bar);
        setSupportActionBar(bar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        _usernameInput=(EditText) findViewById(R.id.username_input);

        _bioInput=(EditText) findViewById(R.id.bio_input);

        _locationInput=(EditText) findViewById(R.id.location_input);

        _birthdateInput=(EditText) findViewById(R.id.birthdate_input);

        Button _saveButton=(Button) findViewById(R.id.save_button);

        ctrl= new UpdateProfileController(this);

        _saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.onClickSaveButton();
            }
        });

        _birthdateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ctrl.onClickBirthDateInput();
            }
        });

        ctrl.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ctrl.onDestroy();
    }

    @Override
    public void setUsername(String username) {
        _usernameInput.setText(username);
    }

    @Override
    public void setBio(String bio) {
        _bioInput.setText(bio);
    }

    @Override
    public void setBirthDate(String birth) {
        _birthdateInput.setText(birth);
    }

    @Override
    public void setLocation(String location) {
        _locationInput.setText(location);
    }


    @Override
    public String getUsername() {
        return _usernameInput.getText().toString();
    }

    @Override
    public String getBio() {
        return _bioInput.getText().toString();
    }

    @Override
    public String setLocation() {
        return _locationInput.getText().toString();
    }

    @Override
    public String getBirthdate() {
        return _birthdateInput.getText().toString();
    }

    @Override
    public void goToUpdateBirthdate() {
        NavigationManager.UpdateBirthdate(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Logger.d("home");
                return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
