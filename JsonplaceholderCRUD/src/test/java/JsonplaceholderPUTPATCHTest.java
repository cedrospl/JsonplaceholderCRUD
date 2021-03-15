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

public class JsonplaceholderPUTPATCHTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String COMMENTS = "comments/";
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
    public void jsonplaceholderUpdateCommentPUT(){

        JSONObject updateComment = new JSONObject();
        updateComment.put("name", fakeName);
        updateComment.put("email", fakeEmail);
        updateComment.put("body", fakeBody);

        Response response = given()
                .pathParam("commentId", 8)
                .contentType("application/json")
                .body(updateComment.toString())
                .when()
                .put(BASE_URL + COMMENTS + "{commentId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeName, json.get("name"));
        assertEquals(fakeEmail, json.get("email"));
        assertEquals(fakeBody, json.get("body"));
    }

    @Test
    public void jsonplaceholderUpdateCommentPATCH(){

        JSONObject updateComment = new JSONObject();
        updateComment.put("body", fakeBody);

        Response response = given()
                .pathParam("commentId", 8)
                .contentType("application/json")
                .body(updateComment.toString())
                .when()
                .patch(BASE_URL + COMMENTS + "{commentId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeBody, json.get("body"));
    }
}
