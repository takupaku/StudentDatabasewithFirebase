package com.example.student.studentdatabasewithfirebase.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.student.studentdatabasewithfirebase.R;
import com.example.student.studentdatabasewithfirebase.adapter.StudentAdapter;
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


                //Toast.makeText(ListViewActivity.this, ""+studentList.get(0).getName(), Toast.LENGTH_SHORT).show();

                StudentAdapter adapter = new StudentAdapter(ListViewActivity.this,studentList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                showDialoge(i);

                return false;
            }
        });
    }

    private void showDialoge(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("WILL YOU EDIT THIS?");
        builder.setCancelable(false);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //DELETE STUDENT
                reference.child(studentList.get(pos).getUser_id()).removeValue();
            }
        });

        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //this will edit
            }
        });
        builder.setNeutralButton("cancel",null);
        builder.show();
    }
}
