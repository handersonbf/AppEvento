package android.triadworks.cursos.appevento.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.triadworks.cursos.appevento.EventosActivity;
import android.triadworks.cursos.appevento.R;
import android.triadworks.cursos.appevento.modelo.ProximosEventos;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by handersonbf on 25/08/16.
 */
public class ListaEventosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragments_lista_eventos,
                container, false);

        ListView listViewEventos = (ListView) layout.findViewById(R.id.lista_eventos);

        ProximosEventos evento01 = new ProximosEventos("JAVOU 8",
                "10/01/2017", "Faculdade X");
        evento01.setPalestras(Arrays.asList("Palestra01",
                "Palestra02", "Palestra03"));


        ProximosEventos evento02 = new ProximosEventos("JAVOU 9",
                "10/02/2017", "Faculdade Y");
        evento02.setPalestras(Arrays.asList("Palestra01",
                "Palestra02", "Palestra03"));

        ProximosEventos evento03 = new ProximosEventos("JAVOU 10",
                "10/04/2017", "Faculdade Z");
        evento03.setPalestras(Arrays.asList("Jogos Android",
                "Usabilidade Android"));

        List<ProximosEventos> eventosList = Arrays.asList(evento01,
                evento02, evento03);

        ArrayAdapter<ProximosEventos> adpter =
                new ArrayAdapter<ProximosEventos>(getActivity(),
                        R.layout.support_simple_spinner_dropdown_item, eventosList);

        listViewEventos.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view, int position, long id) {
                        ProximosEventos eventoSelecionado =
                                (ProximosEventos) parent.getItemAtPosition(position);
                        EventosActivity activity = (EventosActivity) getActivity();
                        activity.selecionarEvento(eventoSelecionado);

                    }
                });

        listViewEventos.setAdapter(adpter);



        return layout;
    }


}
