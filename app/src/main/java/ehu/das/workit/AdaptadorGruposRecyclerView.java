package ehu.das.workit;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.ActivityNavigatorDestinationBuilder;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Esta clase proporciona un adaptador para los grupos (rutinas y ejercicios), guardando el nombre y la imagen de cada uno.
 */
public class AdaptadorGruposRecyclerView extends RecyclerView.Adapter<GroupViewHolder> {

    private List<String> losnombres;
    private List<Integer> lasimagenes;
    private String tipo;

    public AdaptadorGruposRecyclerView(List nombres, List imagenes, String tipo_grupo) {
        losnombres = nombres;
        lasimagenes = imagenes;
        tipo = tipo_grupo;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se selecciona el layout creado para cada elemento
        View elLayoutDeCadaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_element, null);
        return new GroupViewHolder(elLayoutDeCadaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        // Se establece el texto y la imagen correspondiente a cada grupo
        holder.eltexto.setText((String) losnombres.get(position));
        holder.laimagen.setImageResource(lasimagenes.get(position));
        // Se pone un listener para que al clickar el grupo se habra la ventana correspondiente dependiendo de si es una rutina o un ejercicio
        if (this.tipo.equals("rutina")) {
            holder.laimagen.setOnClickListener(v -> abrirRutina(v, String.valueOf(losnombres.get(position))));
        } else {
            holder.laimagen.setOnClickListener(v -> abrirEjercicio(v, String.valueOf(losnombres.get(position))));
        }

    }

    public void abrirRutina(View v, String nombre) {
        // Abre la ventana de añadir rutina
        MainActivity.add = true;
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombre);
        Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_addRutinaFragment, bundle);
    }

    public void abrirEjercicio(View v, String nombre) {
        // Abre la ventana de añadir ejercicio
        MainActivity.add = true;
        Bundle bundle = new Bundle();
        bundle.putString("nombre", nombre);
        Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_addElementoFragment, bundle);
    }

    @Override
    public int getItemCount() {
        // Número de grupos en el adaptador
        return losnombres.size();
    }
}
