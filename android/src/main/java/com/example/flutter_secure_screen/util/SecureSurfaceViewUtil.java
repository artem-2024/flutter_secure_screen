package com.example.flutter_secure_screen.util;

import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;


public class SecureSurfaceViewUtil {
    private SecureSurfaceViewUtil(){}

    public static boolean checkSurfaceViewIsSecure(ViewGroup contentView) {
        if (contentView == null) return true;
        if (isEmptyContainer((View) contentView)) {
            return false;
        } else {
            View splashView = contentView.getChildAt(0);
            if (isEmptyContainer(splashView)) {
                return false;
            } else {
                View flutterView = ((ViewGroup) splashView).getChildAt(0);
                if (isEmptyContainer(flutterView)) {
                    return false;
                } else {
                    View surfaceView = ((ViewGroup) flutterView).getChildAt(0);
                    if (!(surfaceView instanceof SurfaceView)) {
                        return false;
                    }else {
                        return true;
                    }
//                    //禁止被录屏
//                    else {
//                        ((SurfaceView) surfaceView).setSecure(true);
//                        mActivity.getWindow().setFlags(8192, 8192);
//                        return true;
//                    }
                }
            }
        }

    }

    private static boolean isEmptyContainer(View view) {
        if (!(view instanceof ViewGroup)) {
            System.out.println("liuaohan check view = "+view.toString()+" !(view instanceof ViewGroup) = "+(!(view instanceof ViewGroup)));
            return true;
        } else {
            System.out.println("liuaohan check view = "+view.toString()+" ((ViewGroup) view).getChildCount() < 1 = "+(((ViewGroup) view).getChildCount() < 1));
            return ((ViewGroup) view).getChildCount() < 1;
        }
    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    public static  void tmp(Context context){
//        AudioManager mAudioManger = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
//        SystemRecordingCallback mRecordingCallback = new SystemRecordingCallback();
//        mAudioManger.registerAudioRecordingCallback(mRecordingCallback,null);
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private static class SystemRecordingCallback extends AudioManager.AudioRecordingCallback {
//        private final String TAG = "CaptureService.SystemRecordingCallback";
//        @Override
//        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> configs) {
//            super.onRecordingConfigChanged(configs);
//            int activeSize = configs.size();
//            if (activeSize == 0) {
//                //这时候，没有在录音的app.你自己的就可以启动了
//            } else {
//                for (int i = 0; i < activeSize; i++) {
//                    AudioRecordingConfiguration config = configs.get(i);
//                    int source = config.getClientAudioSource();
//                    switch (source) {
//                        case MediaRecorder.AudioSource.MIC:
//                            System.out.println("liuaohan  检测到其他app打开了录音");
//                            break;
//                    }
//                }
//            }
//        }
//    }
}
