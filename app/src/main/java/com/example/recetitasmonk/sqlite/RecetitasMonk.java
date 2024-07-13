package com.example.recetitasmonk.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class RecetitasMonk extends SQLiteOpenHelper {
    private static final String nameDB = "RecetitasMonk.db";
    private static final int versionBD = 4;
    private static final String createTableUsuario = "create table if not exists Usuario (id integer, correo varchar(255), clave varchar(255));";
    private static final String createTablePublicacion = "create table if not exists Publicacion (id INTEGER PRIMARY KEY AUTOINCREMENT,nombrepl varchar(255),ingredientes varchar(255),preparacion varchar(255));";
    private static final String createTableHistorial = "CREATE TABLE IF NOT EXISTS Historial (id INTEGER PRIMARY KEY AUTOINCREMENT, terminoBusqueda VARCHAR(255), fecha TEXT)";

    private static final String dropTableHistorial = "drop table if exists Historial";
    private static final String dropTableUsuario = "drop table if exists Usuario";
    private static final String dropTablePublicacion = "drop table if exists Publicacion";
    public RecetitasMonk(@Nullable Context context) {
        super(context, nameDB, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableUsuario);
        db.execSQL(createTablePublicacion);
        db.execSQL(createTableHistorial);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropTableUsuario);
        db.execSQL(createTableUsuario);

        db.execSQL(dropTablePublicacion);
        db.execSQL(createTablePublicacion);

        db.execSQL(dropTableHistorial);
        db.execSQL(createTableHistorial);

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
    public boolean recordoSesion(){
        SQLiteDatabase db = getReadableDatabase();
        if(db != null){
            Cursor cursor = db.rawQuery("select id from usuario;", null);
            if(cursor.moveToNext())
                return true;
        }
        return false;
    }

    public String getValue(String campo){
        SQLiteDatabase db = getReadableDatabase();
        String consulta = String.format("select %s from usuario", campo);
        if(db != null){
            Cursor cursor = db.rawQuery(consulta, null);
            if(cursor.moveToNext())
                return cursor.getString(0);
        }
        return null;
    }
    public boolean eliminarUsuario(int id) {
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            db.execSQL("delete from usuario where id = "+id+";");
            db.close();
            return true;
        }
        return false;
    }

    public boolean actualizarDatos(int id, String llave, String valor){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            db.execSQL("update Usuario set "+llave+" = '"+valor+"' where id = "+id+";");
            db.close();
            return true;
        }
        return false;
    }
    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    public boolean agregarHistorial(String terminoBusqueda) {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("terminoBusqueda", terminoBusqueda);
            values.put("fecha", getCurrentDate());
            long result = db.insert("Historial", null, values);
            db.close();
            Log.d("RecetitasMonk", "Historial agregado: " + terminoBusqueda);  // Agrega este log
            return result != -1;  // Retorna true si la inserción fue exitosa
        }
        return false;
    }
    public boolean eliminarTablaHistorial() {
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            db.execSQL("drop table if exists Historial");
            db.close();
            return true;
        }
        return false;
    }
}