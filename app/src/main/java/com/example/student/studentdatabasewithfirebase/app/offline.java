package com.example.student.studentdatabasewithfirebase.app;

import android.app.Application;//adding offline capability

import com.google.firebase.database.FirebaseDatabase;

public class offline extends Application {
    @Override
    public void onCreate() {//overriding onCreate
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
