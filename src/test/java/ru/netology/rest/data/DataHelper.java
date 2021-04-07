package ru.netology.rest.data;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.val;

import java.util.Locale;

public class DataHelper {
    static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {

    }

    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void makeRequest(RegistrationDto registration) {
        RestAssured.given()
                .spec(requestSpecification)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationDto getRegisteredUser(String status) {
        val login = faker.name().fullName();
        val password = faker.internet().password();
        val registrarion = new RegistrationDto(login, password, status);
        makeRequest(registrarion);
        return registrarion;
    }

    public static RegistrationDto getUserBlocked(String status) {
        val login = faker.name().fullName();
        val password = faker.internet().password();
        val registrarion = new RegistrationDto(login, password, status);
        makeRequest(registrarion);
        return registrarion;
    }

    public static RegistrationDto getWrongPassword() {
        val login = faker.name().fullName();
        makeRequest(new RegistrationDto(login, faker.internet().password(), "active"));
        return new RegistrationDto(login, faker.internet().password(), "active");
    }

    public static RegistrationDto getWrongLogin() {
        val passwrod = faker.internet().password();
        makeRequest(new RegistrationDto(faker.name().fullName(), passwrod, "active"));
        return new RegistrationDto(faker.name().fullName(), passwrod, "active");
    }

}
