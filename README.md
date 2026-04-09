# File Encryptor

A Java command-line application that encrypts and decrypts files using AES-256, one of the most secure encryption standards used in banking, messaging apps and government systems.

## What it does

- Encrypts any file (documents, images, PDFs...) using AES-256
- Decrypts previously encrypted files
- Derives a secure key from your password using PBKDF2 with HMAC-SHA256
- Generates a unique random IV for every encryption operation

## How it works

1. The user provides a file path and a password
2. The password is transformed into a 256-bit AES key using PBKDF2 (65,536 iterations)
3. A random IV is generated to ensure each encryption is unique
4. The file is encrypted using AES in CBC mode
5. The IV is stored alongside the encrypted data so decryption is possible
6. To decrypt, the IV is extracted from the file and the process is reversed

## Technologies

- Java
- AES/CBC/PKCS5Padding (symmetric encryption)
- PBKDF2WithHmacSHA256 (key derivation)
- SecureRandom (cryptographically secure IV generation)

## Project structure

```text
├── encryptor/
│   ├── CryptoUtils.java    # AES encryption/decryption and key derivation logic
│   ├── FileEncryptor.java  # File read/write operations
│   └── Main.java           # Entry point and user interface
```

## How to run

1. Clone the repository
2. Open the project in Eclipse or any Java IDE
3. Run `Main.java`
4. Choose option 1 to encrypt or 2 to decrypt
5. Enter the file path and your password

## Example

```
=== File Encryptor ===
1. Encrypt a file
2. Decrypt a file
Choose an option: 1
Enter file path: C:\Users\user\Desktop\document.pdf
Enter password: mysecurepassword
File encrypted successfully: C:\Users\user\Desktop\document.pdf.enc
```

## Security notes

- The encryption key is never stored anywhere — it is derived from your password every time
- The IV is not secret and is stored at the beginning of the encrypted file
- The salt is currently fixed — in a production environment it should be randomly generated and stored alongside the encrypted file
- If you forget your password, the file cannot be recovered

## Possible improvements

- Random salt generation per file
- Graphical user interface (GUI)
- Support for encrypting entire folders
- Password strength validation
