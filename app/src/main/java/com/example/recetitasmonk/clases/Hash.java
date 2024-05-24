package com.example.recetitasmonk.clases;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    private String sHash;

    public Hash() {
    }

    public String StringToHash(String sInputString, String sHashType){
        String sOutputString = "";
        try {
            // Create Hash Type
            MessageDigest digest = MessageDigest.getInstance(sHashType);
            digest.update(sInputString.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));

            sOutputString = hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return sOutputString;
    }
}
