import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class ImageUploadBadTypeTests extends BaseTest {
    String uploadedImageId;

    @Test
    void uploadFileMp4Test() {
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/1.mp4"))
                .multiPart("type", "url")
                .multiPart("title", "1.mp4")
                .multiPart("description", "This is an 1x1 pixel image.")
                .expect()
                .statusCode(400)
                .body("success", is(false))
                .body("data.error", is("Bad Request"))
                .body("data.request", is("/3/upload"))
                .body("data.method", is("POST"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

    }

    @Test
    void uploadFileURLTest() {
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", "https://i.imgur.com/U40dypo.png")
                .multiPart("type", "mp4")
                .multiPart("title", "BladeURL")
                .multiPart("description", "This is an 1x1 pixel image.")
                .expect()
                .statusCode(400)
                .body("success", is(false))
                .body("data.error", is("Bad Request"))
                .body("data.request", is("/3/upload"))
                .body("data.method", is("POST"))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

    }

}
