//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.supportwizard.ext.cognizer;

import com.supportwizard.swlicenses.data.LicenseData;
import com.supportwizard.swlicenses.exceptions.InvalidLicenseActionException;
import com.supportwizard.utils.Base64;
import com.supportwizard.utils.SWRuntimeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class KeyGenAiHub {
    public KeyGenAiHub() {
    }

    public static String getLicenseKey(String license, String privateKeyFileName) throws InvalidLicenseActionException {
        return getLicenseData(license, privateKeyFileName).getLicenseKey();
    }

    public static LicenseData getLicenseData(String license, String privateKeyFileName) throws InvalidLicenseActionException {
        LicenseData ld = DataConverterAiHub.getLicenseFromString(license);
        setLicenseKey(ld, privateKeyFileName);
        return ld;
    }

    public static void setLicenseKey(LicenseData ld, String privateKeyFileName) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            digester.update(ld.toString().getBytes());
            System.out.println("ld.toString() = " + ld.toString());
            BigInteger digest = new BigInteger(digester.digest());
            String digestHex = digest.toString(16);
            System.out.println("digestHex = " + digestHex);
            String encryptedDigestHex = encrypt(digestHex, privateKeyFileName);
            System.out.println("encryptedDigestHex = " + encryptedDigestHex);
            ld.setLicenseKey(encryptedDigestHex);
        } catch (NoSuchAlgorithmException var6) {
            System.err.println(var6.toString());
            System.exit(-1);
        }

    }

    public static String encrypt(String data, String privateKeyFileName) {
        ResourceBundle rb = loadPrivateKeyData(privateKeyFileName);
        BigInteger pk = new BigInteger(rb.getString("privateKey"), 16);
        BigInteger mod = new BigInteger(rb.getString("modulus"), 16);
        System.out.println("privateKey = " + rb.getString("privateKey"));
        System.out.println("modulus = " + rb.getString("modulus"));
        byte[] bytes = data.getBytes();
        BigInteger unencrypted = new BigInteger(bytes);
        BigInteger encrypted = unencrypted.modPow(pk, mod);
        return new String(Base64.encode(encrypted.toByteArray()));
    }

    private static ResourceBundle loadPrivateKeyData(String privateKeyFileName) {
        try {
            String fileName = privateKeyFileName;
            if (privateKeyFileName == null) {
                fileName = System.getProperty("installation.dir") + File.separatorChar + "private_key.txt";
            }

            FileInputStream fis = new FileInputStream(fileName);
            ResourceBundle rb = new PropertyResourceBundle(fis);
            fis.close();
            return rb;
        } catch (IOException var4) {
            throw new SWRuntimeException("Cant load private key data", var4);
        }
    }
}
