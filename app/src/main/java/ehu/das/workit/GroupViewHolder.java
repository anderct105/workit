package ehu.das.workit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GroupViewHolder extends RecyclerView.ViewHolder {
    public TextView eltexto;
    public ImageView laimagen;

    public GroupViewHolder(@NonNull View itemView){
        super(itemView);
        eltexto = itemView.findViewById(R.id.nombreGrupo);
        laimagen = itemView.findViewById(R.id.fotoGrupo);
    }


}
