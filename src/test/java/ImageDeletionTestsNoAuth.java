import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ImageDeletionTests extends BaseTest{

    @Test
    void ImageDeletionNoAuth() {
        given()
                .headers("Authorization", clientId)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", username, uploadedImagePngId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
