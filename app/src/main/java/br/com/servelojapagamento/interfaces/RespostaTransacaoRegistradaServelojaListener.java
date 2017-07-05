package br.com.servelojapagamento.interfaces;

import br.com.servelojapagamento.modelo.PedidoPinPadResposta;

/**
 * Created by alexandre on 04/07/2017.
 */

public interface RespostaTransacaoRegistradaServelojaListener {

    void onRespostaTransacaoRegistradaServeloja(boolean status, PedidoPinPadResposta pinPadResposta, String mensagemErro);

}
