package br.com.servelojapagamento.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by alexandre on 04/07/2017.
 */

public class MapaPadroesBinUtils {

    private HashMap<String,Pattern> mapPadroes;
    public MapaPadroesBinUtils(){

        HashMap<String, Pattern> mapPadroes = new HashMap<>();
        mapPadroes.put("Visa",Pattern.compile("^4[0-9]{5}"));
        mapPadroes.put("MasterCard",Pattern.compile("^5[1-5][0-9]{4}|^2(?:2(?:2[1-9]|[3-9]\\d)|[3-6]\\d\\d|7(?:[01]\\d|20))[0-9]{2}$"));
        mapPadroes.put("Amex",Pattern.compile("^3[47][0-9]{4}$"));
        mapPadroes.put("Diners",Pattern.compile("^3(?:0[0-5]|[6][0-9]|[8]([0-3]|[5-9]))[0-9]"));
        mapPadroes.put("Hipercard",Pattern.compile("^606282|(637\\\\d{3})|(3841\\\\d{2})$"));
        mapPadroes.put("Elo",Pattern.compile("^401178|^401179|^431274|^438935|^451416|^457393|^457631|^457632|^504175|^627780|^636297|^636368|^(506699|5067[0-6]\\\\d|50677[0-8])|^(50900\\\\d|5090[1-9]\\\\d|509[1-9]\\\\d{2})|^65003[1-3]|^(65003[5-9]|65004\\\\d|65005[0-1])|^(65040[5-9]|6504[1-3]\\\\d)|^(65048[5-9]|65049\\\\d|6505[0-2]\\\\d|65053[0-8])|^(65054[1-9]|6505[5-8]\\\\d|65059[0-8])|^(65070\\\\d|65071[0-8])|^65072[0-7]|^(65090[1-9]|65091\\\\d|6509([2-6]\\\\d|7[0-8]))|^(65165[2-9]|6516[6-7]\\\\d)|^(65500\\\\d|65501\\\\d)|^(65502[1-9]|6550[3-4]\\\\d|65505[0-8])"));
        mapPadroes.put("FortBrasil",Pattern.compile("^628167"));
        mapPadroes.put("BaneseCard",Pattern.compile("^6361[0-9]{2}|^6374[0-9]{2}"));
        mapPadroes.put("Credishop",Pattern.compile("^6031[0-9]{2}"));
        mapPadroes.put("Sorocred",Pattern.compile("^627892|^606014|^636414|^9555[0-9]{2}"));
        mapPadroes.put("Assomise",Pattern.compile("^639595|^608732"));

        this.mapPadroes = mapPadroes;
    }

    public HashMap<String, Pattern> getMapPadroes() {
        return mapPadroes;
    }

    public String checarBin(String bin){

        Set<Map.Entry<String, Pattern>> set = mapPadroes.entrySet();

        for (Map.Entry<String, Pattern> me : set) {
            if( me.getValue().matcher(bin).matches())
                return me.getKey();

        }
        return "";
    }

}
