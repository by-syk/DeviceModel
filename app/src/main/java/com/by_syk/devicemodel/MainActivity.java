package com.by_syk.devicemodel;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.by_syk.devicemodel.util.C;
import com.by_syk.devicemodel.util.ExtraUtil;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showInfo();
    }

    private void showInfo() {
        int[] resolution = ExtraUtil.getRealResolution(this);

        String info = getString(R.string.info,
                Build.MODEL,
                Build.BRAND,
                Build.MANUFACTURER,
                Build.VERSION.SDK_INT,
                resolution[0],
                resolution[1]);

        ((TextView) findViewById(R.id.tv_info))
                .setText(info);

        Log.i(C.LOG_TAG, info);
    }
}
