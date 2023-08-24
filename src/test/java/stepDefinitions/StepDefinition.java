package stepDefinitions;


import io.restassured.RestAssured;
import io.restassured.builder.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.*;
import io.cucumber.java.en.*;
import pojo.*;
import resources.APIResources;
import resources.TestDataBuild;
import resources.utilities;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;


public class StepDefinition  extends utilities {
    RequestSpecification res;
    ResponseSpecification resspec;
    Response response;

    TestDataBuild data = new TestDataBuild();
    static String place_id;

    @Given("Add Place Payload {string} {string} {string}")
    public void add_place_payload(String name, String language, String address) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        res=given().spec(requestSpecification())
                .body(data.addPlacePayload(name,language,address));
    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_post_http_request(String resource, String method) {
        APIResources resourceApi =  APIResources.valueOf(resource);
        System.out.println(resourceApi.getResource());

        resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        if (method.equalsIgnoreCase("POST"))
        {
            response =res.when().post(resourceApi.getResource());
                    //then().spec(resspec).extract().response();
        }
        else if (method.equalsIgnoreCase("GET"))
        {
            response =res.when().get(resourceApi.getResource());
        }

    }

    @Then("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        assertEquals(200,response.getStatusCode());
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String expectedValue) {


        assertEquals(expectedValue,getJsonPath(response,keyValue));

    }

    @Then("verify place_id created maps to {string} using {string}")
    public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
       place_id = getJsonPath(response,"place_id");
        res = given().spec(requestSpecification()).queryParam("place_id",place_id);

        user_calls_with_post_http_request(resource,"GET");
        String actualName = getJsonPath(response,"name");

        assertEquals(expectedName, actualName);
    }

    @Given("DeletePlace Payload")
    public void delete_place_payload() throws IOException {
        // Write code here that turns the phrase above into concrete actions
      res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
    }

}
