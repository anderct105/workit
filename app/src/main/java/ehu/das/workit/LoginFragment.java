package ehu.das.workit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {


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
        Button login = getActivity().findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BD db = new BD(getContext(), "workit", null, 1);
                SQLiteDatabase bd = db.getReadableDatabase();
                EditText campo_nombre = getActivity().findViewById(R.id.usuario_email);
                String usuario = campo_nombre.getText().toString();
                EditText campo_contra = getActivity().findViewById(R.id.contraseña);
                String contrasena = campo_contra.getText().toString();
                Cursor res = bd.rawQuery("SELECT id FROM usuarios WHERE nombre='" + usuario + "' and contrasena='" + contrasena + "'", null);
                if (res.getCount() == 1) {
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_menuFragment);
                } else {
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_menuFragment);

                    Toast toast = Toast.makeText(getContext(),"Login incorrecto",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                    toast.show();
                    campo_contra.setText("");
                }
                res.close();
                bd.close();
            }
        });
    }
}