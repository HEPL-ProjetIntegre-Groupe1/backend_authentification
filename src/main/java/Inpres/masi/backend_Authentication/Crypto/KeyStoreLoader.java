package Inpres.masi.backend_Authentication.Crypto;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.*;
import java.util.Enumeration;

public class KeyStoreLoader {

    private KeyStore keystore;
    private String keystorePath;
    private String keystorePassword;

    public KeyStoreLoader(String keystorePath, String keystorePassword) throws Exception {
        this.keystorePath = keystorePath;
        this.keystorePassword = keystorePassword;
        this.loadKeyStore();
    }

    private void loadKeyStore() throws Exception {
        FileInputStream fis = new FileInputStream(keystorePath);
        keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(fis, keystorePassword.toCharArray());
        fis.close();
    }

    public KeyPair getKeyPair(String alias) throws Exception {
        if (keystore == null) {
            throw new IllegalStateException("Keystore not loaded.");
        }
        KeyStore.PrivateKeyEntry entry = (KeyStore.PrivateKeyEntry) keystore.getEntry(alias, new KeyStore.PasswordProtection(keystorePassword.toCharArray()));
        if (entry == null) {
            throw new IllegalArgumentException("No key pair found for alias '" + alias + "'.");
        }
        PublicKey publicKey = entry.getCertificate().getPublicKey();
        PrivateKey privateKey = entry.getPrivateKey();
        return new KeyPair(publicKey, privateKey);
    }


    public PublicKey getPublicKey(String alias) throws Exception {
        return getKeyPair(alias).getPublic();
    }

    public PrivateKey getPrivateKey(String alias) throws Exception {
        return getKeyPair(alias).getPrivate();
    }

    public void listAliases() throws Exception {
        if (keystore == null) {
            throw new IllegalStateException("Keystore not loaded.");
        }
        Enumeration<String> aliases = keystore.aliases();
        while (aliases.hasMoreElements()) {
            System.out.println("Alias: " + aliases.nextElement());
        }
    }
}
