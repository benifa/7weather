package com.example.fabricebenimana.a7weather.service.location;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.fabricebenimana.a7weather.bus.Bus;
import com.example.fabricebenimana.a7weather.R;
import com.example.fabricebenimana.a7weather.SevenElevenWeather;
import com.example.fabricebenimana.a7weather.location.LocationUtil;
import com.example.fabricebenimana.a7weather.utils.Commons;
import com.example.fabricebenimana.a7weather.utils.PermissionUtils;
import com.example.fabricebenimana.a7weather.utils.Prefs;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.lang.ref.WeakReference;

public class LocationController implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, PermissionUtils.PermissionListener {

    private static final String TAG = LocationController.class.getSimpleName();
    private WeakReference<Activity> mActivity = new WeakReference<>(null);
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private boolean isGoogleApiConnected;
    private boolean isLocationRequested;
    private boolean isPermissionRequested;
    private boolean mIsLocationUpdatesEnabled;
    private boolean showSettingsDialogIfRequired;
    private Location mLocation;

    private WeakReference<Context> mContext = new WeakReference<>(null);
    private ResultCallback<LocationSettingsResult> settingsResultCallbackForLocation =
            new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            startLocationUpdate();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            break;
                        default:
                            break;
                    }
                }
            };
    private ResultCallback<LocationSettingsResult> settingsResultCallbackForPermission =
            new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                    final Status status = locationSettingsResult.getStatus();
                    if (status.getStatusCode() == LocationSettingsStatusCodes.SUCCESS) {
                        requestAppPermission();
                    } else {

                    }
                }
            };

    public LocationController(Activity activity) {
        setContext(activity);
        mActivity = new WeakReference<Activity>(activity);
        isGoogleApiConnected = false;
        isLocationRequested = false;
        isPermissionRequested = false;
        mIsLocationUpdatesEnabled = false;
        buildAndConnectGoogleApiClient();
        buildLocationRequest();
    }

    private void setContext(final Context context) {
        this.mContext = new WeakReference<>(context);
    }

    private void buildLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(60000);
        mLocationRequest.setSmallestDisplacement(0.1f);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void buildAndConnectGoogleApiClient() {
        if (mGoogleApiClient == null || !mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
            try {
                final GoogleApiClient.Builder builder = new GoogleApiClient.Builder(mContext.get())
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API);

                mGoogleApiClient = builder.build();
                mGoogleApiClient.connect();
            } catch (IllegalStateException e) {
                Log.e(TAG, "buildAndConnectGoogleApiClient", e);
            }
        }
    }

    public void requestLocation(boolean showSettingDialog) {
        isLocationRequested = true;
        showSettingsDialogIfRequired = showSettingDialog;
        if (isGoogleApiConnected) {
            checkLocationSettings(settingsResultCallbackForLocation);
        }
    }

    private void checkLocationSettings(final ResultCallback<LocationSettingsResult> callback) {
        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(callback);
    }

    protected void requestAppPermission() {
        final Activity activity = mActivity.get();
        if (activity != null) {
            final boolean locationPermission = PermissionUtils.hasPermissionToLocateUser(activity);
//            getListener().onAppPermission(locationPermission);
        }
    }


    @SuppressWarnings("MissingPermission")
    protected void startLocationUpdate() {
        final Activity activity = mActivity.get();
        if (activity != null) {
            if (showSettingsDialogIfRequired && Prefs.getInstance().getShowLocationSettings()) {
                PermissionUtils.openSettingsIfDeniedPermission(activity,
                        Commons.LOCATION_REQUEST_PERMISSIONS, activity.getResources().
                                getString(R.string.allow_location_access),
                        activity.getResources().getString(R.string.unable_to_fetch_location));
            } else {
                if (PermissionUtils.appHasPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION,
                        Commons.LOCATION_REQUEST_PERMISSIONS,
                        activity.getResources().getString(R.string.location_permission_rationale),
                        this)) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }
            }
        }
    }

    @Override
    public void cancelFromRationale() {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        isGoogleApiConnected = true;
        if (isLocationRequested) {
            checkLocationSettings(settingsResultCallbackForLocation);
        }
        if (isPermissionRequested) {
            checkLocationSettings(settingsResultCallbackForPermission);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        isGoogleApiConnected = false;

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null) {
            return;
        }
        SevenElevenWeather.getInstance().setCurrentLocation(LocationUtil.getLatLng(location));
        if (mLocation == null) {
            stopLocationUpdate();
            mLocation = location;
            Bus.onLocationObtained(location);
        }

    }

    private void stopLocationUpdate() {
        if (mIsLocationUpdatesEnabled) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }
}
