package br.com.servelojapagamento.interfaces;

import br.com.servelojapagamento.webservice_serveloja.PedidoPinPadResposta;

/**
 * Created by alexandre on 04/07/2017.
 */

public interface RespostaTransacaoRegistradaServelojaListener {

    void onRespostaTransacaoRegistradaServeloja(boolean status, PedidoPinPadResposta pinPadResposta, String mensagemErro);

    void onRespostaTransacaoStatusServeloja(int status);

}
