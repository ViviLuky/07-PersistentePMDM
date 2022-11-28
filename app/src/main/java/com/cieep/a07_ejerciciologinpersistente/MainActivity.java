package com.cieep.a07_ejerciciologinpersistente;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin;

    private SharedPreferences spLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaVistas();


        spLogin = getSharedPreferences(Constantes.LOGIN_PERSISTENTE, MODE_PRIVATE);

        if (spLogin.contains(Constantes.EMAIL_USER) && spLogin.contains(Constantes.PASSWORD_USER)) {
            startActivity(new Intent(MainActivity.this, UserActivity.class));
            finish();
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtEmail.getText().toString().equalsIgnoreCase(Constantes.EMAIL)
                    && txtPassword.getText().toString().equals(Constantes.PASSWORD)) {
                    Toast.makeText(MainActivity.this, "Login Correcto", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, UserActivity.class));
                    SharedPreferences.Editor editor = spLogin.edit();
                    editor.putString(Constantes.EMAIL_USER, txtEmail.getText().toString());
                    editor.putString(Constantes.PASSWORD_USER, Constantes.codificaPassword(txtPassword.getText().toString()));
                    editor.apply();
                }
            }
        });
    }

    private void inicializaVistas() {
        txtEmail = findViewById(R.id.txtEmailMain);
        txtPassword = findViewById(R.id.txtPasswordMain);
        btnLogin = findViewById(R.id.btnLoginMain);
    }
}