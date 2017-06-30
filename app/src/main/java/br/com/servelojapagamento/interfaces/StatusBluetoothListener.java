package br.com.servelojapagamento.interfaces;

import android.bluetooth.BluetoothDevice;

/**
 * Created by Alexandre on 30/06/2017.
 */

public interface StatusBluetoothListener {

    void onDispositivoEncontradoBluetooth(BluetoothDevice dispositivo);
    void onEstadoAlteradoBluetooth(int estadoAtual, int estadoAnterior);
    void onProcuraDispositivoFinalizadaBluetooth();

}
