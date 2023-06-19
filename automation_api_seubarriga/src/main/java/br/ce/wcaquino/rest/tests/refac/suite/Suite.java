package br.ce.wcaquino.rest.tests.refac.suite;

import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.tests.refac.AuthTest;
import br.ce.wcaquino.rest.tests.refac.ContasTest;
import br.ce.wcaquino.rest.tests.refac.MovimentacaoTest;
import br.ce.wcaquino.rest.tests.refac.SaldoTest;
import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;

@RunWith(org.junit.runners.Suite.class)
@org.junit.runners.Suite.SuiteClasses({
        ContasTest.class,
        MovimentacaoTest.class,
        SaldoTest.class,
        AuthTest.class
})
public class Suite extends BaseTest {

    @BeforeClass
    public static void login() {

        Map<String, String> login = new HashMap<>();
        login.put("email","martins@email.com");
        login.put("senha","123456");

        String TOKEN = given().
                body(login).
            when().
                post("/signin").
            then().
                statusCode(200).
                extract().path("token")
                ;

        RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
        RestAssured.get("/reset").then().statusCode(200);

    }

}
