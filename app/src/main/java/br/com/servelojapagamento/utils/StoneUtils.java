package br.com.servelojapagamento.utils;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.servelojapagamento.interfaces.RespostaInstalacaoTabelasStone;
import br.com.servelojapagamento.interfaces.RespostaTransacaoStoneListener;
import stone.application.StoneStart;
import stone.application.enums.InstalmentTransactionEnum;
import stone.application.enums.TypeOfTransactionEnum;
import stone.application.interfaces.StoneCallbackInterface;
import stone.cache.ApplicationCache;
import stone.environment.Environment;
import stone.providers.ActiveApplicationProvider;
import stone.providers.DownloadTablesProvider;
import stone.providers.TransactionProvider;
import stone.user.UserModel;
import stone.utils.GlobalInformations;
import stone.utils.Stone;
import stone.utils.StoneTransaction;

import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.A_VISTA;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.CINCO_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.DEZ_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.DOZE_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.DUAS_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.NOVE_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.OITO_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.ONZE_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.QUATRO_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.SEIS_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.SETE_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.QntParcelas.TRES_PARCELAS_SEM_JUROS;
import static br.com.servelojapagamento.utils.TransacaoEnum.TipoTransacao.CREDITO;
import static br.com.servelojapagamento.utils.TransacaoEnum.TipoTransacao.DEBITO;

/**
 * Created by Alexandre on 03/07/2017.
 */

public class StoneUtils {

    private Activity activity;
    private String TAG;
    private boolean modoDesenvolvedor;

    public StoneUtils(Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.activity = activity;
    }

    public void iniciarStone(boolean modoDesenvolvedor, final RespostaInstalacaoTabelasStone respostaInstalacaoTabelasStone) {
        Log.d(TAG, "iniciarStone: ");
        this.modoDesenvolvedor = modoDesenvolvedor;
        List<UserModel> user = StoneStart.init(activity);
        if (user == null) {
            Log.d(TAG, "iniciarStone: null");
            List<String> stoneCodeList = new ArrayList<>();
            stoneCodeList.add("119555212"); // stone code serveloja

            final ActiveApplicationProvider activeApplicationProvider = new ActiveApplicationProvider(activity, stoneCodeList);
            activeApplicationProvider.setDialogMessage("Ativando o aplicativo...");
            activeApplicationProvider.setDialogTitle("Aguarde");
            activeApplicationProvider.setActivity(activity);
            activeApplicationProvider.setWorkInBackground(true); // informa se este provider ira rodar em background ou nao
            activeApplicationProvider.setConnectionCallback(new StoneCallbackInterface() {
                /* Sempre que utilizar um provider, intancie esta Interface.
                 * Ela ira lhe informar se o provider foi executado com sucesso ou nao
                 */
                    /* Metodo chamado se for executado sem erros */
                public void onSuccess() {
                    //Toast.makeText(activity, "Ativado com sucesso, iniciando o aplicativo", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onSuccess: ");
                }

                /* metodo chamado caso ocorra alguma excecao */
                public void onError() {
                    Log.d(TAG, "onError: ");
                }
            });

            activeApplicationProvider.execute();
        } else {
//            List<String> stoneCodeList = new ArrayList<>();
//            stoneCodeList.add("119555212"); // stone code serveloja
//
//            final ActiveApplicationProvider activeApplicationProvider = new ActiveApplicationProvider(activity, stoneCodeList);
//            activeApplicationProvider.setDialogMessage("Ativando o aplicativo...");
//            activeApplicationProvider.setDialogTitle("Aguarde");
//            activeApplicationProvider.setActivity(activity);
//            activeApplicationProvider.setWorkInBackground(true); // informa se este provider ira rodar em background ou nao
//            activeApplicationProvider.setConnectionCallback(new StoneCallbackInterface() {
//                /* Sempre que utilizar um provider, intancie esta Interface.
//                 * Ela ira lhe informar se o provider foi executado com sucesso ou nao
//                 */
//                    /* Metodo chamado se for executado sem erros */
//                public void onSuccess() {
//                    //Toast.makeText(activity, "Ativado com sucesso, iniciando o aplicativo", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "onSuccess: ");
//                }
//
//                /* metodo chamado caso ocorra alguma excecao */
//                public void onError() {
//                    Log.d(TAG, "onError: ");
//                }
//            });
//
//            activeApplicationProvider.execute();
        }
        //Seta o modo de desenvolvedor
        if (modoDesenvolvedor)
            Stone.setEnvironment(Environment.SANDBOX);
        else
            Stone.setEnvironment(Environment.PRODUCTION);
    }

