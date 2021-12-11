import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class ImageAddFavorite extends BaseTest{
    String uploadedImageId;

    @Test
    void uploadFilePngTest() {
        uploadedImageId = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/classes-slide-blades.png"))
                .multiPart("title", "Blade.png")
                .multiPart("description", "This is an 1x1 pixel image.")
                .expect()
                .statusCode(200)
                .body("data.title", is("Blade.png"))
                .body("data.description", is("This is an 1x1 pixel image."))
                .when()
                .post("https://api.imgur.com/3/upload")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");

    }

    @AfterEach
    void addFavoriteImageTest() {
        given()
                .headers("Authorization", token)
                .when()
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("data", is("favorited"))
                .body("success", is(true));
    }

}
