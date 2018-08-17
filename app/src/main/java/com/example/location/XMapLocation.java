package com.example.location;


import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Administrator on 2017/6/22.
 */

public class XMapLocation {


    private static final String TAG = "TAG";

    private AMapLocationClient client;

    private XMapLocation(Context context, Builder builder) {

        client = new AMapLocationClient(context);//初始化定位
        AMapLocationClientOption option = new AMapLocationClientOption();//初始化AMapLocationClientOption对象


        option.setLocationMode(builder.mode == LocationMode.Height_Accuracy ?
                AMapLocationMode.Battery_Saving :
                builder.mode == LocationMode.Battery_Saving ?
                        AMapLocationMode.Battery_Saving :
                        AMapLocationMode.Device_Sensors)
                .setOnceLocation(true)
                .setInterval(2000)
                .setNeedAddress(builder.needAddress);

        option.setMockEnable(builder.mockEnable);
        option.setWifiScan(builder.wifiScan);
        option.setHttpTimeOut(builder.timeout);
        option.setLocationCacheEnable(builder.cache);
        option.setOnceLocationLatest(true);


        client.setLocationOption(option);//给定位客户端对象设置定位参数
    }

    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }


    public void locate(final LocateListener listener) {
        //设置定位回调监听
        client.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(com.amap.api.location.AMapLocation aMapLocation) {

                listener.onLocated(new Location(aMapLocation));


                client.stopLocation();
                client.onDestroy();
            }
        });

        client.startLocation();//启动定位

    }


    public static class Builder {

        private LocationMode mode;
        private boolean needAddress;
        private boolean wifiScan;
        private boolean mockEnable;
        private long timeout;
        private boolean cache;

        private Context context;

        //设置默认参数
        private Builder(Context context) {
            this.context = context.getApplicationContext();
            mode = LocationMode.Height_Accuracy;
            needAddress = true;
            wifiScan = true;
            timeout = 20000;
            mockEnable = false;
            cache = true;
        }

        public Builder cacheEnable(boolean cache) {
            this.cache = cache;
            return this;
        }

        public XMapLocation build() {
            return new XMapLocation(context, this);
        }
    }


}
