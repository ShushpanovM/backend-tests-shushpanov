import ru.geekbrains.dto.PostImageResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static ru.geekbrains.Endpoints.ADD_FAVORITE;
import static ru.geekbrains.Endpoints.UPLOAD_IMAGE;
import static org.hamcrest.CoreMatchers.is;

public class ImageAddFavorite extends BaseTest{
    String uploadedImageId;
    MultiPartSpecification multiPartSpecWithFileURL;
    static RequestSpecification requestSpecificationWithAuthAndMultipartImageURL;

    @BeforeEach
    void beforeTest() {

        multiPartSpecWithFileURL = new MultiPartSpecBuilder(IMAGE_URL)
                .controlName("image")
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
    void uploadFileURLTest() {
        uploadedImageId = given(requestSpecificationWithAuthAndMultipartImageURL, positiveResponseSpecification)
                .post(UPLOAD_IMAGE)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(PostImageResponse.class)
                .getDataImage().getDeletehash();
    }

    @AfterEach
    void addFavoriteImageTest() {
        given()
                .headers("Authorization", token)
                .when()
                .post(ADD_FAVORITE, uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("data", is("favorited"))
                .body("success", is(true));
    }

}
