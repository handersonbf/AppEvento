package android.triadworks.cursos.appevento.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.triadworks.cursos.appevento.FormularioActivity;
import android.triadworks.cursos.appevento.R;
import android.triadworks.cursos.appevento.modelo.Participante;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by handersonbf on 19/08/16.
 */
public class FormularioHelper {
    private EditText campoNome;
    private EditText campoEmail;
    private EditText campoTelefone;
    private EditText campoEndereco;
    private ImageView campoFoto;
    private Participante participante;

    public FormularioHelper(FormularioActivity activity){
        participante = new Participante();

        campoNome = (EditText) activity.findViewById(R.id.edt_nome);
        campoEmail = (EditText) activity.findViewById(R.id.edt_email);
        campoTelefone = (EditText) activity.findViewById(R.id.edt_telefone);
        campoEndereco = (EditText) activity.findViewById(R.id.edt_endereco);
        campoFoto = (ImageView) activity.findViewById(R.id.formulario_foto);
    }

    public Participante getParticipanteDoFormulario(){
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String endereco = campoEndereco.getText().toString();
        String caminhoFoto = (String) campoFoto.getTag();

        participante.setNome(nome);
        participante.setEmail(email);
        participante.setTelefone(telefone);
        participante.setEndereco(endereco);
        participante.setCaminhoFoto(caminhoFoto);

        return participante;
    }

    public void carregaParticipanteNoFormulario(Participante participante){
        this.campoNome.setText(participante.getNome());
        this.campoEmail.setText(participante.getEmail());
        this.campoTelefone.setText(participante.getTelefone());
        this.campoEndereco.setText(participante.getEndereco());
        carregarImagem(participante.getCaminhoFoto());
    }

    public void carregarImagem(String caminhoFoto) {
        if(caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 200, 200, true);

            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);

            campoFoto.setTag(caminhoFoto);
        }
    }
}
