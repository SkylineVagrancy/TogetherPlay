package com.pzh.yiqiplay.ui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Spinner;

import com.mob.mobapi.API;
import com.mob.mobapi.APICallback;
import com.mob.mobapi.MobAPI;
import com.mob.mobapi.apis.Weather;
import com.pzh.yiqiplay.R;
import com.pzh.yiqiplay.common.BaseUI;

import java.util.Map;

/**
 * Created by pzh on 16/4/29.
 */
public class WeatherUI extends BaseUI {
    private Spinner citys;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Weather api= (Weather) MobAPI.getAPI(Weather.NAME);
        api.getSupportedCities(new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                System.out.println("pzh map="+map.toString());
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });
    }
    public void getData(String cityName){
        Weather api= (Weather) MobAPI.getAPI(Weather.NAME);
        api.queryByCityName(cityName, new APICallback() {
            @Override
            public void onSuccess(API api, int i, Map<String, Object> map) {
                System.out.println("pzh map="+map.toString());
            }

            @Override
            public void onError(API api, int i, Throwable throwable) {

            }
        });
    }

}
