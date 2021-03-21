package ehu.das.workit;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * View holder con la información de un ejercicio de una rutina
 */
public class RutinaViewHolder extends RecyclerView.ViewHolder {

    public TextView nombreEjercicio;
    public EditText numSeries;
    public CheckBox seleccionarEjercicio;
    public EditText serie1;
    public EditText serie2;
    public EditText serie3;
    public EditText serie4;
    public EditText serie5;

    public RutinaViewHolder(@NonNull View itemView) {
        super(itemView);
        nombreEjercicio = itemView.findViewById(R.id.nombreEjercicioRutina);
        numSeries = itemView.findViewById(R.id.numSeries);
        seleccionarEjercicio = itemView.findViewById(R.id.seleccionarEjercicio);
        serie1 = itemView.findViewById(R.id.serie1);
        serie2 = itemView.findViewById(R.id.serie2);
        serie3 = itemView.findViewById(R.id.serie3);
        serie4 = itemView.findViewById(R.id.serie4);
        serie5 = itemView.findViewById(R.id.serie5);
    }

}
