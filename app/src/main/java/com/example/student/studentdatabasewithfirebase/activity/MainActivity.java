package com.example.student.studentdatabasewithfirebase.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.student.studentdatabasewithfirebase.R;
import com.example.student.studentdatabasewithfirebase.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView etName, etId;
    private Spinner spDept;
    private DatabaseReference reference;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVariable();
    }

    private void initVariable() {
        reference = FirebaseDatabase.getInstance().getReference("student database");
        reference.keepSynced(true);//stored stuff saving in offline
    }


    private void initView() {
        etName = findViewById(R.id.stdName);
        etId = findViewById(R.id.stdId);
        spDept = findViewById(R.id.spinnerDept);
        textView = findViewById(R.id.searchResultId);

    }

    public void savaData(View view) {

        String name, id, dept;
        name = etName.getText().toString().trim();
        id = etId.getText().toString().trim();
        dept = spDept.getSelectedItem().toString();

        if (name.isEmpty()) {
            etName.setError("plz enter name");
            etName.requestFocus();
            return;
        }

        if (id.isEmpty()) {
            etId.setError("plz enter id");
            etId.requestFocus();
            return;
        }

        String user_id = reference.push().getKey();
        reference.child(user_id).setValue(new Student(id, name, dept, user_id)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void viewData(View view) {
        Intent intent = new Intent(this, ListViewActivity.class);
        startActivity(intent);

    }

    //search id
    public void searchBtn(View view) {
        //search by id
        //select * from student_table where id = ?;
        final StringBuilder builder = new StringBuilder();

        Query query = reference.orderByChild("id").equalTo(etId.getText().toString().trim());
        //Query query = reference.orderByChild("name").equalTo(etName.getText().toString().trim()).limitToFirst(2);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    textView.setText("no data");
                    return;
                }
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    Student student = child.getValue(Student.class);
                    builder.append(student.getName()).append(" , ").append(student.getDept()).append("\n\n");
                    //textView.setText(student.getName()+" , "+student.getDept());
                }
                textView.setText(builder);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
