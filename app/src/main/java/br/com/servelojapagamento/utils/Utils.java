package br.com.servelojapagamento.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import permissions.dispatcher.NeedsPermission;

/**
 * Created by alexandre on 04/07/2017.
 */

public class Utils {

    private static Rijndael ri = new Rijndael("u1Js6McyRtWNjPbyDkmin3SWUKTroDubU4RoEU20N9fLk6cet6F3dRWSKdI7yBQd");

    public static String encriptar(String str) {
        return ri.encrypt(str.getBytes());
    }

    public static String obterBandeiraPorBin(String bin) {
        return new MapaPadroesBinUtils().checarBin(bin);
    }

    public static String getDataAtualStr() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.applyPattern("dd/MM/yyyy HH:mm:ss");
        String dateStr = formatter.format(calendar.getTime());
        return dateStr;
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = " ";
        try {
            imei = telephonyManager.getDeviceId().toString();
        } catch (NullPointerException e) {
            Log.i("serveloja", "NullPointerException");
        }

        if (imei.length() < 10) {
            imei = " ";
        }
        return imei;
    }

    public static int getServelojaVersionApp(Context context) {
        int VERSAO_APP = 0;
        try {
            VERSAO_APP = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return VERSAO_APP;
    }

}