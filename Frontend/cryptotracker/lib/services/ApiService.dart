import 'dart:convert';
import 'dart:io';

import 'package:http/http.dart' as http;

class ApiService {
  static Future<Map<String, dynamic>> login(
    String email,
    String password,
  ) async {
    Uri url = Uri.parse('http://localhost:8888/login');
    http.Response response = await http.post(url,
        body: jsonEncode({
          'email': email,
          'password': password,
        }),
        headers: {
          HttpHeaders.acceptHeader: "application/json",
          HttpHeaders.contentTypeHeader: "application/json",
        });

    Map<String, dynamic> json = jsonDecode(response.body);

    return json;
  }
}
