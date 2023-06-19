package br.ce.wcaquino.rest.tests.refac;

import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.utils.BarrigaUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import static io.restassured.RestAssured.given;

public class ContasTest extends BaseTest {

    @Test
    public void deveIncluirContaComSucesso() {

        given().
                body("{ \"nome\": \"conta inserida\" }").
            when().
                post("/contas").
            then().
                statusCode(201).
                extract().path("id")
        ;
    }

    @Test
    public void t03deveEditarContaComSucesso() {

        Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta para alterar");

        given().
                body("{ \"nome\": \"Conta alterada\" }").
                pathParams("id", CONTA_ID).
            when().
                put("/contas/{id}").
            then().
                log().all().
                statusCode(200)
                .body("nome", Matchers.is("Conta alterada"))
        ;
    }

    @Test
    public void t04naoDeveInserirContaComSucesso() {

        given().
                body("{ \"nome\": \"Conta mesmo nome\" }").
            when().
                post("/contas").
            then().
                log().all().
                statusCode(400)
                .body("error", Matchers.is("Já existe uma conta com esse nome!"))
        ;
    }

}