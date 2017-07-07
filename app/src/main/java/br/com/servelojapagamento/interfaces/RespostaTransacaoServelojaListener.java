package br.com.servelojapagamento.interfaces;

import br.com.servelojapagamento.webservice_serveloja.PedidoPinPadResposta;

/**
 * Created by alexandre on 04/07/2017.
 */

public interface RespostaTransacaoServelojaListener {

    void onRespostaTransacaoServeloja(int status, Object object, String mensagem);

}
