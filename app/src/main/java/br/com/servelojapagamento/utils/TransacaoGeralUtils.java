package br.com.servelojapagamento.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.com.servelojapagamento.interfaces.RespostaTransacaoClienteListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoRegistradaServelojaListener;
import br.com.servelojapagamento.interfaces.RespostaTransacaoStoneListener;
import br.com.servelojapagamento.modelo.Bandeira;
import br.com.servelojapagamento.modelo.ConteudoBandeira;
import br.com.servelojapagamento.modelo.ParamsRegistrarTransacao;
import br.com.servelojapagamento.modelo.PedidoPinPadResposta;
import br.com.servelojapagamento.modelo.TransacaoServeloja;
import br.com.servelojapagamento.preferences.PrefsHelper;
import br.com.servelojapagamento.webservice.ServelojaWebService;
import stone.application.enums.TransactionStatusEnum;
import stone.database.transaction.TransactionDAO;
import stone.database.transaction.TransactionObject;
import stone.providers.TransactionProvider;

/**
 * Efetua a comunicação entre a transação da Stone com a WebService da Serveloja
 * Created by alexandre on 04/07/2017.
 */

public class TransacaoGeralUtils
        implements RespostaTransacaoStoneListener,
        RespostaTransacaoRegistradaServelojaListener {

    private Activity activity;
    private String TAG;
    private StoneUtils stoneUtils;
    private ServelojaWebService servelojaWebService;
    private ParamsRegistrarTransacao paramsRegistrarTransacao;
    private PrefsHelper prefsHelper;
    private ConteudoBandeira conteudoBandeira;
    private boolean cartaoExigeCvv;
    private boolean cartaoExigeSenha;
    private RespostaTransacaoClienteListener respostaTransacaoClienteListener;


    public TransacaoGeralUtils(Activity activity) {
        this.activity = activity;
        this.TAG = getClass().getSimpleName();
        this.stoneUtils = new StoneUtils(activity);
        this.paramsRegistrarTransacao = new ParamsRegistrarTransacao();
        this.prefsHelper = new PrefsHelper(activity);
        this.servelojaWebService = new ServelojaWebService(activity);
        iniciarListaConteudoBandeira();
    }

    public void iniciarTransacao(TransacaoServeloja transacaoServeloja, RespostaTransacaoClienteListener respostaTransacaoClienteListener) {
        Log.d(TAG, "iniciarTransacao: ");
        this.respostaTransacaoClienteListener = respostaTransacaoClienteListener;
        paramsRegistrarTransacao.setValor(transacaoServeloja.getValor());
        paramsRegistrarTransacao.setValorSemTaxas(transacaoServeloja.getValorSemTaxas());
        paramsRegistrarTransacao.setTipoTransacao(transacaoServeloja.getTipoTransacao());
        paramsRegistrarTransacao.setNumParcelas(transacaoServeloja.getNumParcelas());
        paramsRegistrarTransacao.setPinPadId(prefsHelper.getPinpadModelo());
        paramsRegistrarTransacao.setPinPadMac(prefsHelper.getPinpadMac());
        paramsRegistrarTransacao.setLatLng(prefsHelper.getLocalizacao());
        paramsRegistrarTransacao.setNsuSitef("");
        paramsRegistrarTransacao.setComprovante(transacaoServeloja.getComprovante());
        paramsRegistrarTransacao.setDescricao(transacaoServeloja.getDescricao());
        paramsRegistrarTransacao.setProblemas(transacaoServeloja.getProblemas());
        paramsRegistrarTransacao.setDddTelefone(transacaoServeloja.getDddTelefone());
        paramsRegistrarTransacao.setNumTelefone(transacaoServeloja.getNumTelefone());
        paramsRegistrarTransacao.setUsoTarja(transacaoServeloja.isUsoTarja());
        paramsRegistrarTransacao.setDataTransacao(Utils.getDataAtualStr());
        paramsRegistrarTransacao.setCpfCNPJ("");
        paramsRegistrarTransacao.setCpfCnpjAdesao("");
        // chamando procedimento de transação da Stone, e sua resposta será trazida
        // no Callback onRespostaTransacaoStone
        stoneUtils.iniciarTransacao(paramsRegistrarTransacao.getValor(),
                paramsRegistrarTransacao.getTipoTransacao(),
                paramsRegistrarTransacao.getNumParcelas(), this);
    }

    private void setupParametrosParaRegistrarTransacao(TransactionObject dadosTransacao) {
        Log.d(TAG, "setupParametrosParaRegistrarTransacao: ");
        // parametros retornado referente a transação atual (última transação)
        String cartaoNomeTitular = dadosTransacao.getCardHolderName().trim();
        String cartaoBin = dadosTransacao.getCardHolderNumber().substring(0, 6);
        String cartaoNumero = dadosTransacao.getCardHolderNumber();
        String codAutorizacao = dadosTransacao.getAuthorizationCode();
        String nsuHost = dadosTransacao.getRecipientTransactionIdentification();
        String cartaoBandeira = Utils.obterBandeiraPorBin(cartaoBin);
        String statusTransacao = getStatusTransacao(dadosTransacao.getTransactionStatus());
        // set parametros salvo nas preferencias
        paramsRegistrarTransacao.setNomeTitularCartao(cartaoNomeTitular);
        paramsRegistrarTransacao.setChaveAcesso(prefsHelper.getChaveAcesso());
        paramsRegistrarTransacao.setBandeiraCartao(cartaoBandeira);
        paramsRegistrarTransacao.setNsuHost(nsuHost);
        paramsRegistrarTransacao.setCodAutorizacao(codAutorizacao);
        paramsRegistrarTransacao.setStatusTransacao(statusTransacao);
        paramsRegistrarTransacao.setNumCartao(cartaoNumero);
        paramsRegistrarTransacao.setNumBinCartao(cartaoBin);
        Log.d(TAG, "setupParametrosParaRegistrarTransacao: " + paramsRegistrarTransacao.toString());
    }

    private void criptografarTransacao() {
        Log.d(TAG, "setupParametrosParaRegistrarTransacao: ");
        paramsRegistrarTransacao.setBandeiraCartao(Utils.encriptar(paramsRegistrarTransacao.getBandeiraCartao()));
        paramsRegistrarTransacao.setNumCartao(Utils.encriptar(paramsRegistrarTransacao.getNumCartao()));
    }

    private String getStatusTransacao(TransactionStatusEnum transactionStatusEnum) {
        switch (transactionStatusEnum) {
            case APPROVED:
                return "APR";
            case DECLINED:
                return "DEC";
            case REJECTED:
                return "REJ";
        }
        return "";
    }

    private String tratarData(String data) {
        int ano = Integer.valueOf(data.substring(0, 2));
        int mes = Integer.valueOf(data.substring(2));
        String result = data.substring(2) + "/";
        result += ano < 60 ? String.valueOf(ano + 2000) : String.valueOf(ano + 1900);
        return result;
    }

    /**
     * Resposta da transação via Stone
     * ao efetuar esta transação, será efetuado o registro na base de dados da Serveloja
     *
     * @param status
     * @param transactionProvider
     */
    @Override
    public void onRespostaTransacaoStone(boolean status, TransactionProvider transactionProvider) {
        Log.d(TAG, "onRespostaTransacaoStone: status " + status);
        Log.d(TAG, "onRespostaTransacaoStone: listaErros " + transactionProvider.getListOfErrors().toString());
        if (status) {
            if (transactionProvider.getListOfErrors().size() > 0) {
                Log.d(TAG, "onRespostaTransacaoStone: erro na transação com a Stone - lista de erro > 0");
            } else {
                Log.d(TAG, "onRespostaTransacaoStone: transação efetuada com sucesso");
                // após verificação de não ocorrência de erros, procede para preparação dos parâmetros
                // e seguir para etapa de inserção na base de dados Serveloja
                TransactionDAO transactionDAO = new TransactionDAO(activity);
                TransactionObject transactionObject = transactionDAO.findTransactionWithId(
                        transactionDAO.getLastTransactionId());
                setupParametrosParaRegistrarTransacao(transactionObject);
                criptografarTransacao();
                servelojaWebService.registrarTransacao(paramsRegistrarTransacao, this);
            }
        } else {
            // Vendas por tarja magnética sempre retornam o callback onError (false) pois a stone não suporta
            // esse tipo de operação, neste momento, verificar qual foi o Erro e mudar o fluxo caso
            // o fluxo seja para outras bandeiras.
            if (transactionProvider.getListOfErrors().size() > 0) {
                Log.d(TAG, "onRespostaTransacaoStone: erro na transação com a Stone");
                // indicando erro de transação com a Stone
            } else {
                // transação de débito via tarja, não é permitida
                if (paramsRegistrarTransacao.getTipoTransacao() == TransacaoEnum.TipoTransacao.DEBITO) {

                } else {
                    // indicando que o erro foi referente a tarja
                    Log.d(TAG, "onRespostaTransacaoStone: seguindo o fluxo para transação com tarja");
                    // Usar Reflection para pegar o numero de cartao da resposta do provider tratando a resposta
                    Object dadosCartao = null;
                    try {
                        Field field = transactionProvider.getClass().getDeclaredField("gcrResponseCommand");
                        field.setAccessible(true);
                        // obtem os dados referente ao cartão
                        dadosCartao = field.get(transactionProvider);
                        String cartaoBin = dadosCartao.toString().split(",")[8].split("=")[1].substring(1, 7);
                        String bandeira = Utils.obterBandeiraPorBin(cartaoBin);
                        // verifica se a bandeira do cartão, não é permitida pela STONE, pois assim, este cartão, deverá
                        // ser lido com o CHIP. (Caso seja permitido pela Stone, indica que o mesmo possui CHIP)
                        // e verifica se a operação não é de débito
                        if (!stoneUtils.checkBandeiraPermitidaPelaStone(bandeira)) {
                            Log.d(TAG, "onRespostaTransacaoStone: cartão permitido");
                            TransactionDAO transactionDAO = new TransactionDAO(activity);
                            TransactionObject transactionObject = transactionDAO.findTransactionWithId(
                                    transactionDAO.getLastTransactionId());
                            setupParametrosParaRegistrarTransacao(transactionObject);
                            String[] tracktrace = dadosCartao.toString().split(",")[8].split("=");
                            String dataValidadeCartao = tratarData(tracktrace[2].substring(0, 4));
                            // atualização dos atributos referente ao cartão
                            paramsRegistrarTransacao.setBandeiraCartao(bandeira);
                            paramsRegistrarTransacao.setNumCartao(tracktrace[1].substring(1));
                            paramsRegistrarTransacao.setDataValidadeCartao(dataValidadeCartao);
                            paramsRegistrarTransacao.setNumBinCartao(cartaoBin);
                            preVerificacaoTransacaoSegura();
                        }
                    } catch (NoSuchFieldException e) {
                        Log.d(TAG, "onRespostaTransacaoStone: NoSuchFieldException " + e.getMessage());
                    } catch (IllegalAccessException e) {
                        Log.d(TAG, "onRespostaTransacaoStone: IllegalAccessException " + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Após verificar que a bandeira é aceita pelo autorizador Serveloja, é feito um pré-processamento
     * efetuar a transação segura
     */
    private void preVerificacaoTransacaoSegura() {
        paramsRegistrarTransacao.setCodSeguracaCartao("");
        paramsRegistrarTransacao.setSenhaCartao("");
        Log.d(TAG, "preVerificacaoTransacaoSegura: ");
        Log.d(TAG, "preVerificacaoTransacaoSegura: " + paramsRegistrarTransacao.toString());
        if (paramsRegistrarTransacao.getBandeiraCartao().equalsIgnoreCase("assomise")
                && paramsRegistrarTransacao.getDataValidadeCartao().equals(""))
            paramsRegistrarTransacao.setDataValidadeCartao("01/20");
        if (!paramsRegistrarTransacao.getNumBinCartao().equals("") &&
                !paramsRegistrarTransacao.getDataValidadeCartao().equals("")) {
            if (checkExigeCVV(paramsRegistrarTransacao.getBandeiraCartao().toLowerCase())) {
                Log.d(TAG, "iniciarTransacaoServeloja: checkExigeCVV true");
                cartaoExigeCvv = true;
                // notifica que é necessário o CVV
                respostaTransacaoClienteListener.onRespostaTransacaoCliente(
                        TransacaoEnum.StatusRetorno.CARTAO_EXIGE_INFORMAR_CVV);
            } else if (checkExigeSenha(paramsRegistrarTransacao.getBandeiraCartao().toLowerCase())) {
                Log.d(TAG, "iniciarTransacaoServeloja: checkExigeSenha true");
                cartaoExigeSenha = true;
                // notifica que é necessário a SENHA
                respostaTransacaoClienteListener.onRespostaTransacaoCliente(
                        TransacaoEnum.StatusRetorno.CARTAO_EXIGE_INFORMAR_SENHA);
            } else {
                iniciarTransacaoSegura();
            }
        }
    }

    /**
     * Na transação SEGURA, pode ser feito a solicitação da CVV, logo, o usuário deverá informar a CVV através
     * desse método
     *
     * @param cvv
     */
    public void informarCvv(String cvv) {
        Log.d(TAG, "informarCvv: ");
        if (cartaoExigeCvv) {
            paramsRegistrarTransacao.setCodSeguracaCartao(Utils.encriptar(cvv));
            // após informar a CVV, verifica a exigência de SENHA
            if (checkExigeSenha(paramsRegistrarTransacao.getBandeiraCartao().toLowerCase())) {
                Log.d(TAG, "informarCvv: checkExigeSenha true");
                cartaoExigeSenha = true;
                // notifica o usuário para informar a senha
                respostaTransacaoClienteListener.onRespostaTransacaoCliente(
                        TransacaoEnum.StatusRetorno.CARTAO_EXIGE_INFORMAR_SENHA);
            } else {
                iniciarTransacaoSegura();
            }
        }
    }

    /**
     * Na transação SEGURA, pode ser feito a solicitação da SENHA, logo, o usuário deverá informar a SENHA através
     * desse método
     *
     * @param senha
     */
    public void informarSenha(String senha) {
        Log.d(TAG, "informarSenha: ");
        if (cartaoExigeSenha) {
            paramsRegistrarTransacao.setSenhaCartao(Utils.encriptar(senha));
            // após informar  senha, é iniciada a transação segura
            iniciarTransacaoSegura();
        }
    }

    private void iniciarTransacaoSegura() {
        Log.d(TAG, "iniciarTransacaoSegura: ");
        // criptografia dos campos
        paramsRegistrarTransacao.setImei(Utils.getIMEI(activity));
        paramsRegistrarTransacao.setBandeiraCartao(Utils.encriptar(paramsRegistrarTransacao.getBandeiraCartao()));
        paramsRegistrarTransacao.setNumCartao(Utils.encriptar(paramsRegistrarTransacao.getNumCartao()));
        paramsRegistrarTransacao.setDataValidadeCartao(Utils.encriptar(paramsRegistrarTransacao.getDataValidadeCartao()));
        paramsRegistrarTransacao.setIp(Utils.getLocalIpAddress());
        paramsRegistrarTransacao.setClienteInformouCartaoInvalido(false);
        paramsRegistrarTransacao.setCpfCnpjAdesao(paramsRegistrarTransacao.getCpfCnpjAdesao());
        paramsRegistrarTransacao.setCpfCNPJ(Utils.encriptar(paramsRegistrarTransacao.getCpfCNPJ()));
        // iniciar registro transação
        Log.d(TAG, "iniciarTransacaoSegura: " + paramsRegistrarTransacao.toString());
        servelojaWebService.registrarTransacaoSegura(paramsRegistrarTransacao);
    }

    /**
     * Resposta do registro da transação na base de dados Serveloja
     * Após efetuar a transação com a Stone, este método é chamado
     *
     * @param status
     * @param pedidoPinPadResposta
     * @param mensagemErro
     */
    @Override
    public void onRespostaTransacaoRegistradaServeloja(boolean status, PedidoPinPadResposta pedidoPinPadResposta,
                                                       String mensagemErro) {
        Log.d(TAG, "onRespostaTransacaoRegistradaServeloja: status " + status);
        Log.d(TAG, "onRespostaTransacaoRegistradaServeloja: mensagemErro " + mensagemErro);
        if (status) {
            Log.d(TAG, "onRespostaTransacaoRegistradaServeloja: PedidoPinPadResposta " +
                    pedidoPinPadResposta.toString());
        }

    }

    private boolean checkExigeCVV(String bandeira) {
        Log.d(TAG, "checkExigeCVV: " + bandeira);
        boolean r = false;
        for (Bandeira b : conteudoBandeira.bandeiras)
            if (b.getNomeBandeira().toLowerCase().equals(bandeira.toLowerCase())) {
                r = Boolean.parseBoolean(b.possuiCCV);
            }
        return r;
    }

    private boolean checkExigeSenha(String bandeira) {
        Log.d(TAG, "checkExigeSenha: " + bandeira);
        boolean r = false;
        for (Bandeira b : conteudoBandeira.bandeiras)
            if (b.getNomeBandeira().toLowerCase().equals(bandeira.toLowerCase())) {
                Log.d(TAG, "Bandeira: " + b.toString());
                r = Boolean.parseBoolean(b.possuiSenha);
            }
        return r;
    }

    private void iniciarListaConteudoBandeira() {
        Bandeira[] bandeiras = {
                new Bandeira(2,
                        "AMEX",
                        "False",
                        "True"),

                new Bandeira(18,
                        "ASSOMISE",
                        "True",
                        "True"),

                new Bandeira(4,
                        "DINERS",
                        "False",
                        "True"),

                new Bandeira(16,
                        "ELO",
                        "False",
                        "True"),

                new Bandeira(10,
                        "FORTBRASIL",
                        "False",
                        "True"),

                new Bandeira(7,
                        "HIPER",
                        "False",
                        "True"),

                new Bandeira(5,
                        "HIPERCARD",
                        "False",
                        "True"),

                new Bandeira(3,
                        "MASTERCARD",
                        "False",
                        "True"),

                new Bandeira(6,
                        "SOROCRED",
                        "False",
                        "True"),

                new Bandeira(1,
                        "VISA",
                        "False",
                        "True")
        };
        conteudoBandeira = new ConteudoBandeira(1, bandeiras);
    }

    private class ComunicacaoWebService extends AsyncTask<String, Void, String> {

        int codRetorno = 0;
        int codRetornoErro = 0;
        String numeroAutorizacao = "";
        String mensagemRetorno = "";
        String msgErro = "";
        ArrayList<String> conteudosComprovante;
        long idTransacao = 0;


        @Override
        protected String doInBackground(String... params) {

            try {
                JSONObject jsonObj = new JSONObject();

                jsonObj.put("ChaveAcesso", prefsHelper.getChaveAcesso());
                jsonObj.put("CodChip", paramsRegistrarTransacao.getImei());
                jsonObj.put("Bandeira", paramsRegistrarTransacao.getBandeiraCartao());
                jsonObj.put("CpfCnpjComprador", paramsRegistrarTransacao.getCpfCNPJ());
                jsonObj.put("nmTitular", "");
                jsonObj.put("NrCartao", paramsRegistrarTransacao.getNumCartao());
                jsonObj.put("CodSeguranca", paramsRegistrarTransacao.getCodSeguracaCartao());
                jsonObj.put("DataValidade", paramsRegistrarTransacao.getDataValidadeCartao());
                jsonObj.put("Valor", paramsRegistrarTransacao.getValor());
                jsonObj.put("ValorSemTaxas", paramsRegistrarTransacao.getValorSemTaxas());
                jsonObj.put("QtParcela", paramsRegistrarTransacao.getNumParcelas());
                jsonObj.put("DDDCelular", paramsRegistrarTransacao.getDddTelefone());
                jsonObj.put("NrCelular", paramsRegistrarTransacao.getNumTelefone());
                jsonObj.put("EnderecoIPComprador", Utils.getLocalIpAddress());
                jsonObj.put("DsObservacao", paramsRegistrarTransacao.getDescricao());
                jsonObj.put("CodigoFranquia", "0");
                jsonObj.put("CpfCnpjAdesao", paramsRegistrarTransacao.getCpfCnpjAdesao());
                jsonObj.put("NomeTitularAdesao", "");
                jsonObj.put("UtilizouLeitor", "false");
                jsonObj.put("Origem", "A");
                jsonObj.put("ClienteInformadoCartaoInvalido", false);
                jsonObj.put("LatitudeLongitude", prefsHelper.getLocalizacao());
                jsonObj.put("SenhaCartao", "");
                jsonObj.put("OutrasBandeiras", false);
                // Create the POST object and add the parameters

//                String p = String.valueOf(jsonObj).replaceAll(":", "=");
                Log.d(TAG, "doInBackground: " + jsonObj.toString());

//                String k = jsonObj.toString()
//                        .replaceAll(":", "=")
//                        .replaceAll(",", "&")
//                        .replaceAll("\"", "")
//                        .replaceAll("\\{", "")
//                        .replaceAll("\\}", "");
//
//                Log.d(TAG, "doInBackground: " + String.valueOf(k));

                URL url = new URL("http://desenvolvimento.redeserveloja.com/ServicosWeb/Versao/1.13/Mobile.asmx/EfetuarVendaCreditoMobile");
//                url = new URL("https://www.sistemaserveloja.com.br/ServicosWeb/Versao/1.13/Mobile.asmx/EfetuarVendaCreditoMobile");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream outputStream = new BufferedOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));
                writer.write(String.valueOf(jsonObj));
//                writer.write(String.valueOf(k));
                writer.flush();
                writer.close();
                outputStream.close();


                InputStream inputStream;
                // get stream
                if (conn.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                    inputStream = conn.getInputStream();
                } else {
                    inputStream = conn.getErrorStream();
                }
                // parse stream
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp, json = "";
                while ((temp = bufferedReader.readLine()) != null) {
                    json += temp;
                }
                if (!json.equals("")) {
                    Log.d(TAG, "doInBackground: " + json.toString());
                    return json;
                }

            } catch (Exception e) {
                Log.e(TAG, "Falha ao acessar Web service", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }

    }

}
