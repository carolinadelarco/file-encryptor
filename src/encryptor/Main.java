package encryptor;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== File Encryptor ===");
        System.out.println("1. Encrypt a file");
        System.out.println("2. Decrypt a file");
        System.out.print("Choose an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            if (option == 1) {
                FileEncryptor.encryptFile(filePath, password);
            } else if (option == 2) {
                FileEncryptor.decryptFile(filePath, password);
            } else {
                System.out.println("Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
