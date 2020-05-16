package id.harysuryanto.dinografi.oop;

class MainPoly {
    public static void main(String[] args) {
        PolyalphabeticCipher polyalphabeticCipher = new PolyalphabeticCipher();

        String plain_text = "Hai aku sayang kamu tapi kamu ngga";
        String cipher_text;
        String key = "kasihan sekali dia";

        System.out.println("=== Enkripsi dengan Polyalphabetic Cipher ===");
        System.out.println("Plain text  : " + plain_text);
        System.out.println("Kunci       : " + key);
        cipher_text = polyalphabeticCipher.encrypt(plain_text, key);
        System.out.println("Cipher text : " + cipher_text);
                
        System.out.println("\n=== Dekripsi dengan Polyalphabetic Cipher ===");
        System.out.println("Cipher text : " + cipher_text);
        System.out.println("Kunci       : " + key);
        System.out.println("Plain text  : " + polyalphabeticCipher.decrypt(cipher_text, key));
    }
}