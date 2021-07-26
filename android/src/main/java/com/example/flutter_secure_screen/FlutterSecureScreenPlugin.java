package com.example.flutter_secure_screen;

import android.app.Activity;
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

//    private EventChannel _screenShotsEventChannel;
//    private EventChannel _screenRecordEventChannel;
//
//    private EventChannel.EventSink _screenShotsEventSink;
//    private EventChannel.EventSink _screenRecordEventSink;

//    private ScreenShotHelper _screenShotHelper;

//    private Context _applicationContext;


    private MethodChannel _methodChannel;

    private Activity mActivity;

    private Boolean isSecureScreen;


    private void setAndroidScreenSecure() {
        if (isSecureScreen == null) return;
        if (isSecureScreen) {
            mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            System.out.println("FlutterSecureScreenPlugin 禁用截屏录屏");
        } else {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            System.out.println("FlutterSecureScreenPlugin 允许截屏录屏");
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

//        _applicationContext = flutterPluginBinding.getApplicationContext();

//        // 截屏
//        _screenShotsEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "com.orhan.FlutterSecureScreen/screenShotsEventChannel");
//        _screenShotsEventChannel.setStreamHandler(new EventChannel.StreamHandler() {
//            @Override
//            public void onListen(Object arguments, EventChannel.EventSink events) {
//                System.out.println("开始监听android截屏");
//                _screenShotsEventSink = events;
//                _screenShotHelper = ScreenShotHelper.newInstance(_applicationContext);
//                _screenShotHelper.setScreenShotListener(new ScreenShotHelper.OnScreenShotListener() {
//                    @Override
//                    public void onShot(@Nullable String data) {
//                        System.out.println("android监听到截屏 "+data);
//                        _screenShotsEventSink.success(data);
//                    }
//                });
//            }
//            @Override
//            public void onCancel(Object arguments) {
//                System.out.println("取消监听android截屏");
//                _screenShotsEventSink = null;
//                if(_screenShotHelper != null){
//                    _screenShotHelper.stopListener();
//                }
//            }
//        });
//
//        // 录屏
//        _screenRecordEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "com.orhan.FlutterSecureScreen/screenRecordEventChannel");
//        _screenRecordEventChannel.setStreamHandler(new EventChannel.StreamHandler() {
//            @Override
//            public void onListen(Object arguments, EventChannel.EventSink events) {
//                System.out.println("开始监听android录屏");
//                _screenRecordEventSink = events;
//            }
//
//            @Override
//            public void onCancel(Object arguments) {
//                System.out.println("取消监听android录屏");
//                _screenRecordEventSink = null;
//            }
//        });
    }


    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        _methodChannel.setMethodCallHandler(null);
        _methodChannel = null;
//        if (_screenShotsEventChannel != null) {
//            _screenShotsEventChannel.setStreamHandler(null);
//            _screenShotsEventSink=null;
//        }
//        if (_screenRecordEventChannel != null) {
//            _screenRecordEventChannel.setStreamHandler(null);
//            _screenRecordEventSink =null;
//        }
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
