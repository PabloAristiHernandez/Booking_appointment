package es.eduardsanz.ejercicio03_citastattoo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import es.eduardsanz.ejercicio03_citastattoo.configuraciones.Configuraciones;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitasTattoo;

public class NewCitaActivity extends AppCompatActivity {

    private EditText txtNombre, txtApellidos, txtFechaNacimiento, txtFechaCita, txtAdelanto;
    private Switch swColor, swAutorizado;
    private Button btnEliminar, btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_cita);

        inicializaInterfaz();

        final LocalDate hoy = LocalDate.now();

        txtFechaNacimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    Date fecha = Configuraciones.simpleDateFormat.parse(s.toString());
                    LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (hoy.getYear() - fechaNacimiento.getYear() < 18){
                        swAutorizado.setVisibility(View.VISIBLE);
                        swAutorizado.setChecked(false);
                        btnGuardar.setEnabled(false);
                    }
                    else{
                        swAutorizado.setVisibility(View.GONE);
                        swAutorizado.setChecked(false);
                        btnGuardar.setEnabled(true);
                    }
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });

        swAutorizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnGuardar.setEnabled(isChecked);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtNombre.getText().toString().isEmpty() &&
                    !txtFechaCita.getText().toString().isEmpty() &&
                    !txtFechaNacimiento.getText().toString().isEmpty() ){
                    CitasTattoo citasTattoo = new CitasTattoo();
                    citasTattoo.setNombre(txtNombre.getText().toString());
                    citasTattoo.setApellidos(txtApellidos.getText().toString());
                    try {
                        citasTattoo.setFechaCita(Configuraciones.simpleDateFormat.parse(txtFechaCita.getText().toString()));
                        citasTattoo.setFechaNacimiento(Configuraciones.simpleDateFormat.parse(txtFechaNacimiento.getText().toString()));
                        if (txtAdelanto.getText().toString().isEmpty())
                            citasTattoo.setAdelanto(0);
                        else
                            citasTattoo.setAdelanto(Float.parseFloat(txtAdelanto.getText().toString()));
                        citasTattoo.setColor(swColor.isChecked());
                        citasTattoo.setAutorizado(swAutorizado.isChecked());

                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("CITA", citasTattoo);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    catch (ParseException pex) {
                        Toast.makeText(NewCitaActivity.this, "Fechas Incorrectas", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(NewCitaActivity.this, getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void inicializaInterfaz() {
        txtNombre = findViewById(R.id.txtNombreCita);
        txtApellidos = findViewById(R.id.txtApellidosCita);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimientoCita);
        txtFechaCita = findViewById(R.id.txtFechaCitaCita);
        txtAdelanto = findViewById(R.id.txtAdelantoCita);
        swAutorizado = findViewById(R.id.swAutorizadoCita);
        swColor = findViewById(R.id.swColorCita);
        btnEliminar = findViewById(R.id.btnEliminarCita);
        btnGuardar = findViewById(R.id.btnGuardarCita);

        btnEliminar.setVisibility(View.GONE);
        swAutorizado.setVisibility(View.GONE);


    }
}