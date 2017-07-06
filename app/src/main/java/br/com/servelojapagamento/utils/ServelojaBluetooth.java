package br.com.servelojapagamento.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.Set;

import br.com.servelojapagamento.interfaces.StatusBluetoothListener;


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

    public ServelojaBluetooth(Activity activity) {
        this.TAG = getClass().getSimpleName();
        this.activity = activity;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
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
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        return bluetoothAdapter.startDiscovery();
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
