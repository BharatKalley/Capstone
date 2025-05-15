package com.api.automation.tests;

import com.api.automation.utils.ExcelReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegisterApiTest {

    @DataProvider(name = "credentialsProvider")
    public Object[][] credentialsProvider() throws IOException {
        String filePath = "src/test/resources/credentials.xlsx";
        String sheetName = "Sheet1";
        return new Object[][] {
                ExcelReader.readCredentials(filePath, sheetName, 1),
                ExcelReader.readCredentials(filePath, sheetName, 2)
        };
    }

    @Test(dataProvider = "credentialsProvider")
    public void testRegisterApi(String email, String password) {
        RestAssured.baseURI = "https://reqres.in/api/register";

        String requestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post();

        if (response.statusCode() == 200) {
            int id = response.jsonPath().getInt("id");
            String token = response.jsonPath().getString("token");

            Assert.assertTrue(id > 0, "ID should be a positive integer");
            Assert.assertNotNull(token, "Token should not be null");
        } else {
            String error = response.jsonPath().getString("error");
            Assert.assertNotNull(error, "Error message should not be null for negative cases");
        }
    }

}
