# pagamentoserveloja

## Adicionando a biblioteca ao seu projeto
Em Project.gradle, deve est� assim:
 ```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Em App.gradle, deve est� assim:

```gradle
dependencies {
    ...
    compile 'com.github.alexandresvloja:pagamentoserveloja:1.0.0'
}
```
# Utilizando a biblioteca

## ServelojaBluetooth
Gerencia todas opera��es em rela��o a comunica��o Bluetooth com a Pidpad

* Construtor
```java
public ServelojaBluetooth(Activity activity)
```

* Define o ouvinte dos Status do Bluetooth. Atrav�s deste ouvinte, � poss�vel obter os dispositivos encontrados, os estados (ativado ou desativado) e a finaliza��o de procura de dispositivos.
```java
public void setStatusBluetoothListener(StatusBluetoothListener statusBluetoothListener)
```

* Ap�s chamar esse m�todo, um servi�o ser� inicializado, e assim, detectar� todas altera��es referente aos Status do Bluetooth, onde essas altera��es ser�o passadas para o ouvinte definido no m�todo setStatusBluetoothListener()
```java
public void iniciarServicoBluetooth()
```

* P�ra o servi�o do Bluetooth
```java
public void pararServicoBluetooth()
```

* Verifica se o Bluetooth do aparelho, est� ativo.
```java
public boolean checkBluetoothAtivado()
```

* Inicializa a procura por dispositivos.
```java
public boolean iniciarProcuraDispositivos()
```

* P�ra a procura por dispositivos.
```java
public boolean pararProcuraDispositivo()
```

* Envia uma solicita��o para o usu�rio ativar o Bluetooth do aparelho.
```java
public void solicitarAtivacaoBluetooth()
```

* Constante utilizada para requisi��o de ativa��o do Bluetooth. Atrav�s dela a aplica��o pode interceptar e tratar a resposta da requisi��o.
```java
public static final int SOLICITACAO_HABILITAR_BLUETOOTH = 1000;

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d(TAG, "onActivityResult: requestCode " + requestCode);
    if (requestCode == ServelojaBluetooth.SOLICITACAO_HABILITAR_BLUETOOTH) {
        ...
    }
}
```

* Obt�m uma lista de dispositivos j� pareados.
```java
public Set<BluetoothDevice> getDispositivosPareados()
```

* Inicializa a comunica��o com a Pinpad passada como par�metro (BluetoothDevice)
```java
public void iniciarComunicacaoPinpad(final BluetoothDevice dispositivo, final RespostaConexaoBlueetothPinpadListener respostaConexaoBlueetothPinpadListener)
```

* Verifica se o existe comunica��o entre o aparelho e a Pinpad
```java
servelojaBluetooth.checkPinpadConectado()
```



## Implementa��o ServelojaBluetooth

```java
public class BluetoothActivity extends AppCompatActivity implements StatusBluetoothListener {
    
    private ServelojaBluetooth servelojaBluetooth;
    private Button btAbrirDialogProcurarDispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        servelojaBluetooth = new ServelojaBluetooth(this);
        servelojaBluetooth.setStatusBluetoothListener(this);

        btAbrirDialogProcurarDispositivos = (Button) findViewById(R.id.ac_main_bt_abrir_dialog);

        btAbrirDialogProcurarDispositivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // verifica se o Bluetooth est� ativo
                if (servelojaBluetooth.checkBluetoothAtivado()) {
                    // inicia a busca por dispositivos
                    servelojaBluetooth.iniciarProcuraDispositivos();
                } else {
                    // caso o bluetooth n�o esteja ativo, solicita o usu�rio que o ative
                    servelojaBluetooth.solicitarAtivacaoBluetooth();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode " + requestCode);
        // resultCode = -1, indica que o usu�rio aceitou habilitar o Bluetooth
        if (requestCode == ServelojaBluetooth.SOLICITACAO_HABILITAR_BLUETOOTH && resultCode == -1) {
            Log.d(TAG, "onActivityResult: resultCode " + resultCode);
            ...
        }
    }

    @Override
    public void onDispositivoEncontradoBluetooth(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onDispositivoEncontradoBluetooth: " + bluetoothDevice.getName());
        ...
    }

    @Override
    public void onEstadoAlteradoBluetooth(int estadoAtual, int estadoAnterior) {
        Log.d(TAG, "onEstadoAlteradoBluetooth: estadoAtual " + estadoAtual + " ,estadoAnterior " + estadoAnterior);
        ...
    }

    @Override
    public void onProcuraDispositivoFinalizadaBluetooth() {
        Log.d(TAG, "onProcuraDispositivoFinalizadaBluetooth: ");
        ...
    }

}
```

Outra forma de implementar o ouvinte StatusBluetoothListener

```java
servelojaBluetooth.setStatusBluetoothListener(new StatusBluetoothListener() {
    
    @Override
    public void onDispositivoEncontradoBluetooth(BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onDispositivoEncontradoBluetooth: " + bluetoothDevice.getName());
        ...
    }

    @Override
    public void onEstadoAlteradoBluetooth(int estadoAtual, int estadoAnterior) {
        Log.d(TAG, "onEstadoAlteradoBluetooth: estadoAtual " + estadoAtual + " ,estadoAnterior " + estadoAnterior);
        ...
    }

    @Override
    public void onProcuraDispositivoFinalizadaBluetooth() {
        Log.d(TAG, "onProcuraDispositivoFinalizadaBluetooth: ");
        ...
    }
    
});
```

## Pareando com a Pinpad
Ap�s procurar os dispositivos � necess�rio selecionar algum BluetoothDevice para iniciar a comunica��o com a Pinpad.

� necess�rio passar como par�metro o dispositivo que deseja se comunicar, e o ouvinte para obter a resposta da comunica��o, e assim, identificar se a comunica��o foi efetuada com sucesso.

```java
public void iniciarComunicacaoPinpad(final BluetoothDevice dispositivo, final RespostaConexaoBlueetothPinpadListener respostaConexaoBlueetothPinpadListener);

@Override
public void onRespostaConexaoBlueetothPinpad(boolean status, List<ErrorsEnum> listaErros) {
    // TRUE, caso a comunica��o tenha sido estabelecida
    if (status) {
        // confirma��o da comunica��o Bluetooth
        if (servelojaBluetooth.checkPinpadConectado())
            Toast.makeText(this, "Conexao efetuada com sucesso!", Toast.LENGTH_SHORT).show();
    } else {
        ...
    }
}
``` 