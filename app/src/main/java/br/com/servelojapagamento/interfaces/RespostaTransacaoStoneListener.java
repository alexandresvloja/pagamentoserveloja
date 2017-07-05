package br.com.servelojapagamento.interfaces;

import stone.providers.TransactionProvider;

/**
 * Created by Alexandre on 03/07/2017.
 */

public interface RespostaTransacaoStoneListener {

    void onRespostaTransacaoStone(boolean status, TransactionProvider transactionProvider);

}
