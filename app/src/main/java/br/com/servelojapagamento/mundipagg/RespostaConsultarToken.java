package br.com.servelojapagamento.mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class RespostaConsultarToken {

    @SerializedName("ErrorReport")
    @Expose
    private Object erro;

    @SerializedName("CreditCardDataCollection")
    @Expose
    private List<RespostaConsultarTokenCartao> listaCartao = null;

    public Object getErro() {
        return erro;
    }

    public void setErro(Object erro) {
        this.erro = erro;
    }

    public List<RespostaConsultarTokenCartao> getListaCartao() {
        return listaCartao;
    }

    public void setListaCartao(List<RespostaConsultarTokenCartao> listaCartao) {
        this.listaCartao = listaCartao;
    }

    @Override
    public String toString() {
        return "RespostaConsultarToken{" +
                "erro=" + erro +
                ", listaCartao=" + listaCartao +
                '}';
    }
}
