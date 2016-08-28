package pl.wozniakbartlomiej.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    MyDB db;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new MyDB(this);
        ArrayList<String[]> myRecords = GetAllRecords();
        for (int i = 0; i < myRecords.size(); i++) {
            String[] myRecord = myRecords.get(i);
            // columnName1, columnName2, columnName3 are stored in
            //myRecord[0],myRecord[1], myRecord[2]respectively
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView)findViewById(R.id.editText3);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        String s = "";
        for(int x=0;x<=100;x++){
            s += "Line: "+String.valueOf(x)+"\n";
        }
        tv1.setText(s);
    }

    public ArrayList<String[]> GetAllRecords() {
        db.open();
        Cursor c = db.getAllRecords();
        ArrayList<String[]> records = new ArrayList<String[]>();
        if (c.moveToFirst()){ do{
            String[] record = {c.getString(0),c.getString(1),c.getString(2)};
            records.add(record);
        } while (c.moveToNext());
        }
        db.close(); return records;
    }
}
