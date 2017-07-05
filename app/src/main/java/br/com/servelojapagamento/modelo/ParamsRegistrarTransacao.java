package br.com.servelojapagamento.modelo;

/**
 * Created by alexandre on 04/07/2017.
 */

public class ParamsRegistrarTransacao {

    private String chaveAcesso;
    private String valor;
    private int numParcelas;
    private String descricao;
    private int tipoTransacao;
    private String pinPadId;
    private String pinPadMac;
    private String latLng;
    private String valorSemTaxas;
    private String bandeiraCartao;
    private String numCartao;
    private String nsuHost;
    private String nsuSitef;
    private String codAutorizacao;
    private boolean isUsoTarja;
    private String statusTransacao;
    private String dataTransacao;
    private String problemas;
    private String dddTelefone;
    private String numTelefone;
    private String comprovante;
    private String dataValidadeCartao;
    private String numBinCartao;
    private String imei;
    private String ip;
    private String cpfCNPJ;
    private String codSeguracaCartao;
    private String senhaCartao;
    private String nomeTitularCartao;
    private int codFranquia;
    private String cpfCnpjAdesao;
    private String nomeTitularAdesao;
    private boolean utilizouLeitor;
    private boolean clienteInformouCartaoInvalido;

    public ParamsRegistrarTransacao() {
        // reset campos
        descricao = "";
        pinPadId = "";
        pinPadMac = "";
        latLng = "";
        nsuHost = "";
        nsuSitef = "";
        codAutorizacao = "";
        problemas = "";
        dddTelefone = "";
        numTelefone = "";
        comprovante = "";
        nomeTitularAdesao = "";
        cpfCnpjAdesao = "";
        codFranquia = 0;
    }

    public ParamsRegistrarTransacao(
            String chaveAcesso,
            String valor,
            int numParcelas,
            String descricao,
            int tipoTransacao,
            String pinPadId,
            String pinPadMac,
            String latLng,
            String valorSemTaxas,
            String bandeiraCartao,
            String numCartao,
            String nsuHost,
            String nsuSitef,
            String codAutorizacao,
            boolean isUsoTarja,
            String statusTransacao,
            String dataTransacao,
            String problemas,
            String dddTelefone,
            String numTelefone,
            String comprovante) {

        this.chaveAcesso = chaveAcesso;
        this.valor = valor;
        this.numParcelas = numParcelas;
        this.descricao = descricao;
        this.tipoTransacao = tipoTransacao;
        this.pinPadId = pinPadId;
        this.pinPadMac = pinPadMac;
        this.latLng = latLng;
        this.valorSemTaxas = valorSemTaxas;
        this.bandeiraCartao = bandeiraCartao;
        this.numCartao = numCartao;
        this.nsuHost = nsuHost;
        this.nsuSitef = nsuSitef;
        this.codAutorizacao = codAutorizacao;
        this.isUsoTarja = isUsoTarja;
        this.statusTransacao = statusTransacao;
        this.dataTransacao = dataTransacao;
        this.problemas = problemas;
        this.dddTelefone = dddTelefone;
        this.numTelefone = numTelefone;
        this.comprovante = comprovante;
    }

    public int getCodFranquia() {
        return codFranquia;
    }

    public void setCodFranquia(int codFranquia) {
        this.codFranquia = codFranquia;
    }

    public String getCpfCnpjAdesao() {
        return cpfCnpjAdesao;
    }

    public void setCpfCnpjAdesao(String cpfCnpjAdesao) {
        this.cpfCnpjAdesao = cpfCnpjAdesao;
    }

    public String getNomeTitularAdesao() {
        return nomeTitularAdesao;
    }

    public void setNomeTitularAdesao(String nomeTitularAdesao) {
        this.nomeTitularAdesao = nomeTitularAdesao;
    }

    public boolean isUtilizouLeitor() {
        return utilizouLeitor;
    }

    public void setUtilizouLeitor(boolean utilizouLeitor) {
        this.utilizouLeitor = utilizouLeitor;
    }

    public boolean isClienteInformouCartaoInvalido() {
        return clienteInformouCartaoInvalido;
    }

