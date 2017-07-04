package br.com.servelojapagamento.interfaces;

import br.com.servelojapagamento.modelo.PedidoPinPadResposta;

/**
 * Created by alexandre on 04/07/2017.
 */

public interface RespostaTransacaoRegistradaServelojaListener {

    void onRespostaTransacaoRegistrada(boolean status, PedidoPinPadResposta pedidoPinPadResposta, String mensagemErro);

}
