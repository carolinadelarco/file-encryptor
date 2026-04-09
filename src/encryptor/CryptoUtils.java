package encryptor;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtils {

    // Encryption algorithm: AES in CBC mode with PKCS5 padding
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    
    // Algorithm used to derive the encryption key from the password
    private static final String KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
    
    // Number of iterations to make brute-force attacks harder
    private static final int ITERATIONS = 65536;
    
    // Key length in bits (maximum AES security level)
    private static final int KEY_LENGTH = 256;
    
    // Salt value used to prevent rainbow table attacks
    private static final byte[] SALT = "SecureSalt1234!!".getBytes();
    
    /**
     * Generates a secure AES key derived from the given password.
     * Uses PBKDF2 with HMAC-SHA256 to strengthen weak passwords.
     *
     * @param password the user's password
     * @return a SecretKey ready to use for AES encryption
     * @throws Exception if the key generation fails
     */
    public static SecretKey generateKey(String password) throws Exception {
    	
    	// Create the key factory using the PBKDF2 algorithm
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        
        // Define the key specifications: password, salt, iterations and key length
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT, ITERATIONS, KEY_LENGTH);
        
        // Generate and return the AES key
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

    }
    
    /**
     * Generates a random Initialization Vector (IV) for AES encryption.
     * A new IV should be generated for every encryption operation.
     *
     * @return a 16-byte random IV
     */
    public static byte[] generateIV() {
        
        // Create a 16-byte array to store the IV
        byte[] iv = new byte[16];
        
        // Fill it with cryptographically secure random bytes
        new SecureRandom().nextBytes(iv);
        
        // Return the generated IV
        return iv;
    }
    
    /**
     * Encrypts the given data using AES/CBC with the provided key and IV.
     *
     * @param data the original file content as bytes
     * @param key the AES secret key
     * @param iv the initialization vector
     * @return the encrypted data as bytes
     * @throws Exception if encryption fails
     */
    public static byte[] encrypt(byte[] data, SecretKey key, byte[] iv) throws Exception {
    	
    	// Create a Cipher instance with our algorithm
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        
        // Initialize the cipher in encryption mode with the key and IV
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        
        // Encrypt the data and return the result
        return cipher.doFinal(data);

    }
    
    /**
     * Decrypts the given data using AES/CBC with the provided key and IV.
     *
     * @param data the encrypted file content as bytes
     * @param key the AES secret key
     * @param iv the initialization vector used during encryption
     * @return the decrypted data as bytes
     * @throws Exception if decryption fails
     */
    public static byte[] decrypt(byte[] data, SecretKey key, byte[] iv) throws Exception {
    	
    	// Create a Cipher instance with our algorithm
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // Initialize the cipher in decryption mode with the key and IV
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        // Decrypt the data and return the result
        return cipher.doFinal(data);

       
    }
}