    public void baixarTabelasComCheckTable(boolean modoDesenvolvedor, final RespostaInstalacaoTabelasStone respostaInstalacaoTabelasStone) {

        List<String> stoneCodeList = new ArrayList<>();
        // Adicione seu Stonecode abaixo, como string.
        if (modoDesenvolvedor)
            stoneCodeList.add("167988962"); // stone code teste
        else
            stoneCodeList.add("119555212"); // stone code serveloja

        ApplicationCache applicationCache = new ApplicationCache(activity);
        if (!applicationCache.checkIfHasTables()) {

            // Realiza processo de download das tabelas em sua totalidade.
            DownloadTablesProvider downloadTablesProvider = new DownloadTablesProvider(activity, GlobalInformations.getUserModel(0));
            downloadTablesProvider.setDialogMessage("Baixando as tabelas, por favor aguarde");
            downloadTablesProvider.setWorkInBackground(false); // para dar feedback ao usuario ou nao.
            downloadTablesProvider.setConnectionCallback(new StoneCallbackInterface() {
                public void onSuccess() {
                    if (respostaInstalacaoTabelasStone != null)
                        respostaInstalacaoTabelasStone.onRespostaInstalacaoTabelas(true, "Tabelas baixados com sucesso");
                }

                public void onError() {
                    if (respostaInstalacaoTabelasStone != null)
                        respostaInstalacaoTabelasStone.onRespostaInstalacaoTabelas(true, "Falha ao baixar as tabelas.");
                }
            });
            downloadTablesProvider.execute();
        }
    }

    public void baixarTabelasSemCheckTable(boolean modoDesenvolvedor, final RespostaInstalacaoTabelasStone respostaInstalacaoTabelasStone) {

        List<String> stoneCodeList = new ArrayList<>();
        // Adicione seu Stonecode abaixo, como string.
        if (modoDesenvolvedor)
            stoneCodeList.add("167988962"); // stone code teste
        else
            stoneCodeList.add("119555212"); // stone code serveloja

        ApplicationCache applicationCache = new ApplicationCache(activity);
        // Realiza processo de download das tabelas em sua totalidade.
        DownloadTablesProvider downloadTablesProvider = new DownloadTablesProvider(activity, GlobalInformations.getUserModel(0));
        downloadTablesProvider.setDialogMessage("Baixando as tabelas, por favor aguarde");
        downloadTablesProvider.setWorkInBackground(false); // para dar feedback ao usuario ou nao.
        downloadTablesProvider.setConnectionCallback(new StoneCallbackInterface() {
            public void onSuccess() {
                if (respostaInstalacaoTabelasStone != null)
                    respostaInstalacaoTabelasStone.onRespostaInstalacaoTabelas(true, "Tabelas baixados com sucesso");
            }

            public void onError() {
                if (respostaInstalacaoTabelasStone != null)
                    respostaInstalacaoTabelasStone.onRespostaInstalacaoTabelas(true, "Falha ao baixar as tabelas.");
            }
        });
        downloadTablesProvider.execute();
    }

