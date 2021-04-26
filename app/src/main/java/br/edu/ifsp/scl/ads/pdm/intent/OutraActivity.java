package br.edu.ifsp.scl.ads.pdm.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityMainBinding;
import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {

    // instancia da classe de View Binding
    private ActivityOutraBinding activityOutraBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        // recebendo parametros pela 1a forma
        Bundle parametrosBundle = getIntent().getExtras();
        if (parametrosBundle != null) {
            String parametro = parametrosBundle.getString(MainActivity.PARAMETRO, ""); // const static public do MainActivity
            activityOutraBinding.recebidoTv.setText(parametro);
        }

        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onCreate: Iniciando cliclo completo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onStart: Iniciando cliclo visível");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onResume: Iniciando cliclo em primeiro plano"); // foreground
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onPause: Finalizando cliclo em primeiro plano");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onStop: Finalizando clico visível");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onDestroy: Finalizando cliclo completo");
    }

    public void onClick(View view) { // criar id pro botão if()
        finish(); // chama na sequencia onPause, onStop, onDestroy
    }
}