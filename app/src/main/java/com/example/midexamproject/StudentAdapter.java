package com.example.midexamproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class StudentAdapter extends ArrayAdapter<StudentModel> {
    public StudentAdapter(Context context, List<StudentModel> students) {
        super(context, 0, students);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_item, parent, false);
        }

        TextView txtStudentId = convertView.findViewById(R.id.txtStudentId);
        TextView txtStudentName = convertView.findViewById(R.id.txtStudentName);
        TextView txtStudentAge = convertView.findViewById(R.id.txtStudentAge);

        StudentModel student = getItem(position);
        if (student != null) {
            txtStudentId.setText("ID: " + student.getStudentId());
            txtStudentName.setText("Name: " + student.getStudentName());
            txtStudentAge.setText("Age: " + student.getStudentAge());
        }

        return convertView;
    }
}