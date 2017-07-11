package br.com.servelojapagamento.webservice_mundipagg;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Alexandre on 04/07/2017.
 */

public interface BaseMundipaggAPI {

    @FormUrlEncoded
    @POST("transacao/criarTransacaoSemToken")
    Call<RespostaTransacaoMundipagg> criarTransacaoSemToken(@Field("valor") String valor,
                                                            @Field("ref_pedido") String refPedido,
                                                            @Field("cartao_bandeira") String cartaoBandeira,
                                                            @Field("cartao_numero") String cartaoNumero,
                                                            @Field("cartao_cvv") String cartaoCvv,
                                                            @Field("cartao_ano") String cartaoAno,
                                                            @Field("cartao_mes") String cartaoMes,
                                                            @Field("cartao_nome") String cartaoNome,
                                                            @Field("numero_parcelas") int numParcelas);

    @FormUrlEncoded
    @POST("transacao/criarTransacaoComToken")
    Call<RespostaTransacaoMundipagg> criarTransacaoComToken(@Field("valor") String valor,
                                                            @Field("ref_pedido") String refPedido,
                                                            @Field("token") String token,
                                                            @Field("numero_parcelas") int numParcelas);

    @GET("transacao/consultarTransacaoPorChavePedido")
    Call<RespostaTransacaoMundipagg> consultarTransacaoPorChavePedido(@Query("chave") String chave);

    @GET("transacao/consultarTransacaoPorReferenciaPedido")
    Call<RespostaTransacaoMundipagg> consultarTransacaoPorReferenciaPedido(@Query("referencia") String referencia);

    @FormUrlEncoded
    @POST("transacao/cancelarTransacaoPorChavePedido")
    Call<RespostaTransacaoMundipagg> cancelarTransacaoPorChavePedido(@Field("chave") String chave);

    @FormUrlEncoded
    @POST("token/criarToken")
    Call<RespostaCriarToken> criarToken(@Field("cartao_bandeira") String cartaoBandeira,
                                        @Field("cartao_numero") String cartaoNumero,
                                        @Field("cartao_mes") String cartaoMes,
                                        @Field("cartao_ano") String cartaoAno,
                                        @Field("cartao_nome") String cartaoNome,
                                        @Field("cartao_check") boolean cartaoCheck,
                                        @Field("endereco_descricao") String enderecoDescricao,
                                        @Field("endereco_numero") String endercoNumero,
                                        @Field("endereco_complemento") String enderecoComplemento,
                                        @Field("endereco_capital") String enderecoCapital,
                                        @Field("endereco_cidade") String enderecoCidade,
                                        @Field("endereco_estado") String enderecoEstado,
                                        @Field("endereco_cep") String enderecoCep,
                                        @Field("endereco_pais") String enderecoPais);

    @GET("token/consultarToken")
    Call<RespostaConsultarToken> consultarToken(@Query("token") String token);

}
