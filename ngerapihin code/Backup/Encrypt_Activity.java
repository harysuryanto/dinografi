package id.harysuryanto.dinografi;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Typeface;
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
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Encrypt_Activity extends AppCompatActivity {
    DataHelper dbHelper;

    Dialog dialogSave;


    EditText textPlain, textKey, textNama, textKeterangan;
    TextView textCipher, textPlainInBinary, textCipherInBinary, textKeyInBinary, textClose;
    Button buttonSave, buttonEncrypt, buttonCopy;


    private static String[] alfabet = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    //, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    //, "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"}; // Angka
    //, "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "{", "]", "}", "?", ",", "."}; // Simbol
    private static String jenis_kriptografi_dipilih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        dbHelper = new DataHelper(this);
        //myDb.getAllData();

        dialogSave = new Dialog(this);

        final Spinner spinnerJenisKriptografi = findViewById(R.id.spinnerJenisKriptografi);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.jenis_kriptografi, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerJenisKriptografi.setAdapter(adapter);



        textPlain = findViewById(R.id.textPlain);
        textPlainInBinary = findViewById(R.id.textPlainInBinary);
        textKey = findViewById(R.id.textKey);
        //textKeyInBinary = findViewById(R.id.textKeyInBinary);
        textCipher = findViewById(R.id.textCipher);
        textCipherInBinary = findViewById(R.id.textCipherInBinary);
        textNama = findViewById(R.id.textNama);
        textKeterangan = findViewById(R.id.textKeterangan);

        buttonSave = findViewById(R.id.buttonSave);
        buttonEncrypt = findViewById(R.id.buttonEncrypt);
        buttonCopy = findViewById(R.id.buttonCopy);


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


        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String raw_plain, raw_key, raw_cipher, raw_temp;

                if(jenis_kriptografi_dipilih.equals("Polyalphabetic Cipher")) {
                    // Encrypt

                    String[] array_plain, array_temp;

                    raw_plain = textPlain.getText().toString();
                    raw_plain = raw_plain.toUpperCase();

                    raw_temp = textKey.getText().toString();
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
                        if(x<array_temp.length){
                            if(array_plain[i].equals(" ")){
                                array_key[i]=" ";
                            } else {
                                array_key[i]=array_temp[x];
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
                    textCipher.setText(raw_cipher);

                    Toast.makeText(getApplicationContext(), "Encrypted", Toast.LENGTH_LONG).show();

                } else {
                    //String raw_temp;
                    String[] array_plain, array_plain_binner, array_key, array_cipher, array_temp;

                    // Encrypt

                    raw_plain = textPlain.getText().toString();
                    raw_plain = raw_plain.toUpperCase();

                    raw_temp = textKey.getText().toString();
                    raw_temp = raw_temp.replaceAll("\\s+","");
                    raw_temp = raw_temp.toUpperCase();

                    // Pisahkan string agar menjadi satu karakter terpisah
                    array_plain = raw_plain.split("(?<=\\G.{1})");
                    array_temp = raw_temp.split("(?<=\\G.{1})");

                    array_key = new String[raw_plain.length()];

                    // Membangkitkan kunci
						int x=0;
						for(int i=0; i<array_plain.length; i++){
							if(x<array_temp.length){
								if(array_plain[i].equals(" ")){
									array_key[i]=" ";
								} else {
									array_key[i]=array_temp[x];
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
							}
						}

					raw_temp = join( array_key);
					raw_key = join(array_key);
					textKey.setText(raw_key);

					System.out.print("Plain Bin \t\t: ");
					for (int i = 0; i < raw_plain.length(); i++) {
				      System.out.print(Integer.toBinaryString((int)raw_plain.charAt(0)) + " ");
				    }

					System.out.print("Key Bin \t: ");
					for (int i = 0; i < raw_key.length(); i++) {
				      System.out.print(Integer.toBinaryString((int)raw_key.charAt(0)) + " ");
				    }

					// Encrypt
					int[] cout = new int[raw_plain.length()];
					String[] bin = new String[raw_plain.length()];
				    for (int i = 0; i < raw_plain.length(); i++) {
				      if(array_plain[i].equals(" ")){
				      	bin[i] = " ";
				      } else{
				      	//Proses XOR
					    int c = (Integer.valueOf(raw_plain.charAt(i)) ^ Integer.valueOf(raw_key.charAt(0)));
					    cout[i] = c;
					    bin[i] = Integer.toBinaryString(c);
				      }
				    }
				    String bin_str = join(bin);
				    textCipherInBinary.setText(bin_str);

				    // ASCII Cipher
				    char[] cascii = new char[cout.length];
				    for (int i = 0; i < cout.length; i++) {
				        cascii[i] = (char) cout[i];
				    }

                    // Konversi pascii dari array of char ke array of string
                    String[] casciiInString = new String[cascii.length];
                    for(int i=0; i<cascii.length; i++) {
                        casciiInString[i] = String.valueOf(cascii[i]);
                    }

                    // Menyatukan hasil encrypt yang awalnya kumpulan karakter terpisah menjadi satu
                    //raw_cipher = join(String.valueOf(cascii));
                    raw_cipher = join(casciiInString);
                    textCipher.setText(raw_cipher);

                    Toast.makeText(getApplicationContext(), "Encrypted", Toast.LENGTH_LONG).show();

                }
            }
        });



        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String nama = textNama.getText().toString();
                //String keterangan = textKeterangan.getText().toString();
                String nama = "nama";
                String keterangan = "keterangan";
                String jenis_kriptografi = jenis_kriptografi_dipilih;
                String plain = textPlain.getText().toString();
                String plain_in_binary = textPlainInBinary.getText().toString();
                String key = textKey.getText().toString();
                String cipher = textCipher.getText().toString();
                String cipher_in_binary = textCipherInBinary.getText().toString();

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT INTO data(nama, keterangan, jenis_kriptografi, plain, plain_in_binary, key, cipher, cipher_in_binary) VALUES('" +
                        nama + "','" +
                        keterangan + "','" +
                        jenis_kriptografi + "','" +
                        plain + "','" +
                        plain_in_binary + "','" +
                        key + "','" +
                        cipher + "','" +
                        cipher_in_binary + "')");

                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                //showMessage("Coba alertDialog", "Isinya....");
            }
        });



        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("cipher", textCipher.getText().toString());
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
