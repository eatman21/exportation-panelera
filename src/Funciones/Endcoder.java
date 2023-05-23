/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Cris
 */
public class Endcoder {

    private String clave_encrypt;
    private String clave_dencrypt;

    private final String secretKey = "Valentina2425";

    public String getClave_encrypt() {
        return clave_encrypt;
    }

    public String getClave_dencrypt() {
        return clave_dencrypt;
    }

    public String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        byte[] keyPassword = md5.digest(secretKey.getBytes("utf-8"));
        byte[] bytesKey = Arrays.copyOf(keyPassword, 16);
        SecretKey secretKey = new SecretKeySpec(bytesKey, "AES");

        byte[] plainTextByte = plainText.getBytes();

        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedByte = cipher.doFinal(plainTextByte);

        Base64.Encoder encoder = Base64.getEncoder();

        String encryptedText = encoder.encodeToString(encryptedByte);

        clave_encrypt = encryptedText;
        return encryptedText;
    }

    public void decrypt(String encryptedText) throws Exception {
        System.out.println("This password is " + encryptedText);
        Cipher cipher = Cipher.getInstance("AES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        byte[] keyPassword = md5.digest(secretKey.getBytes("utf-8"));
        byte[] bytesKey = Arrays.copyOf(keyPassword, 16);
        SecretKey secretKey = new SecretKeySpec(bytesKey, "AES");

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] encryptedByte = decoder.decode(encryptedText);

        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decryptedByte = cipher.doFinal(encryptedByte);

        String decryptedText = new String(decryptedByte);
        System.out.println("Descencripta clave:"+decryptedText);

        clave_dencrypt = decryptedText;
    }
}
