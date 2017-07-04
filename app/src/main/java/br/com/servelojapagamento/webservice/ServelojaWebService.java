package br.com.servelojapagamento.webservice;

import android.app.Activity;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.servelojapagamento.interfaces.RespostaTransacaoRegistradaServelojaListener;
import br.com.servelojapagamento.modelo.ObterChaveAcessoResposta;
import br.com.servelojapagamento.modelo.ParamsRegistrarTransacao;
import br.com.servelojapagamento.modelo.PedidoPinPadResposta;
import br.com.servelojapagamento.modelo.UserMobile;
import br.com.servelojapagamento.preferences.PrefsHelper;
import br.com.servelojapagamento.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandre on 04/07/2017.
 */

public class ServelojaWebService {

    private final String URL = "http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx/";
    private Retrofit retrofit;
    private String TAG;
    private PrefsHelper prefsHelper;
    private Activity activity;

    public ServelojaWebService(Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.prefsHelper = new PrefsHelper(activity);
        iniciarRetrofit();
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
        Log.d(TAG, "registrarTransacao: ");
        BaseAPI baseAPI = retrofit.create(BaseAPI.class);
        final Call<PedidoPinPadResposta> call = baseAPI.registrarTransacaoPinPad(
                paramsRegistrarTransacao.getChaveAcesso(),
                paramsRegistrarTransacao.getValor(),
                Integer.valueOf(paramsRegistrarTransacao.getNumParcelas()),
                paramsRegistrarTransacao.getDescricao(),
                paramsRegistrarTransacao.getTipoTransacao(),
                paramsRegistrarTransacao.getPinPadId(),
                paramsRegistrarTransacao.getPinPadMac(),
                paramsRegistrarTransacao.getLatLng(),
                paramsRegistrarTransacao.getValorSemTaxas(),
                Utils.encriptar(paramsRegistrarTransacao.getBandeiraCartao()),
                paramsRegistrarTransacao.getNsuHost(),
                paramsRegistrarTransacao.getNsuSitef(),
                paramsRegistrarTransacao.getCodAutorizacao(),
                paramsRegistrarTransacao.isUsoTarja(),
                paramsRegistrarTransacao.getStatusTransacao(),
                paramsRegistrarTransacao.getDataTransacao(),
                Utils.encriptar(paramsRegistrarTransacao.getNumCartao()),
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
                    PedidoPinPadResposta pedidoPinPadResposta = response.body();
                    respostaTransacaoRegistradaServelojaListener.onRespostaTransacaoRegistrada(true,
                            pedidoPinPadResposta, "");
                } else {
                    respostaTransacaoRegistradaServelojaListener.onRespostaTransacaoRegistrada(false,
                            null, "Erro de comunicação com a Serveloja, tente novamente. | response estava null.");
                }
            }

            @Override
            public void onFailure(Call<PedidoPinPadResposta> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                respostaTransacaoRegistradaServelojaListener.onRespostaTransacaoRegistrada(false, null, t.getMessage());
            }
        });
    }

    public void obterChaveAcesso(final UserMobile user) {
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

//        StringBuilder franquiasString = new StringBuilder();
//        StringBuilder franquiasCod = new StringBuilder();
//
//        if (r.franquias.length > 1) {
//            for (int i = 0; i < r.franquias.length; i++) {
//                franquiasString.append(r.franquias[i].nomeFranquia
//                        + "|");
//                franquiasCod.append(r.franquias[i].codigoFranquia
//                        + "|");
//            }
//            editor.putString("Franquias", franquiasString.toString());
//            editor.putString("FranquiasCod", franquiasCod.toString());
//            editor.remove("codigoFranquia");
//            editor.remove("nomeFranquia");
//
//        } else if (r.franquias.length == 1) {
//            editor.putString("codigoFranquia",
//                    r.franquias[0].codigoFranquia);
//            editor.putString("nomeFranquia",
//                    r.franquias[0].nomeFranquia);
//        } else {
//            editor.remove("codigoFranquia");
//            editor.remove("nomeFranquia");
//        }
//
//        if (r.codigoPerfil.equals("1")) {
//            editor.putString("Perfil", "Consultor");
//        } else if (r.codigoPerfil.equals("2")) {
//            editor.putString("Perfil", "Lojista");
//        } else if (r.codigoPerfil.equals("3")) {
//            editor.putString("Perfil", "Admin");
//        }
//
//        // editor.putBoolean("PodeVenderEstrangeiro",
//        // cca.podeVenderParaEstrangeiro);
//        String permissoes = "";
//        for (int i = 0; i < r.permissoes.length; i++) {
//            if (i + 1 == r.permissoes.length) {
//                permissoes += r.permissoes[i];
//            } else {
//                permissoes += r.permissoes[i] + ",";
//            }
//        }
//        editor.putString("valorObrigatoriedadeDocumento", r.valorObrigatoriedadeDocumento);
//        editor.putString("PermissoesUsuario", permissoes);
//        editor.commit();
    }

}
