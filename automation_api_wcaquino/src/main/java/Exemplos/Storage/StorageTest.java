package Exemplos.Storage;

import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;

public class StorageTest extends AssistanceMethods {

    private static String requestBodyCreateStorage = "{\n" +
            "  \"codigo\": \"COLOCAR CÓDIGO AQUI\",\n" +
            "  \"descricao\": \"COLOCAR DESCRIÇÃO AQUI\"\n" +
            "}";

    private static String requestBodyEditStorage = "{\n" +
            "  \"id\": \"COLOCAR ID AQUI\",\n" +
            "  \"codigo\": \"COLOCAR CÓDIGO AQUI\",\n" +
            "  \"descricao\": \"COLOCAR DESCRIÇÃO AQUI\"\n" +
            "}";

    //Retornando informações de armazéns
    @Test
    public void getStorage() {

        /*BaseUri é o endereço da página onde queremos fazer as requisições*/
        baseURI = "http://172.16.10.243:5001/api/v1";
        String token;
        /*Montagem básica de requisição*/
        Response response =
                given().
                    header("Content-type", "application/json").
                    /*Recebendo argumento(token) para autenticar usuário*/
                    header("Authorization", "Bearer " + tokenReq()).
                when().
                    get("/armazens").
                then().
                    extract().response();

            /*Verificando se a requisição foi concluída com sucesso*/
            assertTrue(response.statusCode() == 200);
            /*Visualizando o corpo da retornado*/
            System.out.println( response.asString() );

    }

    //Criando armazém passando argumento no corpo da requisição.
    @Test
    public void postStorage() {

        baseURI = "http://172.16.10.243:5001/api/v1";
        Response response =
                given().
                    header("Content-type", "application/json").
                    header("Authorization","bearer " + tokenReq() ).
                    and().
                    body(requestBodyCreateStorage).
                when().
                    post("/armazens").
                then().
                    extract().response();

        System.out.println( response.asString() );
        //assertTrue( response.statusCode() == 201 );

    }

    //Editando informações de armazéns
    @Test
    public void editStorage() {

        baseURI = "http://172.16.10.243:5001/api/v1";
        Response response =
                given().
                    header("Content-type", "application/json").
                    header("Authorization","bearer " + tokenReq() ).
                    and().
                    body(requestBodyEditStorage).
                when().
                    put("/armazens/").
                then().
                    extract().response();

        assertTrue( response.statusCode() == 200 );
        System.out.println( response.asString() );

    }

    //Deletando informações de armazéns
    @Test
    public void deleteStorage() {

        baseURI = "http://172.16.10.243:5001/api/v1";
        Response response =
                given().
                        header("Content-type", "application/json").
                        header("Authorization","bearer " + tokenReq() ).
                        and().
                        when().
                        delete("/armazens/"+"COLOCAR ID AQUI").
                        then().
                        extract().response();

        assertTrue( response.statusCode() == 200 );
        System.out.println( response.asString() );

    }
}
