import 'package:cryptotracker/Globals.dart' as globals;
import 'package:cryptotracker/screens/App.dart';
import 'package:cryptotracker/screens/Authentication.dart';
import 'package:flutter/material.dart';

class Wrapper extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    if (globals.repository.token != null) {
      return Scaffold(
        body: App(),
      );
    } else {
      return Scaffold(
        body: Authentication(),
      );
    }
  }
}
