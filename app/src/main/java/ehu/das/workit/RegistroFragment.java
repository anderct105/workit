package ehu.das.workit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Permite registrar un nuevo usuario
 */
public class RegistroFragment extends Fragment  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Establece listeners
        TextView volverLogin = getActivity().findViewById(R.id.registro_a_inicio_sesion);
        volverLogin.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.action_registroFragment_to_loginFragment));
        Button registrarse = getActivity().findViewById(R.id.registrar);
        registrarse.setOnClickListener(v -> registrar());
    }

    public void registrar() {
        // Lee y valida los campos y en caso correcto los añade
        EditText campoNombre = getActivity().findViewById(R.id.usuarioRegistro);
        EditText campoEmail = getActivity().findViewById(R.id.emailRegistro);
        EditText campoContra = getActivity().findViewById(R.id.contraseñaRegistro);
        EditText campoRepitaContra = getActivity().findViewById(R.id.repiteContraseñaRegistro);
        String nombre = campoNombre.getText().toString().trim();
        String email = campoEmail.getText().toString().trim();
        String contra = campoContra.getText().toString().trim();
        String repitaContra = campoRepitaContra.getText().toString().trim();
        if (formularioValido(nombre, email, contra, repitaContra)) {
            if (!existeUsuario(campoNombre.getText().toString(), campoEmail.getText().toString())) {
                if (contra.equals(repitaContra)) {
                    registrarUsuario(nombre, email, contra);
                    Navigation.findNavController(getView()).navigate(R.id.action_registroFragment_to_loginFragment);
                    Toast.makeText(getContext(),  getResources().getString(R.string.usuarioRegistrado), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.contraDiferente), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.usuarioOEmail), Toast.LENGTH_LONG).show();
            }
        }
        else {
            campoContra.setText("");
            campoRepitaContra.setText("");
        }
    }

    public boolean existeUsuario(String nombre, String email) {
        // Devuelve true si el usuario ya estaba creado
        BD db = new BD(getContext(), "workit", null, 1);
        SQLiteDatabase bd = db.getReadableDatabase();
        Cursor query = bd.rawQuery("SELECT nombre FROM usuario WHERE nombre='" + nombre + "' OR email='" + email + "'", null);
        boolean existe = query.getCount() == 1;
        query.close();
        bd.close();
        return existe;
    }

    private void registrarUsuario(String nombre, String email, String contrasena) {
        // Registra el usuario con los datos pasados
        BD db = new BD(getContext(), "workit", null, 1);
        SQLiteDatabase bd = db.getWritableDatabase();
        bd.execSQL("INSERT INTO usuario ('nombre', 'contrasena', 'email') VALUES ('" + nombre + "', '" + contrasena + "', '" + email + "')");
        bd.close();
    }

    private boolean formularioValido(String nombre, String email, String contrasena, String repitacontrasena) {
        // Valida el formulario y en caso de algun fallo muestra un toast
        boolean valido = true;
        if (nombre.equals("") || email.equals("") || contrasena.equals("") || repitacontrasena.equals("")) {
            valido = false;
            Toast.makeText(getContext(),  getResources().getString(R.string.camposVacios), Toast.LENGTH_LONG).show();
        }
        else if (contrasena.length() < 6 || contrasena.length() > 20) {
            valido = false;
            Toast.makeText(getContext(),   getResources().getString(R.string.contraCorta), Toast.LENGTH_LONG).show();
        }
        else if (!email.contains("@")) {
            valido = false;
            Toast.makeText(getContext(),   getResources().getString(R.string.emailNoValido), Toast.LENGTH_LONG).show();
        }
        return valido;
    }


}