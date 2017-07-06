package br.com.servelojapagamento.webservice_serveloja;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandre on 19/06/2017.
 */

public class UserMobile {

    @SerializedName("Username")
    String username;
    @SerializedName("Password")
    String password;
    @SerializedName("Alias")
    String alias;
    @SerializedName("ChaveAcesso")
    String chave_acesso;
    @SerializedName("App")
    App app;

    public UserMobile(String username, String password, String alias, String chave_acesso) {
        this.username = username;
        this.password = password;
        this.alias = alias;
        this.chave_acesso = chave_acesso;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getChave_Acesso() {
        return chave_acesso;
    }

    public void setChave_Acesso(String chave_Acesso) {
        this.chave_acesso = chave_acesso;
    }

    public App getAppDetails() {
        return app;
    }

    public void setAppDetails(App appDetails) {
        this.app = appDetails;
    }

    public static class App {
        @SerializedName("ChipCode")
        String chipCode;
        @SerializedName("OS")
        String os;
        @SerializedName("OSVersion")
        String osVersion;
        @SerializedName("Version")
        String version;

        public App(String chipCode, String os, String osVersion, String version) {
            this.chipCode = chipCode;
            this.os = os;
            this.osVersion = osVersion;
            this.version = version;
        }

        public String getChipCode() {
            return chipCode;
        }

        public void setChipCode(String chipCode) {
            this.chipCode = chipCode;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

}
