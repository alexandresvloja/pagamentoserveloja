package br.com.servelojapagamento.utils;

import android.app.Activity;
import android.util.Log;

import java.util.List;

import br.com.servelojapagamento.interfaces.RespostaTransacaoRegistradaServelojaListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoStoneListener;
import br.com.servelojapagamento.modelo.ParamsRegistrarTransacao;
import br.com.servelojapagamento.modelo.PedidoPinPadResposta;
import br.com.servelojapagamento.preferences.PrefsHelper;
import br.com.servelojapagamento.webservice.ServelojaWebService;
import stone.application.enums.ErrorsEnum;
import stone.application.enums.TransactionStatusEnum;
import stone.database.transaction.TransactionDAO;
import stone.database.transaction.TransactionObject;

/**
 * Efetua a comunicação entre a transação da Stone com a WebService da Serveloja
 * Created by alexandre on 04/07/2017.
 */

public class TransacaoUtils implements RespostaTransacaoStoneListener, RespostaTransacaoRegistradaServelojaListener {

    private Activity activity;
    private String TAG;
    private StoneUtils stoneUtils;
    private ServelojaWebService servelojaWebService;
    private ParamsRegistrarTransacao paramsRegistrarTransacao;
    private PrefsHelper prefsHelper;

    public TransacaoUtils(Activity activity) {
        this.activity = activity;
        this.TAG = getClass().getSimpleName();
        this.stoneUtils = new StoneUtils(activity);
        this.paramsRegistrarTransacao = new ParamsRegistrarTransacao();
        this.prefsHelper = new PrefsHelper(activity);
        this.servelojaWebService = new ServelojaWebService(activity);
    }

    public void iniciarTransacao(String valor, int tipoTransacao, int qntParcelas, boolean isTransacaoTarja) {
        Log.d(TAG, "iniciarTransacao: ");
        paramsRegistrarTransacao.setValor(valor);
        paramsRegistrarTransacao.setValorSemTaxas(valor);
        paramsRegistrarTransacao.setTipoTransacao(tipoTransacao);
        paramsRegistrarTransacao.setNumParcelas(qntParcelas);
        paramsRegistrarTransacao.setValorSemTaxas(valor);
        paramsRegistrarTransacao.setPinPadId(prefsHelper.getPinpadModelo());
        paramsRegistrarTransacao.setPinPadMac(prefsHelper.getPinpadMac());
        paramsRegistrarTransacao.setLatLng(prefsHelper.getLocalizacao());
        paramsRegistrarTransacao.setNsuSitef("");
        paramsRegistrarTransacao.setComprovante("");
        paramsRegistrarTransacao.setDescricao("");
        paramsRegistrarTransacao.setProblemas("");
        paramsRegistrarTransacao.setDddTelefone("");
        paramsRegistrarTransacao.setNumTelefone("");
        paramsRegistrarTransacao.setUsoTarja(isTransacaoTarja);
        paramsRegistrarTransacao.setDataTransacao(Utils.getDataAtualStr());
        // chamando procedimento de transação da Stone, e sua resposta será trazida
        // no Callback onRespostaTransacao
        stoneUtils.iniciarTransacao(valor, tipoTransacao, qntParcelas, this);
    }

    private void setupParametrosParaRegistrarTransacao(TransactionObject dadosTransacao) {
        Log.d(TAG, "setupParametrosParaRegistrarTransacao: ");
        // parametros retornado referente a transação atual (última transação)
        String cartaoNomeTitular = dadosTransacao.getCardHolderName().trim();
        String cartaoBin = dadosTransacao.getCardHolderNumber().substring(0, 6);
        String cartaoNumero = dadosTransacao.getCardHolderNumber();
        String codAutorizacao = dadosTransacao.getAuthorizationCode();
        String nsuHost = dadosTransacao.getRecipientTransactionIdentification();
        String cartaoBandeira = Utils.obterBandeiraPorBin(cartaoBin);
        String statusTransacao = getStatusTransacao(dadosTransacao.getTransactionStatus());

        // set parametros salvo nas preferencias
        paramsRegistrarTransacao.setChaveAcesso(prefsHelper.getChaveAcesso());
        paramsRegistrarTransacao.setBandeiraCartao(Utils.encriptar(cartaoBandeira));
        paramsRegistrarTransacao.setNsuHost(nsuHost);
        paramsRegistrarTransacao.setCodAutorizacao(codAutorizacao);
        paramsRegistrarTransacao.setStatusTransacao(statusTransacao);
        paramsRegistrarTransacao.setNumCartao(Utils.encriptar(cartaoNumero));
        Log.d(TAG, "setupParametrosParaRegistrarTransacao: " + paramsRegistrarTransacao.toString());
    }

    @Override
    public void onRespostaTransacao(boolean status, List<ErrorsEnum> listaErros) {
        Log.d(TAG, "onRespostaTransacao: status " + status);
        Log.d(TAG, "onRespostaTransacao: listaErros " + listaErros.toString());
        if (status) {
            if (listaErros.size() > 0) {

            } else {
                TransactionDAO transactionDAO = new TransactionDAO(activity);
                TransactionObject transactionObject = transactionDAO.findTransactionWithId(
                        transactionDAO.getLastTransactionId());
                setupParametrosParaRegistrarTransacao(transactionObject);
                servelojaWebService.registrarTransacao(paramsRegistrarTransacao, this);
            }
        }
    }

    private String getStatusTransacao(TransactionStatusEnum transactionStatusEnum) {
        switch (transactionStatusEnum) {
            case APPROVED:
                return "APR";
            case DECLINED:
                return "DEC";
            case REJECTED:
                return "REJ";
        }
        return "";
    }

    @Override
    public void onRespostaTransacaoRegistrada(boolean status, PedidoPinPadResposta pedidoPinPadResposta,
                                              String mensagemErro) {
        Log.d(TAG, "onRespostaTransacaoRegistrada: status " + status);
        Log.d(TAG, "onRespostaTransacaoRegistrada: mensagemErro " + mensagemErro);
        if (status) {
            Log.d(TAG, "onRespostaTransacaoRegistrada: PedidoPinPadResposta " + pedidoPinPadResposta.toString());
        }

    }
}
