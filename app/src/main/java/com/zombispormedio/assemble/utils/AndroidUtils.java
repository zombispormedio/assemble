package com.zombispormedio.assemble.utils;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.zombispormedio.assemble.handlers.ISuccessHandler;


/**
 * Created by Xavier Serrano on 26/06/2016.
 */
public final class AndroidUtils {


    public static void showAlert(Context ctx, int msg){
        Toast toast=Toast.makeText(ctx, ctx.getResources().getString(msg), Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showAlert(Context ctx, String msg){
        Toast toast=Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static AlertDialog.Builder createConfirmDialog(Context ctx, String msg, String positiveLabel, String negativeLabel, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new  AlertDialog.Builder(ctx);

        builder.setMessage(msg)
                .setPositiveButton(positiveLabel, listener)
                .setNegativeButton(negativeLabel, listener);



        return builder;
    }


    public static DialogInterface.OnClickListener createDialogClickListener(final ISuccessHandler listener){
        return new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case DialogInterface.BUTTON_POSITIVE:
                        listener.onSuccess();
                        break;


                }
            }
        };
    }

    public static void runOnUiThread(final Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            new Handler(Looper.getMainLooper()).post(runnable);
        } else {
            runnable.run();
        }
    }

    public static class InputLayoutHelper{
        private EditText input;
        private TextInputLayout layout;

        public InputLayoutHelper(EditText input, TextInputLayout layout) {
            this.input = input;
            this.layout = layout;
        }

        public EditText getInput() {
            return input;
        }

        public TextInputLayout getLayout() {
            return layout;
        }

        public void hide(){
            input.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
        }

        public void show(){
            input.setVisibility(View.VISIBLE);
            layout.setVisibility(View.VISIBLE);
        }

        public String getValue(){
            return input.getText()
                    .toString();
        }

        public void setError(String message){
            input.setError(message);
        }
    }


}
