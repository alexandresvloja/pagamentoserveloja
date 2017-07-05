package br.com.servelojapagamento.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by alexandre on 19/06/2017.
 */

public class ConteudoResposta {

    @SerializedName("CodRetorno")
    public int codRetorno;
    @SerializedName("NumeroAutorizacao")
    public String numeroAutorizacao;
    @SerializedName("MensagemRetorno")
    public String mensagemRetorno;
    @SerializedName("IdTransacao")
    public long idTransacao;

    @SerializedName("DsErro")
    public DsErro[] dsErro;

    public ConteudoResposta(int codRetorno, String numeroAutorizacao,
                            String mensagemRetorno, long idTransacao, DsErro[] dsErro) {
        super();
        this.codRetorno = codRetorno;
        this.numeroAutorizacao = numeroAutorizacao;
        this.dsErro = dsErro;
        this.mensagemRetorno = mensagemRetorno;
        this.idTransacao = idTransacao;
    }

    public long getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(long idTransacao) {
        this.idTransacao = idTransacao;
    }

    public int getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(int codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getNumeroAutorizacao() {
        return numeroAutorizacao;
    }

    public void setNumeroAutorizacao(String numeroAutorizacao) {
        this.numeroAutorizacao = numeroAutorizacao;
    }

    public DsErro[] getDsErro() {
        return dsErro;
    }

    public void setDsErro(DsErro[] dsErro) {
        this.dsErro = dsErro;
    }

    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    @Override
    public String toString() {
        return "ConteudoResposta{" +
                "codRetorno=" + codRetorno +
                ", numeroAutorizacao='" + numeroAutorizacao + '\'' +
                ", mensagemRetorno='" + mensagemRetorno + '\'' +
                ", idTransacao=" + idTransacao +
                ", dsErro=" + Arrays.toString(dsErro) +
                '}';
    }
}
