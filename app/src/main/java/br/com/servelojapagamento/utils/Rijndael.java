package br.com.servelojapagamento.utils;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by alexandre on 04/07/2017.
 */

public class Rijndael {

    public static final String serveloja = "serveloja";

    private static String TRANSFORMATION = "AES/CBC/PKCS7Padding";
    private static String ALGORITHM = "AES";
    private static String DIGEST = "MD5";

    private static Cipher _cipher;
    private static SecretKey _password;
    private static IvParameterSpec _IVParamSpec;

    //16-byte private key
    private static byte[] IV = { 0x2, (byte) 0xfe, (byte) 0xf2, 0x2a, 0x45, (byte) 0xc7, (byte) 0xcd, (byte) 0xf9, 0x5, 0x46, (byte) 0x9c, (byte) 0xea, (byte) 0xa8, 0x4b, 0x73, (byte) 0xcc };


    /**
     Constructor
     @password Public key

     */
    public Rijndael(String password) {

        try {

            //Encode digest
            MessageDigest digest;
            digest = MessageDigest.getInstance(DIGEST);
            _password = new SecretKeySpec(digest.digest(password.getBytes()), ALGORITHM);

            //Initialize objects
            _cipher = Cipher.getInstance(TRANSFORMATION);
            _IVParamSpec = new IvParameterSpec(IV);

        } catch (NoSuchAlgorithmException e) {
            Log.e(serveloja, "No such algorithm " + ALGORITHM, e);
        } catch (NoSuchPaddingException e) {
            Log.e(serveloja, "No such padding PKCS7", e);
        }
    }

    /**
     Encryptor.

     @text String to be encrypted
     @return Base64 encrypted text

     */
    public String encrypt(byte[] text) {

        byte[] encryptedData;

        try {

            _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
            encryptedData = _cipher.doFinal(text);

        } catch (InvalidKeyException e) {
            Log.e(serveloja, "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
            return null;
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(serveloja, "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
            return null;
        } catch (IllegalBlockSizeException e) {
            Log.e(serveloja, "The length of data provided to a block cipher is incorrect", e);
            return null;
        } catch (BadPaddingException e) {
            Log.e(serveloja, "The input data but the data is not padded properly.", e);
            return null;
        }

        return Base64.encodeToString(encryptedData,Base64.DEFAULT);

    }

}
