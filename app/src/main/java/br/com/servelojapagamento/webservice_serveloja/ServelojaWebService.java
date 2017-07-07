package br.com.servelojapagamento.webservice_serveloja;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.servelojapagamento.interfaces.RespostaObterChaveAcessoListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoServelojaListener;
import br.com.servelojapagamento.preferences.PrefsHelper;
import br.com.servelojapagamento.utils.LoggingInterceptor;
import br.com.servelojapagamento.utils.TransacaoEnum;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandre on 04/07/2017.
 */

public class ServelojaWebService {

    private final String URL_DEV = "http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx";
    //    http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx/
    private final String URL_PRO = "https://www.sistemaserveloja.com.br/ServicosWeb/Versao/1.13/Mobile.asmx";
    private String URL = URL_DEV;
    private Retrofit retrofit;
    private String TAG;
    private PrefsHelper prefsHelper;
    private Activity activity;
    private RespostaTransacaoServelojaListener respostaTransacaoServelojaListener;

    public ServelojaWebService(Activity activity, RespostaTransacaoServelojaListener respostaTransacaoServelojaListener) {
        this.TAG = getClass().getSimpleName();
        this.prefsHelper = new PrefsHelper(activity);
        this.respostaTransacaoServelojaListener = respostaTransacaoServelojaListener;
    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void setModoDesenvolvedor(boolean modoDesenvolvedor) {
        if (modoDesenvolvedor)
            URL = URL_DEV;
        else
            URL = URL_PRO;
    }

    public void registrarTransacao(ParamsRegistrarTransacao paramsRegistrarTransacao,
                                   final RespostaTransacaoServelojaListener respostaTransacaoServelojaListener) {

        this.respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                TransacaoEnum.StatusSeveloja.ENVIANDO_TRANSACAO_SERVELOJA,
                null,
                "Iniciando envio da transação para base de dados Serveloja.");

        iniciarRetrofit();
        BaseAPI baseAPI = retrofit.create(BaseAPI.class);
        final Call<PedidoPinPadResposta> call = baseAPI.registrarTransacaoPinPad(
                paramsRegistrarTransacao.getChaveAcesso(),
                paramsRegistrarTransacao.getValor(),
                paramsRegistrarTransacao.getNumParcelas(),
                paramsRegistrarTransacao.getDescricao(),
                paramsRegistrarTransacao.getTipoTransacao(),
                paramsRegistrarTransacao.getPinPadId(),
                paramsRegistrarTransacao.getPinPadMac(),
                paramsRegistrarTransacao.getLatLng(),
                paramsRegistrarTransacao.getValorSemTaxas(),
                paramsRegistrarTransacao.getBandeiraCartao(),
                paramsRegistrarTransacao.getNsuHost(),
                paramsRegistrarTransacao.getNsuSitef(),
                paramsRegistrarTransacao.getCodAutorizacao(),
                paramsRegistrarTransacao.isUsoTarja(),
                paramsRegistrarTransacao.getStatusTransacao(),
                paramsRegistrarTransacao.getDataTransacao(),
                paramsRegistrarTransacao.getNumCartao(),
                paramsRegistrarTransacao.getProblemas(),
                paramsRegistrarTransacao.getDddTelefone(),
                paramsRegistrarTransacao.getNumTelefone(),
                paramsRegistrarTransacao.getComprovante()
        );
        call.enqueue(new Callback<PedidoPinPadResposta>() {
            @Override
            public void onResponse(Call<PedidoPinPadResposta> call, Response<PedidoPinPadResposta> response) {
                Log.d(TAG, "onResponse: ");
                if (response != null && response.body() != null) {
                    String mensagem = "Resposta registrar transação gerada com sucesso!";
                    respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                            TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SERVELOJA_SUCESSO,
                            response.body(), mensagem);
                } else {
                    String mensagem = "Resposta registrar transação null | Resposta null";
                    respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                            TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SERVELOJA_FALHA,
                            null, mensagem);
                }
            }

            @Override
            public void onFailure(Call<PedidoPinPadResposta> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                        TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SERVELOJA_FALHA,
                        null,
                        t.getMessage());
            }
        });
    }

    public void obterChaveAcesso(final UserMobile user, final RespostaObterChaveAcessoListener respostaObterChaveAcessoListener) {
        iniciarRetrofit();
        BaseAPI baseAPI = retrofit.create(BaseAPI.class);

        Map<String, UserMobile> request = new HashMap<String, UserMobile>();
        request.put("userMobile", user);

        final Call<ObterChaveAcessoResposta> call = baseAPI.obterChaveAcesso(request);
        call.enqueue(new Callback<ObterChaveAcessoResposta>() {
            @Override
            public void onResponse(Call<ObterChaveAcessoResposta> call, Response<ObterChaveAcessoResposta> response) {
                if (response != null && response.body() != null) {
                    String mensagem = "Resposta obter chave de acesso gerada com sucesso!";
                    respostaObterChaveAcessoListener.onRespostaObterChaveAcesso(true, response.body(), mensagem);
                } else {
                    String mensagem = "Resposta obter chave de acesso null | Resposta null";
                    respostaObterChaveAcessoListener.onRespostaObterChaveAcesso(false, response.body(), mensagem);
                }
            }

            @Override
            public void onFailure(Call<ObterChaveAcessoResposta> call, Throwable t) {
                respostaObterChaveAcessoListener.onRespostaObterChaveAcesso(false, null, t.getMessage());
            }
        });
    }

    public void registrarTransacaoSegura(ParamsRegistrarTransacao paramsRegistrarTransacao) {

        respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                TransacaoEnum.StatusSeveloja.ENVIANDO_TRANSACAO_SERVELOJA,
                null,
                "Iniciando envio da transação para base de dados Serveloja.");

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("ChaveAcesso", paramsRegistrarTransacao.getChaveAcesso());
            jsonObj.put("CodChip", paramsRegistrarTransacao.getImei());
            jsonObj.put("Bandeira", paramsRegistrarTransacao.getBandeiraCartao());
            jsonObj.put("CpfCnpjComprador", paramsRegistrarTransacao.getCpfCNPJ());
            jsonObj.put("nmTitular", paramsRegistrarTransacao.getNomeTitularCartao());
            jsonObj.put("NrCartao", paramsRegistrarTransacao.getNumCartao());
            jsonObj.put("CodSeguranca", paramsRegistrarTransacao.getCodSeguracaCartao());
            jsonObj.put("DataValidade", paramsRegistrarTransacao.getDataValidadeCartao());
            jsonObj.put("Valor", paramsRegistrarTransacao.getValor());
            jsonObj.put("ValorSemTaxas", paramsRegistrarTransacao.getValorSemTaxas());
            jsonObj.put("QtParcela", paramsRegistrarTransacao.getNumParcelas());
            jsonObj.put("DDDCelular", paramsRegistrarTransacao.getDddTelefone());
            jsonObj.put("NrCelular", paramsRegistrarTransacao.getNumTelefone());
            jsonObj.put("EnderecoIPComprador", paramsRegistrarTransacao.getIp());
            jsonObj.put("DsObservacao", paramsRegistrarTransacao.getDescricao());
            jsonObj.put("CodigoFranquia", "0");
            jsonObj.put("CpfCnpjAdesao", paramsRegistrarTransacao.getCpfCnpjAdesao());
            jsonObj.put("NomeTitularAdesao", "");
            jsonObj.put("UtilizouLeitor", false);
            jsonObj.put("Origem", "A");
            jsonObj.put("ClienteInformadoCartaoInvalido", false);
            jsonObj.put("LatitudeLongitude", paramsRegistrarTransacao.getLatLng());
            jsonObj.put("SenhaCartao", "");
            jsonObj.put("OutrasBandeiras", true);

            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(URL + "/")
                    .client(new OkHttpClient.Builder()
                            .addInterceptor(new LoggingInterceptor())
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            BaseAPI baseAPI = retrofit.create(BaseAPI.class);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    (jsonObj).toString());
            final Call<ConteudoResposta> call = baseAPI.realizarTransacaoSeguraJson(body);

            call.enqueue(new Callback<ConteudoResposta>() {
                @Override
                public void onResponse(Call<ConteudoResposta> call, Response<ConteudoResposta> response) {
                    Log.d(TAG, "onResponse: ");
                    if (response != null && response.body() != null) {
                        String mensagem = "Resposta registrar transação segura gerada com sucesso!";
                        respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                                TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SEGURA_SERVELOJA_SUCESSO,
                                response.body(),
                                mensagem);
                    } else {
                        String mensagem = "Resposta registrar transação segura null | Resposta null";
                        respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                                TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SEGURA_SERVELOJA_FALHA,
                                response.body(),
                                mensagem);
                    }
                }

                @Override
                public void onFailure(Call<ConteudoResposta> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                    respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                            TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SEGURA_SERVELOJA_FALHA,
                            null,
                            t.getMessage());
                }
            });

        } catch (JSONException e) {
            respostaTransacaoServelojaListener.onRespostaTransacaoServeloja(
                    TransacaoEnum.StatusSeveloja.REGISTRO_TRANSACAO_SEGURA_SERVELOJA_FALHA,
                    "Exception",
                    e.getMessage());
        }
    }

}
