package br.com.servelojapagamento;

import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;
import java.util.Set;

import br.com.servelojapagamento.adapter_recycler_view.AdapterRvListaDispositivos;
import br.com.servelojapagamento.interfaces.ClickRecyclerViewListener;
import br.com.servelojapagamento.interfaces.RespostaConexaoBlueetothPinpadListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoAplicativoListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoClienteListener;
import br.com.servelojapagamento.interfaces.StatusBluetoothListener;
import br.com.servelojapagamento.webservice_mundipagg.ParamsCriarTokenCartao;
import br.com.servelojapagamento.webservice_mundipagg.ParamsCriarTokenEndereco;
import br.com.servelojapagamento.webservice_mundipagg.ParamsCriarTransacaoSemToken;
import br.com.servelojapagamento.webservice_mundipagg.RespostaConsultarToken;
import br.com.servelojapagamento.webservice_mundipagg.RespostaCriarToken;
import br.com.servelojapagamento.webservice_mundipagg.RespostaTransacaoMundipagg;
import br.com.servelojapagamento.webservice_serveloja.ServelojaWebService;
import br.com.servelojapagamento.webservice_serveloja.TransacaoServeloja;
import br.com.servelojapagamento.webservice_serveloja.UserMobile;
import br.com.servelojapagamento.preferences.PrefsHelper;
import br.com.servelojapagamento.utils.ServelojaBluetooth;
import br.com.servelojapagamento.utils.ServelojaTransacaoUtils;
import br.com.servelojapagamento.utils.StoneUtils;
import br.com.servelojapagamento.utils.TransacaoEnum;
import br.com.servelojapagamento.utils.Utils;
import stone.application.enums.ErrorsEnum;
import stone.utils.Stone;

import static br.com.servelojapagamento.utils.Utils.encriptar;

