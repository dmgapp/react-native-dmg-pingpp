package com.dmgapp.rnpingpp;

import android.app.Activity;
import android.content.Intent;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;
import com.pingplusplus.android.PaymentActivity;

/**
 * Created by scott on 16/9/8.
 */
public class RnDmgPingppModule extends  ReactContextBaseJavaModule implements ActivityEventListener {
    private static final int REQUEST_CODE_PAYMENT = 2;
    public RnDmgPingppModule(ReactApplicationContext reactContext){
        super(reactContext);

    }
    @Override
    public String getName() {
        return "RNDMGPingPP";
    }

    @Override
    public void initialize() {
        super.initialize();
        getReactApplicationContext().addActivityEventListener(this);
    }

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        getReactApplicationContext().removeActivityEventListener(this);
    }

    @ReactMethod
    public void pay(String charge) {
        Intent intent = new Intent(getCurrentActivity(), PaymentActivity.class);
        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        getCurrentActivity().startActivityForResult(intent, REQUEST_CODE_PAYMENT);

    }

    @Override
    public void onActivityResult(Activity activity , final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT && resultCode == activity.RESULT_OK) {
            this.handleResultData(data);
        }else{
                     RCTNativeAppEventEmitter emitter = getReactApplicationContext().getJSModule(RCTNativeAppEventEmitter.class);
                     WritableMap map = Arguments.createMap();
                     map.putString("errMsg", "支付宝返回错误");
                     emitter.emit("Pingpp_Resp", map);

                 }
    }

    private void handleResultData(Intent data) {
        String result = data.getExtras().getString("pay_result");
        if (result != null) {
            RCTNativeAppEventEmitter emitter = getReactApplicationContext().getJSModule(RCTNativeAppEventEmitter.class);
            WritableMap map = Arguments.createMap();
            map.putString("result", result);
            if (!result.equals("success")) {
                map.putInt("errCode", data.getExtras().getInt("code"));
                map.putString("errMsg", data.getExtras().getString("error_msg"));
                map.putString("extraMsg", data.getExtras().getString("extra_msg"));
            }
            emitter.emit("Pingpp_Resp", map);
        }
    }

    @Override
    public void onNewIntent(Intent intent){
    }

}
