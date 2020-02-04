package com.fengxuechao.seed.security.web;

import com.fengxuechao.seed.security.ExampleApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.cloud.bootstrap.encrypt.RsaProperties;
import org.springframework.cloud.context.encrypt.EncryptorFactory;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.security.rsa.crypto.RsaSecretEncryptor;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ExampleApplication.class)
@RunWith(SpringRunner.class)
public class TextEncryptorTest {

    private TextEncryptor textEncryptor;

    private KeyProperties key;

    private RsaProperties rsaProperties;

    @Value("classpath:server.jks")
    private Resource resource;

    @Before
    public void setUp() {
        rsaProperties = new RsaProperties();
        key = new KeyProperties();
        key.getKeyStore().setAlias("changeme");
        key.getKeyStore().setPassword("changeme");
        key.getKeyStore().setSecret("changeme");
        key.getKeyStore().setLocation(resource);
        textEncryptor = textEncryptor();
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            String plainText = "ABCDEFG";
            String encryptedText = this.textEncryptor.encrypt(plainText);
            String decryptedText = this.textEncryptor.decrypt(encryptedText);
            System.out.println(plainText + "----------" + encryptedText + "-----------" + decryptedText);
        }
    }


    private TextEncryptor textEncryptor() {
        KeyProperties.KeyStore keyStore = this.key.getKeyStore();
        if (keyStore.getLocation() != null) {
            if (keyStore.getLocation().exists()) {
                return new RsaSecretEncryptor(
                        new KeyStoreKeyFactory(keyStore.getLocation(),
                                keyStore.getPassword().toCharArray()).getKeyPair(
                                keyStore.getAlias(),
                                keyStore.getSecret().toCharArray()),
                        this.rsaProperties.getAlgorithm(),
                        this.rsaProperties.getSalt(), this.rsaProperties.isStrong());
            }

            throw new IllegalStateException("Invalid keystore location");
        }

        return new EncryptorFactory(this.key.getSalt()).create(this.key.getKey());
    }

}