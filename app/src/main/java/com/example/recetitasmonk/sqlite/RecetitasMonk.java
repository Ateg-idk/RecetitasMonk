package com.example.recetitasmonk.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
public class RecetitasMonk extends SQLiteOpenHelper {
    private static final String nameDB = "RecetitasMonk.db";
    private static final int versionBD = 1;
    private static final String createTableUsuario = "create table if not exists Usuario (id integer, correo varchar(255), clave varchar(255));";
    private static final String createTablePublicacion = "create table if not exists Publicacion (id INTEGER PRIMARY KEY AUTOINCREMENT,nombrepl varchar(255),ingredientes varchar(255),preparacion varchar(255));";
    private static final String dropTableUsuario = "drop table if exists Usuario";
    private static final String dropTablePublicacion = "drop table if exists Publicacion";
    public RecetitasMonk(@Nullable Context context) {
        super(context, nameDB, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUsuario);
        db.execSQL(createTablePublicacion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableUsuario);
        db.execSQL(createTableUsuario);

        db.execSQL(dropTablePublicacion);
        db.execSQL(createTablePublicacion);

    }

    public boolean agregarUsuario(int id, String correo, String clave){
        SQLiteDatabase db = getWritableDatabase();
        if (db != null){
            db.execSQL("insert into Usuario values ("+id+", '"+correo+"', '"+clave+"');");
            db.close();
            return true;
        }
        return false;
    }

    public boolean publicarReceta(String nombrepl, String ingredientes, String preparacion){
        SQLiteDatabase db =getWritableDatabase();
        if (db != null){
            db.execSQL("insert into Publicacion (nombrepl, ingredientes, preparacion) values ('"+nombrepl+"','"+ingredientes+"','"+preparacion+"');");
            db.close();
            return true;
        }
        return  false;
    }
}
