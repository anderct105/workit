package ehu.das.workit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

/**
 * Permite establecer las preferencias de los usuarios
 */

public class Preferencias extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_conf);
        // https://stackoverflow.com/questions/5298370/how-to-add-a-button-to-a-preferencescreen
        // Listener para salir de las preferencias
        Preference button = (Preference)getPreferenceManager().findPreference("return");
        if (button != null) {
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) {
                    MainActivity.add = false;
                    Intent i = new Intent(getContext(), MainActivity.class);
                    i.putExtra("logueado", true);
                    startActivity(i);
                    return true;
                }
            });
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // https://stackoverflow.com/questions/531427/how-do-i-display-the-current-value-of-an-android-preference-in-the-preference-su
        // Si se cambia el idioma reiniciar la aplicación con el idioma cambiado
        Log.i("Preferencias", "Se ha cambiado a " + key);
        if (key.equals("list_preference_1")) {
            String idioma = "";
            if (sharedPreferences.getString(key, "español").equalsIgnoreCase("español")) {
                idioma = "es";
            } else {
                idioma = "en";
            }
            Log.i("Idioma", sharedPreferences.getString(key, "abcd"));
            Locale nuevaloc = new Locale(idioma);
            Locale.setDefault(nuevaloc);
            Configuration configuration =
                    getActivity().getBaseContext().getResources().getConfiguration();
            configuration.setLocale(nuevaloc);
            configuration.setLayoutDirection(nuevaloc);
            Context context =
                    getActivity().getBaseContext().createConfigurationContext(configuration);
            getActivity().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
            getActivity().finish();
            MainActivity.add = false;
            Intent i = getActivity().getIntent();
            i.putExtra("idioma_cambiado", true);
            startActivity(i);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }


}
