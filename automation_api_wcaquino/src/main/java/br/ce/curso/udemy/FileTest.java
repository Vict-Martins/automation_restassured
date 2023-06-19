package br.ce.curso.udemy;

import org.junit.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class FileTest {

    @Test
    public void deveObrigarEnvioArquivo() {

        given()
                .log().all()
            .when()
                .post("http://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .statusCode(404) //Deveria ser 400
                .body("error", is("Arquivo não enviado"))
        ;

    }

    @Test
    public void deveFazerUploadArquivo() {

        given()
                .log().all()
                .multiPart("arquivo", new File("pom.xml"))
            .when()
                .post("http://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .statusCode(200)
                .body("name", is("pom.xml"))
        ;

    }

    @Test
    public void deveFazerUploadArquivoMaior() {

        given()
                .log().all()
                .multiPart("arquivo", new File("src/test/Apresentação PCI-DSS.pdf"))
            .when()
                .post("http://restapi.wcaquino.me/upload")
            .then()
                .log().all()
                .time(lessThan(10000L))
                .statusCode(413)
        ;

    }

    @Test
    public void deveFazerdnwloadArquivo() {

        given()
                .log().all()
            .when()
                .get("http://restapi.wcaquino.me/download")
            .then()
                .log().all()
                .statusCode(200).
                extract().asByteArray();
        ;
    }

    @Test
    public void deveBaixarArquivo() throws IOException {
        byte[] image = given().
                log().all().
            when().
                get("http://restapi.wcaquino.me/download").
            then().
                log().all().
                statusCode(200).
                extract().asByteArray();

        File imagem = new File("src/main/resources/file.jpg");
        OutputStream out = new FileOutputStream(imagem);
        out.write(image);
        out.close();

    }

}
