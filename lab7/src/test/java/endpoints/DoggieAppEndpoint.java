package endpoints;

import config.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Dog;

import java.util.HashMap;
import java.util.Map;

class UserReq {
    public String email;
    public String passwordHash;

    UserReq(String email, String password){
        this.email = email;
        this.passwordHash = password;
    }
}

public class DoggieAppEndpoint {

    public Response createUser(String email, String password) {
        return given()
                .body(new UserReq(email, password))
                .when()
                .post(Config.CREATE_USER)
                .then().extract().response();
    }

    public Response getUserById(String id) {
        return given()
                .pathParam("id", id)
                .when()
                .get(Config.USER_BY_ID)
                .then().extract().response();
    }

    public Response createDog(Dog dog) {
        return given()
                .body(dog)
                .when()
                .post(Config.CREATE_DOG)
                .then().extract().response();
    }

    public Response getDogById(Long id){
        return given()
                .pathParam("id", id)
                .when()
                .get(Config.DOG_BY_ID)
                .then().extract().response();
    }

    public Response deleteUserById(Long id) {
        return given()
                .pathParam("id", id)
                .when()
                .delete(Config.DELETE_USER)
                .then().extract().response();
    }

    public Response createRoute(String polyline, Double lat, Double lng){
        Map<String, String> obj = new HashMap<String, String>();
        obj.put("polyline", polyline);
        obj.put("lat", String.valueOf(lat));
        obj.put("lng", String.valueOf(lng));

        return given()
                .body(obj)
                .when()
                .post(Config.CREATE_ROUTE)
                .then().extract().response();
    }

    public Response getRouteById(Long id){
        return given()
                .pathParam("id", id)
                .when()
                .delete(Config.DELETE_USER)
                .then().extract().response();
    }

    private RequestSpecification given() {
        return RestAssured.given()
                .log().uri()
                .log().body()
                .baseUri(Config.APP_BASE_URL)
                .contentType(ContentType.JSON);
    }
}
