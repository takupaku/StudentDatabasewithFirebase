package com.example.student.studentdatabasewithfirebase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.student.studentdatabasewithfirebase.R;
import com.example.student.studentdatabasewithfirebase.model.Student;

import java.util.List;

public class StudentAdapter extends BaseAdapter {


    private Context context;
    private List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  layoutInflater.inflate(R.layout.student_layout,null);

        }

        TextView name =view.findViewById(R.id._stdName);
        TextView id =view.findViewById(R.id._stdId);
        TextView dept =view.findViewById(R.id._stdDept);

        name.setText(students.get(i).getName());
        id.setText(students.get(i).getId());
        dept.setText(students.get(i).getDept());


        return view;
    }
}
