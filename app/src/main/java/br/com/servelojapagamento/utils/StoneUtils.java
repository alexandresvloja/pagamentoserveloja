package br.com.servelojapagamento.utils;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import br.com.servelojapagamento.interfaces.RespostaConexaoBlueetothPinpadListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoStoneListener;
import br.com.servelojapagamento.preferences.PrefsHelper;
import stone.application.StoneStart;
import stone.application.enums.InstalmentTransactionEnum;
import stone.application.enums.TypeOfTransactionEnum;
import stone.application.interfaces.StoneCallbackInterface;
import stone.cache.ApplicationCache;
import stone.providers.ActiveApplicationProvider;
import stone.providers.BluetoothConnectionProvider;
import stone.providers.DownloadTablesProvider;
import stone.providers.TransactionProvider;
import stone.user.UserModel;
import stone.utils.GlobalInformations;
import stone.utils.PinpadObject;
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
    private RespostaConexaoBlueetothPinpadListener respostaConexaoBlueetothPinpadListener;
    private boolean modoDesenvolvedor;


    public StoneUtils(Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.activity = activity;
    }

    public void iniciarStone(boolean modoDesenvolvedor) {
        this.modoDesenvolvedor = modoDesenvolvedor;
        List<UserModel> user = StoneStart.init(activity);
        // se retornar nulo, voce provavelmente nao ativou a SDK ou as informacoes da Stone SDK foram excluidas
        if (user == null) {
            List<String> stoneCodeList = new ArrayList<>();
            // Adicione seu Stonecode abaixo, como string.
            stoneCodeList.add("167988962"); // stone code teste
            // stoneCodeList.add("119555212"); // stone code serveloja
            final ActiveApplicationProvider activeApplicationProvider = new ActiveApplicationProvider(activity, stoneCodeList);
            activeApplicationProvider.setDialogMessage("Ativando o aplicativo...");
            activeApplicationProvider.setDialogTitle("Aguarde");
            activeApplicationProvider.setActivity(activity);
            activeApplicationProvider.setWorkInBackground(true);
            activeApplicationProvider.setConnectionCallback(new StoneCallbackInterface() {
                public void onSuccess() {
                    Log.d(TAG, "onSuccess");
                }

                public void onError() {
                    Log.d(TAG, "onError");
                    Log.d(TAG, "onError: " + activeApplicationProvider.getListOfErrors().toString());
                }
            });
            activeApplicationProvider.execute();
        } else {

        }
        // Seta o modo de desenvolvedor
        if (modoDesenvolvedor)
            Stone.developerMode();
    }

    public void checkPermissoes() {
        MultiplePermissionsListener dialogMultiplePermissionsListener =
                DialogOnAnyDeniedMultiplePermissionsListener.Builder
                        .withContext(activity)
                        .withTitle("Permissão negada")
                        .withMessage("Para iniciar a sincronização com a Serveloja, é necessário aceitar esta permissão.")
                        .withButtonText(android.R.string.ok)
                        .build();
        Dexter.withActivity(activity).withPermissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(dialogMultiplePermissionsListener)
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Log.d(TAG, "onError: " + error.toString());
                    }
                })
                .check();
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

    public boolean checkPinpadConectado() {
        return Stone.isConnectedToPinpad();
    }

    public void iniciarTransacao(String valor, int tipoTransacao, int qntParcelas,
                                 final RespostaTransacaoStoneListener respostaTransacaoStoneListener) {
        Log.d(TAG, "iniciarTransacao: ");
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
                respostaTransacaoStoneListener.onRespostaTransacaoStone(true, transactionProvider);
            }

            @Override
            public void onError() {
                respostaTransacaoStoneListener.onRespostaTransacaoStone(false, transactionProvider);
            }
        });
        transactionProvider.execute();
    }

    public void downloadTabelas() {
        ApplicationCache applicationCache = new ApplicationCache(activity);
        if (!applicationCache.checkIfHasTables()) {
            // Realiza processo de download das tabelas em sua totalidade.
            final DownloadTablesProvider downloadTablesProvider = new DownloadTablesProvider(activity, GlobalInformations.getUserModel(0));
            downloadTablesProvider.setDialogMessage("Baixando as tabelas, por favor aguarde");
            downloadTablesProvider.setWorkInBackground(false); // para dar feedback ao usuario ou nao.
            downloadTablesProvider.setConnectionCallback(new StoneCallbackInterface() {
                public void onSuccess() {
                    Toast.makeText(activity, "Tabelas baixadas com sucesso", Toast.LENGTH_SHORT).show();
                }

                public void onError() {
                    Toast.makeText(activity, "Erro no download das tabelas", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onError: " + downloadTablesProvider.getListOfErrors().toString());
                }
            });
            downloadTablesProvider.execute();
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