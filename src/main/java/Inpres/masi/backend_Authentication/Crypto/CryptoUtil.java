package Inpres.masi.backend_Authentication.Crypto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class CryptoUtil {
    public static byte[] sendCertificate(PublicKey pk, String apiEndpoint) throws Exception {
        // Encode the certificate to Base64
        String encodedCertificate = Base64.getEncoder().encodeToString(pk.getEncoded());

        // Prepare the request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("certificate", encodedCertificate);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        // Send the request
        ResponseEntity<byte[]> response = restTemplate.exchange(apiEndpoint, HttpMethod.POST, request, byte[].class);

        return Base64.getDecoder().decode(response.getBody());
    }

    public static PublicKey buildPublicKey(byte[] keyBytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }


    public static PublicKey buildPublicKey(String base64Key) throws Exception {
        // Decode the Base64 encoded string
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);

        // Create an X509EncodedKeySpec object from the decoded bytes
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

        // Use KeyFactory to generate a PublicKey from the X509EncodedKeySpec
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static byte[] encrypt(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public static String decrypt(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(encryptedData));
    }

    public static String byteTobase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64ToByte(String base64) {
        return Base64.getDecoder().decode(base64);
    }


    public static byte[] sign(String message, PrivateKey privateKey) throws Exception {
        Signature privateSignature = Signature.getInstance("SHA256withRSA");
        privateSignature.initSign(privateKey);
        privateSignature.update(message.getBytes());
        return privateSignature.sign();
    }

    public static boolean verify(String message, byte[] signature, PublicKey publicKey) throws Exception {
        Signature publicSignature = Signature.getInstance("SHA256withRSA");
        publicSignature.initVerify(publicKey);
        publicSignature.update(message.getBytes());
        return publicSignature.verify(signature);
    }
}
