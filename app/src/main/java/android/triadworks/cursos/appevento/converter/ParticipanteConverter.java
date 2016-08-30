package android.triadworks.cursos.appevento.converter;

import android.triadworks.cursos.appevento.modelo.Participante;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by handersonbf on 30/08/16.
 */
public class ParticipanteConverter {

    public String converterParaJSON(List<Participante> participantes){
        JSONStringer js = new JSONStringer();

        try {
            js.object()
                    .key("lista").array()
                    .object()
                    .key("participante").array();

            for (Participante p : participantes) {
                js.object(); //abrindo ob para cada participante

                js.key("id").value(p.getId());
                js.key("nome").value(p.getNome());
                js.key("email").value(p.getEmail());

                js.endObject(); //fechando objeto criado
            }

            js.endArray()
                    .endObject()
                    .endArray()
                    .endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }

}
