package com.example.simulador;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    EditText txtNumCorredores, txtDistancia, txtCarreraId;
    Button btnCrear, btnListar, btnEliminar;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumCorredores = findViewById(R.id.txtNumCorredores);
        txtDistancia = findViewById(R.id.txtDistancia);
        txtCarreraId = findViewById(R.id.txtCarreraId);
        btnCrear = findViewById(R.id.btnCrear);
        btnListar = findViewById(R.id.btnListar);
        btnEliminar = findViewById(R.id.btnEliminar);
        txtResultado = findViewById(R.id.txtResultado);

        // Botón para crear carrera
        btnCrear.setOnClickListener(v -> {
            String numCorredores = txtNumCorredores.getText().toString().trim();
            String distancia = txtDistancia.getText().toString().trim();

            if (!numCorredores.isEmpty() && !distancia.isEmpty()) {
                String url = "http://192.168.68.108:3001/?numCorredores=" + numCorredores + "&distancia=" + distancia;
                ejecutarRequest(url);
            } else {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón para listar carreras
        btnListar.setOnClickListener(v -> {
            String url = "http://192.168.68.108:3001/carreras";
            ejecutarRequest(url);
        });

        // Botón para eliminar carrera
        btnEliminar.setOnClickListener(v -> {
            String carreraId = txtCarreraId.getText().toString().trim();

            if (!carreraId.isEmpty()) {
                String url = "http://192.168.68.108:3001/carreras/" + carreraId;
                eliminarCarrera(url);
            } else {
                Toast.makeText(this, "Por favor, ingresa el ID de la carrera", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ejecutarRequest(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> txtResultado.setText("Resultado: " + response),
                error -> Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void eliminarCarrera(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                response -> Toast.makeText(this, "Carrera eliminada exitosamente", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
