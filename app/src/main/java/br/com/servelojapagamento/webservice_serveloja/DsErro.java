package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 19/06/2017.
 */

public class DsErro {
    @SerializedName("CodErro")
    public int codErro;
    @SerializedName("DescErro")
    public String descErro;
    @SerializedName("ErroConcatenado")
    public String erroConcatenado;

    public DsErro(int codErro, String descErro, String erroConcatenado) {
        super();
        this.codErro = codErro;
        this.descErro = descErro;
        this.erroConcatenado = erroConcatenado;
    }

    public int getCodErro() {
        return codErro;
    }

    public void setCodErro(int codErro) {
        this.codErro = codErro;
    }

    public String getDescErro() {
        return descErro;
    }

    public void setDescErro(String descErro) {
        this.descErro = descErro;
    }

    public String getErroConcatenado() {
        return erroConcatenado;
    }

    public void setErroConcatenado(String erroConcatenado) {
        this.erroConcatenado = erroConcatenado;
    }

    @Override
    public String toString() {
        return "DsErro{" +
                "codErro=" + codErro +
                ", descErro='" + descErro + '\'' +
                ", erroConcatenado='" + erroConcatenado + '\'' +
                '}';
    }
}
