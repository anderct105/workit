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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import java.util.List;

/**
 * Dialog correspondiente para añadir una rutina o un ejercicio
 */
public class AñadirDialog extends DialogFragment {

    private View m;

    public AñadirDialog(View m) {
        this.m = m;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        // Se crea el dialog con las dos opciones de añadir rutina o ejercicio
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setTitle(R.string.anadir);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View elaspecto = inflater.inflate(R.layout.crear_ejercicio_rutina, null);
        ImageButton cv = elaspecto.findViewById(R.id.imgAñadirEjercicio);
        cv.setOnClickListener(v -> {
            dismiss();
            MainActivity.add = true;
            Navigation.findNavController(m).navigate(R.id.action_menuFragment_to_addElementoFragment);
        });
        ImageButton ar = elaspecto.findViewById(R.id.imgAñadirRutina);
        ar.setOnClickListener(v -> {
            dismiss();
            MainActivity.add = true;
            Navigation.findNavController(m).navigate(R.id.action_menuFragment_to_addRutinaFragment);

        });
        ad.setView(elaspecto);
        return ad.create();
    }
}
