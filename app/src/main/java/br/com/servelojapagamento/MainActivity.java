package br.com.servelojapagamento;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.com.servelojapagamento.interfaces.StatusBluetoothListener;
import br.com.servelojapagamento.utils.BluetoothUtils;

public class MainActivity extends AppCompatActivity implements StatusBluetoothListener {

    private String TAG;
    private BluetoothUtils bluetoothUtils;
    private final int SOLICITACAO_PERMISSAO_BLUETOOTH_PRIVILEGED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.TAG = getClass().getSimpleName();
        this.bluetoothUtils = new BluetoothUtils(this);
        bluetoothUtils.iniciarServicoBluetooth();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: ACCESS_COARSE_LOCATION != PERMISSION_GRANTED");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    SOLICITACAO_PERMISSAO_BLUETOOTH_PRIVILEGED);
        }


        if (bluetoothUtils.checkBluetoothAtivado()) {
            Log.d(TAG, "onCreate: checkBluetoothAtivado");
        }

        if (bluetoothUtils.iniciarProcuraDispositivos()) {
            Log.d(TAG, "onCreate: iniciarProcuraDispositivos");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: requestCode " + requestCode);
        for (String permission : permissions) {
            Log.d(TAG, "onRequestPermissionsResult: permissions " + permission.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetoothUtils.iniciarServicoBluetooth();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetoothUtils.pararServicoBluetooth();
    }

    @Override
    public void onDispositivoEncontradoBluetooth(BluetoothDevice dispositivo) {

    }

    @Override
    public void onEstadoAlteradoBluetooth(int estadoAtual, int estadoAnterior) {

    }

    @Override
    public void onProcuraDispositivoFinalizadaBluetooth() {

    }

}
