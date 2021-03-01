package ehu.das.workit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptadorRecyclerView extends RecyclerView.Adapter<GroupViewHolder> {

        private String[] losnombres;
        private int[] lasimagenes;

        public AdaptadorRecyclerView(String[] nombres, int[] imagenes)
        {
            losnombres=nombres;
            lasimagenes=imagenes;
        }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View elLayoutDeCadaItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_element,null);
        return new GroupViewHolder(elLayoutDeCadaItem);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        holder.eltexto.setText(losnombres[position]);
        holder.laimagen.setImageResource(lasimagenes[position]);
    }

    @Override
    public int getItemCount() {
        return losnombres.length;
    }
}
