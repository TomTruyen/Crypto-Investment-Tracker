import 'package:cryptotracker/Wrapper.dart';
import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'CryptoTracker',
      theme: ThemeData(
        backgroundColor: const Color(0xFF1F2034),
        scaffoldBackgroundColor: const Color(0xFF1F2034),
        textTheme: GoogleFonts.notoSansTextTheme(
          Theme.of(context).textTheme.apply(
                bodyColor: Colors.white,
                displayColor: Colors.white,
              ),
        ),
        // textTheme: Theme.of(context).textTheme.apply(
        //
        //     ),
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => Wrapper(),
      },
    );
  }
}
