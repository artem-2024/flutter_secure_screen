#import "FlutterSecureScreenPlugin.h"
#import "ScreenShotsHandler.h"
#import "ScreenRecordHandler.h"


@implementation FlutterSecureScreenPlugin


+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
    
    // 截屏动作
    FlutterEventChannel* _screenShotsEventChannel = [FlutterEventChannel
                                                     eventChannelWithName:@"com.orhan.FlutterSecureScreen/screenShotsEventChannel"
                                                     binaryMessenger:[registrar messenger]];
    
    [_screenShotsEventChannel setStreamHandler:[ScreenShotsHandler new]];
    
    // 录屏动作
    FlutterEventChannel* _screenRecordEventChannel = [FlutterEventChannel
                                                      eventChannelWithName:@"com.orhan.FlutterSecureScreen/screenRecordEventChannel"
                                                      binaryMessenger:[registrar messenger]];
    
    [_screenRecordEventChannel setStreamHandler:[ScreenRecordHandler new]];
    
}

@end
