package br.com.servelojapagamento.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 04/07/2017.
 */

public class RespostaRegistrarTransacao {

    @SerializedName("CodRetorno")
    @Expose
    private String codRetorno;
    @SerializedName("MensagemRetorno")
    @Expose
    private String mensagemRetorno;
    @SerializedName("IdTransacao")
    @Expose
    private Integer idTransacao;

    public String getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(String codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getMensagemRetorno() {
        return mensagemRetorno;
    }

    public void setMensagemRetorno(String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    public Integer getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Integer idTransacao) {
        this.idTransacao = idTransacao;
    }

    @Override
    public String toString() {
        return "RespostaRegistrarTransacao{" +
                "codRetorno='" + codRetorno + '\'' +
                ", mensagemRetorno='" + mensagemRetorno + '\'' +
                ", idTransacao=" + idTransacao +
                '}';
    }
}
