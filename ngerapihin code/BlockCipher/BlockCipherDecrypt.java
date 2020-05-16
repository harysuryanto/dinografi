package id.harysuryanto.dinografi.oop.BlockCipher;

public class BlockCipherDecrypt {
    
    private static String string_plain="", string_plain_bin="", string_key, string_key_bin="", string_cipher, string_cipher_bin="";
    private static String[] array_cipher, array_cipher_bin, array_key, array_key_temp;

    public static void main(String[] args) {
        string_cipher = "yP@H bGCHS_E]";

        string_key = "1 1 2";
        string_key = string_key.replaceAll("\\s+","").toUpperCase();

        // Pisahkan string agar menjadi satu karakter terpisah
        array_cipher = split(string_cipher);
        array_key_temp = split(string_key);

        array_key = new String[string_cipher.length()];

        // Membangkitkan kunci
        generate_key(array_cipher, array_key, array_key_temp);

        System.out.println("Cipher (string_cipher) \t\t: " + string_cipher);

        string_key = join(array_key);
        System.out.println("Key (string_key) \t\t: " + string_key);

        System.out.print("Cipher Bin (string_cipher_bin) \t: ");
        String hasil;
        for (int i = 0; i < string_cipher.length(); i++) {
            hasil = Integer.toBinaryString((int) string_cipher.charAt(i));

            if (hasil.length() == 6) hasil = "00" + hasil;
            if (hasil.length() == 7) hasil = "0" + hasil;

            string_cipher_bin = string_cipher_bin + hasil + " ";
        }
        string_cipher_bin = string_cipher_bin.trim();
        System.out.print(string_cipher_bin);

        System.out.print("\nKey Bin (string_key_bin) \t: ");
        for (int i = 0; i < string_key.length(); i++) {
            hasil = Integer.toBinaryString((int) string_key.charAt(i));

            if (hasil.length() == 6) hasil = "00" + hasil;
            if (hasil.length() == 7) hasil = "0" + hasil;

            string_key_bin += hasil + " ";
        }
        string_key_bin = string_key_bin.trim();
        System.out.print(string_key_bin);

        array_cipher_bin = split(string_cipher_bin);

        // Decrypt
        String[] bin = new String[string_cipher_bin.length()];
        for (int i = 0; i < string_cipher_bin.length(); i++) {
            if(array_cipher_bin[i].equals(" ")){
                bin[i] = " ";
                string_plain_bin += " ";
            } else{
                string_plain_bin += (string_cipher_bin.charAt(i) == string_key_bin.charAt(i))? 0 : 1;
            }
        }

        System.out.println("\nPlain (string_plain_bin) \t: " + string_plain_bin);

        // Hasil dekripsi
        for (int i=0; i<string_plain_bin.replaceAll("\\s+","").length()/8; i++) {
            int a = Integer.parseInt(string_plain_bin.replaceAll("\\s+","").substring(8*i, (i+1)*8), 2);
            string_plain += (char)(a);
        }
        System.out.println("Cipher (string_plain) \t\t: " + string_plain);
    }

    // This function will generate the key based on the plain text
    public static void generate_key(String[] textToGenerate, String[] array_key, String[] array_key_temp) {
        int x=0;
        for(int i=0; i<textToGenerate.length; i++){
            if(x<array_key_temp.length){
                if(textToGenerate[i].equals(" ")){
                    array_key[i]=" ";
                } else {
                    array_key[i] = array_key_temp[x];
                    x++;
                }
            } else {
                if(textToGenerate[i].equals(" ")){
                    array_key[i]=" ";
                } else {
                    x = 0;
                    array_key[i] = array_key_temp[x];
                    x++;
                }
            }
        }
    }

    // This function will split a single String and will return the result into an array of String
    public static String[] split(String textToSplit) {
        // Statement for split. But the result is in char (different data type). It needs to be converted to String.
        char[] resultInArrayChar = textToSplit.toCharArray();

        // Convert from char[] to String[]
        String[] resultInArrayString = new String[resultInArrayChar.length];
        for(int i=0; i<resultInArrayChar.length; i++) {
            resultInArrayString[i] = String.valueOf(resultInArrayChar[i]);
        }

        return resultInArrayString;
    }

    // This function will join separate characters and will return the result into a single String
    public static String join(String[] textToJoin) {
        String result = "";
        for(int i=0; i<textToJoin.length; i++) {
            result += textToJoin[i];
        }

        return result;
    }
}