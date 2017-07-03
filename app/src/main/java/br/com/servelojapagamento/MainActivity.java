package br.com.servelojapagamento;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Set;

import br.com.servelojapagamento.adapter_recycler_view.AdapterRvListaDispositivos;
import br.com.servelojapagamento.interfaces.ClickRecyclerViewListener;
import br.com.servelojapagamento.interfaces.StatusBluetoothListener;
import br.com.servelojapagamento.utils.BluetoothUtils;

public class MainActivity extends AppCompatActivity implements StatusBluetoothListener, ClickRecyclerViewListener {

    private String TAG;
    private BluetoothUtils bluetoothUtils;
    private final int SOLICITACAO_PERMISSAO_BLUETOOTH_PRIVILEGED = 1;
    private MaterialDialog.Builder dialogParearDispositivos;
    private RecyclerView dialogListaDispositivosRv;
    private RelativeLayout dialogListaDispositivosRlCarregando;
    private AdapterRvListaDispositivos adapterRvListaDispositivos;
    private ArrayList<BluetoothDevice> arrayListaDispositivo;
    private Button btAbrirDialogProcurarDispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.TAG = getClass().getSimpleName();
        this.bluetoothUtils = new BluetoothUtils(this);
        bluetoothUtils.setStatusBluetoothListener(this);
        bluetoothUtils.iniciarServicoBluetooth();

        // setup views
        setupDialogParearDispositivos();
        btAbrirDialogProcurarDispositivos = (Button) findViewById(R.id.ac_main_bt_abrir_dialog);

        btAbrirDialogProcurarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDispositivosJaPareados();
                bluetoothUtils.iniciarProcuraDispositivos();
                dialogListaDispositivosRlCarregando.setVisibility(View.VISIBLE);
                dialogParearDispositivos.show();
            }
        });

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

    private void setupDialogParearDispositivos() {
        dialogParearDispositivos = new MaterialDialog.Builder(this)
                .title("Selecione um dispositivo")
                .customView(R.layout.layout_dialog_parear_dispositivo, true)
                .positiveText("Pesquisar")
                .negativeText("Fechar")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        bluetoothUtils.pararProcuraDispositivo();
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        bluetoothUtils.iniciarProcuraDispositivos();
                        dialogListaDispositivosRlCarregando.setVisibility(View.VISIBLE);
                    }
                })
                .autoDismiss(false);

        View view = dialogParearDispositivos.build().getCustomView();
        dialogListaDispositivosRv = (RecyclerView) view.findViewById(R.id.dialog_parear_dispositivos_rv_lista);
        dialogListaDispositivosRlCarregando = (RelativeLayout) view.findViewById(R.id.dialog_parear_dispositivos_layout_carregando);
        dialogListaDispositivosRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        arrayListaDispositivo = new ArrayList<>();
        dialogListaDispositivosRv.setLayoutManager(linearLayoutManager);
        adapterRvListaDispositivos = new AdapterRvListaDispositivos(arrayListaDispositivo, this);
        dialogListaDispositivosRv.setAdapter(adapterRvListaDispositivos);
        dialogListaDispositivosRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void addDispositivosJaPareados() {
        Set<BluetoothDevice> pareados = bluetoothUtils.getDispositivosPareados();
        arrayListaDispositivo.clear();
        for (BluetoothDevice pareado : pareados) {
            arrayListaDispositivo.add(pareado);
        }
        adapterRvListaDispositivos.atualizarLista();
    }

    private boolean checkDispositivoAdicionado(BluetoothDevice dispositivo) {
        for (BluetoothDevice bluetoothDevice : arrayListaDispositivo) {
            if (dispositivo.getAddress().equals(bluetoothDevice.getAddress())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDispositivoEncontradoBluetooth(BluetoothDevice dispositivo) {
        Log.d(TAG, "onDispositivoEncontradoBluetooth: " + dispositivo.getAddress() + dispositivo.getName());
        if (!checkDispositivoAdicionado(dispositivo)) {
            arrayListaDispositivo.add(dispositivo);
            adapterRvListaDispositivos.atualizarLista();
        }
    }

    @Override
    public void onEstadoAlteradoBluetooth(int estadoAtual, int estadoAnterior) {
        Log.d(TAG, "onEstadoAlteradoBluetooth: estadoAtual " + estadoAtual);
        Log.d(TAG, "onEstadoAlteradoBluetooth: estadoAnterior " + estadoAnterior);

    }

    @Override
    public void onProcuraDispositivoFinalizadaBluetooth() {
        Log.d(TAG, "onProcuraDispositivoFinalizadaBluetooth: ");
        dialogListaDispositivosRlCarregando.setVisibility(View.GONE);
    }

    @Override
    public void onClickDispositivo(BluetoothDevice dispositivo) {
        Toast.makeText(this, dispositivo.getName(), Toast.LENGTH_SHORT).show();
    }

}
