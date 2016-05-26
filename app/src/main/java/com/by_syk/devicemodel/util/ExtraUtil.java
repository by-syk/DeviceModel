package com.by_syk.devicemodel.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    /**
     * Convert the current time in milliseconds to readable string as given format.
     * <p>Notice: The time is counted since January 1, 1970 00:00:00.0 UTC.
     *
     * @param format y year
     *               M month in the year
     *               d day
     *               h hours(12)
     *               H hours(24)
     *               m minute
     *               s second
     *               S millisecond
     *               E weekday
     *               D days in the year
     */
    public static String convertMillisTime(long time_millis, String format) {
        String result = "";
        if (time_millis <= 0) {
            return result;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
            result = dateFormat.format(new Date(time_millis));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Convert the current time in milliseconds to readable string
     * as default format ("yyyy-MM-dd").
     */
    public static String convertMillisTime(long time_millis) {
        return convertMillisTime(time_millis, "yyyy-MM-dd");
    }

    /**
     * Refer to https://en.wikipedia.org/wiki/Android_version_history
     */
    public static Calendar getSdkReleaseDate(int sdk_int, String release) {
        if (release == null) {
            release = "";
        }

        Calendar calendar = Calendar.getInstance();

        switch (sdk_int) {
            case Build.VERSION_CODES.BASE: // 1
                switch (release) {
                    case "1.0":
                    default:
                        calendar.set(2008, 8, 23);
                }
                break;
            case Build.VERSION_CODES.BASE_1_1: // 2
                switch (release) {
                    case "1.1":
                    default:
                        calendar.set(2009, 1, 9);
                }
                break;
            case Build.VERSION_CODES.CUPCAKE: // 3
                switch (release) {
                    case "1.5":
                    default:
                        calendar.set(2009, 3, 27);
                }
                break;
            case Build.VERSION_CODES.DONUT: // 4
                switch (release) {
                    case "1.6":
                    default:
                        calendar.set(2009, 8, 15);
                }
                break;
            case Build.VERSION_CODES.ECLAIR: // 5
                switch (release) {
                    case "2.0":
                    default:
                        calendar.set(2009, 9, 26);
                }
                break;
            case Build.VERSION_CODES.ECLAIR_0_1: // 6
                switch (release) {
                    case "2.0.1":
                    default:
                        calendar.set(2009, 11, 3);
                }
                break;
            case Build.VERSION_CODES.ECLAIR_MR1: // 7
                switch (release) {
                    case "2.1":
                    default:
                        calendar.set(2010, 0, 12);
                }
                break;
            case Build.VERSION_CODES.FROYO: // 8
                switch (release) {
                    case "2.2":
                        calendar.set(2010, 4, 20);
                        break;
                    case "2.2.1":
                        calendar.set(2011, 0, 18);
                        break;
                    case "2.2.2":
                        calendar.set(2011, 0, 22);
                        break;
                    case "2.2.3":
                        calendar.set(2011, 10, 21);
                        break;
                    default:
                        calendar.set(2010, 4, 20);
                }
                break;
            case Build.VERSION_CODES.GINGERBREAD: // 9
                switch (release) {
                    case "2.3":
                        calendar.set(2010, 11, 6);
                        break;
                    case "2.3.1": // ?
                        calendar.set(2010, 11, 7);
                        break;
                    case "2.3.2": // ?
                        calendar.set(2011, 0, 1);
                        break;
                    default:
                        calendar.set(2010, 11, 6);
                }
                break;
            case Build.VERSION_CODES.GINGERBREAD_MR1: // 10
                switch (release) {
                    case "2.3.3":
                        calendar.set(2011, 1, 9);
                        break;
                    case "2.3.4":
                        calendar.set(2011, 3, 28);
                        break;
                    case "2.3.5":
                        calendar.set(2011, 6, 25);
                        break;
                    case "2.3.6":
                        calendar.set(2011, 8, 2);
                        break;
                    case "2.3.7":
                        calendar.set(2011, 8, 21);
                        break;
                    default:
                        calendar.set(2011, 1, 9);
                }
                break;
            case Build.VERSION_CODES.HONEYCOMB: // 11
                switch (release) {
                    case "3.0":
                    default:
                        calendar.set(2011, 1, 22);
                }
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR1: // 12
                switch (release) {
                    case "3.1":
                    default:
                        calendar.set(2011, 4, 10);
                }
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR2: // 13
                switch (release) {
                    case "3.2":
                        calendar.set(2011, 6, 15);
                        break;
                    case "3.2.1":
                        calendar.set(2011, 8, 20);
                        break;
                    case "3.2.2":
                        calendar.set(2011, 7, 30);
                        break;
                    case "3.2.3": // ?
                        calendar.set(2011, 8, 1);
                        break;
                    case "3.2.4": // ?
                        calendar.set(2011, 10, 1);
                        break;
                    case "3.2.5": // ?
                        calendar.set(2012, 0, 1);
                        break;
                    case "3.2.6":
                        calendar.set(2012, 1, 1);
                        break;
                    default:
                        calendar.set(2011, 6, 15);
                }
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH: // 14
                switch (release) {
                    case "4.0":
                        calendar.set(2011, 9, 18);
                        break;
                    case "4.0.1":
                        calendar.set(2011, 9, 21);
                        break;
                    case "4.0.2":
                        calendar.set(2011, 10, 28);
                        break;
                    default:
                        calendar.set(2011, 9, 18);
                }
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1: // 15
                switch (release) {
                    case "4.0.3":
                        calendar.set(2011, 11, 16);
                        break;
                    case "4.0.4":
                        calendar.set(2012, 2, 29);
                        break;
                    default:
                        calendar.set(2011, 11, 16);
                }
                break;
            case Build.VERSION_CODES.JELLY_BEAN: // 16
                switch (release) {
                    case "4.1":
                        calendar.set(2012, 6, 9);
                        break;
                    case "4.1.1":
                        calendar.set(2012, 6, 11);
                        break;
                    case "4.1.2":
                        calendar.set(2012, 9, 9);
                        break;
                    default:
                        calendar.set(2012, 6, 9);
                }
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR1: // 17
                switch (release) {
                    case "4.2":
                        calendar.set(2012, 10, 13);
                        break;
                    case "4.2.1":
                        calendar.set(2012, 10, 27);
                        break;
                    case "4.2.2":
                        calendar.set(2013, 1, 11);
                        break;
                    default:
                        calendar.set(2012, 10, 13);
                }
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR2: // 18
                switch (release) {
                    case "4.3":
                        calendar.set(2013, 6, 24);
                        break;
                    case "4.3.1":
                        calendar.set(2013, 9, 3);
                        break;
                    default:
                        calendar.set(2013, 6, 24);
                }
                break;
            case Build.VERSION_CODES.KITKAT: // 19
                switch (release) {
                    case "4.4":
                        calendar.set(2013, 9, 31);
                        break;
                    case "4.4.1":
                        calendar.set(2013, 11, 5);
                        break;
                    case "4.4.2":
                        calendar.set(2013, 11, 9);
                        break;
                    case "4.4.3":
                        calendar.set(2014, 5, 2);
                        break;
                    case "4.4.4":
                        calendar.set(2014, 5, 19);
                        break;
                    default:
                        calendar.set(2013, 9, 31);
                }
                break;
            case Build.VERSION_CODES.KITKAT_WATCH: // 20
                switch (release) {
                    case "4.4W":
                        calendar.set(2014, 5, 25);
                        break;
                    case "4.4W.1":
                        calendar.set(2014, 8, 6);
                        break;
                    case "4.4W.2":
                        calendar.set(2014, 9, 21);
                        break;
                    default:
                        calendar.set(2014, 5, 25);
                }
                break;
            case Build.VERSION_CODES.LOLLIPOP: // 21
                switch (release) {
                    case "5.0":
                        calendar.set(2014, 10, 12);
                        break;
                    case "5.0.1":
                        calendar.set(2014, 11, 2);
                        break;
                    case "5.0.2":
                        calendar.set(2014, 11, 19);
                        break;
                    default:
                        calendar.set(2014, 10, 12);
                }
                break;
            case Build.VERSION_CODES.LOLLIPOP_MR1: // 22
                switch (release) {
                    case "5.1":
                        calendar.set(2015, 2, 9);
                        break;
                    case "5.1.1":
                        calendar.set(2015, 3, 21);
                        break;
                    default:
                        calendar.set(2015, 2, 9);
                }
                break;
            case Build.VERSION_CODES.M: // 23
                switch (release) {
                    case "6.0":
                        calendar.set(2015, 9, 5);
                        break;
                    case "6.0.1":
                        calendar.set(2015, 11, 7);
                        break;
                    default:
                        calendar.set(2015, 9, 5);
                }
                break;
            default:
                calendar = null;
        }

        return calendar;
    }

    public static Calendar getSdkReleaseDate() {
        return getSdkReleaseDate(Build.VERSION.SDK_INT, Build.VERSION.RELEASE);
    }

    public static String getSdkReleaseDateStr(int sdk_int, String release) {
        Calendar calendar = getSdkReleaseDate(sdk_int, release);
        if (calendar == null) {
            return "";
        }

        return convertMillisTime(calendar.getTimeInMillis());
    }

    public static String getSdkReleaseDateStr() {
        return getSdkReleaseDateStr(Build.VERSION.SDK_INT, Build.VERSION.RELEASE);
    }

    public static int getSdkReleaseDays(int sdk_int, String release) {
        Calendar calendar = getSdkReleaseDate(sdk_int, release);
        if (calendar == null) {
            return -1;
        }

        return (int)((Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis())
                / (24 * 60 * 60 * 1000));
    }

    public static int getSdkReleaseDays() {
        return getSdkReleaseDays(Build.VERSION.SDK_INT, Build.VERSION.RELEASE);
    }
}
