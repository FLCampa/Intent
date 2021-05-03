package br.edu.ifsp.scl.ads.pdm.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.URI;

import br.edu.ifsp.scl.ads.pdm.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // instancia da classe de View Binding
    private ActivityMainBinding activityMainBinding;

    // constante para passagem de parametro e retorno
    public static final String PARAMETRO = "PARAMETRO";

    // code para OutraActivity
    private final int OUTRA_ACTIVITY_REQUEST_CODE = 0;

    //request code de permissão CALL_PHONE
    private final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    //request code pergar arquivo de imagem
    private final int PICK_IMAGE_FILE_REQUEST_CODE = 2;

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
                //Intent outraActivityIntent = new Intent(this, OutraActivity.class); // qual activity q vai abrir

                // abrir activity usando uma acao
                Intent outraActivityIntent = new Intent("RECEBER_E_RETORNAR_ACTION"); // acao q vai ser tratada na intent

                // 1a forma de passagem de parametros
                //Bundle parametrosBundle = new Bundle();
                //parametrosBundle.putString(PARAMETRO, activityMainBinding.parametroEt.getText().toString()); // msm chave da outra activity (p/ recuperar o valor), pegar texto do Et
                //outraActivityIntent.putExtras(parametrosBundle);

                // 2a forma de passagem de parametros
                outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroEt.getText().toString());


                //startActivity(outraActivityIntent); // lança intent para SO
                startActivityForResult(outraActivityIntent, OUTRA_ACTIVITY_REQUEST_CODE);// intent, requestCode

                return true;
            case R.id.viewMi:
                // abrir navegador (passando url)
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
                abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.parametroEt.getText().toString())); // ("http://scl.ifsp.edu.br")
                startActivity(abrirNavegadorIntent); // se o navegador retornar algo startActivityForResult()
                return true;
            case R.id.callMi:
                // fazer ligacao
                verifyCallPhonePermission();
                return true;
            case R.id.dialMi:
                // discar (entra no discador, n faz a chamada direto entao n precisa de permissao)
                Intent discarIntent = new Intent(Intent.ACTION_DIAL);
                discarIntent.setData(Uri.parse("tel:" + activityMainBinding.parametroEt.getText().toString()));
                startActivity(discarIntent);
                return true;
            case R.id.pickMi:
                Intent pegarImagemIntent = new Intent(Intent.ACTION_PICK);
                // onde o arquivo esta e o tipo de arquivo
                // caminho para o diretorio de imagens
                String diretorioImagens = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
                pegarImagemIntent.setDataAndType(Uri.parse(diretorioImagens), "image/*");
                startActivityForResult(pegarImagemIntent, PICK_IMAGE_FILE_REQUEST_CODE);
                return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void verifyCallPhonePermission() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:" + activityMainBinding.parametroEt.getText().toString()));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // verificar se a versao do android é >= 23
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) { // retorna true / false
                // usuario ja deu permissao
                startActivity(ligarIntent);
            } else {
                // solicitar permissão para o usuario em tempo de exec
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_PERMISSION_REQUEST_CODE);
            }
        } else {
            // a premissao foi solicitada no Manifest (versao do android < 23)
            startActivity(ligarIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_PERMISSION_REQUEST_CODE) {
            if (permissions[0].equals(Manifest.permission.CALL_PHONE) && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de ligação é necessaria", Toast.LENGTH_SHORT).show();
            }
            verifyCallPhonePermission();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // resultCode, setResult
        super.onActivityResult(requestCode, resultCode, data);

        // qnd clica em back o result não é o RESULT_OK e n entra
        if (requestCode == OUTRA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // recebendo o retorno da OutraActivity
            Toast.makeText(this, "Voltou para MainActivity", Toast.LENGTH_SHORT).show();

            String retorno = data.getStringExtra(OutraActivity.RETORNO); //chave
            if (retorno != null) {
                activityMainBinding.retornoTv.setText(retorno);
            }
        }
        else {
            //recebendo retorno da Activity pegar imagem
            if (requestCode == PICK_IMAGE_FILE_REQUEST_CODE && resultCode == RESULT_OK) {
                Uri imagemUri = data.getData();

                Intent visualizarImagem = new Intent(Intent.ACTION_VIEW, imagemUri); // exemplo sem o setData
                startActivity(visualizarImagem);
            }
        }
    }
}