# pagamentoserveloja
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

* Obt�m uma lista de dispositivos j� pareados.
```java
public Set<BluetoothDevice> getDispositivosPareados()
```

## Implementa��o ServelojaBluetooth

```java
public class MainActivity extends AppCompatActivity implements StatusBluetoothListener {
    
    private ServelojaBluetooth servelojaBluetooth;
    private Button btAbrirDialogProcurarDispositivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    servelojaBluetooth = new ServelojaBluetooth(this);

    // a pr�ria Activity, implementa o ouvinte, e assim, passando this como par�metro de ouvinte
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
                // caso n�o esteja, solicita o usu�rio que o ative
                servelojaBluetooth.solicitarAtivacaoBluetooth();
            }
        }
    });

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
```

Outra forma de implementar o ouvinte StatusBluetoothListener

```java
servelojaBluetooth.setStatusBluetoothListener(new StatusBluetoothListener() {
    
    @Override
    public void onDispositivoEncontradoBluetooth(BluetoothDevice dispositivo) {

    }

    @Override
    public void onEstadoAlteradoBluetooth(int estadoAtual, int estadoAnterior) {

    }

    @Override
    public void onProcuraDispositivoFinalizadaBluetooth() {

    }
    
});
```