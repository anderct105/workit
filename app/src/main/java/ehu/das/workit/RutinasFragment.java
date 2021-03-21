package ehu.das.workit;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Ofrece un listado con las rutinas del usuario
 */
public class RutinasFragment extends Fragment implements IOnBackPressed{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rutinas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!MainActivity.add) {
            RecyclerView recyclerView = getActivity().findViewById(R.id.listaGruposRutinas);
            recyclerView.setAdapter(obtenerRutinasAdapter());
            // Establece un layout diferente en función de la orientación del movil
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // elLayoutRejillaIgual = new GridLayoutManager(getActivity(), 5, GridLayoutManager.VERTICAL, false);
                recyclerView.setPadding(0, 60, 0, 0);
                LinearLayoutManager elLayoutRejillaIgual = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(elLayoutRejillaIgual);
            } else {
                GridLayoutManager elLayoutRejillaIgual = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(elLayoutRejillaIgual);
            }
        }
    }

    public AdaptadorGruposRecyclerView obtenerRutinasAdapter() {
        // Crea un adaptador con la información de todas las rutinas en el
        BD db = new BD(getContext(), "workit", null, 1);
        SQLiteDatabase bd = db.getReadableDatabase();
        Cursor query = bd.rawQuery("SELECT nombre FROM rutina INNER JOIN rutina_ejercicio ON rutina.nombre=rutina_ejercicio.nombreR WHERE usuario=" + LoginFragment.usuario + " GROUP BY nombre", null);
        ArrayList<String> rutinas = new ArrayList<>();
        ArrayList<Integer> imagenes = new ArrayList<>();
        while (query.moveToNext()) {
            rutinas.add(query.getString(0));
            imagenes.add(R.drawable.punch);
        }
        query.close();
        bd.close();
        AdaptadorGruposRecyclerView adaptadorGruposRecyclerView = new AdaptadorGruposRecyclerView(rutinas, imagenes, "rutina");
        return adaptadorGruposRecyclerView;
    }

    @Override
    public boolean onBackPressed() {
        SalirDialog sd = new SalirDialog();
        sd.show(getActivity().getSupportFragmentManager(), "salir");
        return false;
    }
}