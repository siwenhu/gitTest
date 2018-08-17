package com.example.location;

import android.content.Context;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Button btnOpen;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnOpen.setOnClickListener(this);
        mContext = this;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_open:
                XMapLocation.newBuilder(mContext)
                        .cacheEnable(false)
                        .build()
                        .locate(new LocateListener() {
                            @Override
                            public void onLocated(Location location) {
                                if (location != null) {
                                    if (location.getErrorCode() == Location.CODE_SUCCESS) {
                                        //可在其中解析amapLocation获取相应内容。
                                        Log.e(TAG, (location.getAddress() + location.getLatitude() + "  " + location.getLongitude()));
                                    }else {
                                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                                        Log.e("AmapError","location Error, ErrCode:"
                                                + location.getErrorCode() + ", errInfo:"
                                                + location.getErrorInfo());
                                    }
                                }

                            }
                        });
                break;
            default:
                break;
        }
    }
}
