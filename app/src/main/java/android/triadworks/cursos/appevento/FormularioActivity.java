package android.triadworks.cursos.appevento;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.triadworks.cursos.appevento.dao.ParticipanteDAO;
import android.triadworks.cursos.appevento.helper.FormularioHelper;
import android.triadworks.cursos.appevento.modelo.Participante;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FormularioActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final int CODIGO_CAMERA = 500;
    private static final int CODIGO_GALERIA = 100;
    private FormularioHelper helper;
    private String caminhoFoto;

    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Button botao = (Button) findViewById(R.id.btn_inserir);
        helper = new FormularioHelper(this);

        final Participante participanteSelecionado = (Participante)
                getIntent().getSerializableExtra("participanteSelecionado");

        if(participanteSelecionado != null){
            helper.carregaParticipanteNoFormulario(participanteSelecionado);
            botao.setText("Alterar");
        }

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Participante participanteDoFormulario = helper.getParticipanteDoFormulario();

                ParticipanteDAO dao = new ParticipanteDAO(FormularioActivity.this);

                if (participanteSelecionado != null){
                    participanteDoFormulario.setId(participanteSelecionado.getId());
                    dao.alterar(participanteDoFormulario);
                } else{
                    dao.inserir(participanteDoFormulario);
                }

                dao.close();

                finish();
            }
        });


        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuImagem(v);
            }
        });

        checaPermissoesGaleria();

    }

    public void showMenuImagem(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_foto, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                selecionarCamera();
                break;
            case R.id.galeria:
                selecionarGaleria();
                break;
            default:
                break;
        }
        return false;
    }

    public void selecionarGaleria(){
        Intent abrirGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        abrirGaleria.setType("image/*");
        startActivityForResult(abrirGaleria,CODIGO_GALERIA);
    }

    public void selecionarCamera(){
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        caminhoFoto = getExternalFilesDir(null) + "/"
                + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);

        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
        startActivityForResult(intentCamera, CODIGO_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.carregarImagem(caminhoFoto);
            }

            if (requestCode == CODIGO_GALERIA) {
                helper.carregarImagem(getUrlImage(data.getData()));
            }

        }
    }

    /**
     * Utilizado para recuperar o caminho real da imagem selecionada na galeria para que ela seja
     * recarregada e adicionada no layout.
     * Só é possível recuperar ao passar na Intent que aciona a galeria, uma ACTION_PICK
     * e o MediaStore.Images.Media.EXTERNAL_CONTENT_URI
     * @param uri
     * @return
     */
    private String getUrlImage(Uri uri) {

        String picturePath = "";
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);

            if (picturePath == null) {
                picturePath = uri.getPath();
            }
            cursor.close();
        }

        return picturePath;
    }

    private void checaPermissoesGaleria(){
        if (ActivityCompat.checkSelfPermission(FormularioActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(FormularioActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        }
    }
}
