package br.com.servelojapagamento.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by alexandre on 19/06/2017.
 */

public class ObterChaveAcessoResposta {

    @SerializedName("CodRetorno")
    public int codRetorno;
    @SerializedName("ChaveAcesso")
    public String chaveAcesso;
    @SerializedName("DataExpiracao")
    public String dataExpiracao;
    @SerializedName("UsuarioChave")
    public String usuarioChave;
    @SerializedName("Franquias")
    public Franquia[] franquias;
    @SerializedName("CodigoPerfil")
    public String codigoPerfil;
    @SerializedName("ValorObrigatoriedadeDocumento")
    public String valorObrigatoriedadeDocumento;
    @SerializedName("DsErro")
    public DsErro[] dsErro;
    @SerializedName("PermissoesAcesso")
    public String[] permissoes;
    @SerializedName("EmailVerificado")
    public Boolean emailVerificado;
    @SerializedName("PrecisaRedefinirSenha")
    public Boolean precisaRedefinir;
    @SerializedName("ftpMobile")
    public String ftpMobile;

    public Boolean getPrecisaRedefinir() {
        return precisaRedefinir;
    }

    public void setPrecisaRedefinir(Boolean precisaRedefinir) {
        this.precisaRedefinir = precisaRedefinir;
    }

    public Boolean getEmailVerificado() {
        return emailVerificado;
    }

    public void setEmailVerificado(Boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    public int getCodRetorno() {
        return codRetorno;
    }

    public void setCodRetorno(int codRetorno) {
        this.codRetorno = codRetorno;
    }

    public String getChaveAcesso() {
        return chaveAcesso;
    }

    public void setChaveAcesso(String chaveAcesso) {
        this.chaveAcesso = chaveAcesso;
    }

    public String getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(String dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getUsuarioChave() {
        return usuarioChave;
    }

    public void setUsuarioChave(String usuarioChave) {
        this.usuarioChave = usuarioChave;
    }

    public Franquia[] getFranquias() {
        return franquias;
    }

    public void setFranquias(Franquia[] franquias) {
        this.franquias = franquias;
    }

    public String getCodigoPerfil() {
        return codigoPerfil;
    }

    public void setCodigoPerfil(String codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    public String getValorObrigatoriedadeDocumento() {
        return valorObrigatoriedadeDocumento;
    }

    public void setValorObrigatoriedadeDocumento(String valorObrigatoriedadeDocumento) {
        this.valorObrigatoriedadeDocumento = valorObrigatoriedadeDocumento;
    }

    public DsErro[] getDsErro() {
        return dsErro;
    }

    public void setDsErro(DsErro[] dsErro) {
        this.dsErro = dsErro;
    }

    public String[] getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(String[] permissoes) {
        this.permissoes = permissoes;
    }

    public String getFtpMobile() {
        return ftpMobile;
    }

    public void setFtpMobile(String ftpMobile) {
        this.ftpMobile = ftpMobile;
    }

    @Override
    public String toString() {
        return "ObterChaveAcessoResposta{" +
                "codRetorno=" + codRetorno +
                ", chaveAcesso='" + chaveAcesso + '\'' +
                ", dataExpiracao='" + dataExpiracao + '\'' +
                ", usuarioChave='" + usuarioChave + '\'' +
                ", franquias=" + Arrays.toString(franquias) +
                ", codigoPerfil='" + codigoPerfil + '\'' +
                ", valorObrigatoriedadeDocumento='" + valorObrigatoriedadeDocumento + '\'' +
                ", dsErro=" + Arrays.toString(dsErro) +
                ", permissoes=" + Arrays.toString(permissoes) +
                ", emailVerificado=" + emailVerificado +
                ", precisaRedefinir=" + precisaRedefinir +
                ", ftpMobile='" + ftpMobile + '\'' +
                '}';
    }
}
