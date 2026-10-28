package com.example.midexamproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ViewStudentActivity extends AppCompatActivity {
    private ListView listStudent;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        listStudent = findViewById(R.id.listStudent);
        dbHelper = new DatabaseHelper(this);

        loadStudentData();

        listStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StudentModel selectedItem = (StudentModel) parent.getItemAtPosition(position);
                Intent intent = new Intent(ViewStudentActivity.this, StudentActivity.class);
                intent.putExtra("studentId", selectedItem.getStudentId());
                intent.putExtra("studentName", selectedItem.getStudentName());
                intent.putExtra("studentAge", selectedItem.getStudentAge());
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadStudentData() {
        List<StudentModel> stdList = dbHelper.getAllStudents();
        StudentAdapter stdAdapter = new StudentAdapter(this, stdList);
        listStudent.setAdapter(stdAdapter);
    }
}