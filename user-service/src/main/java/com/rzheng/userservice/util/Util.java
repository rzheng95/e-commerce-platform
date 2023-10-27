package com.rzheng.userservice.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public class Util {

    private static final int ITERATIONS = 10000;  // Number of iterations; adjust if needed
    private static final int KEY_LENGTH = 256;   // Key length in bits; adjust if needed

    public static boolean isStringValid(String str) {
        return null != str && !str.isBlank();
    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();

        PBEKeySpec spec = new PBEKeySpec(passwordChars, saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hashedBytes = skf.generateSecret(spec).getEncoded();

        return Base64.getEncoder().encodeToString(hashedBytes);
    }

    public static String generatePasswordSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // You can adjust the salt size if needed
        random.nextBytes(salt);

        // Convert byte array to base64 string for easier storage
        return Base64.getEncoder().encodeToString(salt);
    }
}
