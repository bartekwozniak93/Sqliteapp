package pl.wozniakbartlomiej.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Bartek on 24/08/16.
 */
public class MyDB {
    MyDBHelper DBHelper;
    SQLiteDatabase db;
    final Context context;

    public MyDB(Context ctx) {
        this.context = ctx;
        DBHelper = new MyDBHelper(this.context);
    }

    public MyDB open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertRecord(String name2_str, String name3_str) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MyDBHelper.columnName2, name2_str);
        initialValues.put(MyDBHelper.columnName3, name3_str);
        return db.insert(MyDBHelper.tableName, null, initialValues);
    }

    public int deleteRecord(long columnNameToBeDeleted) {
        return db.delete(MyDBHelper.tableName, MyDBHelper.columnName1 + "=" + columnNameToBeDeleted, null);
    }

    public void deleteAllRecords() {
        db.delete(MyDBHelper.tableName, null, null);
    }

    public long updateRecord(long name1_str, String name2_str, String name3_str) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(MyDBHelper.columnName2, name2_str);
        initialValues.put(MyDBHelper.columnName3, name3_str);
        return db.update(MyDBHelper.tableName, initialValues, MyDBHelper.columnName1 + "=" + name1_str, null);
    }

    public Cursor getAllRecords() {
        return db.query(
                MyDBHelper.tableName,
                new String[]{MyDBHelper.columnName1, MyDBHelper.columnName2, MyDBHelper.columnName3},
                null, null, null, null, null);
    }

    public Cursor getRecord(long id) {
        Cursor mCursor = db.query(MyDBHelper.tableName,
                new String[]{MyDBHelper.columnName1, MyDBHelper.columnName2, MyDBHelper.columnName3},
                MyDBHelper.columnName1 + "=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
