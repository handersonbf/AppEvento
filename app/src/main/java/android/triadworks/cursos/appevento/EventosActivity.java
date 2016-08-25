package android.triadworks.cursos.appevento;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.triadworks.cursos.appevento.fragments.DetalhesEventosFragment;
import android.triadworks.cursos.appevento.fragments.ListaEventosFragment;
import android.triadworks.cursos.appevento.modelo.ProximosEventos;
import android.view.MenuItem;
import android.widget.Toolbar;

/**
 * Created by handersonbf on 25/08/16.
 */
public class EventosActivity extends Activity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.eventos);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if(ehUmTablet()){
            ft.replace(R.id.eventos_lista, new ListaEventosFragment());
            ft.replace(R.id.eventos_detalhe, new DetalhesEventosFragment());
        }else{
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Início da APP");

            toolbar.setBackgroundResource(R.drawable.toolbar);

            setActionBar(toolbar);
            getActionBar().setDisplayHomeAsUpEnabled(true);

            ft.replace(R.id.eventos_view, new ListaEventosFragment());
        }

        ft.commit();
    }

    private boolean ehUmTablet(){
        return getResources().getBoolean(R.bool.ehTablet);
    }

    public void selecionarEvento(ProximosEventos evento)  {
        Bundle pendurado = new Bundle();
        pendurado.putSerializable("evento", evento);

        DetalhesEventosFragment detalhes = new DetalhesEventosFragment();
        detalhes.setArguments(pendurado);


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if(ehUmTablet()){
            ft.replace(R.id.eventos_detalhe, detalhes);
        }else{
            ft.replace(R.id.eventos_view, detalhes);
            ft.addToBackStack(null);
        }

        ft.commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
