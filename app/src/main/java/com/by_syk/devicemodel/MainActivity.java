package com.by_syk.devicemodel;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.by_syk.devicemodel.util.C;
import com.by_syk.devicemodel.util.ExtraUtil;
import com.by_syk.lib.toast.GlobalToast;

public class MainActivity extends Activity {
    private TextView tvInfo;
    private TextView tvDays;

    private int sdk1_release_days = 0;
    private int sdk_release_days = 0;

    private int days_change = 0;

    private final String MODEL = Build.MODEL;
    private final String BRAND = Build.BRAND;
    private final String MANUFACTURER = Build.MANUFACTURER;
    private final int SDK_INT = Build.VERSION.SDK_INT;
    private final String RELEASE = Build.VERSION.RELEASE;
    private final String DEVICE = Build.DEVICE;

    private static Handler handler = new Handler();
    private Runnable runnable = null;

    private boolean is_cancelled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        showCodeName();

        showInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();

        is_cancelled = true;
    }

    private void init() {
        tvInfo = (TextView) findViewById(R.id.tv_info);
        tvDays = (TextView) findViewById(R.id.tv_days);

        tvDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_cancelled = true;

                GlobalToast.showToast(MainActivity.this, ExtraUtil.getSdkReleaseDateStr());
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                tvDays.setText(String.valueOf(sdk1_release_days));

                if (is_cancelled || sdk1_release_days == sdk_release_days) {
                    tvDays.setText(String.valueOf(sdk_release_days));
                    return;
                }

                if ((sdk1_release_days -= (Math.sqrt(++days_change) * 3)) < sdk_release_days) {
                    sdk1_release_days = sdk_release_days;
                }
                handler.postDelayed(this, 18);
            }
        };
    }

    @TargetApi(11)
    private void showCodeName() {
        if (C.SDK < 11 || MODEL.toLowerCase().contains(DEVICE.toLowerCase())) {
            return;
        }

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(DEVICE);
        }
    }

    private void showInfo() {
        int[] resolution = ExtraUtil.getRealResolution(this);

        String info = getString(R.string.info, MODEL, BRAND, MANUFACTURER, SDK_INT, RELEASE,
                resolution[0], resolution[1]);

        tvInfo.setText(info);

        Log.i(C.LOG_TAG, info);

        sdk1_release_days = ExtraUtil.getSdkReleaseDays(Build.VERSION_CODES.BASE, null);
        sdk_release_days = ExtraUtil.getSdkReleaseDays();
        if (sdk_release_days > 0 && sdk1_release_days >= sdk_release_days) {
            tvDays.setText(String.valueOf(sdk1_release_days));

            handler.postDelayed(runnable, 400);
        }
    }
}
