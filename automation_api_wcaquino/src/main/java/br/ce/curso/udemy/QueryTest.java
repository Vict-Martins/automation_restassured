package br.ce.curso.udemy;

import io.restassured.http.ContentType;
import org.junit.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class QueryTest {

    @Test
    public void enviarDadosViaQuery() {

        given().
                log().all().
                contentType(ContentType.JSON).
        when().
            get("https://restapi.wcaquino.me/v2/users?format=json").
        then().
            log().all().
            statusCode(200).extract()
        ;

    }

    @Test
    public void enviarDadosViaQueryViaParam () {

        given().
                log().all().
                queryParam("format", "xml").
                queryParam("outra", "coisa").
            when().
                get("https://restapi.wcaquino.me/v2/users").
            then().
                log().all().
                statusCode(200).
                contentType( ContentType.XML ).
                contentType( containsString("utf-8") )
        ;

    }

    @Test
    public void enviarDadosViaQueryViaHeader() {

        given().
                log().all().
                accept(ContentType.XML).
            when().
                get("https://restapi.wcaquino.me/v2/users").
            then().
                log().all().
                statusCode(200).
                contentType(ContentType.XML)
        ;

    }

}