import 'package:cryptotracker/Globals.dart';
import 'package:flutter/material.dart';

class App extends StatelessWidget {
  final GlobalRepository repository = GlobalRepository();

  @override
  Widget build(BuildContext context) {
    return Container(child: Text("Value of token: " + repository.token));
  }
}
