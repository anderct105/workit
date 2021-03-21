package ehu.das.workit;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador para con la información necesaria para poder seleccionar los ejecicios que tendrá una rutina.
 */
public class AdaptadorRutinaRecyclerView extends RecyclerView.Adapter<RutinaViewHolder> {

    public List<String> nombres;
    public List<Integer> imagenes;
    public List<Integer> series;
    public List<Boolean> seleccionado;
    public List<List<Integer>> pesos;
    private boolean cargar;

    public AdaptadorRutinaRecyclerView(List<String> nombres, List<Integer> imagenes, List<Integer> series, List<Boolean> seleccionado, List<List<Integer>> pesos, boolean cargar) {
        this.nombres = nombres;
        this.imagenes = imagenes;
        this.series = series;
        this.seleccionado = seleccionado;
        this.pesos = pesos;
        // Este boolean indica si se está creando desde cero o se están leyendo los datos de una rutina
        this.cargar = cargar;
    }

    @NonNull
    @Override
    public RutinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Se establece el layout para cada elemento del adaptador
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.nueva_rutina, null);
        return new RutinaViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RutinaViewHolder holder, int position) {
        // Se añade la información a cada elemento del adaptador
        EditText[] lista_series = {holder.serie1, holder.serie2, holder.serie3, holder.serie4, holder.serie5};
        holder.seleccionarEjercicio.setChecked(seleccionado.get(position));
        holder.seleccionarEjercicio.setOnClickListener(v -> {
            seleccionado.set(position, holder.seleccionarEjercicio.isChecked());
        });
        for (int i = 0; i < lista_series.length; i++) {
            try {
                lista_series[i].setText(pesos.get(position).get(i).toString());
            } catch (Exception e) {
                lista_series[i].setText("0");
            }
            lista_series[i].addTextChangedListener(getTextWatch(position, i));
        }
        // Quitamos la opción de seleccionar en caso de que se cargue una rutina
        if (cargar) {
            holder.seleccionarEjercicio.setVisibility(View.GONE);
        }

        holder.numSeries.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Se modifica el valor del peso en el adaptador
                if (!s.toString().equals("")) {
                    series.set(position, Integer.parseInt(String.valueOf(s)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        holder.nombreEjercicio.setText(nombres.get(position));
        holder.numSeries.setText(series.get(position).toString());
        esconder_repeticiones(lista_series, series.get(position));
        holder.numSeries.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Se esconden aquellos campos que estén por encima del número de series
            int i = 1;
            if (!holder.numSeries.getText().toString().equals("")) {
                i = Integer.parseInt(holder.numSeries.getText().toString());
                if (!(i <= lista_series.length && i > 0)) {
                    i = 1;
                }
            }
            esconder_repeticiones(lista_series, i);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        }

        public void esconder_repeticiones(EditText[] lista_series, int i) {
            for (int k = 0; k < i; k++) {
                lista_series[k].setVisibility(View.VISIBLE);
            }
            for (int j = i; j < 5; j++) {
                lista_series[j].setVisibility(View.GONE);
            }
        }

    @Override
    public int getItemCount() {
        return nombres.size();
    }

    public TextWatcher getTextWatch(int position, int peso) {
        // Devuelve listener para cuando cambian los valores del ejercicio seleccionado
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals("")) {
                    pesos.get(position).set(peso, Integer.parseInt(String.valueOf(s)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
    }
}
