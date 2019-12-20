package com.ics.admin;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ics.admin.BasicAdmin.AdminActivity;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText edt_name,edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        login=(Button)findViewById(R.id.login);
        edt_name=(EditText)findViewById(R.id.edt_name);
        edtPassword=(EditText)findViewById(R.id.edt_password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
