package com.steve.crud;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

// Example sha-512 has with salt.

public class PassHash {
    public static void main(String[] args) {
        System.out.println(toHash("12345678"));
    }


    private static String toHash(String pass) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        System.out.println(Arrays.toString(salt));

        StringBuilder str = new StringBuilder();

        for (byte b :
                salt) {
            str.append(String.format("%02x", b));
        }

        System.out.println(str.toString());

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");

            messageDigest.update(salt);

            byte [] resultBytes = messageDigest.digest();
            System.out.println(Arrays.toString(resultBytes));
            
            StringBuilder sb = new StringBuilder();

            for (byte b :
                    resultBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