public class MainActivity extends AppCompatActivity implements
        StatusBluetoothListener, ClickRecyclerViewListener,
        RespostaConexaoBlueetothPinpadListener, RespostaTransacaoClienteListener, RespostaTransacaoAplicativoListener {

    // views
    private MaterialDialog dialogParearDispositivos;
    private RecyclerView dialogListaDispositivosRv;
    private RelativeLayout dialogListaDispositivosRlCarregando;
    private AdapterRvListaDispositivos adapterRvListaDispositivos;
    private ArrayList<BluetoothDevice> arrayListaDispositivo;
    private Button btAbrirDialogProcurarDispositivos;
    private Button btEfetuarTransacao;
    private MaterialDialog materialDialogProgresso;
    // outros
//    private StoneUtils stoneUtils;
    private ServelojaTransacaoUtils servelojaTransacaoUtils;
    //    private ServelojaWebService servelojaWebService;
    private PrefsHelper prefsHelper;
    private String TAG;
    private ServelojaBluetooth servelojaBluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = getClass().getSimpleName();
        servelojaBluetooth = new ServelojaBluetooth(this);
//        stoneUtils = new StoneUtils(this);
        servelojaTransacaoUtils = new ServelojaTransacaoUtils(this);
//        servelojaWebService = new ServelojaWebService(this);
        prefsHelper = new PrefsHelper(this);

        // passando como parâmetro o callback que compôem os três métodos:
        // onDispositivoEncontradoBluetooth, onEstadoAlteradoBluetooth, onProcuraDispositivoFinalizadaBluetooth
        servelojaBluetooth.setStatusBluetoothListener(this);
        servelojaBluetooth.iniciarServicoBluetooth(false);


        // setup views
        setupDialogParearDispositivos();
        btAbrirDialogProcurarDispositivos = (Button) findViewById(R.id.ac_main_bt_abrir_dialog);
        btEfetuarTransacao = (Button) findViewById(R.id.ac_main_bt_efetuar_transacao);

        btAbrirDialogProcurarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servelojaTransacaoUtils.iniciarSistemaTransacaoServeloja(true);
                if (servelojaBluetooth.checkBluetoothAtivado()) {
                    addDispositivosJaPareados();
                    servelojaBluetooth.iniciarProcuraDispositivos();
                    dialogListaDispositivosRlCarregando.setVisibility(View.VISIBLE);
                    dialogParearDispositivos.show();
                } else {
                    servelojaBluetooth.solicitarAtivacaoBluetooth();
                }
            }
        });

        btEfetuarTransacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Stone.isConnectedToPinpad()) {
                    Log.d(TAG, "onRespostaConexaoBlueetothPinpad: isConnectedToPinpad");
                    TransacaoServeloja transacaoServeloja = new TransacaoServeloja();
                    transacaoServeloja.setTipoTransacao(TransacaoEnum.TipoTransacao.CREDITO);
                    transacaoServeloja.setValor("25");
                    transacaoServeloja.setDddTelefone("79");
                    transacaoServeloja.setNumTelefone("996485108");
                    transacaoServeloja.setValorSemTaxas("25");
                    transacaoServeloja.setNumParcelas(TransacaoEnum.QntParcelas.A_VISTA);
                    transacaoServeloja.setUsoTarja(false);
                    transacaoServeloja.setCpfCnpjAdesao("06130856555");
                    transacaoServeloja.setCpfCnpjComprador("06130856555");
                    servelojaTransacaoUtils.iniciarTransacao(transacaoServeloja, MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "Pinpad não conectada.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ParamsCriarTokenCartao cartao = new ParamsCriarTokenCartao();
        cartao.setCartaoAno("2020");
        cartao.setCartaoBandeira("VISA");
        cartao.setCartaoCheck(true);
        cartao.setCartaoMes("10");
        cartao.setCartaoNome("ALEXANDRE ANDRADE");
        cartao.setCartaoNumero("41111111111111");

        ParamsCriarTokenEndereco endereco = new ParamsCriarTokenEndereco();
        endereco.setEnderecoCidade("Aracaju");
        endereco.setEnderecoDescricao("Rua H");
        endereco.setEnderecoNumero("66");

        // obterTokenCartaoCredito(cartao, endereco);
        String token = "92d6b8db-ed60-4d2c-82f6-2d65e56533a3";
//        consultarToken(token);

        ParamsCriarTransacaoSemToken transacao = new ParamsCriarTransacaoSemToken();
        transacao.setCartaoNome("ALEXANDRE");
        transacao.setCartaoMes("10");
        transacao.setCartaoAno("2020");
        transacao.setCartaoBandeira("VISA");
        transacao.setCartaoCvv("123");
        transacao.setIdTransacao("154789");
        transacao.setNumParcela(1);
        transacao.setCartaoNumero("4111111111111111");
        transacao.setValor("100");

//        criarTransacaoSemToken(transacao);

        String chavePedido = "e78819fd-96a8-4228-accf-bf5b7aff57dc";
        String refPedido = "154789";

//        servelojaTransacaoUtils.consultarTransacaoPorChavePedido(chavePedido, this);
//        servelojaTransacaoUtils.consultarTransacaoPorReferenciaPedido(refPedido, this);
//        obterChaveAcesso();

    }

    private void obterTokenCartaoCredito(ParamsCriarTokenCartao cartao, ParamsCriarTokenEndereco endereco) {
        servelojaTransacaoUtils.obterToken(cartao, endereco, this);
    }

    private void consultarToken(String token) {
        servelojaTransacaoUtils.consultarToken(token, this);
    }

    private void criarTransacaoSemToken(ParamsCriarTransacaoSemToken transacao) {
        servelojaTransacaoUtils.criarTransacaoSemToken(transacao, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode " + requestCode);
        if (requestCode == ServelojaBluetooth.SOLICITACAO_HABILITAR_BLUETOOTH) {
            Log.d(TAG, "onActivityResult: resultCode " + resultCode);
            // bluetooth ativado
            if (resultCode == -1) {
                addDispositivosJaPareados();
                servelojaBluetooth.iniciarProcuraDispositivos();
                dialogListaDispositivosRlCarregando.setVisibility(View.VISIBLE);
                dialogParearDispositivos.show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        stoneUtils.downloadTabelas();
        if (Stone.isConnectedToPinpad()) {
            Log.d(TAG, "onResume: isConnectedToPinpad");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        servelojaBluetooth.pararServicoBluetooth();
    }

    private void setupDialogParearDispositivos() {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this)
                .title("Selecione um dispositivo")
                .customView(R.layout.layout_dialog_parear_dispositivo, true)
                .positiveText("Pesquisar")
                .negativeText("Fechar")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        servelojaBluetooth.pararProcuraDispositivo();
                        dialog.dismiss();
                    }
                })
                .dismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        servelojaBluetooth.pararProcuraDispositivo();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        servelojaBluetooth.iniciarProcuraDispositivos();
                        dialogListaDispositivosRlCarregando.setVisibility(View.VISIBLE);
                    }
                })
                .autoDismiss(false);

        dialogParearDispositivos = materialDialog.build();
        View view = dialogParearDispositivos.getCustomView();
        dialogListaDispositivosRv = (RecyclerView) view.findViewById(R.id.dialog_parear_dispositivos_rv_lista);
        dialogListaDispositivosRlCarregando = (RelativeLayout) view.findViewById(R.id.dialog_parear_dispositivos_layout_carregando);
        dialogListaDispositivosRv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        arrayListaDispositivo = new ArrayList<>();
        dialogListaDispositivosRv.setLayoutManager(linearLayoutManager);
        // passando como parâmetro o onClickDispositivo
        adapterRvListaDispositivos = new AdapterRvListaDispositivos(arrayListaDispositivo, this);
        dialogListaDispositivosRv.setAdapter(adapterRvListaDispositivos);
        dialogListaDispositivosRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void addDispositivosJaPareados() {
        Set<BluetoothDevice> pareados = servelojaBluetooth.getDispositivosPareados();
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

    private void abrirProgressoDialog(String titulo, String mensagem) {
        materialDialogProgresso = new MaterialDialog.Builder(this)
                .title(titulo)
                .content(mensagem)
                .progress(true, 0)
                .show();
    }

    private void fecharProgressoDialog() {
        if (materialDialogProgresso != null && materialDialogProgresso.isShowing()) {
            materialDialogProgresso.dismiss();
        }
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
        servelojaBluetooth.iniciarComunicacaoPinpad(dispositivo, this);
        servelojaBluetooth.pararProcuraDispositivo();
        dialogParearDispositivos.dismiss();
    }

    @Override
    public void onRespostaTransacaoCliente(int status) {
        Log.d(TAG, "onRespostaTransacaoCliente: status " + status);
        switch (status) {
            case TransacaoEnum.StatusSeveloja.CARTAO_EXIGE_INFORMAR_CVV:

                servelojaTransacaoUtils.informarCvv("123");
                break;
            case TransacaoEnum.StatusSeveloja.CARTAO_EXIGE_INFORMAR_SENHA:
                break;
            case TransacaoEnum.StatusSeveloja.TRANSAC_SERVELOJA_DEBITO_NAO_PERMITIDO:
                break;
            case TransacaoEnum.StatusSeveloja.ENVIANDO_TRANSACAO_SERVELOJA:
                abrirProgressoDialog("Aguarde", "Estabelecendo comunicação com a Serveloja, por favor, aguarde...");
                break;
            case TransacaoEnum.StatusSeveloja.TRANSACAO_FINALIZADA:
                fecharProgressoDialog();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRespostaTokenGerado(boolean status, String mensagem, RespostaCriarToken token) {
        Log.d(TAG, "onRespostaTokenGerado: Status " + status);
        Log.d(TAG, "onRespostaTokenGerado: Mensagem " + mensagem);
        if (status) {
            Log.d(TAG, "onRespostaTokenGerado: Token gerado: " + token.toString());
            // salvar token nas preferencias
        }
    }

    @Override
    public void onRespostaConsultarToken(boolean status, String mensagem, RespostaConsultarToken consulta) {
        Log.d(TAG, "onRespostaTokenGerado: Status " + status);
        Log.d(TAG, "onRespostaTokenGerado: Mensagem " + mensagem);
        if (status) {
            Log.d(TAG, "onRespostaConsultarToken: Resposta consultado: " + consulta.toString());
        }
    }

    @Override
    public void onRespostaCriarTransacao(boolean status, String mensagem, RespostaTransacaoMundipagg respostaTransacao) {
        Log.d(TAG, "onRespostaCriarTransacao: Status " + status);
        Log.d(TAG, "onRespostaCriarTransacao: Mensagem " + mensagem);
        if (status) {
            Log.d(TAG, "onRespostaCriarTransacao: Resposta criar transação: " + respostaTransacao.toString());
        }
    }

    @Override
    public void onRespostaConsultarTransacao(boolean status, String mensagem, RespostaTransacaoMundipagg consulta) {
        Log.d(TAG, "onRespostaConsultarTransacao: Status " + status);
        Log.d(TAG, "onRespostaConsultarTransacao: Mensagem " + mensagem);
        if (status) {
            Log.d(TAG, "onRespostaConsultarTransacao: Resposta consulta: " + consulta.toString());
        }
    }

    @Override
    public void onRespostaConexaoBlueetothPinpad(boolean status, List<ErrorsEnum> listaErros, String mensagem) {
        // conexao via Bluetooth efetuada com sucesso
        if (status) {
            if (servelojaBluetooth.checkPinpadConectado())
                Toast.makeText(this, "Conexao efetuada com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            //
        }
    }
}
