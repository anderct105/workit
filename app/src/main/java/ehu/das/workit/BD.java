package ehu.das.workit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper {

    public BD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios ('id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "'nombre' VARCHAR(255), 'apellidos' VARCHAR(350), 'contrasena' VARCHAR(255), 'email' VARCHAR(255))");
        db.execSQL("INSERT INTO usuarios ('nombre', 'apellidos', 'contrasena', 'email') VALUES ('invitado', 'invitado invitado', 'invitado', 'invitado@gmail.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
