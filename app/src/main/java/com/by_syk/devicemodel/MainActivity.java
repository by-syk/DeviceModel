package com.by_syk.devicemodel;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.by_syk.devicemodel.util.C;
import com.by_syk.devicemodel.util.ExtraUtil;
import com.by_syk.lib.toast.GlobalToast;

public class MainActivity extends Activity {
    private TextView tvInfo;
    private TextView tvDaysSysVer;
    private TextView tvDaysSysFlash;

    private int sdk1_release_days = 0;
    private int sdk_release_days = 0;

    private int sys_flash_days = 0;

    private int days_change = 0;

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
        tvDaysSysVer = (TextView) findViewById(R.id.tv_days_sys_ver);
        tvDaysSysFlash = (TextView) findViewById(R.id.tv_days_sys_flash);

        tvDaysSysVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_cancelled = true;

                GlobalToast.showToast(MainActivity.this, ExtraUtil.getSdkReleaseDateStr());
            }
        });
        tvDaysSysFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalToast.showToast(MainActivity.this, ExtraUtil.getSysFlashDateStr());
            }
        });

        runnable = new Runnable() {
            @Override
            public void run() {
                tvDaysSysVer.setText(String.valueOf(sdk1_release_days));

                if (is_cancelled || sdk1_release_days == sdk_release_days) {
                    tvDaysSysVer.setText(String.valueOf(sdk_release_days));
                    if (sys_flash_days > 0) {
                        tvDaysSysFlash.setText(String.valueOf(sys_flash_days));
                    }
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
        if (C.SDK < 11 || Build.MODEL.toLowerCase().contains(Build.DEVICE.toLowerCase())) {
            return;
        }

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(Build.DEVICE);
        }
    }

    private void showInfo() {
        int[] resolution = ExtraUtil.getRealResolution(this);

        String info = getString(R.string.info,
                Build.MODEL,
                Build.BRAND,
                Build.MANUFACTURER,
                Build.VERSION.SDK_INT,
                Build.VERSION.RELEASE,
                resolution[0], resolution[1]);

        tvInfo.setText(info);

        Log.i(C.LOG_TAG, info);

        sys_flash_days = ExtraUtil.getSysFlashDays();

        sdk1_release_days = ExtraUtil.getSdkReleaseDays(Build.VERSION_CODES.BASE, null);
        sdk_release_days = ExtraUtil.getSdkReleaseDays();
        if (sdk_release_days > 0 && sdk1_release_days >= sdk_release_days) {
            tvDaysSysVer.setText(String.valueOf(sdk1_release_days));

            handler.postDelayed(runnable, 400);
        }
    }

    private void viewSourceCode() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.url_sc_github)));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_view_sc:
                viewSourceCode();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
