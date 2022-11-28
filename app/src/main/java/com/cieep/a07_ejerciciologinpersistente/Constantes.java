package com.cieep.a07_ejerciciologinpersistente;

public class Constantes {



    public static final String EMAIL = "algo@servidor.com";
    public static final String PASSWORD = "123456654321";

    public static final String LOGIN_PERSISTENTE = "login_persistente";
    public static final String DATOS_PERSISTENCIA = "datos";


    public static final String EMAIL_USER = "email";
    public static final String PASSWORD_USER = "password";
    public static final String DATOS = "Datos";


    public static String codificaPassword (String password) {
        StringBuilder codificado = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            codificado.append((char)(password.charAt(i) + 1));
        }

        return codificado.toString();
    }

    public static String decodificaPassword (String password) {
        StringBuilder codificado = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            codificado.append((char)(password.charAt(i) - 1));
        }

        return codificado.toString();
    }

}
