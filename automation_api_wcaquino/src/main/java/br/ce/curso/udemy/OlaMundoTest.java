package br.ce.curso.udemy;

import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.*;
import java.util.Arrays;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class OlaMundoTest {

    @Test
    public void testOlaMundo(){

        Response response = request(Method.GET, "https://restapi.wcaquino.me/ola");
        assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        assertTrue(response.statusCode() == 200);
        assertTrue("O status code deveria ser 200", response.statusCode() == 200);
        assertEquals( response.statusCode(), 200);
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

    }
    @Test
    public void devoConhecerOutrasFormasDoRestAssured(){

        get("https://restapi.wcaquino.me/ola").then().statusCode(200);
        given()
        .when() //Ação
                .get("https://restapi.wcaquino.me/ola")
        .then()
                .statusCode(200);

    }

    @Test
    public void conhecendoMatchersHamcrest() {

        int number = 127;
        assertEquals( "Maria", "Maria" );
        assertEquals( 128, 128 );
        MatcherAssert.assertThat( 128, Matchers.isA(Integer.class) );
        MatcherAssert.assertThat( 128d, Matchers.isA(Double.class) );
        MatcherAssert.assertThat( 128d, Matchers.greaterThan(120d) );
        MatcherAssert.assertThat( 128d, Matchers.greaterThan(123d) );

        List<Integer> impares = Arrays.asList(1,3, 5, 7, 9);
        MatcherAssert.assertThat( impares, hasSize(5));
        MatcherAssert.assertThat( impares, contains(1, 3, 5, 7, 9));
        MatcherAssert.assertThat( impares, containsInAnyOrder(1, 3, 5, 7, 9));
        MatcherAssert.assertThat( impares, hasItem(1));
        MatcherAssert.assertThat( impares, hasItems(1, 5));

        MatcherAssert.assertThat( "Maria", is(not("João")));
        MatcherAssert.assertThat( "Maria", not("João"));
        MatcherAssert.assertThat( "Maria", anyOf(is("Maria"), is("Joaquina")));
        MatcherAssert.assertThat( "Maria", allOf(startsWith("Maria"), endsWith("ina"), containsString("qui")));

    }

    @Test
    public void devoValidarBody(){

        given()
            .when()
                .get("https://restapi.wcaquino.me/ola")
            .then()
                .statusCode(200)
                .body(is("Ola Mundo!"))
                .body(containsString("Mundo"))
                .body(is(not(nullValue())));

    }

}