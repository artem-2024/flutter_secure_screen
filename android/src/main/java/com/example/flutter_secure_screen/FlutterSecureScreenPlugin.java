package com.example.flutter_secure_screen;

import android.app.Activity;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * FlutterSecureScreenPlugin
 */
public class FlutterSecureScreenPlugin implements FlutterPlugin, ActivityAware {
    private MethodChannel _methodChannel;

    private Activity mActivity;

    private Boolean isSecureScreen;


    private void setAndroidScreenSecure() {
        if (isSecureScreen == null) return;
        if (isSecureScreen) {
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            Log.d("FlutterSecureScreen"," 禁用截屏录屏 / OFF");
        } else {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            Log.d("FlutterSecureScreen"," 允许截屏录屏 / OPEN");
        }
    }

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        _methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.orhan.FlutterSecureScreen/methodChannel");
        _methodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
                final String methodStr = call.method;
                if ("setAndroidScreenSecure".equals(methodStr)) {
                    final Boolean isSecure = call.argument("isSecure");
                    isSecureScreen = isSecure != null && isSecure;
                    setAndroidScreenSecure();
                    result.success(null);
                } else {
                    result.notImplemented();
                }
            }
        });
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        _methodChannel.setMethodCallHandler(null);
        _methodChannel = null;
    }


    // ----- ActivityAware ---
    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        mActivity = binding.getActivity();
        setAndroidScreenSecure();

    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        mActivity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        mActivity = binding.getActivity();
        setAndroidScreenSecure();
    }

    @Override
    public void onDetachedFromActivity() {
        mActivity = null;
    }
    // ----- ActivityAware ---
}
