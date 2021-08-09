import 'dart:async';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter_secure_screen/flutter_secure_screen.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: '/',
      home: HomeDemo(),
    );
  }
}

class HomeDemo extends StatefulWidget {
  const HomeDemo({Key? key}) : super(key: key);

  @override
  _HomeDemoState createState() => _HomeDemoState();
}

class _HomeDemoState extends State<HomeDemo> {
  /// 录屏动作 暂只支持iOS
  StreamSubscription<void>? _screenRecordListen;

  /// 截屏动作 暂只支持iOS
  StreamSubscription<void>? _screenShotsListen;

  String _platformStateText = '未知设备';
  String _secureState = '允许截屏录屏';

  @override
  void initState() {
    super.initState();
    _screenRecordListen =
        FlutterSecureScreen.singleton.onScreenRecord?.listen(_onScreenRecord);
    _screenShotsListen =
        FlutterSecureScreen.singleton.onScreenShots?.listen(_onScreenShots);

    if (defaultTargetPlatform == TargetPlatform.android) {
      _platformStateText = '当前为android设备';
    } else if (defaultTargetPlatform == TargetPlatform.iOS) {
      _platformStateText = '当前为iOS设备';
    }
  }

  @override
  void dispose() {
    _screenRecordListen?.cancel();
    _screenShotsListen?.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('FlutterSecureScreen'),
      ),
      body: Center(
        child: Column(
          children: [
            const Spacer(),
            Text('$_platformStateText : $_secureState'),
            const Spacer(),
            Text('请试着用手机截屏或录屏\n（有对应提示则代表检测成功）'),
            const Spacer(),
            Visibility(
              visible: defaultTargetPlatform == TargetPlatform.android,
              child: Expanded(
                child: TextButton(
                  onPressed: () async {
                    await FlutterSecureScreen.singleton
                        .setAndroidScreenSecure(true);
                    setState(() {
                      _secureState = '禁止截屏录屏';
                    });
                  },
                  child: Text('禁用Android录屏截屏'),
                ),
              ),
            ),
            Visibility(
              visible: defaultTargetPlatform == TargetPlatform.android,
              child: Expanded(
                child: TextButton(
                    onPressed: () async {
                      FlutterSecureScreen.singleton.setAndroidScreenSecure(false);
                      setState(() {
                        _secureState = '允许截屏录屏';
                      });
                    },
                    child: Text('允许Android录屏截屏')),
              ),
            ),
            const Spacer(),
          ],
        ),
      ),
    );
  }

  void _onScreenRecord(dynamic event) {
    showModalBottomSheet(
        context: context,
        builder: (_) => Container(
            height: 200, child: Center(child: Text('检测到手机在录屏 $event'))));
  }

  void _onScreenShots(dynamic event) {
    showModalBottomSheet(
        context: context,
        builder: (_) => Container(
            height: 200, child: Center(child: Text('检测到手机在截屏 $event'))));
  }
}
