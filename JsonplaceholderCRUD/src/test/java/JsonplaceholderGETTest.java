import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderGETTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String COMMENTS = "comments";

    @Test
    public void jsonplaceholderReadAllComments() {
        Response response = given()
                .when()
                .get(BASE_URL + COMMENTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> comments = json.getList("id");

        assertEquals(500, comments.size());
    }

    @Test
    public void jsonplaceholderReadOneCommentWithPathVariable() {
        Response response = given()
                .pathParam("commentId", 8)
                .when()
                .get(BASE_URL + COMMENTS + "/" + "{commentId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("et omnis dolorem", json.get("name"));
        assertEquals("Mallory_Kunze@marie.org", json.get("email"));
        assertEquals("ut voluptatem corrupti velit\nad voluptatem maiores\net nisi velit vero accusamus maiores\nvoluptates quia aliquid ullam eaque", json.get("body"));
    }

    @Test
    public void jsonplaceholderReadCommentsWithQueryParam() {
        Response response = given()
                .queryParam("email", "Mallory_Kunze@marie.org")
                .when()
                .get(BASE_URL + COMMENTS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("et omnis dolorem", json.getList("name").get(0));
        assertEquals("Mallory_Kunze@marie.org", json.getList("email").get(0));
        assertEquals("ut voluptatem corrupti velit\nad voluptatem maiores\net nisi velit vero accusamus maiores\nvoluptates quia aliquid ullam eaque", json.getList("body").get(0));
    }
}
