package br.ce.curso.udemy;

import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {

    @Test
    public void deveAcessarSWAPI() {

        given().
                log().all().
            when().
                get("http://swapi.dev/api/people/1").
            then().
                log().all().
                statusCode(200).
                body("name", is("Luke Skywalker"))
        ;

    }

    @Test
    public void deveObterClima() {

        given().
                log().all().
                queryParam("q", "Fortaleza,BR").
                queryParam("appid", "e7e6bfb2fe0b7677fd8392b5fdcddc06").
                queryParam("units", "metric").
            when().
                get("https://api.openweathermap.org/data/2.5/weather").
            then().
                log().all().
                statusCode(200).
                body("name", is("Fortaleza")).
                body("coord.lon", is(-38.5247f)).
                body("main.temp", greaterThan(25f))

        ;
    }

    @Test
    public void naoDeveAcessarSemSenha() {

        given().
                log().all().
            when().
                get("https://restapi.wcaquino.me/basicauth").
            then()
                .log().all()
                .statusCode(401)
        ;

    }

    @Test
    public void deveFazerAutenticacaoBasica() {

        given().
                log().all().
            when().
                get("https://admin:senha@restapi.wcaquino.me/basicauth").
            then()
                .log().all()
                .statusCode(200).
                body("status", is("logado"))
        ;

    }

    @Test
    public void deveFazerAutenticacaoBasica2() {

        given().
                log().all().
                auth().basic("admin", "senha").
            when().
                get("https://restapi.wcaquino.me/basicauth").
            then()
                .log().all()
                .statusCode(200).
                body("status", is("logado"))
        ;

    }

    @Test
    public void deveFazerAutenticacaoBasicaComChallenge() {

        given().
                log().all().
                auth().preemptive().basic("admin", "senha").
            when().
                get("https://restapi.wcaquino.me/basicauth2").
            then().
                log().all().
                statusCode(200).
                body("status", is("logado"))
        ;

    }

    @Test
    public void deveFazerAutenticacaoComTokenJWT() {

        Map<String, String> login = new HashMap<String, String>();
        login.put("email", "martins@email.com");
        login.put("senha", "123456");

        String token = given().
                log().all().
                body(login).
                contentType(ContentType.JSON).
            when().
                post("https://barrigarest.wcaquino.me/signin").
            then().
                log().all().
                statusCode(200).
                extract().path("token");

        //receber o token
        given().
                log().all().
                header("Authorization", "JWT " + token).
            when().
                get("http://barrigarest.wcaquino.me/contas").
            then().
                log().all().
                statusCode(200).
                body("nome", hasItem("martinsConta"))
        ;

    }

    @Test
    public void deveAcessarAplicacaoWeb() {

        String cookie = given()
                .log().all().
                formParam("email", "martins@email.com").
                formParam("senha", "123456").
                contentType(ContentType.URLENC.withCharset("UTF-8")).
            when().
                post("http://seubarriga.wcaquino.me/logar").
            then().
                log().all().
                statusCode(200).
                extract().header("set-cookie");

        ;

        cookie = cookie.split("=")[1].split(";")[0];

        String body = given()
                .log().all().
                cookie("connect.sid", cookie).
            when().
                get("http://seubarriga.wcaquino.me/contas").
            then().
                log().all().
                statusCode(200).
                body("html.body.table.tbody.tr[0].td[0]", is("martinsConta")).
                extract().body().asString();
        ;

        System.out.println("--------------");
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, body);
        System.out.println(xmlPath.getString("html.body.table.tbody.tr[0].td[0]"));

    }

}