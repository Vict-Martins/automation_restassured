package br.ce.curso.udemy;

import io.restassured.http.ContentType;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class VerbosTest {

    @Test
    public void deveSalvarUsuario(){

        given().
            log().all().
            contentType("application/json").
            body("{ \"name\": \"jose\", \"age\": 50 }").
        when().
            post("https://restapi.wcaquino.me/users").
        then().
            log().all().
            statusCode(201).
            body("id", is(notNullValue())).
            body("name", is("jose")).
            body("age", is(50))
        ;

    }

    @Test
    public void salvarUsuarioSemNome(){

        given().
                log().all().
                contentType("application/json").
                body("{  \"age\": 50 }").
            when().
                post("https://restapi.wcaquino.me/users").
            then().
                log().all().
                statusCode(400).
                body("id", is(nullValue())).
                body("error", is("Name é um atributo obrigatório"))
        ;

    }

    @Test
    public void deveAlterarUsuario(){

        given().
                log().all().
                contentType("application/json").
                body("{ \"name\": \"jose1\", \"age\": 21 }").
            when().
                put("https://restapi.wcaquino.me/users/1").
            then().
                log().all().
                statusCode(200).
                body( "id", is(notNullValue()) ).
                body( "name", is("jose1")).
                body( "age", is(21) ).
                body( "salary", is(1234.5677f))
        ;

    }

    @Test
    public void deveCustomizarURL(){

        given().
                log().all().
                contentType("application/json").
                body("{ \"name\": \"jose1\", \"age\": 21 }").
                pathParam("entidade", "users").
                pathParam("userId", 1).
                when().
                put("https://restapi.wcaquino.me/{entidade}/{userId}", "users", "1").
                then().
                log().all().
                statusCode(200).
                body( "id", is(notNullValue()) ).
                body( "name", is("jose1")).
                body( "age", is(21) ).
                body( "salary", is(1234.5677f))
        ;

    }

    @Test
    public void deveCustomizarURLParte2(){

        given().
                log().all().
                contentType("application/json").
                body("{ \"name\": \"jose1\", \"age\": 21 }").
                pathParam("entidade", "users").
                pathParam("userId", 1).
                when().
                put("https://restapi.wcaquino.me/{entidade}/{userId}").
                then().
                log().all().
                statusCode(200).
                body( "id", is(notNullValue()) ).
                body( "name", is("jose1")).
                body( "age", is(21) ).
                body( "salary", is(1234.5677f))
        ;

    }

    @Test
    public void naoDeveRemoverUsuario(){

        given().
            log().all().
        when().
            delete("https://restapi.wcaquino.me/users/1000").
        then().
            log().all().
            statusCode(400).
            body("error", is("Registro inexistente"))
        ;

    }

    @Test
    public void deveRemoverUsuario(){

        given().
                log().all().
                when().
                delete("https://restapi.wcaquino.me/users/1").
                then().
                log().all().
                statusCode(204)
        ;

    }

    @Test
    public void deveSalvarUsuarioComMap(){

        Map<String, Object> params = new HashMap<>();
        params.put("name", "Usuario via map");
        params.put("age", 25);

        given().
                log().all().
                body(params).
            when().
                post("https://restapi.wcaquino.me/users/").
            then().
                log().all()
        ;

    }

    @Test
    public void deveSalvarUsuarioComMapObjeto(){
        User user = new User("Usuario via objeto", 35);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "Usuario via map");
        params.put("age", 25);

        given().
                log().all().
                body(user).
            when().
                post("https://restapi.wcaquino.me/users/").
            then().
                log().all()
        ;

    }

    @Test
    public void deveDeserializarObjetoAoSalavarUsuario(){
        User user = new User("UsuárioTester", 35);

        User usuarioInserido = given().
                log().all().
                contentType("application/json").
                body(user).
            when().
                post("https://restapi.wcaquino.me/users").
            then().
                extract().body().as(User.class)
        ;

        System.out.println(usuarioInserido);

    }

    @Test
    public void deveSalvarUsuarioViaXMLComObjeto() {

        User user = new User("Usuario XML", 40);

        given().
                log().all().
                contentType(ContentType.XML).
                body(user).
            when().
                post("https://restapi.wcaquino.me/usersXML").
            then().
                log().all().
                statusCode(201).
                body("user.@id", is(notNullValue())).
                body("user.name", is("Usuario XML")).
                body("user.age", is("40"))
        ;

    }

    @Test
    public void deveDeserializarXMLSalvarUsuario() {

        User user = new User("Usuario XML", 40);

        User usuarioInserido = given().
                log().all().
                contentType(ContentType.XML).
                body(user).
            when().
                post("https://restapi.wcaquino.me/usersXML").
            then().
                log().all().
                statusCode(201).
                extract().body().as(User.class)
        ;
        assertThat(usuarioInserido.getId(), notNullValue());
        assertThat(usuarioInserido.getName(), is("Usuario XML"));
        assertThat(usuarioInserido.getAge(), is(40));
        assertThat(usuarioInserido.getSalary(), nullValue());

    }

}