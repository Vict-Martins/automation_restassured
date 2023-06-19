package Exemplos.Storage;
import static io.restassured.RestAssured.*;


public class AssistanceMethods {

    //Corpo da requisição do método tokenReq
    private static String requestBody = "{\n" +
            "  \"userName\": \"barretao\",\n" +
            "  \"password\": \"Barretao123**\"\n" +
            "}";

    //Geração de token e retornando String
    public String tokenReq() {

        baseURI = "http://172.16.10.243:5001/api/v1";

        String token =
                 given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestBody)
                .when()
                    .post("/login")
                .then()
                    .extract()
                    .path("data.accessToken");

        return token;

    }

}
