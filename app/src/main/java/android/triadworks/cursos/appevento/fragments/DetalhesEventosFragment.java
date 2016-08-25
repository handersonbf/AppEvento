package android.triadworks.cursos.appevento.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.triadworks.cursos.appevento.R;
import android.triadworks.cursos.appevento.modelo.ProximosEventos;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by handersonbf on 25/08/16.
 */
public class DetalhesEventosFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragments_eventos_detalhes, container, false);

        if(getArguments()!= null){
            ProximosEventos evento =
                    (ProximosEventos) getArguments().getSerializable("evento");

            Date data = null;
            try {
                data = new SimpleDateFormat("dd/MM/yyyy").parse(evento.getData());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CalendarView calendario = (CalendarView) layout.findViewById(R.id.calendarView);
            calendario.setDate(data.getTime(), true, true);
        }


        return layout;
    }
}
