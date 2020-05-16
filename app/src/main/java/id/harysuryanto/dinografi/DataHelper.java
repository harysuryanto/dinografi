package id.harysuryanto.dinografi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;


public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dinografi.db";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "CREATE TABLE data(id_data INTEGER PRIMARY KEY AUTOINCREMENT, nama text NULL, keterangan text NULL, jenis_kriptografi text NULL, plain text NULL, plain_in_binary text NULL, key text NULL, cipher text NULL, cipher_in_binary text NULL);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS data");

        onCreate(db);

        // Toast.makeText(getApplicationContext(), "DataHelper: onUpgrade", Toast.LENGTH_SHORT).show();
    }

}