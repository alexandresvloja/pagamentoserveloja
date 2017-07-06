package br.com.servelojapagamento.webservice_mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class RespostaCriarToken {

    @SerializedName("ErrorReport")
    @Expose
    private String erro;
    @SerializedName("InstantBuyKey")
    @Expose
    private String token;
    @SerializedName("Success")
    @Expose
    private boolean sucesso;

    public RespostaCriarToken() {
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    @Override
    public String toString() {
        return "RespostaCriarToken{" +
                "erro='" + erro + '\'' +
                ", token='" + token + '\'' +
                ", sucesso=" + sucesso +
                '}';
    }

}
