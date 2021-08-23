package com.example.appwithvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    // Creacion de los objetos
    EditText edtcodigo, edtnombre, edtdireccion;
    Button btnagregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtcodigo=(EditText)findViewById(R.id.txtcodigo);
        edtnombre=(EditText)findViewById(R.id.txtnombre);
        edtdireccion=(EditText)findViewById(R.id.txtdireccion);
        btnagregar = (Button)findViewById(R.id.btnagregar);
        // implementando el evento click
        btnagregar.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){ insertar(); }
        });
    }

    public void insertar(){
        final String codigo = edtcodigo.getText().toString();
        final String nombre = edtnombre.getText().toString();
        final String direccion = edtdireccion.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        if(codigo.isEmpty()){
            edtcodigo.setError("Complete codigo");
        }
        else if(nombre.isEmpty()){
            edtnombre.setError("Complete nombre");
        }else if(direccion.isEmpty()){
            edtdireccion.setError("Complete direccion");
        }else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.100.3:80/agregar_alumno.php", new Response.Listener<String>(){
               @Override
               public void onResponse(String response){
                 if(response.equalsIgnoreCase("Datos insertados")){
                     Toast.makeText(MainActivity.this, "Datos insertados", Toast.LENGTH_SHORT).show();
                 }else {
                     Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                 }
               }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("codigo", codigo);
                    parametros.put("nombre", nombre);
                    parametros.put("direccion", direccion);
                    return parametros;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        }
    }
}
