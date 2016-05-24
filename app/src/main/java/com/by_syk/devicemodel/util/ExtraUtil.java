package com.by_syk.devicemodel.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by By_syk on 2016-05-24.
 */
public class ExtraUtil {
    /**
     * Get real physical resolution.
     */
    @TargetApi(17)
    public static int[] getRealResolution(Context context) {
        int[] resolution = {-1, -1};

        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        if (C.SDK >= 17) {
            Point point = new Point();
            display.getRealSize(point);
            resolution[0] = point.x;
            resolution[1] = point.y;
        } else if (C.SDK >= 13) {
            try {
                Method methodW = Display.class.getMethod("getRawWidth");
                Method methodH = Display.class.getMethod("getRawHeight");
                resolution[0] = (Integer) methodW.invoke(display);
                resolution[1] = (Integer) methodH.invoke(display);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resolution[0] = display.getWidth();
            resolution[1] = display.getHeight();
        }

        return resolution;
    }
}
