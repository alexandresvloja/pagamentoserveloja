package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 19/06/2017.
 */

public class Franquia {
    @SerializedName("NomeFranquia")
    public String nomeFranquia;
    @SerializedName("CodigoFranquia")
    public String codigoFranquia;

    public String getNomeFranquia() {
        return nomeFranquia;
    }

    public void setNomeFranquia(String nomeFranquia) {
        this.nomeFranquia = nomeFranquia;
    }

    public String getCodigoFranquia() {
        return codigoFranquia;
    }

    public void setCodigoFranquia(String codigoFranquia) {
        this.codigoFranquia = codigoFranquia;
    }

    public Franquia(String nomeFranquia, String codigoFranquia) {
        super();
        this.nomeFranquia = nomeFranquia;
        this.codigoFranquia = codigoFranquia;
    }

    @Override
    public String toString() {
        return "Franquia{" +
                "nomeFranquia='" + nomeFranquia + '\'' +
                ", codigoFranquia='" + codigoFranquia + '\'' +
                '}';
    }

}
