package ehu.das.workit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.Navigation;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Menú principal de la aplicación con un tab con 3 submenus
 */

public class MenuFragment extends Fragment implements IOnBackPressed{

    private static final int NUM_PAGES = 3;
    private ViewPager mPager;
    private ViewPagerAdapter pagerAdapter;
    private int[] tabs = {R.id.rutinas, R.id.entrenar, R.id.ejercicios};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Filtro de color para los botones
        ImageButton addContenido = getActivity().findViewById(R.id.añadirContenido);
        addContenido.setColorFilter(Color.WHITE);
        ImageButton configuracion = getActivity().findViewById(R.id.configuracion);
        configuracion.setColorFilter(Color.WHITE);
        configuracion.setOnClickListener(v -> {
            MainActivity.add = true;
            Navigation.findNavController(v).navigate(R.id.action_menuFragment_to_preferencias);
        });
        // Abrir dialogo
        addContenido.setOnClickListener(v -> {
            AñadirDialog ad = new AñadirDialog(configuracion);
            ad.show(getActivity().getSupportFragmentManager(), "añadir");
        });
        ImageView cerrarSesion = getActivity().findViewById(R.id.cerrarSesion);
        cerrarSesion.setColorFilter(Color.WHITE);
        // Cerrar sesión deshabilitar inicio de sesión por defecto
        cerrarSesion.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("switch_preference_1", false);
            editor.apply();
            editor.commit();
            getActivity().finish();
            Intent i = getActivity().getIntent();
            i.putExtra("logueado", false);
            startActivity(getActivity().getIntent());
            /**Navigation.findNavController(getView()).navigate(R.id.action_menuFragment_to_loginFragment);
            getActivity().recreate();**/
        });
        // Inicializa el page view con los tres fragments
        mPager = (ViewPager) getActivity().findViewById(R.id.viewPage);
        pagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(new RutinasFragment(),"rutinas");
        pagerAdapter.addFragment(new EntrenarFragment(),"entrenar");
        pagerAdapter.addFragment(new EjerciciosFragment(),"ejercicios");
        mPager.setAdapter(pagerAdapter);
        mPager.setCurrentItem(1);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                BottomNavigationView navegacion = getActivity().findViewById(R.id.opcionesNavigation);
                navegacion.setSelectedItemId(tabs[position]);
            }
        });
        // Evento del nav bar para que esté sincronizada con el page view
        BottomNavigationView navigationView = getActivity().findViewById(R.id.opcionesNavigation);
        navigationView.setSelectedItemId(R.id.entrenar);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == tabs[0]) {
                mPager.setCurrentItem(0);
            } else if (item.getItemId() == tabs[1]) {
                mPager.setCurrentItem(1);
            } else {
                mPager.setCurrentItem(2);
            }
            return true;
        });
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}