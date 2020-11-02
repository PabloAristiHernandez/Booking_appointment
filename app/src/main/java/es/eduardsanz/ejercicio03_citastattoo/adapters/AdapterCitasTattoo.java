package es.eduardsanz.ejercicio03_citastattoo.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import es.eduardsanz.ejercicio03_citastattoo.R;
import es.eduardsanz.ejercicio03_citastattoo.configuraciones.Configuraciones;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitasTattoo;

public class AdapterCitasTattoo extends ArrayAdapter<CitasTattoo> {

    private Context context;
    private int resource;
    private List<CitasTattoo> objects;

    public AdapterCitasTattoo(@NonNull Context context, int resource, @NonNull List<CitasTattoo> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View fila = LayoutInflater.from(context).inflate(resource, null);
        CitasTattoo citasTattoo = objects.get(position);

        TextView txtNombre = fila.findViewById(R.id.txtNombreFila);
        TextView txtFechaCita = fila.findViewById(R.id.txtFechaFila);

        txtNombre.setText(citasTattoo.getNombre());

        txtFechaCita.setText(Configuraciones.simpleDateFormat.format(citasTattoo.getFechaCita()));

        return fila;
    }
}
