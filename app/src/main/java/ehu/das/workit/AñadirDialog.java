package ehu.das.workit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

public class AñadirDialog extends DialogFragment {

    private View m;

    public AñadirDialog(View m) {
        this.m = m;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setTitle("Añadir");
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View elaspecto = inflater.inflate(R.layout.crear_ejercicio_rutina, null);
        TextView et1 = elaspecto.findViewById(R.id.etiqueta1);
        et1.setText("Ejercicios");
        ImageButton cv = elaspecto.findViewById(R.id.imgAñadirEjercicio);
        cv.setOnClickListener(v -> {
            dismiss();
            Navigation.findNavController(m).navigate(R.id.action_menuFragment_to_addElementoFragment);
        });
        ad.setView(elaspecto);
        return ad.create();
    }
}
