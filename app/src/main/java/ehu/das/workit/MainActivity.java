package ehu.das.workit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static boolean idioma_cambiado = false;
    public static boolean add = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // this.deleteDatabase("workit");
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("Idioma", sharedPreferences.getString("list_preference_1", "abcd"));
        // Si no está el castellano establecido, cambiar
        if (!idioma_cambiado) {
            idioma_cambiado = true;
            Locale nuevaloc = new Locale(sharedPreferences.getString("list_preference_1", "en"));
            Locale.setDefault(nuevaloc);
            Configuration configuration =
                    getBaseContext().getResources().getConfiguration();
            configuration.setLocale(nuevaloc);
            configuration.setLayoutDirection(nuevaloc);
            Context context =
                    getBaseContext().createConfigurationContext(configuration);
            getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
            finish();
            Intent i = getIntent();
            i.putExtra("idioma_cambiado", true);
            startActivity(i);
        }
    }


    @Override
    public void onBackPressed() {
        // Modifica el comportamiento de presionar hacia atrás
        // https://stackoverflow.com/questions/5448653/how-to-implement-onbackpressed-in-fragments
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        Fragment fragment = fragments.get(fragments.size() - 1);
        if (!(fragment instanceof IOnBackPressed)) {
            SalirDialog sd = new SalirDialog();
            sd.show(getSupportFragmentManager(), "salir");
        } else {
            ((IOnBackPressed) fragment).onBackPressed();
        }
    }


}