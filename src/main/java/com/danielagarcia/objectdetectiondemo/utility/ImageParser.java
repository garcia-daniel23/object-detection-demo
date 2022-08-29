package com.danielagarcia.objectdetectiondemo.utility;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;


public class ImageParser {


    public static String imageUrlAsBase64Str(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            InputStream urlStream = url.openStream();
            byte[] imageBytes = urlStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Request to URL failed, possible bad link");
        }
    }

    public static Resource base64StrAsResource(String imageStrBase64) {
        byte[] imageBytes = Base64.getDecoder().decode(imageStrBase64);
        return inputStreamAsResource(imageBytes);
    }

    private static Resource inputStreamAsResource(byte[] imageBytes) {
        InputStream imageStream = new ByteArrayInputStream(imageBytes);
        InputStreamSource imageStreamSource = new InputStreamResource(imageStream);
        return (Resource) imageStreamSource;
    }
}
