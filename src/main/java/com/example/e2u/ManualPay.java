package com.example.e2u;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ManualPay {

  private static final String CHARSET_UTF_8 = "utf-8";
  private static final int HTTP_CONNECTION = 10000;
  private static final int HTTP_READ_TIMEOUT = 60000;
  private static final String AUTHORIZATION = "Authorization";
  private static final String CONTENT_TYPE = "Content-Type";
  private static final String CONNECTION = "Connection";
  private static final String REQUEST_METHOD = "POST";

    private static final String PAY_URL = "https://api.e2u.kr/api/pay";

  public String approval(String payKey, Pay pay) throws Exception {
    HttpURLConnection connection = getConnection(new URL(PAY_URL), payKey);
    String result = "";
    try {
      request(connection, new Gson().toJson(wrapData(pay)));
      result = response(connection);
    } finally {
      connection.disconnect();
    }

    return result;
  }

  private Map<String, Pay> wrapData(Pay pay) {
    Map<String, Pay> data = new HashMap<>();

    data.put("pay", pay);

    return data;
  }

  private void request(HttpURLConnection connection, String data) throws IOException {
    try (OutputStream os = connection.getOutputStream()) {
      byte[] input = data.getBytes(CHARSET_UTF_8);
      os.write(input, 0, input.length);
      os.flush();
    }
  }

  private String response(HttpURLConnection connection) throws IOException {
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(connection.getInputStream(), CHARSET_UTF_8))) {
      StringBuilder response = new StringBuilder();
      String responseLine = null;

      while ((responseLine = br.readLine()) != null) {
        response.append(responseLine.trim());
      }

      return response.toString();
    }
  }

  private HttpURLConnection getConnection(URL url, String payKey) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod(REQUEST_METHOD);
    connection.setRequestProperty(CONTENT_TYPE, "application/json");
    connection.setRequestProperty(CONNECTION, "close");
    connection.setRequestProperty(AUTHORIZATION, payKey);

    connection.setConnectTimeout(HTTP_CONNECTION);
    connection.setReadTimeout(HTTP_READ_TIMEOUT);

    connection.setUseCaches(false);
    connection.setDoInput(true);
    connection.setDoOutput(true);

    return connection;
  }
}
