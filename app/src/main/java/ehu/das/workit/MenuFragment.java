package ehu.das.workit;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MenuFragment extends Fragment {

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
        ImageButton addContenido = getActivity().findViewById(R.id.añadirContenido);
        addContenido.setColorFilter(Color.WHITE);
        ImageButton configuracion = getActivity().findViewById(R.id.configuracion);
        configuracion.setColorFilter(Color.WHITE);
        addContenido.setOnClickListener(v -> {
            AñadirDialog ad = new AñadirDialog(configuracion);
            ad.show(getActivity().getSupportFragmentManager(), "añadir");
        });
        mPager = (ViewPager) getActivity().findViewById(R.id.viewPage);
        pagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        pagerAdapter.addFragment(new RutinasFragment(),"rutinas");
        pagerAdapter.addFragment(new EntrenarFragment(),"entrenar");
        pagerAdapter.addFragment(new EjerciciosFragment(),"ejercicios");
        mPager.setAdapter(pagerAdapter);
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
        BottomNavigationView navigationView = getActivity().findViewById(R.id.opcionesNavigation);
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

}