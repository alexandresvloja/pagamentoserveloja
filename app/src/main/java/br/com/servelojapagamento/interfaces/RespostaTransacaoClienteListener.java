package br.com.servelojapagamento.interfaces;

/**
 * Created by alexandre on 05/07/2017.
 */

public interface RespostaTransacaoClienteListener {

    void onRespostaTransacaoCliente(int status, Object object, String mensagem);

}
