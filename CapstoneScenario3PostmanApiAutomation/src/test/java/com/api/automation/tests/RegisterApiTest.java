package com.api.automation.tests;

import com.api.automation.utils.ConfigReader;
import com.api.automation.utils.ExcelReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegisterApiTest {
    private static final String API_KEY = ConfigReader.get("api.key");

    private static final Logger logger = LogManager.getLogger(RegisterApiTest.class);

    @DataProvider(name = "credentialsProvider")
    public Object[][] credentialsProvider() throws IOException {
        String filePath = "src/test/resources/credentials.xlsx";
        String sheetName = "Sheet1";
        return ExcelReader.readMultipleCredentials(filePath, sheetName);
    }

    @Test(dataProvider = "credentialsProvider")
    public void testRegisterApi(String email, String password) {
        RestAssured.baseURI = "https://reqres.in/api";
        String requestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", API_KEY)
                .body(requestBody)
                .post("/register");

        logger.info("Status Code: {}", response.getStatusCode());
        logger.info("Response: {}", response.getBody().asPrettyString());

        if (response.statusCode() == 200) {
            int id = response.jsonPath().getInt("id");
            logger.info("ID: {}", id);
            String token = response.jsonPath().getString("token");
            logger.info("Token: {}", token);
            Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");
            Assert.assertTrue(id > 0, "ID should be a positive integer");
            Assert.assertNotNull(token, "Token should not be null");
        } else {
            String error = response.jsonPath().getString("error");
            logger.error("Error: {}", error);
            Assert.assertEquals(response.statusCode(), 400, "Status code should be 400 for negative cases");
            Assert.assertNotNull(error, "Error message should not be null for negative cases");
        }
    }
}
