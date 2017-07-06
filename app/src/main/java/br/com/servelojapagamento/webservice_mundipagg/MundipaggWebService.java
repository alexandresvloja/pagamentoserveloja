package br.com.servelojapagamento.webservice_mundipagg;

import android.util.Log;

import br.com.servelojapagamento.interfaces.RespostaTransacaoAplicativoListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class MundipaggWebService {

    private final String URL = "http://serveloja.com.br/lojavirtual/serveloja_mundipagg_ws/api";
    private Retrofit retrofit;
    private String TAG;

    public MundipaggWebService() {
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

    public void criarTransacaoSemToken(ParamsCriarTransacaoSemToken paramsCriarTransacaoSemToken,
                                       final RespostaTransacaoAplicativoListener respostaTransacaoAplicativoListener) {
        Log.d(TAG, "criarTransacaoSemToken: ");
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
                Log.d(TAG, "onResponse: ");
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    String mensagem = "Resposta criar transação gerada com sucesso!";
                    respostaTransacaoAplicativoListener.onRespostaCriarTransacao(true, mensagem, response.body());
                } else {
                    String mensagem = "Resposta criar transação null | Resposta null";
                    respostaTransacaoAplicativoListener.onRespostaCriarTransacao(false, mensagem, null);
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                respostaTransacaoAplicativoListener.onRespostaCriarTransacao(false, t.getMessage(), null);
            }
        });
    }

    public void consultarTransacaoPorChavePedido(String chave,
                                                 final RespostaTransacaoAplicativoListener respostaTransacaoAplicativoListener) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.consultarTransacaoPorChavePedido(
                chave
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    String mensagem = "Resposta consultar transação por chave pedido, gerada com sucesso!";
                    respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(true, mensagem, response.body());
                } else {
                    String mensagem = "Resposta consultar transação por chave pedido null | Resposta null";
                    respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(false, mensagem, null);
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(false, t.getMessage(), null);
            }
        });
    }

    public void consultarTransacaoPorReferenciaPedido(String referencia,
                                                      final RespostaTransacaoAplicativoListener respostaTransacaoAplicativoListener) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.consultarTransacaoPorReferenciaPedido(
                referencia
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    String mensagem = "Resposta consultar transação por referência pedido, gerada com sucesso!";
                    respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(true, mensagem, response.body());
                } else {
                    String mensagem = "Resposta consultar transação por referência pedido null | Resposta null";
                    respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(false, mensagem, null);
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(false, t.getMessage(), null);
            }
        });

    }

    public void cancelarTransacaoPorChavePedido(String chave,
                                                final RespostaTransacaoAplicativoListener respostaTransacaoAplicativoListener) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaTransacaoMundipagg> call = baseMundipaggAPI.cancelarTransacaoPorChavePedido(
                chave
        );

        call.enqueue(new Callback<RespostaTransacaoMundipagg>() {
            @Override
            public void onResponse(Call<RespostaTransacaoMundipagg> call, Response<RespostaTransacaoMundipagg> response) {
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    String mensagem = "Resposta consultar transação por chave pedido, gerada com sucesso!";
                    respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(true, mensagem, response.body());
                }else{
                    String mensagem = "Resposta consultar transação por chave pedido null | Resposta null";
                    respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(false, mensagem, null);
                }
            }

            @Override
            public void onFailure(Call<RespostaTransacaoMundipagg> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                respostaTransacaoAplicativoListener.onRespostaConsultarTransacao(false, t.getMessage(), null);
            }
        });
    }

    public void criarToken(ParamsCriarTokenCartao cartao, ParamsCriarTokenEndereco endereco,
                           final RespostaTransacaoAplicativoListener respostaTransacaoAplicativoListener) {

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
                Log.d(TAG, "onResponse: ");
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    String mensagem = "Resposta do token gerada com sucesso!";
                    respostaTransacaoAplicativoListener.onRespostaTokenGerado(true, mensagem, response.body());
                } else {
                    String mensagem = "Resposta do token null | Resposta null";
                    respostaTransacaoAplicativoListener.onRespostaTokenGerado(false, mensagem, null);
                }
            }

            @Override
            public void onFailure(Call<RespostaCriarToken> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                respostaTransacaoAplicativoListener.onRespostaTokenGerado(false, t.getMessage(), null);
            }
        });
    }

    public void consultarToken(String token,
                               final RespostaTransacaoAplicativoListener respostaTransacaoAplicativoListener) {
        BaseMundipaggAPI baseMundipaggAPI = retrofit.create(BaseMundipaggAPI.class);
        final Call<RespostaConsultarToken> call = baseMundipaggAPI.consultarToken(
                token
        );

        call.enqueue(new Callback<RespostaConsultarToken>() {
            @Override
            public void onResponse(Call<RespostaConsultarToken> call, Response<RespostaConsultarToken> response) {
                Log.d(TAG, "onResponse: ");
                if (response != null && response.body() != null) {
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    String mensagem = "Resposta consultar token gerada com sucesso!";
                    respostaTransacaoAplicativoListener.onRespostaConsultarToken(true, mensagem, response.body());
                } else {
                    String mensagem = "Resposta de consultar token null | Resposta null";
                    respostaTransacaoAplicativoListener.onRespostaConsultarToken(false, mensagem, null);
                }
            }

            @Override
            public void onFailure(Call<RespostaConsultarToken> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                respostaTransacaoAplicativoListener.onRespostaConsultarToken(false, t.getMessage(), null);
            }
        });
    }

}
