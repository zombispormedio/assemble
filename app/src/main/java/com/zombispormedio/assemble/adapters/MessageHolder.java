package com.zombispormedio.assemble.adapters;

import com.zombispormedio.assemble.R;
import com.zombispormedio.assemble.models.Message;
import com.zombispormedio.assemble.models.UserProfile;
import com.zombispormedio.assemble.utils.ISODate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by Xavier Serrano on 04/10/2016.
 */

public class MessageHolder extends AbstractHolder<MessageHolder.Container> {


    @BindView(R.id.friend_layout)
    LinearLayout friendLayout;


    @BindView(R.id.user_layout)
    FrameLayout userLayout;


    @BindView(R.id.friend_image_view)
    ImageView friendImageView;


    @BindView(R.id.friend_content_text)
    TextView friendContent;


    @BindView(R.id.user_content_text)
    TextView userContent;


    @BindView(R.id.message_state)
    ImageView userImageView;


    @BindView(R.id.message_date_label)
    TextView messageDateLabel;


    @BindView(R.id.date_label)
    TextView dateLabel;


    @BindView(R.id.date_label_layout)
    FrameLayout dateLabelLayout;

    private boolean isRoot;


    public MessageHolder(View itemView) {
        super(itemView);
        isRoot = false;
    }


    @Override
    public void bind(int position, @NonNull Container container) {
        isRoot = true;
        setupOnClick(container);
        Message message = container.getMessage();
        renderData(message);
        renderDate(message.getCreatedAt());
    }


    public void bind(int position, @NonNull Container container, @NonNull Container previousContainer) {
        setupOnClick(container);

        Message message = container.getMessage();
        Message previous = previousContainer.getMessage();

        registerIfIsRoot(message, previous);

        renderData(message);

        if (message.beforeCreated(previous.getCreatedAt())) {
            renderDate(message.getCreatedAt());
        } else {
            dateLabelLayout.setVisibility(View.GONE);
        }
    }

    private void renderDate(@NonNull ISODate created) {
        dateLabelLayout.setVisibility(View.VISIBLE);

        String text = created.isToday() ? getString(R.string.today)
                : created.isYesterday() ? getString(R.string.yesterday) :
                        created.format(getString(R.string.simple_date));
        dateLabel.setText(text);
    }


    public void renderData(@NonNull Message message) {

        if (message.sender instanceof UserProfile) {
            renderUserMessage(message);

        } else {
            renderFriendMessage(message);

        }
        bindDate(message);


    }

    private void bindDate(@NonNull Message message) {
        boolean isToday = message.isCreatedToday();
        boolean isYesterday = message.isCreatedYesterday();

        String formatDate = isToday ? message.formatCreated(getString(R.string.message_date_today))
                : isYesterday ? message.formatCreated(getString(R.string.message_date_yesterday))
                        : message.formatCreated(getString(R.string.message_date));
        messageDateLabel.setText(formatDate);

        int marginLeft;
        if ((message.sender instanceof UserProfile) && (message.content.length() < 38)) {
            int shortDate = (int) getDimen(R.dimen.short_date_message_margin);
            int nearDate = (int) getDimen(R.dimen.near_date_message_margin);
            marginLeft = isToday || isYesterday ? nearDate : shortDate;
        } else {
            marginLeft = (int) getDimen(R.dimen.date_message_margin_left);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) messageDateLabel.getLayoutParams();
        params.setMargins(marginLeft, params.topMargin, params.rightMargin, params.bottomMargin);
        messageDateLabel.setLayoutParams(params);
    }

    private void renderFriendMessage(@NonNull Message message) {
        showFriend();

        friendContent.setText(message.content);
        if (isRoot) {
            doIfRootFriendMessage(message);
            setTopToFriendLayout((int) getDimen(R.dimen.root_message_margin_top));


        } else {
            friendImageView.setVisibility(View.INVISIBLE);
        }

    }

    private void setTopToFriendLayout(int top) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) friendLayout.getLayoutParams();
        params.setMargins(params.leftMargin, top, params.rightMargin, params.bottomMargin);
        friendLayout.setLayoutParams(params);
    }


    private void doIfRootFriendMessage(@NonNull Message message) {
        friendImageView.setVisibility(View.VISIBLE);
        message.sender.getMediumImageBuilder()
                .context(getContext())
                .imageView(friendImageView)
                .build();
    }

    private void renderUserMessage(@NonNull Message message) {
        showUser();
        userContent.setText(message.content);
        renderUserMessageState(message);
        if (isRoot) {
            setTopToUserLayout((int) getDimen(R.dimen.root_message_margin_top));
        }
    }


    private void setTopToUserLayout(int top) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) userLayout.getLayoutParams();
        params.setMargins(params.leftMargin, top, params.rightMargin, params.bottomMargin);
        userLayout.setLayoutParams(params);
    }

    private void renderUserMessageState(@NonNull Message message) {
        if (message.is_read) {
            userImageView.setImageResource(R.drawable.message_check_all_layer);
        } else if (message.is_sent) {
            userImageView.setImageResource(R.drawable.message_check_layer);
        } else {
            userImageView.setImageResource(R.drawable.message_clock_layer);
        }
    }

    private void showFriend() {
        friendLayout.setVisibility(View.VISIBLE);
        userLayout.setVisibility(View.GONE);
    }


    private void showUser() {
        userLayout.setVisibility(View.VISIBLE);
        friendLayout.setVisibility(View.GONE);
    }

    private void registerIfIsRoot(@NonNull Message that, @NonNull Message previous) {
        isRoot = that.sender.id != previous.sender.id;
    }

    private void setupOnClick(@NonNull Container container) {
        if (container.isClicked()) {
            showDate();
        } else {
            hideDate();
        }

        getView().setOnClickListener(v -> {
            if (container.isClicked()) {
                container.setClicked(false);
                hideDate();
            } else {
                container.setClicked(true);
                showDate();
            }
        });
    }

    private void showDate() {
        messageDateLabel.setVisibility(View.VISIBLE);
    }

    private void hideDate() {
        messageDateLabel.setVisibility(View.GONE);
    }


    public static class Container {

        private Message message;

        private boolean clicked;

        public Container(Message message) {
            this.message = message;
            clicked = false;
        }

        public void read() {
            message.is_read = true;
        }

        public boolean isClicked() {
            return clicked;
        }

        public void setClicked(boolean clicked) {
            this.clicked = clicked;
        }

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }
    }

}
