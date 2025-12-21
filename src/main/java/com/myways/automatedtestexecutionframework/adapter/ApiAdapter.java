//package com.myways.automatedtestexecutionframework.adapter;
//
//import com.myways.automatedtestexecutionframework.entity.TestExecution;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//
//import java.nio.file.Path;
//import java.time.LocalDateTime;
//
//public class ApiAdapter {
//    public static void runApiTest(String payload, TestExecution execution, Path reportsDir) {
//        execution.setStartedAt(LocalDateTime.now());
//        try {
//            String[] parts = payload.split("\\s+", 2);
//            String method = parts[0].toUpperCase();
//            String url = parts.length > 1 ? parts[1] : parts[0];
//
//            Response response;
//            if ("GET".equals(method)) {
//                response = RestAssured.get(url);
//            } else if ("POST".equals(method)) {
//                response = RestAssured.given().body("").post(url);
//            } else {
//                response = RestAssured.get(url);
//            }
//
//            int code = response.getStatusCode();
//            if (code >= 200 && code < 300) execution.setStatus("PASSED");
//            else {
//                execution.setStatus("FAILED");
//                execution.setErrorMessage("Status code: " + code);
//            }
//        } catch (Exception ex) {
//            execution.setStatus("FAILED");
//            execution.setErrorMessage(ex.getMessage());
//        } finally {
//            execution.setCompletedAt(LocalDateTime.now());
//        }
//    }
//}



package com.myways.automatedtestexecutionframework.adapter;

import com.myways.automatedtestexecutionframework.entity.TestExecution;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class ApiAdapter {

    public static void runApiTest(String payload, TestExecution execution, Path reportsDir) {

        execution.setStartedAt(LocalDateTime.now());

        try {
            // payload = "GET https://httpbin.org/status/200"
            String[] parts = payload.split("\\s+", 2);
            String method = parts[0].toUpperCase();
            String url = parts[1];

            Response response;

            switch (method) {
                case "POST":
                    response = RestAssured.post(url);
                    break;
                case "PUT":
                    response = RestAssured.put(url);
                    break;
                case "DELETE":
                    response = RestAssured.delete(url);
                    break;
                default:
                    response = RestAssured.get(url);
            }

            int statusCode = response.getStatusCode();

            if (statusCode >= 200 && statusCode < 300) {
                execution.setStatus("PASSED");
            } else {
                execution.setStatus("FAILED");
                execution.setErrorMessage("Status code: " + statusCode);
            }

        } catch (Exception e) {
            execution.setStatus("FAILED");
            execution.setErrorMessage(e.getMessage());
        }

        execution.setCompletedAt(LocalDateTime.now());
    }
}
