import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

///
/// 截屏和录屏的动作监听（TODO 暂只支持iOS）、
/// 禁止或允许android截屏和录屏
///
class FlutterSecureScreen {
  static FlutterSecureScreen singleton = FlutterSecureScreen._internal();

  factory FlutterSecureScreen() {
    return singleton;
  }

  FlutterSecureScreen._internal() {
    if (defaultTargetPlatform == TargetPlatform.iOS) {
      _screenShotsEventChannel = const EventChannel(
          'com.orhan.FlutterSecureScreen/screenShotsEventChannel');
      _screenRecordEventChannel = const EventChannel(
          'com.orhan.FlutterSecureScreen/screenRecordEventChannel');
    } else if (defaultTargetPlatform == TargetPlatform.android) {
      _methodChannel =
          const MethodChannel('com.orhan.FlutterSecureScreen/methodChannel');
    }
  }

  /// 方法桥
  MethodChannel _methodChannel;

  /// 截屏事件桥
  EventChannel _screenShotsEventChannel;

  /// 录屏事件桥
  EventChannel _screenRecordEventChannel;

  /// 截屏动作
  Stream<void> _onScreenShots;

  /// 录屏动作
  Stream<void> _onScreenRecord;

  ///
  /// 获取监听截屏动作的[Stream]。用法：
  ///
  /// ```
  /// FlutterSecureScreen.singleton.onScreenShots.listen((event) {
  ///   print("监听到了截屏动作");
  /// });
  /// ```
  Stream<dynamic> get onScreenShots {
    if (_onScreenShots == null) {
      _onScreenShots = _screenShotsEventChannel?.receiveBroadcastStream();
    }
    return _onScreenShots;
  }

  ///
  /// 获取监听录屏动作的[Stream]。用法：
  ///
  /// ```
  /// FlutterSecureScreen.singleton.onScreenRecord.listen((event) {
  ///   print("监听到了录屏动作");
  /// });
  /// ```
  Stream<dynamic> get onScreenRecord {
    if (_onScreenRecord == null) {
      _onScreenRecord = _screenRecordEventChannel?.receiveBroadcastStream();
    }
    return _onScreenRecord;
  }

  /// 是否禁用设备截屏或录屏 只支持Android
  Future<void> setAndroidScreenSecure(bool isSecure) async {
    if (defaultTargetPlatform == TargetPlatform.android) {
      return await _methodChannel
          ?.invokeMethod("setAndroidScreenSecure", {"isSecure": isSecure});
    } else {
      debugPrint('FlutterSecureScreen 仅Android平台支持禁用截屏和录屏');
      return null;
    }
  }
}
