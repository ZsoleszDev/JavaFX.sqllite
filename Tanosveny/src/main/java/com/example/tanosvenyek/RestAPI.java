package com.example.tanosvenyek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestAPI {
    private static final String TOKEN = "Bearer c62375f96cc6065d6512c4041d01dcf516712f748925b44440baff4895ef9be4";

    public static String get (String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if (con != null) {
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("'GET' request is sent to URL : " + url + "\nResponse Code: " + responseCode);
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        }
        return "";
    }

    public static String post(String url, String jsonString) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if (con != null) {
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", TOKEN);
            con.setDoOutput(true);
            con.setDoInput(true);
            OutputStream os = con.getOutputStream();
            os.write(jsonString.getBytes());
            os.flush();
            int responseCode = con.getResponseCode();
            System.out.println("'POST' request is sent to URL : " + url + "\nResponse Code: " + responseCode);
            System.out.println("jsonString: " + jsonString);
            if (responseCode == 200 || responseCode == 202) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response);
                return response.toString();
            }
        }
        return "";
    }

    public static String put(String url, String jsonString) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if (con != null) {
            con.setRequestMethod("PUT");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", TOKEN);
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            os.write(jsonString.getBytes());
            os.flush();
            int responseCode = con.getResponseCode();
            System.out.println("'PUT' request is sent to URL : " + url + "\nResponse Code: " + responseCode);
            System.out.println("jsonString: " + jsonString);
            if (responseCode == 200) {
                System.out.println("'PUT' Request is Succeeded Http Status Code: " + responseCode);
            }
        }
        return "";
    }

    public static String delete(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        if (con != null) {
            con.setRequestMethod("DELETE");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", TOKEN);
            con.setDoOutput(true);
            int responseCode = con.getResponseCode();
            System.out.println("'DELETE' request is sent to URL : " + url + "\nResponse Code: " + responseCode);
            if (responseCode == 200) {
                System.out.println("'DELETE' Request is Succeeded Http Status Code: " + responseCode);
            }
        }
        return "";
    }
}
