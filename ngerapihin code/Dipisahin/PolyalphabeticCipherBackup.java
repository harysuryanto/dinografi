class PolyalphabeticCipherBackup {

    private static String[] alfabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static final String dummy_raw_plain = "TES YA GUYS";
    private static final String dummy_raw_temp = "abcdefgh";

    public static void main(String[] args) {

        String raw_plain, raw_key, raw_cipher, raw_temp;
        
        String[] array_plain, array_temp;

        raw_plain = dummy_raw_plain;
        raw_plain = raw_plain.toUpperCase();

        raw_temp = dummy_raw_temp;
        raw_temp = raw_temp.replaceAll("\\s+","");
        raw_temp = raw_temp.toUpperCase();

        String[] array_cipher = new String[raw_plain.length()];
        String[] array_key = new String[raw_plain.length()];


        // Pisahkan string agar menjadi satu karakter terpisah
        // Menggunakan method bawaan
        array_plain = raw_plain.split("(?<=\\G.{1})");
        array_temp = raw_temp.split("(?<=\\G.{1})");
        // Menggunakan method buatan. Masih error
        array_plain = split(raw_plain);
        array_temp = split(raw_temp);


        // Membangkitkan kunci
        int x=0;
        for(int i=0; i<array_plain.length; i++){
            /* if(x<array_temp.length){
                if(array_plain[i].equals(" ")){
                    array_key[i]=" ";
                } else {
                    array_key[i] = array_temp[x];
                    x++;
                }
            } else {
                if(array_plain[i].equals(" ")){
                    array_key[i]=" ";
                } else {
                    x = 0;
                    array_key[i] = array_temp[x];
                    x++;
                }
            } */

            if(array_plain[i].equals(" ")){
                array_key[i]=" ";
            } else {
                if(x<array_temp.length) {
                    x = 0;
                }
                array_key[i] = array_temp[x];
                x++;
            }
        }


        // Proses encrypt
        for(int i=0; i<raw_plain.length(); i++) {
            if(array_plain[i].equals(" ")) {
                array_cipher[i] = " ";
            } else {
                array_cipher[i] = encrypt(array_plain[i], array_key[i]);
            }
        }

        // Menyatukan hasil encrypt yang awalnya kumpulan karakter terpisah menjadi satu
        raw_cipher = join(array_cipher);

        // Menampilkan hasil encrypt
        System.out.println("Hasil: " + raw_cipher);

    }

    // Fungsi split dan join secara manual
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

    public static String join(String[] textToJoin) {
        String result = "";
        for(int i=0; i<textToJoin.length; i++) {
            result += textToJoin[i];
        }

        return result;
    }
    public static int get_index(String[] arr, String s) {
        int index = 0;
        for(int i=0; i<alfabet.length; i++) {
            if(alfabet[i].equals(s)) {
                index=i;
            }
        }

        return index;
    }
    public static String encrypt(String p, String k) {
        p = String.valueOf(get_index(alfabet, p));
        k = String.valueOf(get_index(alfabet, k));

        return String.valueOf( alfabet[(Integer.parseInt(p) + Integer.parseInt(k)) % alfabet.length] );
    }
}