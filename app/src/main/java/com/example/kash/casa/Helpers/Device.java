package com.example.kash.casa.Helpers;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.WindowManager;

import com.example.kash.casa.Models.DeviceModel;

/**
 * Created by Kash on 2/9/2015.
 */
public class Device {

    public static DeviceModel getDeviceInfo(Context context){
        DeviceModel deviceModel = new DeviceModel();

        //set device model info
        Point point = new Point();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getRealSize(point);
        deviceModel.name = String.format("%s %s", Build.BRAND, Build.MODEL);
        deviceModel.height = Math.max(point.x, point.y);
        deviceModel.width = Math.min(point.x, point.y);
        deviceModel.id = Settings.Secure.ANDROID_ID;

        return deviceModel;
    }
}
