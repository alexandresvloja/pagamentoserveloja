package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by alexandre on 20/06/2017.
 */

public class PedidoPinPadResposta {

    @SerializedName("CodRetorno")
    public int codRetorno;
    @SerializedName("IdTransacao")
    public String idPedido;

    @SerializedName("DsErro")
    public DsErro[] dsErro;

    public int getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(int codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public DsErro[] getDsErro() {
        return dsErro;
    }

    public void setDsErro(DsErro[] dsErro) {
        this.dsErro = dsErro;
    }

    @Override
    public String toString() {
        return "PedidoPinPadResposta{" +
                "codRetorno=" + codRetorno +
                ", idPedido='" + idPedido + '\'' +
                ", dsErro=" + Arrays.toString(dsErro) +
                '}';
    }
}
