package com.example.fabricebenimana.a7weather.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.example.fabricebenimana.a7weather.R;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

public class PermissionUtils {

    private PermissionUtils() {
        // hide constructor
    }

    public static boolean appHasPermission(final Context context, final String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean appHasPermissions(final Context context, final List<String> permissions) {
        boolean hasPermissions = false;

        if (!CollectionUtils.isEmpty(permissions)) {
            hasPermissions = true;

            for (String permission : permissions) {
                hasPermissions &= appHasPermission(context, permission);
            }
        }

        return hasPermissions;
    }

    public static boolean hasPermissionToLocateUser(final Context context) {
        return appHasPermissions(context,
                Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public static void openSettingsIfDeniedPermission(final Activity activity, final int permissionRequestCode,
                                                      String subTitle, String title) {
        final Resources res = activity.getResources();

        DialogUtils.showDialog(true, activity, 0, subTitle,
                title,
                res.getString(R.string.Ok), res.getString(R.string.settings),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivityForResult(createOpenSettingsScreen(activity),
                                permissionRequestCode);
                    }
                });
    }

    public static boolean appHasPermission(final Activity activity,
                                           final String permission,
                                           final int requestCode,
                                           final String rationale,
                                           final PermissionListener listener) {
        boolean hasPermission = false;
        if (activity != null && listener != null) {
            if (appHasPermission(activity, permission)) {
                hasPermission = true;
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    showPermissionRationale(activity, permission, requestCode, rationale, listener);
                } else {
                    requestPermission(activity, permission, requestCode);
                }
            }
        }
        return hasPermission;
    }

    private static void showPermissionRationale(final Activity activity,
                                                final String permission,
                                                final int requestCode,
                                                final String rationale,
                                                final PermissionListener listener) {
        final DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (activity != null) {
                    requestPermission(activity, permission, requestCode);
                }
            }
        };

        final DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.cancelFromRationale();
            }
        };

        final Resources res = activity.getResources();
        DialogUtils.showDialog(true, activity, 0, rationale, permission, res.getString(R.string.Ok), res.getString(R.string.Cancel),
                okListener, cancelListener);
    }

    private static void requestPermission(final Activity activity,
                                          final String permission,
                                          final int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    private static Intent createOpenSettingsScreen(final Context context) {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        final Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }

    public interface PermissionListener {
        void cancelFromRationale();
    }
}
