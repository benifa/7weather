package com.example.fabricebenimana.a7weather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

public class DialogUtils {
    private DialogUtils() {

    }

    public static void showDialog(final boolean isCancellable,
                                  Context context, int theme, String msg, String title,
                                  String okButtonTitle, String cancelButtonTitle,
                                  DialogInterface.OnClickListener okButtonClickListener,
                                  DialogInterface.OnClickListener cancelButtonClickListener) {
        if (context != null) {
            final AlertDialog.Builder builder = theme == 0 ?
                    new AlertDialog.Builder(context) :
                    new AlertDialog.Builder(context, theme);
            builder.setMessage(msg);
            builder.setCancelable(isCancellable);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (!TextUtils.isEmpty(okButtonTitle) && okButtonClickListener != null) {
                builder.setNegativeButton(okButtonTitle, okButtonClickListener);
            }
            if (!TextUtils.isEmpty(cancelButtonTitle) && cancelButtonClickListener != null) {
                builder.setPositiveButton(cancelButtonTitle, cancelButtonClickListener);
            }

            final AlertDialog dialog = builder.create();
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing()) {
                    dialog.show();
                }
            } else {
                dialog.show();
            }
        }
    }
}
