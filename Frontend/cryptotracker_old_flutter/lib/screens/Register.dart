import 'package:cryptotracker/services/ApiService.dart';
import 'package:cryptotracker/utils/Utils.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

class Register extends StatefulWidget {
  final Function updatePage;

  Register({this.updatePage});

  @override
  _RegisterState createState() => _RegisterState();
}

class _RegisterState extends State<Register> {
  bool isRegistering = false;

  String error = "";
  String success = "";

  bool isHoverSignIn = false;
  bool obscurePassword = true;

  bool emailError = false;
  bool passwordError = false;

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

  bool validate() {
    if (!Utils.isValidEmail(emailController.text)) {
      setState(() {
        error = "Email is invalid";
        emailError = true;
        passwordError = false;
      });

      return false;
    }

    if (!Utils.isValidPassword(passwordController.text)) {
      setState(() {
        error =
            "Password must be at least 8 characters long and contain uppercase, lowercase and digits";
        emailError = false;
        passwordError = true;
      });

      return false;
    }

    if (passwordController.text != repeatPasswordController.text) {
      setState(() {
        error = "Passwords don't match";
        emailError = false;
        passwordError = true;
      });

      return false;
    }

    setState(() {
      error = "";
      emailError = false;
      passwordError = false;
    });

    return true;
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
                        color: emailError ? Color(0xFFDA2C43) : Colors.white,
                        width: 2.0,
                      ),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: emailError ? Color(0xFFDA2C43) : Colors.white,
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
                        color: passwordError ? Color(0xFFDA2C43) : Colors.white,
                        width: 2.0,
                      ),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: passwordError ? Color(0xFFDA2C43) : Colors.white,
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
                        color: passwordError ? Color(0xFFDA2C43) : Colors.white,
                        width: 2.0,
                      ),
                    ),
                    enabledBorder: UnderlineInputBorder(
                      borderSide: BorderSide(
                        color: passwordError ? Color(0xFFDA2C43) : Colors.white,
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
              if (error != "" || success != "") SizedBox(height: 50.0),
              if (error != "")
                Flexible(
                  child: Container(
                    height: 60.0,
                    alignment: Alignment.center,
                    width: double.infinity,
                    child: Text(
                      error,
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        color: Color(0xFFDA2C43),
                      ),
                    ),
                  ),
                ),
              if (success != "" && error == "")
                Flexible(
                  child: Container(
                    height: 60.0,
                    alignment: Alignment.center,
                    width: double.infinity,
                    child: Text(
                      success,
                      textAlign: TextAlign.center,
                      style: TextStyle(
                        color: Colors.green,
                      ),
                    ),
                  ),
                ),
              SizedBox(height: 50.0),
              Flexible(
                child: Container(
                  width: double.infinity,
                  height: 50.0,
                  child: OutlinedButton(
                    child: isRegistering
                        ? SizedBox(
                            height: 20.0,
                            width: 20.0,
                            child: CircularProgressIndicator(
                              valueColor: new AlwaysStoppedAnimation<Color>(
                                  Colors.white),
                              strokeWidth: 2.0,
                            ),
                          )
                        : Text("Sign up"),
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
                    onPressed: isRegistering
                        ? null
                        : () async {
                            if (validate()) {
                              setState(() {
                                isRegistering = true;
                              });

                              Future.delayed(Duration(seconds: 1), () async {
                                String email = emailController.text;
                                String password = passwordController.text;

                                Map<String, dynamic> response =
                                    await ApiService.register(email, password);

                                if (response['success']) {
                                  setState(() {
                                    success = response['message'];
                                  });
                                } else {
                                  String _error =
                                      "Something went wrong. Please try again";

                                  if (response['message'] != null &&
                                      response['message'] != '')
                                    _error = response['message'];

                                  setState(() {
                                    error = _error;
                                    isRegistering = false;
                                  });
                                }
                              });
                            }
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
