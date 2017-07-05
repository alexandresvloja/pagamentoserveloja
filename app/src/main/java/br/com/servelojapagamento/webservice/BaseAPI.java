package br.com.servelojapagamento.webservice;

import java.util.Map;

import br.com.servelojapagamento.modelo.AdicionarTerminalResposta;
import br.com.servelojapagamento.modelo.ConteudoResposta;
import br.com.servelojapagamento.modelo.ObterChaveAcessoResposta;
import br.com.servelojapagamento.modelo.PedidoPinPadResposta;
import br.com.servelojapagamento.modelo.UserMobile;
import okhttp3.RequestBody;
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
    @POST("EfetuarVendaCreditoMobile")
    Call<ConteudoResposta> realizarTransacaoSegura(@Field("ChaveAcesso") String chaveAcesso,
                                                   @Field("CodigoChip") String codChip,
                                                   @Field("Bandeira") String bandeira,
                                                   @Field("CpfCnpjComprador") String cpfCnpjComprador,
                                                   @Field("NrCartao") String numeroCartao,
                                                   @Field("CodSeguranca") String codSeguranca,
                                                   @Field("DataValidade") String dataValidade,
                                                   @Field("Valor") String valor,
                                                   @Field("QtParcela") String quantidadeParcelas,
                                                   @Field("DDDCelular") String dDDCelular,
                                                   @Field("NrCelular") String numeroCelular,
                                                   @Field("EnderecoIPComprador") String enderecoIPComprador,
                                                   @Field("DsObservacao") String observacao,
                                                   @Field("CodigoFranquia") int codigoFranquia,
                                                   @Field("CpfCnpjAdesao") String cpfCnpjAdesao,
                                                   @Field("NomeTitularAdesao") String nomeTitularAdesao,
                                                   @Field("UtilizouLeitor") boolean utilizouLeitor,
                                                   @Field("Origem") String origem,
                                                   @Field("ClienteInformadoCartaoInvalido") boolean clienteInformadoCartaoInvalido,
                                                   @Field("SenhaCartao") String senhaCartao,
                                                   @Field("LatitudeLongitude") String latitudeLongitude,
                                                   @Field("ValorSemTaxas") String valorSemTaxas,
                                                   @Field("nmTitular") String nomeTitular,
                                                   @Field("OutrasBandeiras") boolean outrasBandeiras);

    @POST("EfetuarVendaCreditoMobile")
    Call<ConteudoResposta> realizarTransacaoSeguraJson(@Body RequestBody chaveAcesso);

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
