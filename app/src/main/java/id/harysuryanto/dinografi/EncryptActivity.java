package id.harysuryanto.dinografi;

import id.harysuryanto.dinografi.oop.BlockCipher;
import id.harysuryanto.dinografi.oop.PolyalphabeticCipher;

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

public class EncryptActivity extends AppCompatActivity {
    DataHelper dbHelper;

    Dialog dialogSave;


    EditText textPlain, textKey, textNama, textKeterangan;
    TextView textCipher, textPlainInBinary, textCipherInBinary, textKeyInBinary, textClose;
    Button buttonSave, buttonEncrypt, buttonCopy;

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
        //textPlainInBinary = findViewById(R.id.textPlainInBinary);
        textKey = findViewById(R.id.textKey);
        //textKeyInBinary = findViewById(R.id.textKeyInBinary);
        textCipher = findViewById(R.id.textCipher);
        //textCipherInBinary = findViewById(R.id.textCipherInBinary);
        textNama = findViewById(R.id.textNama);
        textKeterangan = findViewById(R.id.textKeterangan);

        buttonSave = findViewById(R.id.buttonSave);
        buttonEncrypt = findViewById(R.id.buttonEncrypt);
        buttonCopy = findViewById(R.id.buttonCopy);


        spinnerJenisKriptografi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object jenis_kriptografi = parent.getSelectedItem().toString();
                jenis_kriptografi_dipilih = parent.getSelectedItem().toString();

                /*Toast.makeText(getApplicationContext(), jenis_kriptografi + " is selected", Toast.LENGTH_LONG).show();

                if(!jenis_kriptografi.equals("Block Cipher")) {
                    textPlainInBinary.setVisibility(View.GONE);
                    textCipherInBinary.setVisibility(View.GONE);
                } else {
                    textPlainInBinary.setVisibility(View.VISIBLE);
                    textCipherInBinary.setVisibility(View.VISIBLE);
                    textPlainInBinary.setText("");
                    textCipherInBinary.setText("");
                }*/
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String plain_text, key;

                plain_text = textPlain.getText().toString();
                key = textKey.getText().toString();

                if(jenis_kriptografi_dipilih.equals("Polyalphabetic Cipher")) {
                    PolyalphabeticCipher polyalphabeticCipher = new PolyalphabeticCipher();
                    textCipher.setText(polyalphabeticCipher.encrypt(plain_text, key));
                    System.out.println("-----------------------------------" + polyalphabeticCipher.encrypt(plain_text, key));
                } else {
                    BlockCipher blockCipher = new BlockCipher();
                    textCipher.setText(blockCipher.encrypt(plain_text, key));
                    System.out.println("-----------------------------------" + blockCipher.encrypt(plain_text, key));
                }
                //Toast.makeText(getApplicationContext(), "Encrypted", Toast.LENGTH_LONG).show();
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
}