package com.example.student.studentdatabasewithfirebase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView etName,etId;
    private Spinner spDept;
    private DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initVariable();
    }

    private void initVariable() {
        reference = FirebaseDatabase.getInstance().getReference("student database");
    }



    private void initView() {
        etName=findViewById(R.id.stdName);
        etId = findViewById(R.id.stdId);
        spDept= findViewById(R.id.spinnerDept);

    }

    public void savaData(View view) {

        String name, id, dept;
        name = etName.getText().toString().trim();
        id = etId.getText().toString().trim();
        dept =  spDept.getSelectedItem().toString();

        if(name.isEmpty()){
            etName.setError("plz enter name");
            etName.requestFocus();
            return;
        }

        if(id.isEmpty()){
            etId.setError("plz enter id");
            etId.requestFocus();
            return;
        }

        String user_id = reference.push().getKey();
        reference.child(user_id).setValue(new Student(id,name,dept,user_id)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });






    }
}
