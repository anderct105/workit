package ehu.das.workit;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {

    private int imagen;
    private String nombre;

    public ScreenSlidePageFragment(int image, String nombre) {
        this.imagen = image;
        this.nombre = nombre;
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.single_slide, container, false);
            ImageView i = rootView.findViewById(R.id.imagenOpcion);
            i.setImageResource(imagen);
            TextView tv = rootView.findViewById(R.id.nombreOpcion);
            tv.setText(nombre);
            return rootView;
        }
}
