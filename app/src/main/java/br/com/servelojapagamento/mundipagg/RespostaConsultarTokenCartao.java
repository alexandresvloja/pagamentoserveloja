package br.com.servelojapagamento.mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class RespostaConsultarTokenCartao {

    @SerializedName("CreditCardBrand")
    @Expose
    private String cartaoBandeira;

    @SerializedName("ExpirationMonth")
    @Expose
    private Integer cartaoMes;

    @SerializedName("ExpirationYear")
    @Expose
    private Integer cartaoAno;

    @SerializedName("HolderName")
    @Expose
    private String cartaoNome;

    @SerializedName("InstantBuyKey")
    @Expose
    private String token;

    @SerializedName("IsExpiredCreditCard")
    @Expose
    private Boolean isCartaoExpirado;

    @SerializedName("MaskedCreditCardNumber")
    @Expose
    private String cartaoNumeroMascara;

    public String getCartaoBandeira() {
        return cartaoBandeira;
    }

    public void setCartaoBandeira(String cartaoBandeira) {
        this.cartaoBandeira = cartaoBandeira;
    }

    public Integer getCartaoMes() {
        return cartaoMes;
    }

    public void setCartaoMes(Integer cartaoMes) {
        this.cartaoMes = cartaoMes;
    }

    public Integer getCartaoAno() {
        return cartaoAno;
    }

    public void setCartaoAno(Integer cartaoAno) {
        this.cartaoAno = cartaoAno;
    }

    public String getCartaoNome() {
        return cartaoNome;
    }

    public void setCartaoNome(String cartaoNome) {
        this.cartaoNome = cartaoNome;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsCartaoExpirado() {
        return isCartaoExpirado;
    }

    public void setIsCartaoExpirado(Boolean isCartaoExpirado) {
        this.isCartaoExpirado = isCartaoExpirado;
    }

    public String getCartaoNumeroMascara() {
        return cartaoNumeroMascara;
    }

    public void setCartaoNumeroMascara(String cartaoNumeroMascara) {
        this.cartaoNumeroMascara = cartaoNumeroMascara;
    }

    @Override
    public String toString() {
        return "RespostaConsultarTokenCartao{" +
                "cartaoBandeira='" + cartaoBandeira + '\'' +
                ", cartaoMes=" + cartaoMes +
                ", cartaoAno=" + cartaoAno +
                ", cartaoNome='" + cartaoNome + '\'' +
                ", token='" + token + '\'' +
                ", isCartaoExpirado=" + isCartaoExpirado +
                ", cartaoNumeroMascara='" + cartaoNumeroMascara + '\'' +
                '}';
    }
}
