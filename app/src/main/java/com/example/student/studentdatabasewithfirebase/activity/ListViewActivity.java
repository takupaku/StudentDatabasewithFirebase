package com.example.student.studentdatabasewithfirebase.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.studentdatabasewithfirebase.R;
import com.example.student.studentdatabasewithfirebase.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference reference;
    private List<Student>  studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        initView();
        initVariable();
        loadData();
    }

    private void loadData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> allStudents = dataSnapshot.getChildren();
                for(DataSnapshot student : allStudents){
                   Student std1 = student.getValue(Student.class);
                   studentList.add(std1);
                }


                Toast.makeText(ListViewActivity.this, ""+studentList.get(0).getName(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e("read_error",databaseError.getMessage());//read_error is the given name of the error that the logCat will show
            }
        });
    }

    private void initVariable() {
        reference = FirebaseDatabase.getInstance().getReference("student_database");
    }

    private void initView() {
        listView = findViewById(R.id.ivLd);
    }
}
