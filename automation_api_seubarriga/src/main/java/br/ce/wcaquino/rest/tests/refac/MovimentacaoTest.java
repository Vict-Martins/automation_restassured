package br.ce.wcaquino.rest.tests.refac;

import br.ce.wcaquino.rest.core.BaseTest;
import br.ce.wcaquino.rest.tests.Movimentacap;
import br.ce.wcaquino.rest.utils.BarrigaUtils;
import br.ce.wcaquino.rest.utils.DataUtils;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MovimentacaoTest extends BaseTest {

    @Test
    public void deveInserirMovimentacaoComSucesso() {

        Movimentacap mov = getMovimentacaoValida();

        given().
                body(mov).
            when().
                post("/transacoes").
            then().
                statusCode(201)
        ;
    }

    @Test
    public void deveValidarCamposObrigatoriosMovimentacao() {

        Movimentacap mov = getMovimentacaoValida();

        given().
                //header("Authorization", "JWT " + TOKEN).
                body("{}").
                when().
                post("/transacoes").
                then().
                log().all().
                statusCode(400).
                body("$", hasSize(8)).
                body("msg", hasItems(
                        "Data da Movimentação é obrigatório",
                        "Data do pagamento é obrigatório",
                        "Descrição é obrigatório",
                        "Interessado é obrigatório",
                        "Valor é obrigatório",
                        "Valor deve ser um número",
                        "Conta é obrigatório",
                        "Situação é obrigatório"

                ))
        ;
    }

    @Test
    public void naoDeveInserirMovimentacaoComDataFutura() {
        Movimentacap mov = getMovimentacaoValida();
        mov.setData_transacao("20/05/2025");

        given().
                body(mov).
            when().
                post("/transacoes").
            then().
                statusCode(400).
                body("$", hasSize(1)).
                body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
        ;

    }

    @Test
    public void naoDeveRemoverContaComMovimentacaoComDataFutura() {

        Integer CONTA_ID = BarrigaUtils.getIdContaPeloNome("Conta com movimentacao");

        given().
            pathParams("id", CONTA_ID).
            when().
                delete("/contas/{id}").
            then().
                statusCode(500).
                body("constraint", is("transacoes_conta_id_foreign"))
        ;

    }

    @Test
    public void deveRemoverMovimentacao () {

        Integer MOV_ID = BarrigaUtils.getIdMovPelaDescricao("Movimentacao para exclusao");

        given().
            pathParams("id", MOV_ID).
            when().
                delete("/transacoes/{id}").
            then().
                statusCode(204)
        ;

    }


    private Movimentacap getMovimentacaoValida() {

        Movimentacap mov = new Movimentacap();
        mov.setConta_id(BarrigaUtils.getIdContaPeloNome("Conta para movimentacoes"));
        mov.setDescricao("Descricao da movimentacao");
        mov.setEnvolvido("Envolvido na mov");
        mov.setTipo("REC");
        mov.setData_transacao(DataUtils.getDataDifDiasInteger(-1));
        mov.setData_pagamento(DataUtils.getDataDifDiasInteger(5));
        mov.setValor(100f);
        mov.setStatus(true);
        return mov;

    }

}
