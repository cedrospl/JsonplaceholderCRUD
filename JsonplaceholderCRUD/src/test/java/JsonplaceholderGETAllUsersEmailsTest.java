import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.w3c.dom.ls.LSOutput;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderGETAllUsersEmailsTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String USERS = "users";

    @Test
    public void jsonplaceholderGETNoEmailEndsWithPLTest(){
        Response response = given()
                .when()
                .get(BASE_URL + USERS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> users = json.getList("email");

        assertEquals(10, users.size());

        Boolean isTherePLEmail = users.stream()
                .anyMatch(email -> email.endsWith(".pl"));

        assertEquals(false, isTherePLEmail);

    }
}
