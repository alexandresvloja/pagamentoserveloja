package br.com.servelojapagamento.webservice;

import java.util.Map;

import br.com.servelojapagamento.modelo.AdicionarTerminalResposta;
import br.com.servelojapagamento.modelo.ConteudoResposta;
import br.com.servelojapagamento.modelo.ObterChaveAcessoResposta;
import br.com.servelojapagamento.modelo.PedidoPinPadResposta;
import br.com.servelojapagamento.modelo.UserMobile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by alexandre on 04/07/2017.
 */

public interface BaseAPI {

    @FormUrlEncoded
    @POST("AdicionarTerminalGsurf")
    Call<AdicionarTerminalResposta>
    adicionarTerminalGsurf(
            @Field("ChaveAcesso") String chaveAcesso,
            @Field("MACAddress") String macaddress);

    @POST("ObterChaveAcessoSimples")
    Call<ObterChaveAcessoResposta> obterChaveAcesso(@Body Map<String, UserMobile> user);

    @FormUrlEncoded
    @POST("EfetuarTransacaoMaquininhaESitef")
    Call<ConteudoResposta> realizarTransacaoSegura(@Field("ChaveAcesso") String chaveAcesso,
                                                   @Field("CodigoChip") String codChip,
                                                   @Field("Bandeira") String bandeira,
                                                   @Field("CpfCnpjComprador") String cpfCnpjComprador,
                                                   @Field("NumeroCartao") String numeroCartao,
                                                   @Field("CodSeguranca") String codSeguranca,
                                                   @Field("DataValidade") String dataValidade,
                                                   @Field("Valor") String valor,
                                                   @Field("QuantidadeParcelas") String quantidadeParcelas,
                                                   @Field("DDDCelular") String dDDCelular,
                                                   @Field("NumeroCelular") String numeroCelular,
                                                   @Field("EnderecoIPComprador") String enderecoIPComprador,
                                                   @Field("Observacao") String observacao,
                                                   @Field("Origem") String origem,
                                                   @Field("SenhaCartao") String senhaCartao,
                                                   @Field("LatitudeLongitude") String latitudeLongitude,
                                                   @Field("ValorSemTaxas") String valorSemTaxas,
                                                   @Field("PinpadId") String pinpadId,
                                                   @Field("PinpadMac") String pinpadMac,
                                                   @Field("NomeTitular") String nomeTitular,
                                                   @Field("NrCartaoComprovante") String nrCartaoComprovante);

    @FormUrlEncoded
    @POST("RegistrarTransacaoPinPad")
    Call<PedidoPinPadResposta> registrarTransacaoPinPad(
            @Field("ChaveAcesso") String ChaveAcesso,
            @Field("Valor") String Valor,
            @Field("Parcelas") int Parcelas,
            @Field("Observacao") String Observacao,
            @Field("TipoTransacao") int TipoTransacao,
            @Field("PinpadId") String PinpadId,
            @Field("PinpadMac") String PinpadMac,
            @Field("LatitudeLongitude") String LatitudeLongitude,
            @Field("ValorSemTaxas") String ValorSemTaxas,
            @Field("Cartao") String Cartao,
            @Field("NSUHost") String NSUHost,
            @Field("NSUSitef") String NSUSitef,
            @Field("CodAutorizacao") String CodAutorizacao,
            @Field("UtilizouTarja") boolean UtilizouTarja,
            @Field("Status") String Status,
            @Field("Data") String Data,
            @Field("NumeroCartao") String NumeroCartao,
            @Field("Problemas") String Problemas,
            @Field("DDD") String DDD,
            @Field("Telefone") String Telefone,
            @Field("ComprovanteOriginal") String ComprovanteOriginal);

}
