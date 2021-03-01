package ehu.das.workit;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntrenarFragment} factory method to
 * create an instance of this fragment.
 */
public class EntrenarFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entrenar, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] nombres = {"Pierna 1", "Pecho2"};
        int[] imagenes = {R.drawable.punch, R.drawable.punch};
        AdaptadorRecyclerView adaptadorRecyclerView = new AdaptadorRecyclerView(nombres, imagenes);
        RecyclerView recyclerView = getActivity().findViewById(R.id.listaGruposEntrenar);
        recyclerView.setAdapter(adaptadorRecyclerView);
        GridLayoutManager elLayoutRejillaIgual= new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(elLayoutRejillaIgual);

    }
}