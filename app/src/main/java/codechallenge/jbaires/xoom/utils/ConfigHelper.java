package codechallenge.jbaires.xoom.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import codechallenge.jbaires.xoom.R;

import static android.support.v4.content.ContextCompat.getSystemService;

public class ConfigHelper {
    private static final String TAG = "Helper";

    public static String getConfigValue(Context context, String name) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }
        return null;
    }


    public static boolean checkReadPhoneState(Context context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return  false;
        }
    }

    public static String getUUID(Context context) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService((context.TELEPHONY_SERVICE));
            return tManager.getDeviceId();
        } else {
            return "";
        }
    }
}
