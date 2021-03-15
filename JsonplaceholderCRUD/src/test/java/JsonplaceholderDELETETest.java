import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderDELETETest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final String COMMENTS = "comments/";

    @Test
    public void jsonplaceholderDELETEComment(){

        Response response = given()
                .pathParam("commentId", 8)
                .when()
                .delete(BASE_URL + COMMENTS + "{commentId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
