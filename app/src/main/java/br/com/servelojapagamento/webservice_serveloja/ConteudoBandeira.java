package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 19/06/2017.
 */

public class ConteudoBandeira {

    @SerializedName("CodRetorno")
    public int codRetorno;
    @SerializedName("Bandeiras")
    public Bandeira[] bandeiras;

    public ConteudoBandeira(int codRetorno, Bandeira[] bandeiras) {
        super();
        this.codRetorno = codRetorno;
        this.bandeiras = bandeiras;
    }

    public int getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(int codRetorno) {
        this.codRetorno = codRetorno;
    }

    public Bandeira[] getBandeiras() {
        return bandeiras;
    }

    public void setBandeiras(Bandeira[] bandeiras) {
        this.bandeiras = bandeiras;
    }

}
