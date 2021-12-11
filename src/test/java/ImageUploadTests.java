import dto.PostImageResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.java.Endpoints;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ImageUploadTests extends BaseTest {
    private final String PATH_TO_IMAGE = "src/test/resources/classes-slide-blades.png";
    private final String PATH_TO_VIDEO = "src/test/resources/1.mp4";
    private final String IMAGE_URL = "https://i.imgur.com/U40dypo.png";
    static String encodedFile;
    String uploadedImageId;
    MultiPartSpecification base64MultiPartSpec;
    MultiPartSpecification multiPartSpecWithFilePng;
    MultiPartSpecification multiPartSpecWithFileMp4;
    MultiPartSpecification multiPartSpecWithFileURL;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImage;
    static RequestSpecification requestSpecificationWithAuthWithBase64;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImageMp4;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImageURL;

    @BeforeEach
    void beforeTest() {
        byte[] byteArray = getFileContent();
        encodedFile = Base64.getEncoder().encodeToString(byteArray);

        base64MultiPartSpec = new MultiPartSpecBuilder(encodedFile)
                .controlName("image")
                .build();

        multiPartSpecWithFilePng = new MultiPartSpecBuilder(new File(PATH_TO_IMAGE))
                .controlName("image")
                .build();

        multiPartSpecWithFileMp4 = new MultiPartSpecBuilder(new File(PATH_TO_VIDEO))
                .controlName("image")
                .build();

        multiPartSpecWithFileURL = new MultiPartSpecBuilder(IMAGE_URL)
                .controlName("image")
                .build();

        requestSpecificationWithAuthWithBase64 = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addMultiPart(base64MultiPartSpec)
                .build();

        requestSpecificationWithAuthAndMultipartImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "Picture")
                .addFormParam("type", "png")
                .addFormParam("description", "This is an 1x1 pixel image.")
                .addMultiPart(multiPartSpecWithFilePng)
                .build();

        requestSpecificationWithAuthAndMultipartImageMp4 = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "Video")
                .addFormParam("type", "mp4")
                .addFormParam("description", "This is an 1x1 pixel image.")
                .addMultiPart(multiPartSpecWithFileMp4)
                .build();

        requestSpecificationWithAuthAndMultipartImageURL = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addFormParam("title", "Picture")
                .addFormParam("type", "url")
                .addFormParam("description", "This is an 1x1 pixel image.")
                .addMultiPart(multiPartSpecWithFileURL)
                .build();

    }

    @Test
    void uploadFileBase64Test() {
        uploadedImageId = given(requestSpecificationWithAuthWithBase64, positiveResponseSpecification)
                .post(Endpoints.UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class)
                .getData().getDeletehash();
    }

    @Test
    void uploadFilePngTest() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImage, positiveImageUploadResponseSpecification)
                .post(Endpoints.UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class)
                .getData().getDeletehash();
    }

    @Test
    void uploadFileMp4Test() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImageMp4, positiveResponseSpecification)
                .post(Endpoints.UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class)
                .getData().getDeletehash();
    }

//    @Test
//    void uploadFileBase64Test() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", encodedFile)
//                .multiPart("title", "BladeBase64")
//                .multiPart("description", "This is an 1x1 pixel image.")
//                .expect()
//                .body("success", is(true))
//                .body("data.id", is(notNullValue()))
//                .body("data.title", is("BladeBase64"))
//                .body("data.description", is("This is an 1x1 pixel image."))
//                .when()
//                .post("https://api.imgur.com/3/image")
//                .prettyPeek()
//                .then()
//                .extract()
//                .response()
//                .jsonPath()
//                .getString("data.deletehash");
//    }
//
//
//    @Test
//    void uploadFilePngTest() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", new File("src/test/resources/classes-slide-blades.png"))
//                .multiPart("title", "Blade.png")
//                .multiPart("description", "This is an 1x1 pixel image.")
//                .expect()
//                .statusCode(200)
//                .body("data.title", is("Blade.png"))
//                .body("data.description", is("This is an 1x1 pixel image."))
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
//    void uploadFileMp4Test() {
//        uploadedImageId = given()
//                .headers("Authorization", token)
//                .multiPart("image", new File("src/test/resources/1.mp4"))
//                .multiPart("type", "mp4")
//                .multiPart("title", "1.mp4")
//                .multiPart("description", "This is an 1x1 pixel image.")
//                .expect()
//                .statusCode(200)
//                .body("data.title", is("1.mp4"))
//                .body("data.description", is("This is an 1x1 pixel image."))
//                .body("data.type", is("video/mp4"))
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
//                .multiPart("type", "url")
//                .multiPart("title", "BladeURL")
//                .multiPart("description", "This is an 1x1 pixel image.")
//                .expect()
//                .statusCode(200)
//                .body("data.title", is("BladeURL"))
//                .body("data.description", is("This is an 1x1 pixel image."))
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

    @AfterEach
    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/account/{username}/image/{deleteHash}", username, uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
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