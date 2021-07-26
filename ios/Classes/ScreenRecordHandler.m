//
//  ScreenRecordHandler.m
//  flutter_secure_screen
//
//  Created by Orhan on 2021/7/22.
//
#import <Flutter/Flutter.h>
#import "ScreenRecordHandler.h"

@implementation ScreenRecordHandler
{
    FlutterEventSink _eventSink;
}

- (FlutterError* _Nullable)onListenWithArguments:(id _Nullable)arguments
                                       eventSink:(FlutterEventSink)events
{
    _eventSink = events;
    if (@available(iOS 11.0, *)) {
        //检测到当前设备录屏状态发生变化
        [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(screenRecord) name:UIScreenCapturedDidChangeNotification  object:nil];
    } else {
        // Fallback on earlier versions
    }
    
    return nil;
}

- (FlutterError* _Nullable)onCancelWithArguments:(id _Nullable)arguments
{
    _eventSink = nil;
    
    
    if (@available(iOS 11.0, *)) {
        [[NSNotificationCenter defaultCenter] removeObserver:self name:UIScreenCapturedDidChangeNotification object:nil];
    } else {
        // Fallback on earlier versions
    }
    
    return nil;
}


-(void)screenRecord
{
    _eventSink(@"");
}

@end
