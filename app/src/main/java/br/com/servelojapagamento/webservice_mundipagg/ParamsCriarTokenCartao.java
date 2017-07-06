package br.com.servelojapagamento.webservice_mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class ParamsCriarTokenCartao {

    @SerializedName("cartao_bandeira")
    @Expose
    private String cartaoBandeira;
    @SerializedName("cartao_numero")
    @Expose
    private String cartaoNumero;
    @SerializedName("cartao_mes")
    @Expose
    private String cartaoMes;
    @SerializedName("cartao_ano")
    @Expose
    private String cartaoAno;
    @SerializedName("cartao_nome")
    @Expose
    private String cartaoNome;
    @SerializedName("cartao_check")
    @Expose
    private boolean cartaoCheck;

    public ParamsCriarTokenCartao() {
    }

    public String getCartaoBandeira() {
        return cartaoBandeira;
    }

    public void setCartaoBandeira(String cartaoBandeira) {
        this.cartaoBandeira = cartaoBandeira;
    }

    public String getCartaoNumero() {
        return cartaoNumero;
    }

    public void setCartaoNumero(String cartaoNumero) {
        this.cartaoNumero = cartaoNumero;
    }

    public String getCartaoMes() {
        return cartaoMes;
    }

    public void setCartaoMes(String cartaoMes) {
        this.cartaoMes = cartaoMes;
    }

    public String getCartaoAno() {
        return cartaoAno;
    }

    public void setCartaoAno(String cartaoAno) {
        this.cartaoAno = cartaoAno;
    }

    public String getCartaoNome() {
        return cartaoNome;
    }

    public void setCartaoNome(String cartaoNome) {
        this.cartaoNome = cartaoNome;
    }

    public boolean isCartaoCheck() {
        return cartaoCheck;
    }

    public void setCartaoCheck(boolean cartaoCheck) {
        this.cartaoCheck = cartaoCheck;
    }

    @Override
    public String toString() {
        return "ParamsCriarTokenCartao{" +
                "cartaoBandeira='" + cartaoBandeira + '\'' +
                ", cartaoNumero='" + cartaoNumero + '\'' +
                ", cartaoMes='" + cartaoMes + '\'' +
                ", cartaoAno='" + cartaoAno + '\'' +
                ", cartaoNome='" + cartaoNome + '\'' +
                ", cartaoCheck=" + cartaoCheck +
                '}';
    }

}
