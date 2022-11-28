package com.cieep.a07_ejerciciologinpersistente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cieep.a07_ejerciciologinpersistente.modelo.ContactoMatricula;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private SharedPreferences spUser;
    private SharedPreferences spDatos;

    private List<ContactoMatricula> contactos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        contactos = new ArrayList<>();


        Button btnLogout = findViewById(R.id.btnLogoutUser);
        Button btnGuardar=findViewById(R.id.btnGuardarUser);
        Button btnCargar=findViewById(R.id.btnCargardatos);
        TextView txtEmail = findViewById(R.id.lblEmailUser);
        TextView txtPassword = findViewById(R.id.lblPasswordUser);
        TextView txtCantidad=findViewById(R.id.lblCantidad);
        txtCantidad.setText("tenemos "+contactos.size()+" contactos");


        spUser = getSharedPreferences(Constantes.LOGIN_PERSISTENTE, MODE_PRIVATE);
        txtEmail.setText(spUser.getString(Constantes.EMAIL_USER, ""));
        txtPassword.setText(Constantes.decodificaPassword(spUser.getString(Constantes.PASSWORD_USER, "")));

        spDatos = getSharedPreferences(Constantes.DATOS_PERSISTENCIA, MODE_PRIVATE);

        if (!spDatos.contains(Constantes.DATOS)) {
            crearContactos();
        }
        else {
            String contactosJSON = spDatos.getString(Constantes.DATOS, "");
            // Con TypeToken genero la estructura del tipo de datos codificado en el String
            Type tipo  = new TypeToken< ArrayList<ContactoMatricula> >(){}.getType();
            List<ContactoMatricula> temp = new Gson().fromJson(contactosJSON,  tipo);
            contactos.addAll(temp);
            Toast.makeText(this, "Datos cargados desde las Preferencias", Toast.LENGTH_SHORT).show();
        }
        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Type tipo=new TypeToken <ArrayList<ContactoMatricula> > (){}.getType();
                ArrayList<ContactoMatricula> temp=new Gson().fromJson(spDatos.getString(Constantes.DATOS,"[]"),tipo);
                contactos.clear();
                contactos.addAll(temp);
                txtCantidad.setText("tenemos "+contactos.size()+" contactos");
            }
        });

        //Button btnGuardar = findViewById(R.id.btnGuardarUser);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = spDatos.edit();
                String datos = new Gson().toJson(contactos);
                Log.d("JSON", datos);
                editor.putString(Constantes.DATOS, datos);
                editor.apply();
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = spUser.edit();;
                editor.remove(Constantes.PASSWORD_USER);
                editor.remove(Constantes.EMAIL_USER);
                editor.apply();
                startActivity(new Intent(UserActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void crearContactos() {
        for (int i = 1; i < 11; i++) {
            contactos.add(new ContactoMatricula("Nombre "+i, "Ciclo "+i, "Email "+i, "Telefono "+i));
        }
    }
}