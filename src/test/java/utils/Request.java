package utils;

import constants.RestEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Request {

    public static String getToken(){
        String payload = "{\"username\": \"admin\",\"password\" : \"password123\"}";
        Response response = Request.getTokenR(payload,RestEndpoints.CREATE_TOKEN);

        return response.body().asString();
    }

    public static Response getTokenR(String payload, String endpoint){
        RestAssured.baseURI = RestEndpoints.BASE_URL;
        Response response = RestAssured
                .given().contentType(ContentType.JSON).body(payload)
                .when().post(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response get(String endpoint){
        RestAssured.baseURI = RestEndpoints.BASE_URL;
        Response response = RestAssured
                .when().get(endpoint);;
        response.then().log().body();
        return response;
    }

    public static Response getById(String endpoint, String id){
        RestAssured.baseURI = RestEndpoints.BASE_URL;
        Response response = RestAssured
                .given().pathParam("id", id)
                .when().get(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response post(String endpoint, String payload){
        RestAssured.baseURI = RestEndpoints.BASE_URL;
        Response response = RestAssured
                .given().contentType(ContentType.JSON)
                .body(payload)
                .when().post(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response put(String endpoint, String id, String payload){
        String token = getToken().substring(10,25);

        RestAssured.baseURI = RestEndpoints.BASE_URL;
        Response response = RestAssured
                .given().contentType(ContentType.JSON)
                .cookie("token",token)
                .body(payload)
                .pathParam("id",id)
                .when().put(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response delete(String endpoint, String id){
        String token = getToken().substring(10,25);

        RestAssured.baseURI = RestEndpoints.BASE_URL;
        Response response = RestAssured
                .given().contentType(ContentType.JSON)
                .cookie("token", token)
                .pathParam("id", id)
                .when().delete(endpoint);
        response.then().log().body();
        return response;
    }
}
