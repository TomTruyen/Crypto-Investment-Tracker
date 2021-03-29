import 'package:cryptotracker/Globals.dart' as globals;
import 'package:flutter/material.dart';

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container(
        child: Text("Value of token: " + globals.repository.token));
  }
}
