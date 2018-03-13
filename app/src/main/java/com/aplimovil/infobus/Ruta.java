package com.aplimovil.infobus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Ruta extends AppCompatActivity {
    //Definimos las variables
    private Button miconsulta;
    private Spinner miorigen;
    private Spinner midestino;
    private String[] rutas;
    private EditText mitexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruta);
        //Inflamemos_todo
        miorigen = (Spinner) findViewById(R.id.origen);
        midestino = (Spinner) findViewById(R.id.destino);
        miconsulta = (Button) findViewById(R.id.consultar);
        mitexto = (EditText) findViewById(R.id.example);
        //Llenamos los Spinner con informacion
        String[] nombres = new String[]{"campanario", "Bellohorizonte", "Comuneros", "Ortigal", "Campamento", "Estancia"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,nombres);
        miorigen.setAdapter(adapter);
        midestino.setAdapter(adapter);
        miconsulta.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                String so = miorigen.getSelectedItem().toString();
                String sd = midestino.getSelectedItem().toString();
                mitexto.setText("");
                mitexto.setText("Su Origen: "+so+",Su Destino: "+sd);
            }
        });
    }

}
