package ehu.das.workit;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    // Id del usuario que ha iniciado sesión
    public static int usuario = -1;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Si ya estaba logueado directamente ir al menú
        boolean saltoHecho = false;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (getActivity().getIntent().getExtras() != null) {
            if (getActivity().getIntent().getExtras().getBoolean("logueado")) {
                saltoHecho = true;
                Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_menuFragment);
            }
        }
        // Si el usuario quiere tener la sesión iniciada
        if (!saltoHecho && sharedPreferences.getBoolean("switch_preference_1", false)) {
            usuario = sharedPreferences.getInt("usuario", -1);
            Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_menuFragment);
        }
        Button login = getActivity().findViewById(R.id.login);
        TextView registrarse = getActivity().findViewById(R.id.registrarse);
        registrarse.setOnClickListener(v -> {Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_registroFragment);});
        // Al iniciar sesión buscar en la base de datos si lo ha hecho bien
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD db = new BD(getContext(), "workit", null, 1);
                SQLiteDatabase bd = db.getReadableDatabase();
                EditText campoNombre = getActivity().findViewById(R.id.usuarioRegistro);
                String nombreUsuario = campoNombre.getText().toString();
                EditText campoContra = getActivity().findViewById(R.id.contraseñaRegistro);
                String contrasena = campoContra.getText().toString();
                Cursor res = bd.rawQuery("SELECT id FROM usuario WHERE (nombre='" + nombreUsuario + "' or email='" + nombreUsuario + "') and contrasena='" + contrasena + "'", null);
                // Si hay coincidencia, entrar
                if (res.getCount() == 1) {
                    res.moveToNext();
                    usuario = res.getInt(0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("usuario", usuario);
                    editor.apply();
                    editor.commit();
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_menuFragment);
                } else {
                    Toast toast = Toast.makeText(getContext(),"Login incorrecto",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                    toast.show();
                    campoContra.setText("");
                }
                res.close();
                bd.close();
            }
        });
    }


}