    /**
     * verifica se o cartÃ£o lido pela TARJA, Ã© permitido pela STONE, e assim nÃ£o permitir a
     * conclusÃ£o da transaÃ§Ã£o pois o mesmo Ã© aceito com CHIP
     *
     * @param bandeira
     * @return
     */
    public boolean checkBandeiraPermitidaPelaStone(String bandeira) {
        if ((bandeira.toLowerCase().equals("visa electron")
                || bandeira.toLowerCase().equals("visa")
                || bandeira.toLowerCase().equals("mastercard")
                || bandeira.toLowerCase().equals("maestro"))) {
            return true;
        } else {
            return false;
        }
    }

    void iniciarTransacao(String valor, int tipoTransacao, int qntParcelas,
                          final RespostaTransacaoStoneListener respostaTransacaoStoneListener) {
        Log.d(TAG, "iniciarTransacao: ");
        try {
            final StoneTransaction stoneTransaction = new StoneTransaction(Stone.getPinpadFromListAt(0));
            stoneTransaction.setAmount(valor);
            stoneTransaction.setEmailClient(null);
            stoneTransaction.setRequestId("");
            stoneTransaction.setUserModel(GlobalInformations.getUserModel(0));
            // tipo de transaÃ§Ã£o (dÃ©bito ou crÃ©dito)
            stoneTransaction.setTypeOfTransaction(getTipoTransacaoStone(tipoTransacao));
            // quantidade de parcelas.
            stoneTransaction.setInstalmentTransactionEnum(getParcelaStone(qntParcelas));
            // processo para envio da transacao.
            final TransactionProvider transactionProvider = new
                    TransactionProvider(activity, stoneTransaction, GlobalInformations.getPinpadFromListAt(0));
            transactionProvider.setWorkInBackground(false);
            transactionProvider.setDialogMessage("Enviando..");
            transactionProvider.setDialogTitle("Aguarde");
            transactionProvider.setConnectionCallback(new StoneCallbackInterface() {
                @Override
                public void onSuccess() {
                    if (respostaTransacaoStoneListener != null)
                        respostaTransacaoStoneListener.onRespostaTransacaoStone(true, transactionProvider);
                }

                @Override
                public void onError() {
                    if (respostaTransacaoStoneListener != null)
                        respostaTransacaoStoneListener.onRespostaTransacaoStone(false, transactionProvider);
                }
            });
            transactionProvider.execute();
        } catch (Exception e) {
            Log.d(TAG, "iniciarTransacao: Exception " + e.getMessage());
            if (respostaTransacaoStoneListener != null)
                respostaTransacaoStoneListener.onRespostaTransacaoStone(false, null);
        }
    }


    private InstalmentTransactionEnum getParcelaStone(int qntParcelas) {
        switch (qntParcelas) {
            case A_VISTA:
                return InstalmentTransactionEnum.ONE_INSTALMENT;
            case DUAS_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.TWO_INSTALMENT_NO_INTEREST;
            case TRES_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.THREE_INSTALMENT_NO_INTEREST;
            case QUATRO_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.FOUR_INSTALMENT_NO_INTEREST;
            case CINCO_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.FIVE_INSTALMENT_NO_INTEREST;
            case SEIS_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.SIX_INSTALMENT_NO_INTEREST;
            case SETE_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.SEVEN_INSTALMENT_NO_INTEREST;
            case OITO_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.EIGHT_INSTALMENT_NO_INTEREST;
            case NOVE_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.NINE_INSTALMENT_NO_INTEREST;
            case DEZ_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.TEN_INSTALMENT_NO_INTEREST;
            case ONZE_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.ELEVEN_INSTALMENT_NO_INTEREST;
            case DOZE_PARCELAS_SEM_JUROS:
                return InstalmentTransactionEnum.TWELVE_INSTALMENT_NO_INTEREST;
            default:
                return null;
        }
    }

    private TypeOfTransactionEnum getTipoTransacaoStone(int tipoTransacao) {
        switch (tipoTransacao) {
            case DEBITO:
                return TypeOfTransactionEnum.DEBIT;
            case CREDITO:
                return TypeOfTransactionEnum.CREDIT;
            default:
                return null;
        }
    }
}