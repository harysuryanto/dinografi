package id.harysuryanto.dinografi.oop;

public class Cipher {
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