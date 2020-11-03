package es.eduardsanz.ejercicio03_citastattoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import es.eduardsanz.ejercicio03_citastattoo.adapters.AdapterCitasTattoo;
import es.eduardsanz.ejercicio03_citastattoo.configuraciones.Configuraciones;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitasTattoo;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_CITA = 100;
    private final int ADD_CITA = 77;
    // Modelo Datos
    private ArrayList<CitasTattoo> listaCitas;
    // Contenedor - Adapter Asociado
    private ListView contenedorCitas;
    private AdapterCitasTattoo adapterCitasTattoo;
    // Fila o elemento que se repite
    private int filaCita;

    // Almacenamiento Persistente
    private SharedPreferences spLista;
    private Gson parser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spLista = getSharedPreferences(Configuraciones.SP_DATOS, MODE_PRIVATE);
        parser = new Gson();
        
        listaCitas = new ArrayList<>();
        cargaLista();
        contenedorCitas = findViewById(R.id.contenedorCitas);
        filaCita = R.layout.fila_cita_tattoo;
        adapterCitasTattoo = new AdapterCitasTattoo(this, filaCita, listaCitas);


        contenedorCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("POS", position);
                CitasTattoo citasTattoo = listaCitas.get(position);
                bundle.putParcelable("CITA", citasTattoo);
                Intent intent = new Intent(MainActivity.this, EditCitaActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_CITA);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, NewCitaActivity.class)
                        , ADD_CITA);
            }
        });
    }

    private void cargaLista() {
        String tempString = spLista.getString(Configuraciones.D_LISTA, null);
        if (tempString != null){
            ArrayList<CitasTattoo> temp = parser.fromJson(tempString, new TypeToken< ArrayList<CitasTattoo> >(){}.getType());
            listaCitas.addAll(temp);
        }
    }

    private void guardaLista(){
        String tempString = parser.toJson(listaCitas);
        SharedPreferences.Editor editor = spLista.edit();
        editor.putString(Configuraciones.D_LISTA, tempString);
        editor.apply();
    }


    @Override
    protected void onResume() {
        super.onResume();
        contenedorCitas.setAdapter(adapterCitasTattoo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CITA && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                CitasTattoo citasTattoo = data.getExtras().getParcelable("CITA");
                listaCitas.add(citasTattoo);
                adapterCitasTattoo.notifyDataSetChanged();
                guardaLista();
            }
        }
        if (requestCode == EDIT_CITA && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                int posicion = data.getExtras().getInt("POS");
                CitasTattoo citasTattoo = data.getExtras().getParcelable("CITA");
                if (citasTattoo == null)
                {
                    listaCitas.remove(posicion);
                }
                else
                {
                    listaCitas.set(posicion, citasTattoo);
                }
                adapterCitasTattoo.notifyDataSetChanged();
                guardaLista();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("LISTA", listaCitas);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<CitasTattoo> temp = savedInstanceState.getParcelableArrayList("LISTA");
        listaCitas.clear();
        listaCitas.addAll(temp);
        Log.d("ELEMENTOS", ""+listaCitas.size());
    }
}