package br.com.servelojapagamento.webservice_mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class ParamsCriarTransacaoComToken {

    @SerializedName("valor")
    @Expose
    private String valor;

    @SerializedName("ref_pedido")
    @Expose
    private String refPedido;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("numero_parcelas")
    @Expose
    private int numParcela;

    public ParamsCriarTransacaoComToken() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getRefPedido() {
        return refPedido;
    }

    public void setRefPedido(String refPedido) {
        this.refPedido = refPedido;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    @Override
    public String toString() {
        return "ParamsCriarTransacaoComToken{" +
                "valor='" + valor + '\'' +
                ", refPedido='" + refPedido + '\'' +
                ", token='" + token + '\'' +
                ", numParcela=" + numParcela +
                '}';
    }

}
