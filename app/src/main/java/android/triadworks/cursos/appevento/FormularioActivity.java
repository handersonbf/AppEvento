package android.triadworks.cursos.appevento;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.triadworks.cursos.appevento.dao.ParticipanteDAO;
import android.triadworks.cursos.appevento.helper.FormularioHelper;
import android.triadworks.cursos.appevento.modelo.Participante;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class FormularioActivity extends AppCompatActivity {

    private static final int CODIGO_CAMERA = 500;
    private FormularioHelper helper;
    private String caminhoFoto;


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
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                caminhoFoto = getExternalFilesDir(null) + "/"
                        + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);

                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {

                helper.carregarImagem(caminhoFoto);
            }
        }
    }
}
