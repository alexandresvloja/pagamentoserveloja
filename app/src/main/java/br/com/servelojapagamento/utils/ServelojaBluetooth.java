package br.com.servelojapagamento.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.Set;

import br.com.servelojapagamento.interfaces.RespostaConexaoBlueetothPinpadListener;
import br.com.servelojapagamento.interfaces.StatusBluetoothListener;
import br.com.servelojapagamento.preferences.PrefsHelper;
import stone.application.interfaces.StoneCallbackInterface;
import stone.providers.BluetoothConnectionProvider;
import stone.utils.PinpadObject;
import stone.utils.Stone;


/**
 * Created by Alexandre on 30/06/2017.
 */

public class ServelojaBluetooth {

    private String TAG;
    public static final int SOLICITACAO_HABILITAR_BLUETOOTH = 1000;
    private Activity activity;
    private BluetoothAdapter bluetoothAdapter;
    private StatusBluetoothListener statusBluetoothListener;
    private boolean servicoIniciado;
    private boolean comunicacaoPinpadIniciada;
    private PrefsHelper prefsHelper;

    public ServelojaBluetooth(Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.activity = activity;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.prefsHelper = new PrefsHelper(activity);
    }

    public void setStatusBluetoothListener(StatusBluetoothListener statusBluetoothListener) {
        this.statusBluetoothListener = statusBluetoothListener;
    }

    public void iniciarServicoBluetooth() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        activity.registerReceiver(mReceiver, filter);
        servicoIniciado = true;
    }

    public void pararServicoBluetooth() {
        if (servicoIniciado) {
            activity.unregisterReceiver(mReceiver);
            servicoIniciado = false;
        }
    }

    public boolean checkBluetoothAtivado() {
        return bluetoothAdapter.isEnabled();
    }

    public boolean iniciarProcuraDispositivos() {
        if (!bluetoothAdapter.isDiscovering()) {
            return bluetoothAdapter.startDiscovery();
        }
        return false;
    }

    public boolean pararProcuraDispositivo() {
        if (bluetoothAdapter.isDiscovering()) {
            return bluetoothAdapter.cancelDiscovery();
        }
        return false;
    }

    public void solicitarAtivacaoBluetooth() {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, SOLICITACAO_HABILITAR_BLUETOOTH);
    }

    public Set<BluetoothDevice> getDispositivosPareados() {
        Set<BluetoothDevice> dispositivosPareados = bluetoothAdapter.getBondedDevices();
        return dispositivosPareados;
    }

    public void iniciarComunicacaoPinpad(final BluetoothDevice dispositivo, final RespostaConexaoBlueetothPinpadListener
            respostaConexaoBlueetothPinpadListener) {
        if (!comunicacaoPinpadIniciada) {
            try {
                Log.d(TAG, "iniciarComunicacaoPinpad: ");
                if (checkPinpadConectado()) {
                    Toast.makeText(activity, "Pinpad já conectada.", Toast.LENGTH_SHORT).show();
                } else {
                    PinpadObject pinpadObject = new PinpadObject(dispositivo.getName(), dispositivo.getAddress(), false);
                    final BluetoothConnectionProvider bluetoothConnectionProvider =
                            new BluetoothConnectionProvider(activity, pinpadObject);
                    bluetoothConnectionProvider.setDialogMessage("Criando conexao com o pinpad selecionado");
                    bluetoothConnectionProvider.setWorkInBackground(false);
                    bluetoothConnectionProvider.setConnectionCallback(new StoneCallbackInterface() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "onSuccess: ");
                            respostaConexaoBlueetothPinpadListener.onRespostaConexaoBlueetothPinpad(true,
                                    bluetoothConnectionProvider.getListOfErrors(), "");
                            prefsHelper.salvarPinpadMac(dispositivo.getAddress());
                            prefsHelper.salvarPinpadModelo(dispositivo.getName());
                            parearDispositivo(dispositivo);
                            comunicacaoPinpadIniciada = false;
                        }

                        @Override
                        public void onError() {
                            Log.d(TAG, "onError: ");
                            respostaConexaoBlueetothPinpadListener.onRespostaConexaoBlueetothPinpad(false,
                                    bluetoothConnectionProvider.getListOfErrors(), "");
                            comunicacaoPinpadIniciada = false;
                        }
                    });
                    bluetoothConnectionProvider.execute();
                    comunicacaoPinpadIniciada = true;
                }
            } catch (Exception e) {
                Log.d(TAG, "iniciarComunicacaoPinpad: Exception " + e.getMessage());
                respostaConexaoBlueetothPinpadListener.onRespostaConexaoBlueetothPinpad(false,
                        null, e.getMessage() + " | Dispositivo selecionado não é suportado.");
            }
        }

    }

    private boolean checkProtocoloPinpad(BluetoothDevice bluetoothDevice) {
        String uuid1 = "00001101-0000-1000-8000";
        String uuid2 = "00000000-0000-1000-8000";
        for (int i = 0; i < bluetoothDevice.getUuids().length; i++) {
            String uuid = bluetoothDevice.getUuids()[i].toString();
            Log.d(TAG, "checkProtocoloPinpad: " + uuid);
            if (uuid.contains(uuid1) || uuid.contains(uuid2)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkPinpadConectado() {
        return Stone.isConnectedToPinpad();
    }

    private void parearDispositivo(BluetoothDevice dispositivo) {
        try {
            Method method = dispositivo.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(dispositivo, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: action " + action);
            switch (action) {
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (statusBluetoothListener != null)
                        statusBluetoothListener.onDispositivoEncontradoBluetooth(dispositivo);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    if (statusBluetoothListener != null)
                        statusBluetoothListener.onProcuraDispositivoFinalizadaBluetooth();
                    break;
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    final int estadoAtual = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                    final int estadoAnterior = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
                    if (statusBluetoothListener != null)
                        statusBluetoothListener.onEstadoAlteradoBluetooth(estadoAtual, estadoAnterior);
                    break;
            }
        }
    };

}
