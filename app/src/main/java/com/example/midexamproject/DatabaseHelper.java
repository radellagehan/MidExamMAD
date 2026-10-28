package com.example.midexamproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";

    private static final String COLUMN_ID = "student_id";
    private static final String COLUMN_NAME = "student_name";
    private static final String COLUMN_AGE = "student_age";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + " ("
                + COLUMN_ID + " TEXT PRIMARY KEY, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AGE + " INTEGER)";
        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public boolean addStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, student.getStudentId());
        values.put(COLUMN_NAME, student.getStudentName());
        values.put(COLUMN_AGE, student.getStudentAge());

        long result = db.insert(TABLE_STUDENTS, null, values);
        db.close();
        return result != -1;
    }

    public List<StudentModel> getAllStudents() {
        List<StudentModel> studentList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StudentModel student = new StudentModel();
                student.setStudentId(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                student.setStudentName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                student.setStudentAge(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)));
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return studentList;
    }

    public boolean updateStudent(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getStudentName());
        values.put(COLUMN_AGE, student.getStudentAge());

        int rows = db.update(TABLE_STUDENTS, values, COLUMN_ID + " = ?", new String[]{student.getStudentId()});
        db.close();
        return rows > 0;
    }

    public boolean deleteStudent(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_STUDENTS, COLUMN_ID + " = ?", new String[]{studentId});
        db.close();
        return rows > 0;
    }
}