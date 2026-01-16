package util;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Utilidad para cifrar contraseñas con la clave pública del servidor.
 */
public class RSAEncryptionUtil {
    
    private static PublicKey publicKey;
    
    /**
     * Establece la clave pública recibida del servidor.
     */
    public static void setPublicKey(String publicKeyString) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKey = keyFactory.generatePublic(keySpec);
        AppLogger.info("Clave pública del servidor configurada");
    }
    
    /**
     * Cifra un texto plano con la clave pública del servidor.
     */
    public static String encrypt(String plainText) throws Exception {
        if (publicKey == null) {
            throw new IllegalStateException("La clave pública no ha sido configurada");
        }
        
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    /**
     * Verifica si la clave pública ha sido configurada.
     */
    public static boolean isPublicKeyConfigured() {
        return publicKey != null;
    }
}
