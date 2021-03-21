package ehu.das.workit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;

/**
 * Este fragment permite añadir un ejercicio
 */
public class AddEjercicioFragment extends Fragment implements IOnBackPressed {


    private EditText campoNombre;
    private EditText campoDescripcion;
    private CheckBox campoPecho;
    private CheckBox campoHombro;
    private CheckBox campoBiceps;
    private CheckBox campoTriceps;
    private CheckBox campoCuadriceps;
    private CheckBox campoFemoral;
    private CheckBox campoAbdominales;
    private CheckBox campoGluteos;
    private CheckBox campoCardio;
    private CheckBox campoEspalda;
    private Spinner campoIntensidad;
    private Spinner campoDificultad;
    private String nombreEjercicio = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_ejercicio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Carga los campos del formulario
        obtenerCampos();
        // Botón volver
        ImageButton botonVolverEjercicio = getActivity().findViewById(R.id.botonVolverRutina);
        /**botonVolverEjercicio.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_addElementoFragment_to_menuFragment);
            getActivity().recreate();
        });**/
        TextView eliminar = getActivity().findViewById(R.id.eliminarEjercicio);

        botonVolverEjercicio.setOnClickListener(v -> {
            MainActivity.add = false;
            getActivity().finish();
         Intent i = getActivity().getIntent();
         i.putExtra("logueado", true);
         startActivity(getActivity().getIntent());
         });
        ImageView addFoto = getActivity().findViewById(R.id.addFotoEjercicio);
        addFoto.setOnClickListener(v -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, 1);
//          Este código sería para sacar foto con la camara
//            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(takePicture, 0);//zero can be replaced with any action code (called requestCode)
        });
        TextView guardar = getActivity().findViewById(R.id.guardarRutina);
        // Si el ejercicio se ha cargado y no se está creando una nueva
        if (getArguments() != null) {
            // Se asignan los valores que tenga el ejercicio
            EditText campo_nombre = getActivity().findViewById(R.id.nombreEjercicio);
            nombreEjercicio = getArguments().getString("nombre");
            campo_nombre.setText(nombreEjercicio);
            cargarEjercicio(nombreEjercicio);
            // Eliminar solo está disponible si el ejercicio ya estaba creado
            eliminar.setOnClickListener(v -> {
                BD db = new BD(getContext(), "workit", null, 1);
                SQLiteDatabase bd = db.getWritableDatabase();
                bd.execSQL("DELETE FROM ejercicio WHERE nombre='" + nombreEjercicio + "'");
                Toast.makeText(getContext(), nombreEjercicio + " eliminado", Toast.LENGTH_LONG).show();
                botonVolverEjercicio.callOnClick();
            });
        } else {
            eliminar.setVisibility(View.GONE);
        }
        guardar.setOnClickListener(v -> {
            guardarEjercicio();
        });
    }

    public void obtenerCampos() {
        campoNombre = getActivity().findViewById(R.id.nombreEjercicio);
        campoDescripcion = getActivity().findViewById(R.id.descripcionEjercicio);
        campoPecho = getActivity().findViewById(R.id.pecho);
        campoHombro = getActivity().findViewById(R.id.hombros);
        campoBiceps = getActivity().findViewById(R.id.biceps);
        campoTriceps = getActivity().findViewById(R.id.triceps);
        campoCuadriceps = getActivity().findViewById(R.id.cuadriceps);
        campoFemoral = getActivity().findViewById(R.id.femoral);
        campoAbdominales = getActivity().findViewById(R.id.abdominales);
        campoGluteos = getActivity().findViewById(R.id.gluteos);
        campoCardio = getActivity().findViewById(R.id.cardio);
        campoEspalda = getActivity().findViewById(R.id.espalda);
        campoIntensidad = getActivity().findViewById(R.id.intensidad);
        campoDificultad = getActivity().findViewById(R.id.dificultad);
    }

    public void cargarEjercicio(String nombre) {
        // Lee los datos del ejercicio de la base de datos
        BD db = new BD(getContext(), "workit", null, 1);
        SQLiteDatabase bd = db.getReadableDatabase();
        Cursor query = bd.rawQuery("SELECT * FROM ejercicio WHERE nombre='" + nombre + "'", null);
        query.moveToNext();
        campoDescripcion.setText(query.getString(query.getColumnIndex("descripcion")));
        campoPecho.setChecked(query.getInt(query.getColumnIndex("pecho")) == 1);
        campoCuadriceps.setChecked(query.getInt(query.getColumnIndex("cuadriceps")) == 1);
        campoAbdominales.setChecked(query.getInt(query.getColumnIndex("abdominales")) == 1);
        campoHombro.setChecked(query.getInt(query.getColumnIndex("hombros")) == 1);
        campoEspalda.setChecked(query.getInt(query.getColumnIndex("espalda")) == 1);
        campoGluteos.setChecked(query.getInt(query.getColumnIndex("gluteos")) == 1);
        campoTriceps.setChecked(query.getInt(query.getColumnIndex("triceps")) == 1);
        campoBiceps.setChecked(query.getInt(query.getColumnIndex("biceps")) == 1);
        campoFemoral.setChecked(query.getInt(query.getColumnIndex("femoral")) == 1);
        campoCardio.setChecked(query.getInt(query.getColumnIndex("cardio")) == 1);
        campoIntensidad.setSelection(query.getInt(query.getColumnIndex("intensidad")), true);
        campoDificultad.setSelection(query.getInt(query.getColumnIndex("dificultad")), true);
        query.close();
        bd.close();
    }


    public void guardarEjercicio() {
        // Guarda el ejercicio de la base de datos
        String nombre = campoNombre.getText().toString();
        String nombre_nuevo = nombre;
        if (getArguments() != null) {
            nombre = getArguments().getString("nombre");
        }
        if (campoNombre.getText().toString().equals("")) {
            Toast.makeText(getContext(),  getResources().getString(R.string.nombreVacio), Toast.LENGTH_LONG).show();
        } else if (!existe(nombre_nuevo)){
            BD db = new BD(getContext(), "workit", null, 1);
            SQLiteDatabase bd = db.getWritableDatabase();
            Cursor query = bd.rawQuery("SELECT nombre FROM ejercicio WHERE nombre='" + nombre + "'", null);
            if (query.getCount() == 0) {
                notificarEjercicioCreado(nombre);
                bd.execSQL("INSERT INTO ejercicio ('nombre', 'descripcion', 'pecho', 'cuadriceps', 'abdominales', 'hombros', 'espalda', 'gluteos', 'triceps', 'biceps', 'femoral', 'cardio', 'intensidad', 'dificultad') VALUES ('" + campoNombre.getText().toString() + "', '" + campoDescripcion.getText().toString() + "', " + getInt(campoPecho.isChecked()) + "," + getInt(campoCuadriceps.isChecked()) + ", " + getInt(campoAbdominales.isChecked()) + "," + getInt(campoHombro.isChecked()) + ", " + getInt(campoEspalda.isChecked()) + ", " + getInt(campoGluteos.isChecked()) + "," + getInt(campoTriceps.isChecked()) + "," + getInt(campoBiceps.isChecked()) + "," + getInt(campoFemoral.isChecked()) + "," + getInt(campoCardio.isChecked()) + "," + campoIntensidad.getSelectedItemPosition() + "," + campoDificultad.getSelectedItemPosition() + ")");
            } else {
                bd.execSQL(" UPDATE ejercicio SET nombre = '" + campoNombre.getText().toString() + "', descripcion='" + campoDescripcion.getText().toString() + "', pecho=" + getInt(campoPecho.isChecked()) + ", cuadriceps=" + getInt(campoCuadriceps.isChecked()) + ", abdominales=" + getInt(campoAbdominales.isChecked()) + ", hombros=" + getInt(campoHombro.isChecked()) + ", espalda=" + getInt(campoEspalda.isChecked()) + ", gluteos=" + getInt(campoGluteos.isChecked()) + ", triceps=" + getInt(campoTriceps.isChecked()) + ", biceps=" + getInt(campoBiceps.isChecked()) + ", femoral=" + getInt(campoFemoral.isChecked()) + ", cardio=" + getInt(campoCardio.isChecked()) + ", intensidad=" + campoIntensidad.getSelectedItemPosition() + ", dificultad=" + campoDificultad.getSelectedItemPosition() + " WHERE nombre='" + nombre + "'");
            }
            bd.close();
            ImageView iv = getActivity().findViewById(R.id.botonVolverRutina);
            iv.callOnClick();
        } else {
            Toast.makeText(getContext(),  getResources().getString(R.string.ejercicioExiste), Toast.LENGTH_LONG).show();
        }
    }

    public int getInt(boolean b) {
        return b ? 1 : 0;
    }


    public void notificarEjercicioCreado(String nombre) {
        // Notificación cuando se crea un ejercicio nuevo
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
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String texto = "";
        if (sharedPreferences.getString("list_preference_1", "español").equalsIgnoreCase("español")) {
            texto = "Ya puedes ver " + nombre + " en tu lista de ejercicios";
        } else {
            texto = nombre + " has been created";
        }
        PendingIntent intentEnNot = PendingIntent.getActivity(getContext(), 0, getActivity().getIntent(), 0);
        elBuilder
                .setSmallIcon(R.drawable.workit)
                .setContentTitle(getResources().getString(R.string.ejercicioAdd))
                .setContentText(texto)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setAutoCancel(true)
                .setContentIntent(intentEnNot);
        elManager.notify(1, elBuilder.build());
    }

    /**
     * Desactiva el botón back
     *
     * @return
     */
    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        // https://stackoverflow.com/questions/3879992/how-to-get-bitmap-from-an-uri
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        ImageView addFoto = getActivity().findViewById(R.id.addFotoEjercicio);
        int width = addFoto.getWidth();
        int height = addFoto.getHeight();

        if (resultCode == RESULT_OK) {
            try {
                Uri selectedImage = imageReturnedIntent.getData();
                Picasso.with(getContext()).load(selectedImage).resize(width, height).into(addFoto);

                // addFoto.setImageBitmap(resizedSignature);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean existe(String nombre) {
        // Comprueba que el ejercicio que se vaya a crear no esté creado ya
        if (nombreEjercicio != null && nombre.equals(nombreEjercicio)) {
            return false;
        } else {
            BD db = new BD(getContext(), "workit", null, 1);
            SQLiteDatabase bd = db.getReadableDatabase();
            Cursor query = bd.rawQuery("SELECT nombre FROM ejercicio WHERE nombre='" + nombre + "'", null);
            boolean existe =  query.getCount() >= 1;
            query.close();
            db.close();
            return existe;
        }
    }
}