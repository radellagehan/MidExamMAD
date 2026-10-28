package com.example.midexamproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    private TextView txtStudentId;
    private EditText editStudentName, editStudentAge;
    private Button btnEdit, btnDelete;
    private DatabaseHelper dbHelper;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        txtStudentId = findViewById(R.id.txtStudentId);
        editStudentName = findViewById(R.id.editStudentName);
        editStudentAge = findViewById(R.id.editStudentAge);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");
        String studentName = intent.getStringExtra("studentName");
        int studentAge = intent.getIntExtra("studentAge", 0);

        txtStudentId.setText("ID: " + studentId);
        editStudentName.setText(studentName);
        editStudentAge.setText(String.valueOf(studentAge));

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editStudentName.getText().toString().trim();
                String ageStr = editStudentAge.getText().toString().trim();

                if (TextUtils.isEmpty(newName) || TextUtils.isEmpty(ageStr)) {
                    Toast.makeText(StudentActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int newAge = Integer.parseInt(ageStr);
                if (newAge < 17 || newAge > 50) {
                    Toast.makeText(StudentActivity.this, "Age must be between 17 and 50 years old!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StudentModel updatedStudent = new StudentModel(studentId, newName, newAge);
                boolean success = dbHelper.updateStudent(updatedStudent);

                if (success) {
                    Toast.makeText(StudentActivity.this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
                    Intent viewIntent = new Intent(StudentActivity.this, ViewStudentActivity.class);
                    startActivity(viewIntent);
                    finish();
                } else {
                    Toast.makeText(StudentActivity.this, "Failed to update data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = dbHelper.deleteStudent(studentId);

                if (success) {
                    Toast.makeText(StudentActivity.this, "Data deleted successfully!", Toast.LENGTH_SHORT).show();
                    Intent viewIntent = new Intent(StudentActivity.this, ViewStudentActivity.class);
                    startActivity(viewIntent);
                    finish();
                } else {
                    Toast.makeText(StudentActivity.this, "Failed to delete data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}