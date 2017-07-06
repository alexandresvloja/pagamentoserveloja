# pagamentoserveloja

## Adicionando a biblioteca ao seu projeto
Em Project.gradle, deve está assim:
 ```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Em App.gradle, deve está assim:

```gradle
dependencies {
    ...
    compile 'com.github.alexandresvloja:pagamentoserveloja:1.0.0'
}
```
# Utilizando a biblioteca

## ServelojaBluetooth
Gerencia todas operações em relação a comunicação Bluetooth com a Pidpad

* Construtor
```java
public ServelojaBluetooth(Activity activity)
```

* Define o ouvinte dos Status do Bluetooth. Através deste ouvinte, é possível obter os dispositivos encontrados, os estados (ativado ou desativado) e a finalização de procura de dispositivos.
```java
public void setStatusBluetoothListener(StatusBluetoothListener statusBluetoothListener)
```

* Após chamar esse método, um serviço será inicializado, e assim, detectará todas alterações referente aos Status do Bluetooth, onde essas alterações serão passadas para o ouvinte definido no método setStatusBluetoothListener()
```java
public void iniciarServicoBluetooth()
```

* Pára o serviço do Bluetooth
```java
public void pararServicoBluetooth()
```

* Verifica se o Bluetooth do aparelho, está ativo.
```java
public boolean checkBluetoothAtivado()
```

* Inicializa a procura por dispositivos.
```java
public boolean iniciarProcuraDispositivos()
```

* Pára a procura por dispositivos.
```java
public boolean pararProcuraDispositivo()
```

* Envia uma solicitação para o usuário ativar o Bluetooth do aparelho.
```java
public void solicitarAtivacaoBluetooth()
```

* Constante utilizada para requisição de ativação do Bluetooth. Através dela a aplicação pode interceptar e tratar a resposta da requisição.
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

* Obtém uma lista de dispositivos já pareados.
```java
public Set<BluetoothDevice> getDispositivosPareados()
```

* Inicializa a comunicação com a Pinpad passada como parâmetro (BluetoothDevice)
```java
public void iniciarComunicacaoPinpad(final BluetoothDevice dispositivo, final RespostaConexaoBlueetothPinpadListener respostaConexaoBlueetothPinpadListener)
```

* Verifica se o existe comunicação entre o aparelho e a Pinpad
```java
servelojaBluetooth.checkPinpadConectado()
```



## Implementação ServelojaBluetooth

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
                // verifica se o Bluetooth está ativo
                if (servelojaBluetooth.checkBluetoothAtivado()) {
                    // inicia a busca por dispositivos
                    servelojaBluetooth.iniciarProcuraDispositivos();
                } else {
                    // caso o bluetooth não esteja ativo, solicita o usuário que o ative
                    servelojaBluetooth.solicitarAtivacaoBluetooth();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode " + requestCode);
        // resultCode = -1, indica que o usuário aceitou habilitar o Bluetooth
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
Após procurar os dispositivos é necessário selecionar algum BluetoothDevice para iniciar a comunicação com a Pinpad.

É necessário passar como parâmetro o dispositivo que deseja se comunicar, e o ouvinte para obter a resposta da comunicação, e assim, identificar se a comunicação foi efetuada com sucesso.

```java
public void iniciarComunicacaoPinpad(final BluetoothDevice dispositivo, final RespostaConexaoBlueetothPinpadListener respostaConexaoBlueetothPinpadListener);

@Override
public void onRespostaConexaoBlueetothPinpad(boolean status, List<ErrorsEnum> listaErros) {
    // TRUE, caso a comunicação tenha sido estabelecida
    if (status) {
        // confirmação da comunicação Bluetooth
        if (servelojaBluetooth.checkPinpadConectado())
            Toast.makeText(this, "Conexao efetuada com sucesso!", Toast.LENGTH_SHORT).show();
    } else {
        ...
    }
}
``` 