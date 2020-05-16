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

import id.harysuryanto.dinografi.oop.BlockCipher;
import id.harysuryanto.dinografi.oop.PolyalphabeticCipher;

public class DecryptActivity extends AppCompatActivity {
    DataHelper myDb;

    Dialog dialogSave;


    /*TextView textTitle, textSubTitle, textLogo;
    LinearLayout llEncrypt, llDecrypt, llSavedCryptography;*/
    EditText textCipher, textKey;
    TextView textPlainInBinary, textCipherInBinary, textPlain;
    Button buttonSave, buttonDecrypt, buttonCopy;

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
        /*textPlainInBinary = (TextView) findViewById(R.id.textPlainInBinary);
        textCipherInBinary = (TextView) findViewById(R.id.textCipherInBinary);*/
        buttonDecrypt = (Button) findViewById(R.id.buttonDecrypt);
        buttonCopy = (Button) findViewById(R.id.buttonCopy);


        spinnerJenisKriptografi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object jenis_kriptografi = parent.getSelectedItem().toString();
                jenis_kriptografi_dipilih = parent.getSelectedItem().toString();

                /*Toast.makeText(getApplicationContext(), jenis_kriptografi + " is selected", Toast.LENGTH_LONG).show();

                /*if(!jenis_kriptografi.equals("Block Cipher")) {
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


        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cipher_text, key;

                cipher_text = textCipher.getText().toString();
                key = textKey.getText().toString();

                if(jenis_kriptografi_dipilih.equals("Polyalphabetic Cipher")) {
                    PolyalphabeticCipher polyalphabeticCipher = new PolyalphabeticCipher();
                    textPlain.setText(polyalphabeticCipher.decrypt(cipher_text, key));
                } else {
                    BlockCipher blockCipher = new BlockCipher();
                    textPlain.setText(blockCipher.decrypt(cipher_text, key));
                }
                //Toast.makeText(getApplicationContext(), "Decrypted", Toast.LENGTH_LONG).show();
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
}