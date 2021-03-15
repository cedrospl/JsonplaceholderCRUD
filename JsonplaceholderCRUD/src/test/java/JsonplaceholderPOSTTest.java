import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderPOSTTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String COMMENTS = "comments";
    private static Faker faker;
    private String fakeName;
    private String fakeEmail;
    private String fakeBody;

    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach(){
        fakeName = faker.book().title();
        fakeEmail = faker.internet().emailAddress();
        fakeBody = faker.shakespeare().hamletQuote();
    }

    @Test
    public void jsonplaceholderCreateNewComment(){

        JSONObject newComment = new JSONObject();
        newComment.put("postId", 50);
        newComment.put("name", fakeName);
        newComment.put("email", fakeEmail);
        newComment.put("body", fakeBody);

        Response response = given()
                .when()
                .contentType("application/json")
                .body(newComment.toString())
                .post(BASE_URL + COMMENTS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeEmail, json.get("email"));
        assertEquals(fakeBody, json.get("body"));
    }
}
