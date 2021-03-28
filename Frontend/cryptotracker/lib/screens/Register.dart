import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class Register extends StatefulWidget {
  final Function updatePage;

  Register({this.updatePage});

  @override
  _RegisterState createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  bool isHoverSignIn = false;
  bool obscurePassword = true;

  TextEditingController emailController = new TextEditingController();
  TextEditingController passwordController = new TextEditingController();
  TextEditingController repeatPasswordController = new TextEditingController();

  @override
  void dispose() {
    emailController.dispose();
    passwordController.dispose();
    repeatPasswordController.dispose();

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
                    "Welcome",
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
              SizedBox(height: 25.0),
              Flexible(
                child: TextFormField(
                  textAlign: TextAlign.center,
                  cursorColor: Colors.white,
                  cursorWidth: 2.0,
                  controller: repeatPasswordController,
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
                    hintText: "Repeat password",
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
              SizedBox(height: 50.0),
              Flexible(
                child: Container(
                  width: double.infinity,
                  height: 50.0,
                  child: OutlinedButton(
                    child: Text("Sign up"),
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
                      print("SIGN UP");
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
                        "Already have an account? Sign in",
                        style: TextStyle(
                          decoration:
                              isHoverSignIn ? TextDecoration.underline : null,
                        ),
                      ),
                      onTap: () {
                        widget.updatePage(true);
                      },
                    ),
                    onEnter: (PointerEvent event) {
                      setState(() {
                        isHoverSignIn = true;
                      });
                    },
                    onExit: (PointerEvent event) {
                      setState(() {
                        isHoverSignIn = false;
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
