package id.harysuryanto.dinografi.oop;

class MainBlock {
    public static void main(String[] args) {
        BlockCipher blockCipher = new BlockCipher();

        String plain_text = "Hary Suryanto";
        String cipher_text;
        String key = "1 1 2";

        System.out.println("=== Enkripsi dengan Block Cipher ===");
        System.out.println("Plain text  : " + plain_text);
        System.out.println("Kunci       : " + key);
        cipher_text = blockCipher.encrypt(plain_text, key);
        System.out.println("Cipher text : " + cipher_text);
                
        System.out.println("\n=== Dekripsi dengan Block Cipher ===");
        System.out.println("Cipher text : " + cipher_text);
        System.out.println("Kunci       : " + key);
        System.out.println("Plain text  : " + blockCipher.decrypt(cipher_text, key));
    }
}