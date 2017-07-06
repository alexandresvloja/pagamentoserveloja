package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 16/06/2017.
 */

public class AdicionarTerminalResposta {

    @SerializedName("CodRetorno")
    private String CodRetorno;
    @SerializedName("IdTerminal")
    private String IdTerminal;
    @SerializedName("ChaveInstalacao")
    private String ChaveInstalacao;

    public String getCodRetorno() {
        return CodRetorno;
    }

    public void setCodRetorno(String codRetorno) {
        CodRetorno = codRetorno;
    }

    public String getIdTerminal() {
        return IdTerminal;
    }

    public void setIdTerminal(String idTerminal) {
        IdTerminal = idTerminal;
    }

    public String getChaveInstalacao() {
        return ChaveInstalacao;
    }

    public void setChaveInstalacao(String chaveInstalacao) {
        ChaveInstalacao = chaveInstalacao;
    }
}