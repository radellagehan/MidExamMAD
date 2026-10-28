package com.example.midexamproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText txtStudentId, txtStudentName, txtStudentAge;
    private Button btnAdd, btnView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStudentId = findViewById(R.id.txtStudentId);
        txtStudentName = findViewById(R.id.txtStudentName);
        txtStudentAge = findViewById(R.id.txtStudentAge);
        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);

        dbHelper = new DatabaseHelper(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtStudentId.getText().toString().trim();
                String name = txtStudentName.getText().toString().trim();
                String ageStr = txtStudentAge.getText().toString().trim();

                if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr)) {
                    Toast.makeText(MainActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageStr);
                if (age < 17 || age > 50) {
                    Toast.makeText(MainActivity.this, "Age must be between 17 and 50 years old!", Toast.LENGTH_SHORT).show();
                    return;
                }

                StudentModel student = new StudentModel(id, name, age);
                boolean success = dbHelper.addStudent(student);

                if (success) {
                    Toast.makeText(MainActivity.this, "Data added successfully!", Toast.LENGTH_SHORT).show();
                    txtStudentId.setText("");
                    txtStudentName.setText("");
                    txtStudentAge.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Failed to add data (ID already exists)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewStudentActivity.class);
                startActivity(intent);
            }
        });
    }
}