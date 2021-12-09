import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImageTests extends BaseTest {
    private final String PATH_TO_IMAGE = "src/test/resources/classes-slide-blades.png";
    static String encodedFile;
    String uploadedImageId1;
    String uploadedImageId2;
    String uploadedImageId3;

    @BeforeEach
    void beforeTest() {
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);
    }

    @Test
    void uploadFileBase64Test() {
        uploadedImageId1 = given()
                .headers("Authorization", token)
                .multiPart("image", encodedFile)
                .multiPart( "title", "BladeBase64")
                .multiPart( "description", "This is an 1x1 pixel image.")
                .expect()
                .body("success", is(true))
                .body("data.id", is(notNullValue()))
                .body("data.title", is("BladeBase64"))
                .body("data.description", is("This is an 1x1 pixel image."))
                .when()
                .post("https://api.imgur.com/3/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }


    @Test
    void uploadFilePngTest() {
        uploadedImageId2 = given()
                .headers("Authorization", token)
                .multiPart("image", new File("src/test/resources/classes-slide-blades.png"))
                .multiPart( "title", "Blade.png")
                .multiPart( "description", "This is an 1x1 pixel image.")
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

    @Test
    void uploadFileURLTest() {
        uploadedImageId3 = given()
                .headers("Authorization", token)
                .multiPart("image", "https://i.imgur.com/U40dypo.png")
                .multiPart( "type", "url")
                .multiPart( "title", "BladeURL")
                .multiPart( "description", "This is an 1x1 pixel image.")
                .expect()
                .statusCode(200)
                .body("data.title", is("BladeURL"))
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


    private byte[] getFileContent() {
        byte[] byteArray = new byte[0];
        try {
            byteArray = FileUtils.readFileToByteArray(new File(PATH_TO_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }


}