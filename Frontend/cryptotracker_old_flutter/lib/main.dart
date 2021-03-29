import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

import 'Wrapper.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
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
      ),
      initialRoute: '/',
      routes: {
        '/': (context) => Wrapper(),
      },
    );
  }
}
