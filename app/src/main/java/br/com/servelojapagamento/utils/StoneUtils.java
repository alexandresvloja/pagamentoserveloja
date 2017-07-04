package br.com.servelojapagamento.utils;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.servelojapagamento.interfaces.RespostaConexaoBlueetothPinpadListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoListener;
import permissions.dispatcher.NeedsPermission;
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

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
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

    public void iniciarComunicacaoPinpad(BluetoothDevice dispositivo, final RespostaConexaoBlueetothPinpadListener
            respostaConexaoBlueetothPinpadListener) {
        try {
            Log.d(TAG, "iniciarComunicacaoPinpad: ");
            this.respostaConexaoBlueetothPinpadListener = respostaConexaoBlueetothPinpadListener;
            PinpadObject pinpadObject = new PinpadObject(dispositivo.getName(), dispositivo.getAddress(), false);
            final BluetoothConnectionProvider bluetoothConnectionProvider =
                    new BluetoothConnectionProvider(activity, pinpadObject);
            bluetoothConnectionProvider.setDialogMessage("Criando conexao com o pinpad selecionado"); // Mensagem exibida do dialog.
            bluetoothConnectionProvider.setWorkInBackground(false); // Informa que haverá um feedback para o usuário.
            bluetoothConnectionProvider.setConnectionCallback(new StoneCallbackInterface() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: ");
                    respostaConexaoBlueetothPinpadListener.onRespostaConexaoBlueetothPinpad(true,
                            bluetoothConnectionProvider.getListOfErrors());
                }

                @Override
                public void onError() {
                    Log.d(TAG, "onError: ");
                    respostaConexaoBlueetothPinpadListener.onRespostaConexaoBlueetothPinpad(true,
                            bluetoothConnectionProvider.getListOfErrors());
                }
            });
            bluetoothConnectionProvider.execute();
        } catch (Exception e) {
            Log.d(TAG, "iniciarComunicacaoPinpad: Exception " + e.getMessage());
        }
    }

    public void iniciarTransacao(String valor, int tipoTransacao, final RespostaTransacaoListener respostaTransacaoListener) {
        Log.d(TAG, "iniciarTransacao: ");
        final StoneTransaction stoneTransaction = new StoneTransaction(Stone.getPinpadFromListAt(0));
        stoneTransaction.setAmount(valor);
        stoneTransaction.setEmailClient(null);
        stoneTransaction.setRequestId("");
        stoneTransaction.setUserModel(GlobalInformations.getUserModel(0));

        if (tipoTransacao == 0) {
            stoneTransaction.setTypeOfTransaction(TypeOfTransactionEnum.DEBIT);
        } else if (tipoTransacao == 1) {
            stoneTransaction.setTypeOfTransaction(TypeOfTransactionEnum.CREDIT);
        }

        // Informa a quantidade de parcelas.
        stoneTransaction.setInstalmentTransactionEnum(InstalmentTransactionEnum.ONE_INSTALMENT);
        // Processo para envio da transacao.
        final TransactionProvider transactionProvider = new
                TransactionProvider(activity, stoneTransaction, GlobalInformations.getPinpadFromListAt(0));
        transactionProvider.setWorkInBackground(false);
        transactionProvider.setDialogMessage("Enviando..");
        transactionProvider.setDialogTitle("Aguarde");
        transactionProvider.setConnectionCallback(new StoneCallbackInterface() {
            @Override
            public void onSuccess() {
                respostaTransacaoListener.onRespostaTransacao(true, transactionProvider.getListOfErrors());
            }

            @Override
            public void onError() {
                respostaTransacaoListener.onRespostaTransacao(false, transactionProvider.getListOfErrors());
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

}