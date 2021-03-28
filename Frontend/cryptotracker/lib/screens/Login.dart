import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class Login extends StatefulWidget {
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  bool isHoverForgotPassword = false;
  bool isHoverSignUp = false;
  bool obscurePassword = true;

  TextEditingController emailController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();

  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();

    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Container(
          width: MediaQuery.of(context).size.width / 3,
          padding: EdgeInsets.all(32.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.end,
            children: <Widget>[
              Flexible(
                child: Container(
                  alignment: Alignment.center,
                  width: double.infinity,
                  child: Text("[LOGO HERE]"),
                ),
              ),
              Flexible(
                child: Container(
                  width: double.infinity,
                  alignment: Alignment.center,
                  child: Text(
                    "Welcome Back",
                    style: TextStyle(
                      fontSize:
                          Theme.of(context).textTheme.bodyText2.fontSize * 2.5,
                      fontWeight: FontWeight.w100,
                    ),
                  ),
                ),
              ),
              Flexible(
                child: TextFormField(
                  textInputAction: TextInputAction.next,
                  textAlign: TextAlign.center,
                  cursorColor: Colors.white,
                  cursorWidth: 2.0,
                  controller: emailController,
                  decoration: InputDecoration(
                    contentPadding: EdgeInsets.all(12.0),
                    isDense: true,
                    focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: Colors.white,
                        width: 2.0,
                      ),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: Colors.white,
                        width: 2.0,
                      ),
                    ),
                    hintText: "Email",
                    hintStyle: TextStyle(
                      color: Colors.grey[700],
                    ),
                  ),
                  style: TextStyle(
                    color: Colors.white,
                  ),
                  onChanged: (String value) {},
                ),
              ),
              SizedBox(height: 25.0),
              Flexible(
                child: TextFormField(
                  textAlign: TextAlign.center,
                  cursorColor: Colors.white,
                  cursorWidth: 2.0,
                  controller: passwordController,
                  obscureText: obscurePassword,
                  decoration: InputDecoration(
                    contentPadding: EdgeInsets.all(12.0),
                    isDense: true,
                    focusedBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: Colors.white,
                        width: 2.0,
                      ),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: Colors.white,
                        width: 2.0,
                      ),
                    ),
                    hintText: "Password",
                    hintStyle: TextStyle(
                      color: Colors.grey[700],
                    ),
                  ),
                  style: TextStyle(
                    color: Colors.white,
                  ),
                  onChanged: (String value) {},
                ),
              ),
              SizedBox(height: 12.5),
              Flexible(
                child: MouseRegion(
                  cursor: SystemMouseCursors.click,
                  child: GestureDetector(
                    child: Text(
                      "Forgot your password?",
                      style: TextStyle(
                        decoration: isHoverForgotPassword
                            ? TextDecoration.underline
                            : null,
                      ),
                    ),
                    onTap: () {
                      print("FORGOT PASSWORD");
                    },
                  ),
                  onEnter: (PointerEvent event) {
                    setState(() {
                      isHoverForgotPassword = true;
                    });
                  },
                  onExit: (PointerEvent event) {
                    setState(() {
                      isHoverForgotPassword = false;
                    });
                  },
                ),
              ),
              SizedBox(height: 50.0),
              Flexible(
                child: Container(
                  width: double.infinity,
                  height: 50.0,
                  child: OutlinedButton(
                    child: Text("Sign in"),
                    style: OutlinedButton.styleFrom(
                      primary: Colors.white,
                      side: BorderSide(
                        color: Colors.white,
                        width: 2.0,
                      ),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(16.0),
                      ),
                    ),
                    onPressed: () async {
                      print("LOGIN");
                    },
                  ),
                ),
              ),
              SizedBox(height: 12.5),
              Flexible(
                child: Container(
                  height: 20.0,
                  alignment: Alignment.center,
                  width: double.infinity,
                  child: MouseRegion(
                    cursor: SystemMouseCursors.click,
                    child: GestureDetector(
                      child: Text(
                        "Need an account? Sign up",
                        style: TextStyle(
                          decoration:
                              isHoverSignUp ? TextDecoration.underline : null,
                        ),
                      ),
                      onTap: () {
                        print("SIGN UP");
                      },
                    ),
                    onEnter: (PointerEvent event) {
                      setState(() {
                        isHoverSignUp = true;
                      });
                    },
                    onExit: (PointerEvent event) {
                      setState(() {
                        isHoverSignUp = false;
                      });
                    },
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
