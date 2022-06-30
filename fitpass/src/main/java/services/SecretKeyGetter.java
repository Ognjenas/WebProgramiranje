package services;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.io.*;
import java.security.Key;

public class SecretKeyGetter {

    private static Key cache = null;

    public static Key get() {

        if(cache != null) {
            return cache;
        }
        try {
            File file = new File("./storage/key.csv");
            FileInputStream fl = new FileInputStream(file);

            // Now creating byte array of same length as file
            byte[] arr = new byte[(int)file.length()];
            fl.read(arr);
            fl.close();
            cache = Keys.hmacShaKeyFor(arr);
            return cache;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
