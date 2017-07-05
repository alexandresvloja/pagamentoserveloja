package br.com.servelojapagamento.webservice;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import br.com.servelojapagamento.interfaces.RespostaTransacaoRegistradaServelojaListener;
import br.com.servelojapagamento.modelo.ConteudoResposta;
import br.com.servelojapagamento.modelo.ObterChaveAcessoResposta;
import br.com.servelojapagamento.modelo.ParamsRegistrarTransacao;
import br.com.servelojapagamento.modelo.PedidoPinPadResposta;
import br.com.servelojapagamento.modelo.UserMobile;
import br.com.servelojapagamento.preferences.PrefsHelper;
import br.com.servelojapagamento.utils.LoggingInterceptor;
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

    private final String URL = "http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx";
    //    http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx/
//    private final String URL = "https://www.sistemaserveloja.com.br/ServicosWeb/Versao/1.13/Mobile.asmx";
    private Retrofit retrofit;
    private String TAG;
    private PrefsHelper prefsHelper;
    private Activity activity;

    public ServelojaWebService(Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.prefsHelper = new PrefsHelper(activity);
    }

    private void iniciarRetrofit() {
        retrofit = new Retrofit
                .Builder()
                .baseUrl(URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void registrarTransacao(ParamsRegistrarTransacao paramsRegistrarTransacao,
                                   final RespostaTransacaoRegistradaServelojaListener respostaTransacaoRegistradaServelojaListener) {
        iniciarRetrofit();
        Log.d(TAG, "registrarTransacao: ");
        Log.d(TAG, "registrarTransacao: " + paramsRegistrarTransacao);
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
                    PedidoPinPadResposta pinPadResposta = response.body();
                    respostaTransacaoRegistradaServelojaListener.onRespostaTransacaoRegistradaServeloja(true,
                            pinPadResposta, "");
                } else {
                    respostaTransacaoRegistradaServelojaListener.onRespostaTransacaoRegistradaServeloja(false,
                            null, "Erro de comunicação com a Serveloja, tente novamente. | response estava null.");
                }
            }

            @Override
            public void onFailure(Call<PedidoPinPadResposta> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                respostaTransacaoRegistradaServelojaListener.onRespostaTransacaoRegistradaServeloja(false, null, t.getMessage());
            }
        });
    }

    public void obterChaveAcesso(final UserMobile user) {
        iniciarRetrofit();
        BaseAPI baseAPI = retrofit.create(BaseAPI.class);

        Map<String, UserMobile> request = new HashMap<String, UserMobile>();
        request.put("userMobile", user);

        final Call<ObterChaveAcessoResposta> call = baseAPI.obterChaveAcesso(request);
        call.enqueue(new Callback<ObterChaveAcessoResposta>() {
            @Override
            public void onResponse(Call<ObterChaveAcessoResposta> call, Response<ObterChaveAcessoResposta> response) {
                if (response != null) {
                    Log.d(TAG, "obterChaveAcesso " + response.body());
                    ObterChaveAcessoResposta r = response.body();
                    if (r.getCodRetorno() != 2) {
                        // tratar erro
                    } else {
                        salvarDadosUsuario(r);
                    }
                } else {
                    Log.d(TAG, "onResponse: Falha ao acessar WebService");
                }
            }

            @Override
            public void onFailure(Call<ObterChaveAcessoResposta> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void salvarDadosUsuario(ObterChaveAcessoResposta r) {
        Log.d(TAG, r.toString());
        // Coloca as informações obtidas no sharedPreferences
        prefsHelper.salvarChaveAcesso(r.getChaveAcesso());
        prefsHelper.salvarDataExpiracao(r.getDataExpiracao());
        prefsHelper.salvarUsuarioChave(r.getUsuarioChave());
    }

    public void registrarTransacaoSegura(ParamsRegistrarTransacao paramsRegistrarTransacao) {
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
                    .baseUrl("http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx/")
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
                    if (response != null && response.body() != null) {
                        Log.d(TAG, "onResponse: " + response.body());


                    } else if (response != null) {
                        Log.d(TAG, "onResponse: response " + response.toString());
                        Log.d(TAG, "onResponse: message " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<ConteudoResposta> call, Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
