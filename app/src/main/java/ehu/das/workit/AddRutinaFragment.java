package ehu.das.workit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Este fragment permite añadir una rutina
 */
public class AddRutinaFragment extends Fragment implements IOnBackPressed{

    private AdaptadorRutinaRecyclerView aa;
    private String nombre = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_rutina, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
        TextView eliminar = getActivity().findViewById(R.id.eliminarRutina);
        ImageButton botonVolverEjercicio = getActivity().findViewById(R.id.botonVolverRutina);
        botonVolverEjercicio.setOnClickListener(v -> {
            //Navigation.findNavController(getView()).navigate(R.id.action_addRutinaFragment_to_menuFragment);
            //getActivity().recreate();
            MainActivity.add = false;
            getActivity().finish();
             Intent i = getActivity().getIntent();
             i.putExtra("logueado", true);
             startActivity(getActivity().getIntent());
        });
        // Si la rutina ya estaba creada
        if (getArguments() != null) {
            // Cargar los datos de la rutina
            nombre = getArguments().getString("nombre");
            EditText et = getActivity().findViewById(R.id.nombreRutinaAdd);
            et.setText(nombre);
            cargarRutina();
            // Eliminar solo está disponible si la rutina estaba creada
            eliminar.setOnClickListener(v -> {
                BD db = new BD(getContext(), "workit", null, 1);
                SQLiteDatabase bd = db.getWritableDatabase();
                bd.execSQL("DELETE FROM rutina WHERE nombre='" + nombre + "'");
                Toast.makeText(getContext(), nombre + " eliminado", Toast.LENGTH_LONG).show();
                bd.close();
                botonVolverEjercicio.callOnClick();
            });
        }
        // Si no poner valores por defecto
        else {
            inicializarAdaptador();
            eliminar.setVisibility(View.GONE);
        }
        // Visualización de los ejercicios
        RecyclerView rv = getActivity().findViewById(R.id.add_ejercicios);
        rv.setAdapter(aa);
        GridLayoutManager elLayoutRejillaIgual;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            elLayoutRejillaIgual = new GridLayoutManager(getActivity(),2, GridLayoutManager.VERTICAL,false);
        }
        else {
            elLayoutRejillaIgual = new GridLayoutManager(getActivity(),1, GridLayoutManager.VERTICAL,false);
        }
        rv.setLayoutManager(elLayoutRejillaIgual);
        // Listener para guardar la rutina
        guardarRutina();
    }

    public void inicializarAdaptador() {
        // Carga todos los ejercicios de la bd y inicializa a valores por defecto
        BD db = new BD(getContext(), "workit", null, 1);
        SQLiteDatabase bd = db.getReadableDatabase();
        Cursor query = bd.rawQuery("SELECT nombre FROM ejercicio", null);
        ArrayList<String> ejercicios = new ArrayList<>();
        ArrayList<Integer> imagenes = new ArrayList<>();
        ArrayList<Integer> series = new ArrayList<>();
        ArrayList<Boolean> seleccionado = new ArrayList<>();
        ArrayList<List<Integer>> pesos = new ArrayList<>();
        while (query.moveToNext()) {
            ejercicios.add(query.getString(0));
            imagenes.add(R.drawable.punch);
            series.add(1);
            ArrayList<Integer> lista_pesos = new ArrayList<>();
            lista_pesos.add(1);
            lista_pesos.add(0);
            lista_pesos.add(0);
            lista_pesos.add(0);
            lista_pesos.add(0);
            pesos.add(lista_pesos);
            seleccionado.add(false);
        }
        query.close();
        bd.close();
        aa = new AdaptadorRutinaRecyclerView(ejercicios, imagenes, series, seleccionado, pesos, false);
    }

    public void cargarRutina() {
        // Carga la rutina de la base de datos
        BD db = new BD(getContext(), "workit", null, 1);
        SQLiteDatabase bd = db.getReadableDatabase();
        Cursor query = bd.rawQuery("SELECT nombreR,nombreE,series,peso1,peso2,peso3,peso4,peso5 FROM rutina INNER JOIN rutina_ejercicio ON rutina.nombre=rutina_ejercicio.nombreR WHERE nombre='" + nombre + "' and usuario=" + LoginFragment.usuario, null);
        ArrayList<String> ejercicios = new ArrayList<>();
        ArrayList<Integer> imagenes = new ArrayList<>();
        ArrayList<Integer> series = new ArrayList<>();
        ArrayList<Boolean> seleccionado = new ArrayList<>();
        ArrayList<List<Integer>> pesos = new ArrayList<>();
        while (query.moveToNext()){
            ejercicios.add(query.getString(query.getColumnIndex("nombreE")));
            imagenes.add(R.drawable.punch);
            series.add(query.getInt(query.getColumnIndex("series")));
            seleccionado.add(true);
            ArrayList<Integer> cada_serie = new ArrayList();
            cada_serie.add(query.getInt(query.getColumnIndex("peso1")));
            cada_serie.add(query.getInt(query.getColumnIndex("peso2")));
            cada_serie.add(query.getInt(query.getColumnIndex("peso3")));
            cada_serie.add(query.getInt(query.getColumnIndex("peso4")));
            cada_serie.add(query.getInt(query.getColumnIndex("peso5")));
            pesos.add(cada_serie);
        }
        query.close();
        bd.close();
        aa = new AdaptadorRutinaRecyclerView(ejercicios, imagenes, series, seleccionado, pesos, true);
    }

    public void guardarRutina() {
        // Pone un listener para guardar la rutina en la base de datos
        TextView guardarRutina = getActivity().findViewById(R.id.guardarRutina);
        guardarRutina.setOnClickListener(v -> {
            EditText campoNombre = getActivity().findViewById(R.id.nombreRutinaAdd);

            if (!existe(campoNombre.getText().toString())) {
                BD db = new BD(getContext(), "workit", null, 1);
                SQLiteDatabase bd = db.getWritableDatabase();
                if (nombre != null) {
                    bd.execSQL("DELETE FROM rutina WHERE nombre='" + nombre + "'");
                    notificarRutinaCreada(campoNombre.getText().toString());
                }
                bd.execSQL("INSERT INTO rutina ('nombre') VALUES ('" + campoNombre.getText().toString() + "')");
                for (int i = 0; i < aa.nombres.size(); i++) {
                    if (aa.seleccionado.get(i)) {
                        int peso1 = 0;
                        int peso2 = 0;
                        int peso3 = 0;
                        int peso4 = 0;
                        int peso5 = 0;
                        try {
                            peso1 = aa.pesos.get(i).get(0);
                            peso2 = aa.pesos.get(i).get(1);
                            peso3 = aa.pesos.get(i).get(2);
                            peso4 = aa.pesos.get(i).get(3);
                            peso5 = aa.pesos.get(i).get(4);
                        } catch (Exception e) {
                        }

                        bd.execSQL("INSERT INTO rutina_ejercicio ('usuario', 'nombreR', 'nombreE', 'series', 'peso1', 'peso2', 'peso3', 'peso4', 'peso5') VALUES (" + LoginFragment.usuario + ",'" + campoNombre.getText().toString() + "', '" + aa.nombres.get(i) + "'," + aa.series.get(i) + "," +
                                peso1 + "," + peso2 + "," + peso3 + "," + peso4 + "," + peso5 + ")");
                        bd.close();
                    }
                    ImageButton botonVolverEjercicio = getActivity().findViewById(R.id.botonVolverRutina);
                    botonVolverEjercicio.callOnClick();
                }
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.rutinaExiste), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void notificarRutinaCreada(String nombre) {
        // Notifica cuando se cree la rutina
        NotificationManager elManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(getContext(), "01");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("01", "Add",
                    NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
            elCanal.setDescription(getResources().getString(R.string.descripcionCanal));
            elCanal.enableLights(true);
            elCanal.setLightColor(Color.RED);
            elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            elCanal.enableVibration(true);
        }
        // En función del idioma
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String texto = "";
        if (sharedPreferences.getString("list_preference_1", "español").equalsIgnoreCase("español")) {
            texto = "Ya puedes ver " + nombre + " en tu lista de rutinas";
        } else {
            texto = nombre + " has been created";
        }
        elManager.notify(1, elBuilder.build());
        PendingIntent intentEnNot = PendingIntent.getActivity(getContext(), 0, getActivity().getIntent(), 0);
        elBuilder.setSmallIcon(R.drawable.workit)
                .setContentTitle(getResources().getString(R.string.rutinaAdd))
                .setContentText(texto)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setAutoCancel(true)
                .setContentIntent(intentEnNot);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public boolean existe(String nombreRutina) {
        // Comprueba que el ejercicio que se vaya a crear no esté creado ya
        if (nombre != null && nombre.equals(nombreRutina)) {
            return false;
        } else {
            BD db = new BD(getContext(), "workit", null, 1);
            SQLiteDatabase bd = db.getReadableDatabase();
            Cursor query = bd.rawQuery("SELECT nombre FROM rutina WHERE nombre='" + nombreRutina + "'", null);
            boolean existe =  query.getCount() >= 1;
            query.close();
            db.close();
            return existe;
        }
    }
}