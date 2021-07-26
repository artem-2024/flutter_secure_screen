//
//  ScreenShotsHandler.m
//  flutter_secure_screen
//
//  Created by Orhan on 2021/7/21.
//
#import <Flutter/Flutter.h>
#import "ScreenShotsHandler.h"

@implementation ScreenShotsHandler
{
    FlutterEventSink _eventSink;
}

- (FlutterError* _Nullable)onListenWithArguments:(id _Nullable)arguments
                                       eventSink:(FlutterEventSink)events
{
    _eventSink = events;
    [[NSNotificationCenter defaultCenter]addObserver:self selector:@selector(screenshots) name:UIApplicationUserDidTakeScreenshotNotification  object:nil];
    return nil;
}

- (FlutterError* _Nullable)onCancelWithArguments:(id _Nullable)arguments
{
    _eventSink = nil;
    [[NSNotificationCenter defaultCenter] removeObserver:self name:UIApplicationUserDidTakeScreenshotNotification object:nil];
    return nil;
}


-(void)screenshots
{
    _eventSink(@"");
}

@end
