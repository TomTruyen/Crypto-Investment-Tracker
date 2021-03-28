import 'package:cryptotracker/screens/Login.dart';
import 'package:cryptotracker/screens/Register.dart';
import 'package:flutter/material.dart';

class Authentication extends StatefulWidget {
  @override
  _AuthenticationState createState() => _AuthenticationState();
}

class _AuthenticationState extends State<Authentication> {
  bool _isLogin = true;

  void updatePage(bool isLogin) {
    setState(() {
      _isLogin = isLogin;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _isLogin
          ? Login(updatePage: updatePage)
          : Register(updatePage: updatePage),
    );
  }
}
