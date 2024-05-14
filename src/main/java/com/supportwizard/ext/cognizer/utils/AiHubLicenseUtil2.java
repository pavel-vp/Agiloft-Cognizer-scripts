package com.supportwizard.ext.cognizer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supportwizard.ext.cognizer.model.AiHubLicense2;
import com.supportwizard.seance.Seance;
import com.supportwizard.swlicenses.ejb.entity.License;
import com.supportwizard.swlicenses.ejb.session.LicenseLocal;
import com.supportwizard.utils.Base64;
import org.apache.log4j.Logger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Collection;

public class AiHubLicenseUtil2 {

  private static final Logger log = Logger.getLogger(AiHubLicenseUtil2.class);

  private static final byte[] salt = new byte[] {44, 47, 49, 31,44, 47, 49, 31,44, 47, 49, 31,44, 47, 49, 31};

  public static AiHubLicense2 readAiHubLicense(Seance seance, String key) {
    LicenseLocal licenseFacade = seance.getHomesGetter().getSessionBean(LicenseLocal.class);
    Collection<License> licenses = licenseFacade.getAiHubLicense(seance.getProjectID());
    if (licenses.size() == 1) {
      try {
        License license = licenses.iterator().next();
        String encoded = license.getLanguages();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
          return objectMapper.readValue(Base64.decode(decrypt(encoded, key).toCharArray()), AiHubLicense2.class);
        } catch (IOException e) {
          log.error("Exception during parsing aiHubLicense :"+encoded, e);
        }

      } catch (Exception e) {
        log.error("Exception during parsing aiHubLicense", e);
      }
    }
    return null;
  }

  public static boolean isAiHubLicenseInstalled(Seance seance) {
    LicenseLocal licenseFacade = seance.getHomesGetter().getSessionBean(LicenseLocal.class);
    Collection<License> licenses = licenseFacade.getAiHubLicense(seance.getProjectID());
    return licenses.size() == 1;
  }

  private static IvParameterSpec getIv() {
    return new IvParameterSpec(salt);
  }

  private static SecretKey getKeyFromPassword(String password, String salt)
    throws NoSuchAlgorithmException, InvalidKeySpecException {

    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
      .getEncoded(), "AES");
    return secret;
  }
  public static String encrypt(String input, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
    String algorithm = "AES/CBC/PKCS5Padding";
    IvParameterSpec iv = getIv();
    SecretKey key = getKeyFromPassword(password, new String(salt));

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    byte[] cipherText = cipher.doFinal(input.getBytes());
    return new String(java.util.Base64.getUrlEncoder().encode(cipherText));
  }

  public static String decrypt(String cipherText, String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

    String algorithm = "AES/CBC/PKCS5Padding";
    IvParameterSpec iv = getIv();
    SecretKey key = getKeyFromPassword(password, new String(salt));

    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] plainText = cipher.doFinal(java.util.Base64.getUrlDecoder().decode(cipherText));
    return new String(plainText);
  }
}
