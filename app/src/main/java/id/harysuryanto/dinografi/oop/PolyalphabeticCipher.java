package id.harysuryanto.dinografi.oop;

public class PolyalphabeticCipher extends Cipher {

    private static String[] alphabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    
    private static String string_plain, string_cipher, string_key;
    private static String[] array_plain, array_cipher, array_key, array_key_temp;

    public static String encrypt(String plain_text, String key) {
        // Make all the character uppercase
        string_plain = plain_text.toUpperCase();

        // Remove all spaces and make all the character uppercase
        string_key = key.replaceAll("\\s+","").toUpperCase();

        // Set the capacity of array_cipher and array_key based on the length of string_plain
        array_cipher = new String[string_plain.length()];
        array_key = new String[string_plain.length()];

        // Pisahkan string agar menjadi satu karakter terpisah
        array_plain = string_plain.split("(?<=\\G.{1})");
        array_key_temp = string_key.split("(?<=\\G.{1})");

        // Generating the key
        Cipher.generate_key(array_plain, array_key, array_key_temp);

        // Proses encrypt
        for(int i=0; i<array_plain.length; i++) {
            if(array_plain[i].equals(" ")) {
                array_cipher[i] = " ";
            } else {
                array_cipher[i] = encrypt_one_character(array_plain[i], array_key[i]);
            }
        }

        // Menyatukan hasil encrypt yang awalnya kumpulan karakter terpisah menjadi satu
        string_cipher = join(array_cipher);

        // Return the encrypt result
        return string_cipher;
    }

    public static String decrypt(String cipher_text, String key) {
        // Make all the character uppercase
        string_cipher = cipher_text.toUpperCase();

        // Remove all spaces and make all the character uppercase
        string_key = key.replaceAll("\\s+","").toUpperCase();

        // Set the capacity of array_cipher and array_key based on the length of string_plain
        array_plain = new String[string_cipher.length()];
        array_key = new String[string_cipher.length()];


        // Pisahkan string agar menjadi satu karakter terpisah
        array_cipher = string_cipher.split("(?<=\\G.{1})");
        array_key_temp = string_key.split("(?<=\\G.{1})");

        // Membangkitkan kunci
        generate_key(array_cipher, array_key, array_key_temp);

        // Proses decrypt
        for(int i=0; i<array_cipher.length; i++) {
            if(array_cipher[i].equals(" ")) {
                array_plain[i] = " ";
            } else {
                array_plain[i] = decrypt_one_character(array_cipher[i], array_key[i]);
            }
        }

        // Menyatukan hasil decrypt yang awalnya kumpulan karakter terpisah menjadi satu
        string_plain = join(array_plain);

        // Return the encrypt result
        return string_plain;
    }

    // This function will return the index of a character of string from an array
    private static int get_index(String[] arr, String s) {
        int index = 0;
        for(int i=0; i<alphabet.length; i++) {
            if(alphabet[i].equals(s)) {
                index=i;
            }
        }

        return index;
    }

    // This function will return the result of one character encryption
    private static String encrypt_one_character(String p, String k) {
        p = String.valueOf(get_index(alphabet, p));
        k = String.valueOf(get_index(alphabet, k));

        return String.valueOf( alphabet[(Integer.parseInt(p) + Integer.parseInt(k)) % alphabet.length] );
    }
    
    // This function will return the result of one character decryption
    private static String decrypt_one_character(String c, String k) {
        c = String.valueOf(get_index(alphabet, c));
        k = String.valueOf(get_index(alphabet, k));

        if(((Integer.parseInt(c) - Integer.parseInt(k)) % alphabet.length) >= 0)
            return String.valueOf( alphabet[(Integer.parseInt(c) - Integer.parseInt(k)) % alphabet.length] );
        else
            return String.valueOf( alphabet[(Integer.parseInt(c) - Integer.parseInt(k)) + alphabet.length] );
    }
}