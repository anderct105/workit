package ehu.das.workit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * Conexión a la BD
 */
public class BD extends SQLiteOpenHelper {


    public BD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Inicializa las tablas necesarias para la persistencia de datos
        db.execSQL("CREATE TABLE usuario ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "'nombre' VARCHAR(255), 'apellidos' VARCHAR(350), 'contrasena' VARCHAR(255), 'email' VARCHAR(255)) ");
        db.execSQL("INSERT INTO usuario ('nombre', 'contrasena', 'email') VALUES ('invitado',  'invitado', 'invitado@gmail.com')");
        db.execSQL("CREATE TABLE ejercicio ('nombre' VARCHAR(255) PRIMARY KEY UNIQUE, 'descripcion' VARCHAR(255), 'pecho' BOOLEAN not null default 0, 'cuadriceps' BOOLEAN not null default 0," +
                "'abdominales' VARCHAR(255), 'hombros' BOOLEAN not null default 0, 'espalda' BOOLEAN not null default 0, 'gluteos' BOOLEAN not null default 0, 'triceps' BOOLEAN not null default 0," +
                "'biceps' BOOLEAN not null default 0, 'femoral' BOOLEAN not null default 0, 'cardio' BOOLEAN not null default 0, 'intensidad' INTEGER, 'dificultad' INTEGER)");
        db.execSQL("INSERT INTO ejercicio ('nombre', 'pecho', 'intensidad', 'dificultad') VALUES ('Dorsal sentado', 1, 'baja', 'media')");
        db.execSQL("INSERT INTO ejercicio ('nombre', 'pecho', 'intensidad', 'dificultad') VALUES ('Polea alta', 1, 'media', 'baja')");
        db.execSQL("INSERT INTO ejercicio ('nombre', 'cuadriceps', 'intensidad', 'dificultad') VALUES ('Extensión cuádriceps', 1, 'baja', 'baja')");
        db.execSQL("INSERT INTO ejercicio ('nombre', 'gluteos', 'intensidad', 'dificultad') VALUES ('Sentadillas con barra', 1, 'alta', 'alta')");
        db.execSQL("INSERT INTO ejercicio ('nombre', 'espalda', 'intensidad', 'dificultad') VALUES ('Peso muerto', 1, 'alta', 'alta')");
        db.execSQL("INSERT INTO ejercicio ('nombre', 'femoral', 'intensidad', 'dificultad') VALUES ('Curl femoral', 1, 'baja', 'baja')");
        db.execSQL("CREATE TABLE rutina ('nombre' VARCHAR(255) PRIMARY KEY)");
        db.execSQL("CREATE TABLE rutina_ejercicio ('usuario' INTEGER, 'nombreR' VARCHAR(255), 'nombreE' VARCHAR(255), 'series' INTEGER DEFAULT 1, 'peso1' INTEGER DEFAULT 0, 'peso2' INTEGER DEFAULT 0, 'peso3' INTEGER DEFAULT 0, 'peso4' INTEGER DEFAULT 0, 'peso5' INTEGER DEFAULT 0, " +
        "PRIMARY KEY ('usuario', 'nombreR', 'nombreE'), FOREIGN KEY ('usuario') REFERENCES usuario('id') ON DELETE CASCADE, FOREIGN KEY ('nombreR') REFERENCES rutina('nombre') ON DELETE CASCADE, FOREIGN KEY ('nombreE') REFERENCES ejercicio('nombre') ON DELETE CASCADE)");
    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        // Necesario para que funcionen las foreign keys
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
