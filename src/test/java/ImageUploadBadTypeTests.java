import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.geekbrains.Endpoints.UPLOAD_IMAGE;

public class ImageUploadBadTypeTests extends BaseTest {
    private final String PATH_TO_IMAGE = "src/test/resources/classes-slide-blades.png";

    MultiPartSpecification multiPartSpecWithFilePng;
    MultiPartSpecification multiPartSpecWithFileURL;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImage;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImageURL;

    @BeforeEach
    void beforeTest() {

        multiPartSpecWithFilePng = new MultiPartSpecBuilder(new File(PATH_TO_IMAGE))
                .controlName("image")
                .build();


        multiPartSpecWithFileURL = new MultiPartSpecBuilder(IMAGE_URL)
                .controlName("image")
                .build();


        requestSpecificationWithAuthAndMultipartImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "Picture")
                .addFormParam("type", "base64")
                .addFormParam("description", "This is an 1x1 pixel image.")
                .addMultiPart(multiPartSpecWithFilePng)
                .build();


        requestSpecificationWithAuthAndMultipartImageURL = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "Picture")
                .addFormParam("type", "dfghdfgh")
                .addFormParam("description", "This is an 1x1 pixel image.")
                .addMultiPart(multiPartSpecWithFileURL)
                .build();

    }

    @Test
    void uploadFilePngTest() {
        given(requestSpecificationWithAuthAndMultipartImage, negativeImageUploadResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body();
    }


    @Test
    void uploadFileURLTest() {
        given(requestSpecificationWithAuthAndMultipartImageURL, negativeImageUploadResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body();

    }

//    @Test
//    void uploadFileMp4Test() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", new File("src/test/resources/1.mp4"))
//                .multiPart("type", "url")
//                .multiPart("title", "1.mp4")
//                .multiPart("description", "This is an 1x1 pixel image.")
//                .expect()
//                .statusCode(400)
//                .body("success", is(false))
//                .body("data.error", is("Bad Request"))
//                .body("data.request", is("/3/upload"))
//                .body("data.method", is("POST"))
//                .when()
//                .post("https://api.imgur.com/3/upload")
//                .prettyPeek()
//                .then()
//                .extract()
//                .response()
//                .jsonPath()
//                .getString("data.deletehash");
//
//    }
//
//    @Test
//    void uploadFileURLTest() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", "https://i.imgur.com/U40dypo.png")
//                .multiPart("type", "mp4")
//                .multiPart("title", "BladeURL")
//                .multiPart("description", "This is an 1x1 pixel image.")
//                .expect()
//                .statusCode(400)
//                .body("success", is(false))
//                .body("data.error", is("Bad Request"))
//                .body("data.request", is("/3/upload"))
//                .body("data.method", is("POST"))
//                .when()
//                .post("https://api.imgur.com/3/upload")
//                .prettyPeek()
//                .then()
//                .extract()
//                .response()
//                .jsonPath()
//                .getString("data.deletehash");
//
//    }

}
