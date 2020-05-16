package id.harysuryanto.dinografi.oop;

public class BlockCipher extends Cipher {
    
    private static String string_plain, string_plain_bin="", string_cipher="", string_cipher_bin="", string_key, string_key_bin="";
    private static String[] array_plain, array_plain_bin, array_cipher, array_cipher_bin, array_key, array_key_temp;

    
    public static String encrypt(String plain_text, String key) {
        string_plain = plain_text; // Kalo diisi "HARY SURYANTO" hasilnya akan salah (jumlah karakter plain dengan cipher berbeda satu karakter)

        // Remove all spaces and make all the character uppercase
        string_key = key.replaceAll("\\s+","").toUpperCase();

        // Pisahkan string agar menjadi satu karakter terpisah
        array_plain = string_plain.split("(?<=\\G.{1})");
        array_key_temp = string_key.split("(?<=\\G.{1})");

        // Set the capacity of array_key based on the length of string_plain
        array_key = new String[string_plain.length()];

        // Generating the key
        generate_key(array_plain, array_key, array_key_temp);

        string_key = join(array_key);

        // Pisahkan string dari string_plain_bin agar menjadi satu karakter terpisah, lalu masukkan ke array
        array_plain_bin = split(string_plain_bin);

        // Encrypt (in binary)
        String[] bin = new String[string_plain_bin.length()];
        for (int i = 0; i < string_plain_bin.length(); i++) {
            if(array_plain_bin[i].equals(" ")){
                bin[i] = " ";
                string_cipher_bin += " ";
            } else{
                string_cipher_bin += (string_plain_bin.charAt(i) == string_key_bin.charAt(i))? 0 : 1;
            }
        }
        
        // Hasil enkripsi
        for (int i=0; i<string_cipher_bin.replaceAll("\\s+","").length()/8; i++) {
            int a = Integer.parseInt(string_cipher_bin.replaceAll("\\s+","").substring(8*i, (i+1)*8), 2);
            string_cipher += (char)(a);
        }

        return string_cipher;
    }

    public static String decrypt(String cipher_text, String key) {
        string_cipher = cipher_text;

        // Remove all spaces and make all the character uppercase
        string_key = key.replaceAll("\\s+","").toUpperCase();

        // Pisahkan string agar menjadi satu karakter terpisah
        array_cipher = split(string_cipher);
        array_key_temp = split(string_key);

        // Set the capacity of array_key based on the length of string_cipher
        array_key = new String[string_cipher.length()];

        // Generating the key
        generate_key(array_cipher, array_key, array_key_temp);

        string_key = join(array_key);

        // Pisahkan string dari string_cipher_bin agar menjadi satu karakter terpisah, lalu masukkan ke array
        array_cipher_bin = split(string_cipher_bin);

        // Decrypt (in binary)
        String[] bin = new String[string_cipher_bin.length()];
        for (int i = 0; i < string_cipher_bin.length(); i++) {
            if(array_cipher_bin[i].equals(" ")){
                bin[i] = " ";
                string_plain_bin += " ";
            } else{
                string_plain_bin += (string_cipher_bin.charAt(i) == string_key_bin.charAt(i))? 0 : 1;
            }
        }

        // Hasil dekripsi
        for (int i=0; i<string_plain_bin.replaceAll("\\s+","").length()/8; i++) {
            int a = Integer.parseInt(string_plain_bin.replaceAll("\\s+","").substring(8*i, (i+1)*8), 2);
            string_plain += (char)(a);
        }

        return string_plain;
    }
}