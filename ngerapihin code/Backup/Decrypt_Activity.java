package id.harysuryanto.dinografi;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Decrypt_Activity extends AppCompatActivity {
    DataHelper myDb;

    Dialog dialogSave;


    TextView textTitle, textSubTitle, textLogo;
    LinearLayout llEncrypt, llDecrypt, llSavedCryptography;
    EditText textCipher, textKey;
    TextView textPlainInBinary, textCipherInBinary, textPlain;
    Button buttonSave, buttonDecrypt, buttonCopy;


    private static String[] alfabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    //, "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}; // Angka
    //, "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "{", "]", "}", "?", ",", "."}; // Simbol
    private static String jenis_kriptografi_dipilih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        myDb = new DataHelper(this);
        //myDb.getAllData();

        dialogSave = new Dialog(this);


        final Spinner spinnerJenisKriptografi = (Spinner) findViewById(R.id.spinnerJenisKriptografi);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenis_kriptografi, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerJenisKriptografi.setAdapter(adapter);



        textPlain = (TextView) findViewById(R.id.textPlain);
        textKey = (EditText) findViewById(R.id.textKey);
        textCipher = (EditText) findViewById(R.id.textCipher);
        textPlainInBinary = (TextView) findViewById(R.id.textPlainInBinary);
        textCipherInBinary = (TextView) findViewById(R.id.textCipherInBinary);
        buttonDecrypt = (Button) findViewById(R.id.buttonDecrypt);
        buttonCopy = (Button) findViewById(R.id.buttonCopy);


        spinnerJenisKriptografi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object jenis_kriptografi = parent.getSelectedItem().toString();
                jenis_kriptografi_dipilih = parent.getSelectedItem().toString();

                Toast.makeText(getApplicationContext(), jenis_kriptografi + " is selected", Toast.LENGTH_LONG).show();

                if(!jenis_kriptografi.equals("Block Cipher")) {
                    textPlainInBinary.setVisibility(View.GONE);
                    textCipherInBinary.setVisibility(View.GONE);
                } else {
                    textPlainInBinary.setVisibility(View.VISIBLE);
                    textCipherInBinary.setVisibility(View.VISIBLE);
                    textPlainInBinary.setText("");
                    textCipherInBinary.setText("");
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String raw_plain, raw_key, raw_cipher, temp;

                if(jenis_kriptografi_dipilih.equals("Polyalphabetic Cipher")) {
                    // Decrypt

                    String[] array_cipher, array_temp;

                    raw_cipher = textCipher.getText().toString();
                    raw_cipher = raw_cipher.toUpperCase();

                    temp = textKey.getText().toString();
                    temp = temp.replaceAll("\\s+","");
                    temp = temp.toUpperCase();

                    String[] array_plain = new String[raw_cipher.length()];
                    String[] array_key = new String[raw_cipher.length()];

                    // Pisahkan string agar menjadi satu karakter terpisah
                    array_cipher = split(raw_cipher);
                    array_temp = split(temp);

                    // Membangkitkan kunci
                    int x=0;
                    for(int i=0; i<array_cipher.length; i++){
                        if(x<array_temp.length){
                            if(array_cipher[i].equals(" ")){
                                array_key[i]=" ";
                            } else {
                                array_key[i]=array_temp[x];
                                x++;
                            }
                        } else {
                            x = 0;
                            array_key[i] = array_temp[x];
                            x++;
                        }
                    }


                    // Proses decrypt
                    for(int i=0; i<raw_cipher.length(); i++) {
                        if(array_cipher[i].equals(" ")) {
                            array_plain[i] = " ";
                        } else {
                            array_plain[i] = decrypt(array_cipher[i], array_key[i]);
                        }
                    }

                    // Menyatukan hasil decrypt yang awalnya kumpulan karakter terpisah menjadi satu
                    raw_plain = join(array_plain);
                    textPlain.setText(raw_plain);

                    Toast.makeText(getApplicationContext(), "Decrypted", Toast.LENGTH_LONG).show();

                } else {
                    // Decrypt

                    String raw_temp;
                    String[] array_plain, array_key, array_cipher, array_temp;


                    raw_cipher = textCipher.getText().toString();
                    raw_cipher = raw_cipher.toUpperCase();

                    raw_temp = textKey.getText().toString();
                    raw_temp = raw_temp.replaceAll("\\s+","");
                    raw_temp = raw_temp.toUpperCase();

                    raw_cipher = textCipher.getText().toString();
                    raw_cipher = raw_cipher.toUpperCase();

                    raw_temp = raw_temp.replaceAll("\\s+","");

                    // Pisahkan string agar menjadi satu karakter terpisah
                    array_cipher = split(raw_cipher);
                    array_temp = split(raw_temp);

                    array_key = new String[raw_cipher.length()];

                    // Membangkitkan kunci
                    int x=0;
                    for(int i=0; i<array_cipher.length; i++){
                        if(x<array_temp.length){
                            if(array_cipher[i].equals(" ")){
                                array_key[i]=" ";
                            } else {
                                array_key[i]=array_temp[x];
                                x++;
                            }
                        } else {
                            x = 0;
                            array_key[i] = array_temp[x];
                            x++;
                        }
                    }

                    raw_temp = join(array_key);
                    System.out.println(raw_temp);
                    raw_key = join(array_key);

                    System.out.print("Cipher Bin \t\t: ");
                    for (int i = 0; i < raw_cipher.length(); i++) {
                        System.out.print(Integer.toBinaryString((int)raw_cipher.charAt(0)) + " ");
                    }
                    System.out.println();

                    System.out.print("Key Bin \t: ");
                    for (int i = 0; i < raw_key.length(); i++) {
                        System.out.print(Integer.toBinaryString((int)raw_key.charAt(0)) + " ");
                    }
                    System.out.println();

                    //dekrip
                    System.out.println("DEKRIPSI\n");
                    int[] cout = new int[raw_cipher.length()];
                    String[] bin = new String[raw_cipher.length()];
                    for (int i = 0; i < raw_cipher.length(); i++) {
                        if(array_cipher[i].equals(" ")){
                            bin[i] = " ";
                        } else{
                            //Proses XOR
                            int c = (Integer.valueOf(raw_cipher.charAt(i)) ^ Integer.valueOf(raw_key.charAt(0)));
                            cout[i] = c;
                            bin[i] = Integer.toBinaryString(c);
                        }
                        //System.out.print(cout[i] + "  ");
                    }
                    String bin_str = join(bin);
                    textPlainInBinary.setText(bin_str);

                    //ASCII Cipher
                    char[] pascii = new char[cout.length];
                    System.out.print("ASCII \t\t: ");
                    for (int i = 0; i < cout.length; i++) {
                        pascii[i] = (char) cout[i];
                        //System.out.print(pascii[i] + "");
                    }

                    // Konversi pascii dari array of char ke array of string
                    String[] pasciiInString = new String[pascii.length];
                    for(int i=0; i<pascii.length; i++) {
                        pasciiInString[i] = String.valueOf(pascii[i]);
                    }

                    // Menyatukan hasil decrypt yang awalnya kumpulan karakter terpisah menjadi satu
                    //raw_plain = String.join("", String.valueOf(pascii));
                    raw_plain = join(pasciiInString);
                    textPlain.setText(raw_plain);

                    Toast.makeText(getApplicationContext(), "Decrypted", Toast.LENGTH_LONG).show();
                }




            }
        });






        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cipher", textPlain.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(), "Copied", Toast.LENGTH_SHORT).show();
            }
        });

    }




    // Alert dialog
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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

    public static String decrypt(String c, String k) {
        c = String.valueOf(get_index(alfabet, c));
        k = String.valueOf(get_index(alfabet, k));

        if(((Integer.parseInt(c) - Integer.parseInt(k)) % alfabet.length) >= 0)
            return String.valueOf( alfabet[(Integer.parseInt(c) - Integer.parseInt(k)) % alfabet.length] );
        else
            return String.valueOf( alfabet[(Integer.parseInt(c) - Integer.parseInt(k)) + alfabet.length] );
    }



    public static int get_xor(int a, int b) {
        return a^b;
    }

    public static String[] get_binary(String sRaw){
        char[] arrChar = sRaw.toCharArray();
        String[] array_temp = new String[arrChar.length];
        int i=0;
        for(char c : arrChar){
            if(arrChar[i] == ' '){
                array_temp[i] = " ";
                i++;
            } else {
                array_temp[i] = "0"+Integer.toBinaryString(c);
                i++;
            }
        }
        return array_temp;
    }

    public static String[] get_char(String sRaw){
        char[] arrChar = sRaw.toCharArray();
        String[] array_temp = new String[arrChar.length];
        int i=0;
        for(char c : arrChar){
            if(arrChar[i] == ' '){
                array_temp[i] = " ";
                i++;
            } else {
                String bin = "0"+Integer.toBinaryString(c);
                int ascii = Integer.parseInt(bin,8);
                char kar = (char) ascii;
                array_temp[i] = Character.toString(kar);
            }
        }
        return array_temp;
    }

}
