package br.com.servelojapagamento.interfaces;

import br.com.servelojapagamento.webservice_mundipagg.RespostaConsultarToken;
import br.com.servelojapagamento.webservice_mundipagg.RespostaCriarToken;
import br.com.servelojapagamento.webservice_mundipagg.RespostaTransacaoMundipagg;

/**
 * Created by alexandre on 06/07/2017.
 */

public interface RespostaTransacaoAplicativoListener {

    void onRespostaTokenGerado(boolean status, String mensagem, RespostaCriarToken token);

    void onRespostaConsultarToken(boolean status, String mensagem, RespostaConsultarToken consulta);

    void onRespostaCriarTransacao(boolean status, String mensagem, RespostaTransacaoMundipagg respostaTransacao);

    void onRespostaConsultarTransacao(boolean status, String mensagem, RespostaTransacaoMundipagg consulta);

}
