/*
 * Anton Mishchenko
 */

import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * <p>PasswordGenerator generates random passwords of user specified
 * amount and length. Passwords are generated using SecureRandom
 * number generator. Passwords are displayed to console.</p>
 * <p> Menu commands:
 * <p> GEN - generate passwords
 * <p> EXIT - close application
 */
public class PasswordGenerator {
    private final int MIN_PASSWORD_LENGTH = 8;
    private final int MAX_PASSWORD_LENGTH = 64;
    private final int MIN_PASSWORD_AMOUNT = 1;
    private final int MAX_PASSWORD_AMOUNT = 20;
    private final int FIRST_ASCII = 32; // space
    private final int LAST_ASCII = 126; // ~
    private SecureRandom randomGenerator;

    /**
     * <p> Constructor for PasswordGenerator class. It assigns
     * SecureRandom number generator to randomGenerator variable.</p>
     */
    PasswordGenerator(){
        this.randomGenerator = new SecureRandom();
    }

    /**
     * <p> generatePassword generates passwords of user specified length and amount.
     * It validates user input and prints generated passwords or an error
     * message to console. Uses SecureRandom number generator to generate passwords
     * and reseeds it between password batches.</p>
     *
     * <p> Called by: {@link #main(String[]) main} </p>
     *
     * @param passwordLength length of the passwords.
     * @param passwordNum amount of passwords to generate.
     */
    public void generatePassword(int passwordLength, int passwordNum) {
        StringBuilder stringBuilder;
        // verify inputs
        if (passwordLength >= MIN_PASSWORD_LENGTH && passwordLength <= MAX_PASSWORD_LENGTH) {
            if (passwordNum >= MIN_PASSWORD_AMOUNT && passwordNum <= MAX_PASSWORD_AMOUNT) {
                stringBuilder = new StringBuilder();
                try {
                    System.out.println("\n" + passwordNum + " random passwords of length " + passwordLength + " :" + "\n");
                    // reseed randomGenerator for new batch of passwords
                    randomGenerator.reseed();
                    for (int j = 0; j < passwordNum; ++j) {
                        for (int i = 0; i < passwordLength; ++i) {
                            // generate passwords
                            int characterNumber =
                                    randomGenerator.nextInt(FIRST_ASCII, LAST_ASCII + 1);// +1 to include last character
                            char character = (char) characterNumber;
                            stringBuilder.append(character);
                        }
                        // print passwords to console
                        String password = stringBuilder.toString();
                        System.out.println(password);
                        // reset stringBuilder for the next password
                        stringBuilder.setLength(0);
                    }
                }catch (UnsupportedOperationException e) {
                    System.out.println("Error reseeding randomGenerator." + e.getMessage());
                }catch (IllegalArgumentException e) {
                    System.out.println("Error generating new number: FIRST_ASCII is greater than or equal to LAST_ASCII.");
                }
            } else {
                System.out.println("ERROR: Can only generate " + MIN_PASSWORD_AMOUNT
                        + " to " + MAX_PASSWORD_AMOUNT + " passwords.");
            }
        } else {
            System.out.println("ERROR: Password length must be between " + MIN_PASSWORD_LENGTH
                    + " and " + MAX_PASSWORD_LENGTH + " characters.");
        }
    }

    /**
     * <p> main method of {@link #PasswordGenerator() PasswordGenerator}
     * class. It contains menu for user interaction, creates PasswordGenerator
     * object and calls its generatePassword method. Additionally it verifies
     * user input.</p>
     *
     * <p> Calls: {@link #generatePassword(int, int) generatePassword} </p>
     *
     * @param args command line arguments, unused.
     */
    public static void main(String[] args) {
        // initiate variables
        Scanner scanner = new Scanner(System.in);
        PasswordGenerator pg = new PasswordGenerator();
        String command;
        int passwordLength;
        int passwordNum;
        // password generator menu
        System.out.println("Password generator is operational.");
        System.out.println("Passwords generated using: " + "\n"
                + "a-z,A-Z,0-9, spaces and printable symbols such as: !,.|,? ..." + "\n");
        while(true) {
            System.out.println("Enter GEN to generate passwords or EXIT to close application: ");
            command = scanner.next();
            // exit application
            if (command.equals("EXIT")) {
                try{
                    scanner.close();
                } catch (IllegalStateException e) {
                    System.out.println("Error closing scanner.");
                    System.exit(1);
                }
                System.out.println("Password generator closed.");
                return;
            // generate passwords
            } else if (command.equals("GEN")) {
                try {
                    System.out.println("Enter length of the generated password: ");
                    passwordLength = scanner.nextInt();
                    System.out.println("Enter amount of passwords to generate: ");
                    passwordNum = scanner.nextInt();
                    // generate passwords
                    pg.generatePassword(passwordLength, passwordNum);
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, enter a number.");
                    scanner.next();
                } catch (Exception e) {
                    System.out.println("Error occurred when generating passwords: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid command.");
            }
            System.out.println();
        }
    }
}