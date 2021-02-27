package com.steve.crud;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordHasher {

    private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final int ITERATION_COUNT = 200000;
    private static final int KEY_LENGTH = 512;

    // Private constructor so instance cannot be created of this utility class.
    private PasswordHasher() {};

    /**
     * The hashedPass method will be provide the hashed password using PBKDF2WithHmacSHA1
     * hasing through java.security library.
     * @param pass The password to be hashed.
     * @param salt The salt created.
     * @return The hashed password as hexadecimal string.
     */
    public static String hashedPass(String pass, byte[] salt) {

        try {
            KeySpec keySpec = new PBEKeySpec(pass.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            byte[] hash = secretKeyFactory.generateSecret(keySpec).getEncoded();

            return byteArrayToHexString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * The salt method will return a random salt for password hashing.
     * @return The salt as byte array.
     */
    public static byte[] salt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }



    /**
     * The byteArrayToHexString will convert a binary byte array to hexadecimal string.
     * @param bytes The binary byte array.
     * @return The hexadecimal string.
     */
    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder str = new StringBuilder();
        for (byte b :
                bytes) {
            str.append(String.format("%02x", b));
        }
        return str.toString();
    }


    /**
     * The hexToByteArray will convert hex string to binary byte array.
     * @param hexStr The hex string to convert.
     * @return The converted binary byte array.
     */
    public static byte[] hexToByteArray(String hexStr) {
        return new BigInteger(hexStr,16).toByteArray();
    }


}