    public void setClienteInformouCartaoInvalido(boolean clienteInformouCartaoInvalido) {
        this.clienteInformouCartaoInvalido = clienteInformouCartaoInvalido;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCpfCNPJ() {
        return cpfCNPJ;
    }

    public void setCpfCNPJ(String cpfCNPJ) {
        this.cpfCNPJ = cpfCNPJ;
    }

    public String getCodSeguracaCartao() {
        return codSeguracaCartao;
    }

    public void setCodSeguracaCartao(String codSeguracaCartao) {
        this.codSeguracaCartao = codSeguracaCartao;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public String getNomeTitularCartao() {
        return nomeTitularCartao;
    }

    public void setNomeTitularCartao(String nomeTitularCartao) {
        this.nomeTitularCartao = nomeTitularCartao;
    }

    public String getNumBinCartao() {
        return numBinCartao;
    }

    public void setNumBinCartao(String numBinCartao) {
        this.numBinCartao = numBinCartao;
    }

    public String getDataValidadeCartao() {
        return dataValidadeCartao;
    }

    public void setDataValidadeCartao(String dataValidadeCartao) {
        this.dataValidadeCartao = dataValidadeCartao;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }


    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getNumParcelas() {
        return numParcelas;
    }

    public void setNumParcelas(int numParcelas) {
        this.numParcelas = numParcelas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(int tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public String getPinPadId() {
        return pinPadId;
    }

    public void setPinPadId(String pinPadId) {
        this.pinPadId = pinPadId;
    }

    public String getPinPadMac() {
        return pinPadMac;
    }

    public void setPinPadMac(String pinPadMac) {
        this.pinPadMac = pinPadMac;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getValorSemTaxas() {
        return valorSemTaxas;
    }

    public void setValorSemTaxas(String valorSemTaxas) {
        this.valorSemTaxas = valorSemTaxas;
    }

    public String getBandeiraCartao() {
        return bandeiraCartao;
    }

    public void setBandeiraCartao(String bandeiraCartao) {
        this.bandeiraCartao = bandeiraCartao;
    }

    public String getNsuHost() {
        return nsuHost;
    }

    public void setNsuHost(String nsuHost) {
        this.nsuHost = nsuHost;
    }

    public String getNsuSitef() {
        return nsuSitef;
    }

    public void setNsuSitef(String nsuSitef) {
        this.nsuSitef = nsuSitef;
    }

    public String getCodAutorizacao() {
        return codAutorizacao;
    }

    public void setCodAutorizacao(String codAutorizacao) {
        this.codAutorizacao = codAutorizacao;
    }

    public boolean isUsoTarja() {
        return isUsoTarja;
    }

    public void setUsoTarja(boolean usoTarja) {
        isUsoTarja = usoTarja;
    }

    public String getStatusTransacao() {
        return statusTransacao;
    }

    public void setStatusTransacao(String statusTransacao) {
        this.statusTransacao = statusTransacao;
    }

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public String getProblemas() {
        return problemas;
    }

    public void setProblemas(String problemas) {
        this.problemas = problemas;
    }

    public String getDddTelefone() {
        return dddTelefone;
    }

    public void setDddTelefone(String dddTelefone) {
        this.dddTelefone = dddTelefone;
    }

    public String getNumTelefone() {
        return numTelefone;
    }

    public void setNumTelefone(String numTelefone) {
        this.numTelefone = numTelefone;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    @Override
    public String toString() {
        return "ParamsRegistrarTransacao{" + "\n" +
                "chaveAcesso='" + chaveAcesso + '\'' + "\n" +
                ", valor='" + valor + '\'' + "\n" +
                ", numParcelas=" + numParcelas + "\n" +
                ", descricao='" + descricao + '\'' + "\n" +
                ", tipoTransacao=" + tipoTransacao + "\n" +
                ", pinPadId='" + pinPadId + '\'' + "\n" +
                ", pinPadMac='" + pinPadMac + '\'' + "\n" +
                ", latLng='" + latLng + '\'' + "\n" +
                ", valorSemTaxas='" + valorSemTaxas + '\'' + "\n" +
                ", bandeiraCartao='" + bandeiraCartao + '\'' + "\n" +
                ", numCartao='" + numCartao + '\'' + "\n" +
                ", nsuHost='" + nsuHost + '\'' + "\n" +
                ", nsuSitef='" + nsuSitef + '\'' + "\n" +
                ", codAutorizacao='" + codAutorizacao + '\'' + "\n" +
                ", isUsoTarja=" + isUsoTarja + "\n" +
                ", statusTransacao='" + statusTransacao + '\'' + "\n" +
                ", dataTransacao='" + dataTransacao + '\'' + "\n" +
                ", problemas='" + problemas + '\'' + "\n" +
                ", dddTelefone='" + dddTelefone + '\'' + "\n" +
                ", numTelefone='" + numTelefone + '\'' + "\n" +
                ", comprovante='" + comprovante + '\'' + "\n" +
                ", dataValidadeCartao='" + dataValidadeCartao + '\'' + "\n" +
                ", numBinCartao='" + numBinCartao + '\'' + "\n" +
                ", imei='" + imei + '\'' + "\n" +
                ", ip='" + ip + '\'' + "\n" +
                ", cpfCNPJ='" + cpfCNPJ + '\'' + "\n" +
                ", codSeguracaCartao='" + codSeguracaCartao + '\'' + "\n" +
                ", senhaCartao='" + senhaCartao + '\'' + "\n" +
                ", nomeTitularCartao='" + nomeTitularCartao + '\'' + "\n" +
                ", codFranquia=" + codFranquia + "\n" +
                ", cpfCnpjAdesao='" + cpfCnpjAdesao + '\'' + "\n" +
                ", nomeTitularAdesao='" + nomeTitularAdesao + '\'' + "\n" +
                ", utilizouLeitor=" + utilizouLeitor + "\n" +
                ", clienteInformouCartaoInvalido=" + clienteInformouCartaoInvalido + "\n" +
                '}';
    }


}
