package id.harysuryanto.dinografi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout llEncrypt, llDecrypt, llSavedCryptography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llEncrypt = (LinearLayout) findViewById(R.id.llEncrypt);
        llDecrypt = (LinearLayout) findViewById(R.id.llDecrypt);
        llSavedCryptography = (LinearLayout) findViewById(R.id.llSavedCryptography);

        llEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent encryptActivity = new Intent(getApplicationContext(), EncryptActivity.class);
                startActivity(encryptActivity);
            }
        });

        llDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent decryptActivity = new Intent(getApplicationContext(), DecryptActivity.class);
                startActivity(decryptActivity);
            }
        });

        llSavedCryptography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent savedCryptographyActivity = new Intent(getApplicationContext(), SavedCryptography.class);
                startActivity(savedCryptographyActivity);
            }
        });

    }
}
