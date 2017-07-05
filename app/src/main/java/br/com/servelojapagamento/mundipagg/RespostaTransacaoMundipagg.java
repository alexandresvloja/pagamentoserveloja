package br.com.servelojapagamento.mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class RespostaTransacaoMundipagg {

    @SerializedName("nomeAdquirente")
    @Expose
    private String nomeAdquirente;
    @SerializedName("codigoAdquirente")
    @Expose
    private String codigoAdquirente;
    @SerializedName("mensagemAdquirente")
    @Expose
    private String mensagemAdquirente;
    @SerializedName("codigoAutorizacao")
    @Expose
    private String codigoAutorizacao;
    @SerializedName("dataCaptura")
    @Expose
    private String dataCaptura;
    @SerializedName("operacaoCartaoCredito")
    @Expose
    private String operacaoCartaoCredito;
    @SerializedName("statusTransacaoId")
    @Expose
    private Integer statusTransacaoId;
    @SerializedName("statusTransacaoMensagem")
    @Expose
    private String statusTransacaoMensagem;
    @SerializedName("metodoPagamento")
    @Expose
    private String metodoPagamento;
    @SerializedName("sucesso")
    @Expose
    private Boolean sucesso;
    @SerializedName("referenciaTransacao")
    @Expose
    private String referenciaTransacao;
    @SerializedName("chaveTransacao")
    @Expose
    private String chaveTransacao;
    @SerializedName("referenciaPedido")
    @Expose
    private String referenciaPedido;
    @SerializedName("dataCriacaoPedido")
    @Expose
    private String dataCriacaoPedido;
    @SerializedName("chavePedido")
    @Expose
    private String chavePedido;

    public String getNomeAdquirente() {
        return nomeAdquirente;
    }

    public void setNomeAdquirente(String nomeAdquirente) {
        this.nomeAdquirente = nomeAdquirente;
    }

    public String getCodigoAdquirente() {
        return codigoAdquirente;
    }

    public void setCodigoAdquirente(String codigoAdquirente) {
        this.codigoAdquirente = codigoAdquirente;
    }

    public String getMensagemAdquirente() {
        return mensagemAdquirente;
    }

    public void setMensagemAdquirente(String mensagemAdquirente) {
        this.mensagemAdquirente = mensagemAdquirente;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public void setCodigoAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    public String getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(String dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public String getOperacaoCartaoCredito() {
        return operacaoCartaoCredito;
    }

    public void setOperacaoCartaoCredito(String operacaoCartaoCredito) {
        this.operacaoCartaoCredito = operacaoCartaoCredito;
    }

    public Integer getStatusTransacaoId() {
        return statusTransacaoId;
    }

    public void setStatusTransacaoId(Integer statusTransacaoId) {
        this.statusTransacaoId = statusTransacaoId;
    }

    public String getStatusTransacaoMensagem() {
        return statusTransacaoMensagem;
    }

    public void setStatusTransacaoMensagem(String statusTransacaoMensagem) {
        this.statusTransacaoMensagem = statusTransacaoMensagem;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public Boolean getSucesso() {
        return sucesso;
    }

    public void setSucesso(Boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getReferenciaTransacao() {
        return referenciaTransacao;
    }

    public void setReferenciaTransacao(String referenciaTransacao) {
        this.referenciaTransacao = referenciaTransacao;
    }

    public String getChaveTransacao() {
        return chaveTransacao;
    }

    public void setChaveTransacao(String chaveTransacao) {
        this.chaveTransacao = chaveTransacao;
    }

    public String getReferenciaPedido() {
        return referenciaPedido;
    }

    public void setReferenciaPedido(String referenciaPedido) {
        this.referenciaPedido = referenciaPedido;
    }

    public String getDataCriacaoPedido() {
        return dataCriacaoPedido;
    }

    public void setDataCriacaoPedido(String dataCriacaoPedido) {
        this.dataCriacaoPedido = dataCriacaoPedido;
    }

    public String getChavePedido() {
        return chavePedido;
    }

    public void setChavePedido(String chavePedido) {
        this.chavePedido = chavePedido;
    }

    @Override
    public String toString() {
        return "RespostaTransacaoMundipagg{" +
                "nomeAdquirente='" + nomeAdquirente + '\'' + "\n" +
                ", codigoAdquirente='" + codigoAdquirente + '\'' + "\n" +
                ", mensagemAdquirente='" + mensagemAdquirente + '\'' + "\n" +
                ", codigoAutorizacao='" + codigoAutorizacao + '\'' + "\n" +
                ", dataCaptura='" + dataCaptura + '\'' + "\n" +
                ", operacaoCartaoCredito='" + operacaoCartaoCredito + '\'' + "\n" +
                ", statusTransacaoId=" + statusTransacaoId + "\n" +
                ", statusTransacaoMensagem='" + statusTransacaoMensagem + '\'' + "\n" +
                ", metodoPagamento='" + metodoPagamento + '\'' + "\n" +
                ", sucesso=" + sucesso + "\n" +
                ", referenciaTransacao='" + referenciaTransacao + '\'' + "\n" +
                ", chaveTransacao='" + chaveTransacao + '\'' + "\n" +
                ", referenciaPedido='" + referenciaPedido + '\'' + "\n" +
                ", dataCriacaoPedido='" + dataCriacaoPedido + '\'' + "\n" +
                ", chavePedido='" + chavePedido + '\'' + "\n" +
                '}';
    }

}
