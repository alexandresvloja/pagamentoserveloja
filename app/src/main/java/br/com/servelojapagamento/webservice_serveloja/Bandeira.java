package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 19/06/2017.
 */

public class Bandeira {
    @SerializedName("CodigoBandeira")
    public int codBandeira;
    @SerializedName("NomeBandeira")
    public String nomeBandeira;
    @SerializedName("PossuiSenha")
    public String possuiSenha;
    @SerializedName("PossuiCCV")
    public String possuiCCV;

    public Bandeira(int codBandeira, String nomeBandeira, String possuiSenha, String possuiCCV) {
        this.codBandeira = codBandeira;
        this.nomeBandeira = nomeBandeira;
        this.possuiSenha = possuiSenha;
        this.possuiCCV = possuiCCV;
    }

    public String getPossuiSenha() {
        return possuiSenha;
    }

    public void setPossuiSenha(String possuiSenha) {
        this.possuiSenha = possuiSenha;
    }

    public String getPossuiCCV() {
        return possuiCCV;
    }

    public void setPossuiCCV(String possuiCCV) {
        this.possuiCCV = possuiCCV;
    }

    public int getCodBandeira() {
        return codBandeira;
    }

    public void setCodBandeira(int codBandeira) {
        this.codBandeira = codBandeira;
    }

    public String getNomeBandeira() {
        return nomeBandeira;
    }

    public void setNomeBandeira(String nomeBandeira) {
        this.nomeBandeira = nomeBandeira;
    }

    @Override
    public String toString() {
        return "Bandeira{" +
                "codBandeira=" + codBandeira +
                ", nomeBandeira='" + nomeBandeira + '\'' +
                ", possuiSenha='" + possuiSenha + '\'' +
                ", possuiCCV='" + possuiCCV + '\'' +
                '}';
    }
}
