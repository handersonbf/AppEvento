package android.triadworks.cursos.appevento.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.triadworks.cursos.appevento.services.WebClient;
import android.widget.Toast;

/**
 * Created by handersonbf on 30/08/16.
 */
public class BuscarParticipanteGitHubTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private String nome;
    private ProgressDialog loading;

    public BuscarParticipanteGitHubTask(Context context, String nome) {
        this.context = context;
        this.nome = nome;
    }

    protected void onPreExecute() {
        loading = ProgressDialog.show(context, "Aguarde",
                "Pesquisando no GITHUB....",
                true, true);
    }

    protected String doInBackground(Void... params) {
        WebClient web = new WebClient();
        String resposta = web.get(nome);

        return resposta;
    }

    protected void onPostExecute(String o) {
        Toast.makeText(context, o.toString(), Toast.LENGTH_LONG).show();
        loading.dismiss();
    }
}
