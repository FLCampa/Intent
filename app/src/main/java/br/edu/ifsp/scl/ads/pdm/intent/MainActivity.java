package br.edu.ifsp.scl.ads.pdm.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // instancia da classe de View Binding
    private ActivityMainBinding activityMainBinding;

    // constante para passagem de parametro e retorno
    public static final String PARAMETRO = "PARAMETRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("Tem subtítulo");
        setContentView(activityMainBinding.getRoot());

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
        Log.v(getString(R.string.app_name) + "/" + getLocalClassName(), "onResume: Iniciando cliclo em primeiro plano");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // inflate(qual o arquivo q contem a descrição(xml), qual o obj da classe menu)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { // chamado qnd um item de menu for clicado
        switch (item.getItemId()) {                                // retorna true(se for tratado) / false
            case R.id.outraActivityMi:
                // abrir a OutraActivity
                Intent outraActivityIntent = new Intent(this, OutraActivity.class);

                // 1a forma de passagem de parametros
                Bundle parametrosBundle = new Bundle();
                parametrosBundle.putString(PARAMETRO, activityMainBinding.parametroEt.getText().toString()); // msm chave da outra activity (p/ recuperar o valor), pegar texto do Et
                outraActivityIntent.putExtras(parametrosBundle);

                startActivity(outraActivityIntent);

                return true;
            case R.id.viewMi:
                return true;
        }
        
        return super.onOptionsItemSelected(item);
    }


}