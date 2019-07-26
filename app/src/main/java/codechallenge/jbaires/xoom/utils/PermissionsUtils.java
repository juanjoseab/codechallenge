package codechallenge.jbaires.xoom.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PermissionsUtils {
    public static final int REQUEST_PERMISSION_MULTIPLE = 0;
    public static final int REQUEST_CONNECTIVITY_SERVICE = 1;

    public static boolean checkAndRequestPermissions(Activity activity) {
        System.out.println("PermissionsUtils checkAndRequestPermissions()");

        int permissionAccessNetworkState = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE);

        // Permission List
        List<String> listPermissionsNeeded = new ArrayList<>();

        // Camera Permission
        if (permissionAccessNetworkState != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_NETWORK_STATE)) {
                Toast.makeText(activity, "Access Network State is required for this app to run properly", Toast.LENGTH_LONG)
                        .show();
            }
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_PERMISSION_MULTIPLE);
            return false;
        }

        return true;
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void requestAccessNetworkStatePermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_NETWORK_STATE)) {
                Toast.makeText(activity, "Access Network State permission is needed to validate the connection ", Toast.LENGTH_SHORT).show();
                // Show an explanation to the user *asynchronously* -- don't
                // block this thread waiting for the user's response! After the
                // user sees the explanation, try again to request the
                // permission.
                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_NETWORK_STATE },
                        REQUEST_CONNECTIVITY_SERVICE);

                Toast.makeText(activity, "REQUEST ACCESS NETWORK STATE PERMISSION", Toast.LENGTH_LONG).show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_NETWORK_STATE },
                        REQUEST_CONNECTIVITY_SERVICE);

            }
        }
    }
}
