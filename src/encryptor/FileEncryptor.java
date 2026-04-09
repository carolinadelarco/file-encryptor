package encryptor;

import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.SecretKey;

public class FileEncryptor {

    /**
     * Encrypts a file and saves the result with .enc extension.
     *
     * @param filePath path to the file to encrypt
     * @param password the user's password
     * @throws Exception if encryption or file operations fail
     */
    public static void encryptFile(String filePath, String password) throws Exception {

        // Step 1: Read the original file as bytes
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));

        // Step 2: Generate the AES key from the password
        SecretKey key = CryptoUtils.generateKey(password);

        // Step 3: Generate a random IV
        byte[] iv = CryptoUtils.generateIV();

        // Step 4: Encrypt the file data
        byte[] encryptedData = CryptoUtils.encrypt(fileData, key, iv);

        // Step 5: Combine IV + encrypted data into a single array
        byte[] combined = new byte[iv.length + encryptedData.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedData, 0, combined, iv.length, encryptedData.length);

        // Step 6: Save the combined data to a new .enc file
        Files.write(Paths.get(filePath + ".enc"), combined);

        System.out.println("File encrypted successfully: " + filePath + ".enc");
    }

    /**
     * Decrypts a .enc file and saves the original content.
     *
     * @param filePath path to the encrypted file
     * @param password the user's password
     * @throws Exception if decryption or file operations fail
     */
    public static void decryptFile(String filePath, String password) throws Exception {

        // Step 1: Read the encrypted file as bytes
        byte[] fileData = Files.readAllBytes(Paths.get(filePath));

        // Step 2: Extract the IV from the first 16 bytes
        byte[] iv = new byte[16];
        System.arraycopy(fileData, 0, iv, 0, 16);

        // Step 3: Extract the encrypted data after the IV
        byte[] encryptedData = new byte[fileData.length - 16];
        System.arraycopy(fileData, 16, encryptedData, 0, encryptedData.length);

        // Step 4: Generate the AES key from the password
        SecretKey key = CryptoUtils.generateKey(password);

        // Step 5: Decrypt the data
        byte[] decryptedData = CryptoUtils.decrypt(encryptedData, key, iv);

        // Step 6: Save the decrypted file removing the .enc extension
        String outputPath = filePath.replace(".enc", "");
        Files.write(Paths.get(outputPath), decryptedData);

        System.out.println("File decrypted successfully: " + outputPath);
    }
}