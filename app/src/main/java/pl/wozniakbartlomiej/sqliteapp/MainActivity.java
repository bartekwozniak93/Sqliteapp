package pl.wozniakbartlomiej.sqliteapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private MyDB db;
    private TextView scrollBarViewText;
    private EditText furnitureText;
    private EditText classText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeDataBase();
        initializeViewItems();
        updateAllRecords();
        setListeners();
    }

    private void initializeDataBase() {
        db = new MyDB(this);
    }

    private void initializeViewItems() {
        furnitureText = (EditText) findViewById(R.id.furnitureText);
        classText = (EditText) findViewById(R.id.classText);
        scrollBarViewText = (TextView) findViewById(R.id.scrollBarView);
        scrollBarViewText.setMovementMethod(new ScrollingMovementMethod());
    }


    private void updateAllRecords() {
        String text = "";
        ArrayList<String[]> myRecords = GetAllRecords();
        for (int i = 0; i < myRecords.size(); i++) {
            String[] myRecord = myRecords.get(i);
            text += myRecord[0] + " " + myRecord[1] + " " + myRecord[2] + "\n";
        }
        scrollBarViewText.setText(text);
    }

    private void setListeners() {
        furnitureText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });

        classText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideSoftKeyboard(v);
                }
            }
        });
    }


    private ArrayList<String[]> GetAllRecords() {
        db.open();
        Cursor c = db.getAllRecords();
        ArrayList<String[]> records = new ArrayList<String[]>();
        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1), c.getString(2)};
                records.add(record);
            } while (c.moveToNext());
        }

        db.close();
        return records;
    }

    public void addRecord(View view) {
        String _furnitureText = furnitureText.getText().toString();
        String _classText = classText.getText().toString();
        if (_furnitureText.isEmpty() || _classText.isEmpty()) {
            Toast.makeText(getApplicationContext(), _furnitureText.isEmpty() ? "Fill furniture field." : "Fill class field.", Toast.LENGTH_LONG).show();
        } else {
            InsertRecord(_furnitureText, _classText);
            furnitureText.setText("");
            classText.setText("");
            updateAllRecords();
        }
    }

    public void deleteAllRecords(View view) {
        db.open();
        db.deleteAllRecords();
        db.close();
        updateAllRecords();
    }

    public String[] InsertRecord(String name2_str, String name3_str) {
        db.open();
        db.insertRecord(name2_str, name3_str);
// insertRecord is a user-defined method in MyDB that will call db.insert ()
        db.close();
        return new String[]{name2_str, name3_str};
    }


    private void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    //Lose focus after click outside EditText
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


}
