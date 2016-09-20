package com.zombispormedio.assemble.adapters;

import com.zombispormedio.assemble.R;
import com.zombispormedio.assemble.handlers.IOnClickComponentItemHandler;
import com.zombispormedio.assemble.handlers.IOnClickItemListHandler;
import com.zombispormedio.assemble.models.FriendProfile;
import com.zombispormedio.assemble.utils.ImageUtils;
import com.zombispormedio.assemble.utils.StringUtils;
import com.zombispormedio.assemble.utils.Utils;
import com.zombispormedio.assemble.views.IFriendHolder;


import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xavier Serrano on 26/08/2016.
 */
public class FriendViewHolder extends AbstractViewHolder<FriendProfile> implements IFriendHolder{

    private View view;

    private IOnClickItemListHandler<FriendProfile> listener;

    private IOnClickComponentItemHandler<FriendProfile, IFriendHolder> removeButtonListener;

    @BindView(R.id.username_label)
    TextView usernameLabel;

    @BindView(R.id.email_label)
    TextView emailLabel;

    @BindView(R.id.image_view)
    ImageView imageView;

    @BindView(R.id.remove_friend_button)
    ImageButton removeButton;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    public FriendViewHolder(View view) {
        super(view);
        this.view = view;
        this.listener = null;
        setup();

    }

    private void setup() {
        ButterKnife.bind(this, view);
    }

    @Override
    public void bind(int position, FriendProfile itemData) {
        bindData(itemData);
        setupOnClickListener(position, itemData);
        setupOnClickRemoveButton(position, itemData);
    }



    private void setupOnClickListener(final int position, final FriendProfile itemData) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(position, itemData);
                }

            }
        });
    }

    private void setupOnClickRemoveButton(final int position, final FriendProfile itemData) {
        final IFriendHolder holder=this;
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(removeButtonListener!=null){
                    removeButtonListener.onClick(position, itemData, holder);
                }
            }
        });
    }


    private void bindData(FriendProfile itemData) {
        usernameLabel.setText(itemData.username);
        emailLabel.setText(itemData.email);
        setupImage(itemData.large_avatar_url, StringUtils.firstLetter(itemData.username));
    }

    private void setupImage(String url, String letter){
        ImageUtils.ImageBuilder builder=new ImageUtils.ImageBuilder(view.getContext(), imageView)
                .letter(letter)
                .circle(true);
        if(Utils.presenceOf(url)){
            builder=builder.url(url);
        }

        builder.build();
    }


    public void setOnClickListener(IOnClickItemListHandler<FriendProfile> listener) {
        this.listener = listener;
    }

    public void setRemoveButtonListener(
            IOnClickComponentItemHandler<FriendProfile, IFriendHolder> removeButtonListener) {
        this.removeButtonListener = removeButtonListener;
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        removeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        removeButton.setVisibility(View.GONE);
    }
}
