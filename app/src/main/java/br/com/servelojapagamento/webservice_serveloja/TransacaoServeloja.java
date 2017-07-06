package br.com.servelojapagamento.webservice_serveloja;

/**
 * Created by alexandre on 05/07/2017.
 */

public class TransacaoServeloja {

    private String comprovante;
    private String descricao;
    private String problemas;
    private String dddTelefone;
    private String numTelefone;
    private boolean isUsoTarja;
    private String valor;
    private String valorSemTaxas;
    private int tipoTransacao;
    private int numParcelas;
    private String cpfCnpjComprador;
    private String cpfCnpjAdesao;

    public TransacaoServeloja() {
        comprovante = "";
        descricao = "";
        problemas = "";
        dddTelefone = "";
        numTelefone = "";
        cpfCnpjComprador = "";
        cpfCnpjAdesao = "";
    }

    public String getCpfCnpjAdesao() {
        return cpfCnpjAdesao;
    }

    public void setCpfCnpjAdesao(String cpfCnpjAdesao) {
        this.cpfCnpjAdesao = cpfCnpjAdesao;
    }

    public String getCpfCnpjComprador() {
        return cpfCnpjComprador;
    }

    public void setCpfCnpjComprador(String cpfCnpjComprador) {
        this.cpfCnpjComprador = cpfCnpjComprador;
    }

    public String getComprovante() {
        return comprovante;
    }

    public void setComprovante(String comprovante) {
        this.comprovante = comprovante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public boolean isUsoTarja() {
        return isUsoTarja;
    }

    public void setUsoTarja(boolean usoTarja) {
        isUsoTarja = usoTarja;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValorSemTaxas() {
        return valorSemTaxas;
    }

    public void setValorSemTaxas(String valorSemTaxas) {
        this.valorSemTaxas = valorSemTaxas;
    }

    public int getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(int tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public int getNumParcelas() {
        return numParcelas;
    }

    public void setNumParcelas(int numParcelas) {
        this.numParcelas = numParcelas;
    }

    @Override
    public String toString() {
        return "TransacaoServeloja{" +
                "comprovante='" + comprovante + '\'' +
                ", descricao='" + descricao + '\'' +
                ", problemas='" + problemas + '\'' +
                ", dddTelefone='" + dddTelefone + '\'' +
                ", numTelefone='" + numTelefone + '\'' +
                ", isUsoTarja=" + isUsoTarja +
                ", valor='" + valor + '\'' +
                ", valorSemTaxas='" + valorSemTaxas + '\'' +
                ", tipoTransacao=" + tipoTransacao +
                ", numParcelas=" + numParcelas +
                '}';
    }

}
