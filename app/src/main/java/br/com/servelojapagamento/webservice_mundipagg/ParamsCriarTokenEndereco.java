package br.com.servelojapagamento.webservice_mundipagg;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandre on 04/07/2017.
 */

public class ParamsCriarTokenEndereco {

    @SerializedName("endereco_descricao")
    @Expose
    private String enderecoDescricao;
    @SerializedName("'endereco_numero'")
    @Expose
    private String enderecoNumero;
    @SerializedName("endereco_complemento")
    @Expose
    private String enderecoComplemento;
    @SerializedName("endereco_capital")
    @Expose
    private String enderecoCapital;
    @SerializedName("endereco_cidade")
    @Expose
    private String enderecoCidade;
    @SerializedName("endereco_estado")
    @Expose
    private String enderecoEstado;
    @SerializedName("endereco_cep")
    @Expose
    private String enderecoCep;
    @SerializedName("endereco_pais")
    @Expose
    private String enderecoPais;

    public ParamsCriarTokenEndereco() {
        enderecoCapital = "Brasilia";
        enderecoCep = "";
        enderecoCidade = "";
        enderecoComplemento = "";
        enderecoDescricao = "";
        enderecoEstado = "";
        enderecoNumero = "";
        enderecoPais = "Brasil";
    }

    public String getEnderecoDescricao() {
        return enderecoDescricao;
    }

    public void setEnderecoDescricao(String enderecoDescricao) {
        this.enderecoDescricao = enderecoDescricao;
    }

    public String getEnderecoNumero() {
        return enderecoNumero;
    }

    public void setEnderecoNumero(String enderecoNumero) {
        this.enderecoNumero = enderecoNumero;
    }

    public String getEnderecoComplemento() {
        return enderecoComplemento;
    }

    public void setEnderecoComplemento(String enderecoComplemento) {
        this.enderecoComplemento = enderecoComplemento;
    }

    public String getEnderecoCapital() {
        return enderecoCapital;
    }

    public void setEnderecoCapital(String enderecoCapital) {
        this.enderecoCapital = enderecoCapital;
    }

    public String getEnderecoCidade() {
        return enderecoCidade;
    }

    public void setEnderecoCidade(String enderecoCidade) {
        this.enderecoCidade = enderecoCidade;
    }

    public String getEnderecoEstado() {
        return enderecoEstado;
    }

    public void setEnderecoEstado(String enderecoEstado) {
        this.enderecoEstado = enderecoEstado;
    }

    public String getEnderecoCep() {
        return enderecoCep;
    }

    public void setEnderecoCep(String enderecoCep) {
        this.enderecoCep = enderecoCep;
    }

    public String getEnderecoPais() {
        return enderecoPais;
    }

    public void setEnderecoPais(String enderecoPais) {
        this.enderecoPais = enderecoPais;
    }

    @Override
    public String toString() {
        return "ParamsCriarTokenEndereco{" +
                "enderecoDescricao='" + enderecoDescricao + '\'' +
                ", enderecoNumero='" + enderecoNumero + '\'' +
                ", enderecoComplemento='" + enderecoComplemento + '\'' +
                ", enderecoCapital='" + enderecoCapital + '\'' +
                ", enderecoCidade='" + enderecoCidade + '\'' +
                ", enderecoEstado='" + enderecoEstado + '\'' +
                ", enderecoCep='" + enderecoCep + '\'' +
                ", enderecoPais='" + enderecoPais + '\'' +
                '}';
    }

}
