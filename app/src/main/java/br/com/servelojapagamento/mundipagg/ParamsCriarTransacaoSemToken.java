package br.com.servelojapagamento.mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class ParamsCriarTransacaoSemToken {

    @SerializedName("valor")
    @Expose
    private String valor;
    @SerializedName("id_transacao")
    @Expose
    private String idTransacao;
    @SerializedName("cartao_bandeira")
    @Expose
    private String cartaoBandeira;
    @SerializedName("cartao_numero")
    @Expose
    private String cartaoNumero;
    @SerializedName("cartao_cvv")
    @Expose
    private String cartaoCvv;
    @SerializedName("cartao_ano")
    @Expose
    private String cartaoAno;
    @SerializedName("cartao_mes")
    @Expose
    private String cartaoMes;
    @SerializedName("cartao_nome")
    @Expose
    private String cartaoNome;
    @SerializedName("numero_parcelas")
    @Expose
    private int numParcela;

    public ParamsCriarTransacaoSemToken() {
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(String idTransacao) {
        this.idTransacao = idTransacao;
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

    public String getCartaoCvv() {
        return cartaoCvv;
    }

    public void setCartaoCvv(String cartaoCvv) {
        this.cartaoCvv = cartaoCvv;
    }

    public String getCartaoAno() {
        return cartaoAno;
    }

    public void setCartaoAno(String cartaoAno) {
        this.cartaoAno = cartaoAno;
    }

    public String getCartaoMes() {
        return cartaoMes;
    }

    public void setCartaoMes(String cartaoMes) {
        this.cartaoMes = cartaoMes;
    }

    public String getCartaoNome() {
        return cartaoNome;
    }

    public void setCartaoNome(String cartaoNome) {
        this.cartaoNome = cartaoNome;
    }

    public int getNumParcela() {
        return numParcela;
    }

    public void setNumParcela(int numParcela) {
        this.numParcela = numParcela;
    }

    @Override
    public String toString() {
        return "ParamsCriarTransacaoSemToken{" +
                "valor='" + valor + '\'' +
                ", idTransacao='" + idTransacao + '\'' +
                ", cartaoBandeira='" + cartaoBandeira + '\'' +
                ", cartaoNumero='" + cartaoNumero + '\'' +
                ", cartaoCvv='" + cartaoCvv + '\'' +
                ", cartaoAno='" + cartaoAno + '\'' +
                ", cartaoMes='" + cartaoMes + '\'' +
                ", cartaoNome='" + cartaoNome + '\'' +
                ", numParcela=" + numParcela +
                '}';
    }

}
