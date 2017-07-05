package br.com.servelojapagamento.mundipagg;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class MundipaggWebServiceUtils {

    private final String URL = "http://serveloja.com.br/lojavirtual/serveloja_mundipagg_ws/api";
    private Retrofit retrofit;
    private String TAG;

    public MundipaggWebServiceUtils() {
        TAG = getClass().getSimpleName();
        iniciarRetrofit();
    }

    private void iniciarRetrofit() {
        this.retrofit = new Retrofit
                .Builder()
                .baseUrl(URL + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void criarTransacaoSemToken(ParamsCriarTransacaoSemToken paramsCriarTransacaoSemToken) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.criarTransacaoSemToken(
                paramsCriarTransacaoSemToken.getValor(),
                paramsCriarTransacaoSemToken.getIdTransacao(),
                paramsCriarTransacaoSemToken.getCartaoBandeira(),
                paramsCriarTransacaoSemToken.getCartaoNumero(),
                paramsCriarTransacaoSemToken.getCartaoCvv(),
                paramsCriarTransacaoSemToken.getCartaoAno(),
                paramsCriarTransacaoSemToken.getCartaoMes(),
                paramsCriarTransacaoSemToken.getCartaoNome(),
                paramsCriarTransacaoSemToken.getNumParcela()
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void consultarTransacaoPorChavePedido(String chave) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.consultarTransacaoPorChavePedido(
                chave
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void consultarTransacaoPorReferenciaPedido(String referencia) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.consultarTransacaoPorReferenciaPedido(
                referencia
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void cancelarTransacaoPorChavePedido(String chave) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.cancelarTransacaoPorChavePedido(
                chave
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void criarToken(ParamsCriarTokenCartao cartao, ParamsCriarTokenEndereco endereco) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaCriarToken> call = baseMundipaggAPI.criarToken(
                cartao.getCartaoBandeira(),
                cartao.getCartaoNumero(),
                cartao.getCartaoMes(),
                cartao.getCartaoAno(),
                cartao.getCartaoNome(),
                cartao.isCartaoCheck(),
                endereco.getEnderecoDescricao(),
                endereco.getEnderecoNumero(),
                endereco.getEnderecoComplemento(),
                endereco.getEnderecoCapital(),
                endereco.getEnderecoCidade(),
                endereco.getEnderecoEstado(),
                endereco.getEnderecoCep(),
                endereco.getEnderecoPais()
        );

        call.enqueue(new Callback<RespostaCriarToken>() {
            @Override
            public void onResponse(Call<RespostaCriarToken> call, Response<RespostaCriarToken> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RespostaCriarToken> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void consultarToken(String token) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaConsultarToken> call = baseMundipaggAPI.consultarToken(
                token
        );

        call.enqueue(new Callback<RespostaConsultarToken>() {
            @Override
            public void onResponse(Call<RespostaConsultarToken> call, Response<RespostaConsultarToken> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RespostaConsultarToken> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

}
