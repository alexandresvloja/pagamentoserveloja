package br.com.servelojapagamento.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexandre on 04/07/2017.
 */

public class PrefsHelper {

    private Context context;
    private SharedPreferences sharedPreferences;
    private final String PREFS = "prefs_serveloja_pagamento";
    private final String PINPAD_MODELO = "pinpad_modelo";
    private final String PINPAD_MAC = "pinpad_mac";
    private final String CHAVE_ACESSO = "chave_acesso";
    private final String DATA_EXPIRACAO = "data_expiracao";
    private final String USUARIO_CHAVE = "usuario_chave";
    private final String LOCALIZACAO = "localizacao";
    private final String COD_CHIP = "cod_chip";

    public PrefsHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void salvarPinpadModelo(String modelo) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PINPAD_MODELO, modelo);
        editor.commit();
    }

    public String getPinpadModelo() {
        return sharedPreferences.getString(PINPAD_MODELO, "");
    }

    public void salvarPinpadMac(String mac) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PINPAD_MAC, mac);
        editor.commit();
    }

    public String getPinpadMac() {
        return sharedPreferences.getString(PINPAD_MAC, "");
    }

    public void salvarChaveAcesso(String chaveAcesso) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CHAVE_ACESSO, chaveAcesso);
        editor.commit();
    }

    public String getChaveAcesso() {
        return sharedPreferences.getString(CHAVE_ACESSO, "");
    }

    public void salvarLocalizacao(String localizacao) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCALIZACAO, localizacao);
        editor.commit();
    }

    public String getLocalizacao() {
        return sharedPreferences.getString(LOCALIZACAO, "");
    }

    public void salvarDataExpiracao(String dataExpiracao) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DATA_EXPIRACAO, dataExpiracao);
        editor.commit();
    }

    public String getDataExpiracao() {
        return sharedPreferences.getString(DATA_EXPIRACAO, "");
    }

    public void salvarUsuarioChave(String dataExpiracao) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USUARIO_CHAVE, dataExpiracao);
        editor.commit();
    }

    public String getUsuarioChave() {
        return sharedPreferences.getString(USUARIO_CHAVE, "");
    }

    public void salvarCodChip(String codChip) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COD_CHIP, codChip);
        editor.commit();
    }

    public String getCodChip() {
        return sharedPreferences.getString(COD_CHIP, "");
    }

}
