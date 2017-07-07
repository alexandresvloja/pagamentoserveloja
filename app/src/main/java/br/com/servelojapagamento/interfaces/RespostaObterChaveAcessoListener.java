package br.com.servelojapagamento.interfaces;

import br.com.servelojapagamento.webservice_serveloja.ObterChaveAcessoResposta;

/**
 * Created by Alexandre on 06/07/2017.
 */

public interface RespostaObterChaveAcessoListener {

    void onRespostaObterChaveAcesso(boolean status, ObterChaveAcessoResposta resposta, String mensagem);

}
