package com.example.asm2_tkgd.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KhoanThuChiDB extends SQLiteOpenHelper {

    public KhoanThuChiDB(Context context) {
        super(context, "KHOANTHUCHIDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String qLoai = "CREATE TABLE LOAI(maloai integer primary key autoincrement, tenloai text, trangthai text)";
        sqLiteDatabase.execSQL(qLoai);

        String qKhoan = "CREATE TABLE KHOANTHUCHI(makhoan integer primary key autoincrement, tien integer, maloai integer)";
        sqLiteDatabase.execSQL(qKhoan);

        // data mẫu
        String ins1 = "INSERT INTO loai VALUES(1, 'tiền xăng', 'chi'),(2,'tiền lương', 'thu'),(3, 'tiền ăn sáng', 'chi')";
        sqLiteDatabase.execSQL(ins1);
        String ins2 = "INSERT INTO khoanthuchi VALUES (1,5000, 2),(2, 15000, 3),(3, 1000, 1)";
        sqLiteDatabase.execSQL(ins2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i != i1) {
            String dLoai = "DROP TABLE IF EXISTS LOAI";
            sqLiteDatabase.execSQL(dLoai);
            String dKhoan = "DROP TABLE IF EXISTS KHOANTHUCHI";
            sqLiteDatabase.execSQL(dKhoan);
            onCreate(sqLiteDatabase);
        }
    }
}
