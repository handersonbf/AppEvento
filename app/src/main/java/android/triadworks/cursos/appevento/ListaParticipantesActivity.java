package android.triadworks.cursos.appevento;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.triadworks.cursos.appevento.adapter.ParticipantesAdapter;
import android.triadworks.cursos.appevento.converter.ParticipanteConverter;
import android.triadworks.cursos.appevento.dao.ParticipanteDAO;
import android.triadworks.cursos.appevento.modelo.Participante;
import android.triadworks.cursos.appevento.services.WebClient;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ListaParticipantesActivity extends AppCompatActivity {

    private ListView listaParticipantes;
    private ArrayAdapter<Participante> adapter;
    private Participante participante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_participantes);


//        String[] participantes = {
//                "Handerson Frota", "Guilherme Frota", "Nahan Frota",
//                "William Frota", "Gabriel Frota"};


        this.listaParticipantes = (ListView) findViewById(R.id.lista_participantes);

        registerForContextMenu(this.listaParticipantes);

        this.listaParticipantes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Participante participanteSelecionado =
                        (Participante) parent.getItemAtPosition(position);


                Intent irParaOFormulario = new Intent(ListaParticipantesActivity.this,
                        FormularioActivity.class);
                irParaOFormulario.putExtra("participanteSelecionado", participanteSelecionado);

                startActivity(irParaOFormulario);
            }
        });

        this.listaParticipantes.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id) {

                        participante = (Participante) parent.getItemAtPosition(position);

                        Toast.makeText(ListaParticipantesActivity.this,
                                "Clique longo",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });


        Button botaoAdiciona = (Button) findViewById(R.id.bt_flutuante_lista_participantes);
        botaoAdiciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intencao = new Intent(ListaParticipantesActivity.this,
                        FormularioActivity.class);
                startActivity(intencao);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inflate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.novo:
                Intent intencao = new Intent(this, FormularioActivity.class);
                startActivity(intencao);
                break;
            case R.id.proximos_eventos:
                Intent intentEventos = new Intent(this, EventosActivity.class);
                startActivity(intentEventos);
                break;
            case R.id.exportar_json:
                exportarListaJSON();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        carregarListaParticipantes();
    }

    private void carregarListaParticipantes() {
        ParticipanteDAO dao = new ParticipanteDAO(ListaParticipantesActivity.this);
        List<Participante> participantes = dao.getLista();

        ParticipantesAdapter adapter = new ParticipantesAdapter(this,participantes);

        this.listaParticipantes.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        menu.add("Tirar foto");

        MenuItem menuLigar = menu.add("Ligar...");
        menuLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaParticipantesActivity.this,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(ListaParticipantesActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);

                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + participante.getTelefone()));

                    startActivity(intentLigar);
                }

                return false;
            }
        });

        MenuItem menuSMS = menu.add("Enviar SMS");

        Intent itemSMS = new Intent(Intent.ACTION_VIEW);
        itemSMS.setData(Uri.parse("sms:" + participante.getTelefone()));
        menuSMS.setIntent(itemSMS);


        MenuItem menuMapa = menu.add("Ver no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + Uri.encode(participante.getEndereco())));
        menuMapa.setIntent(intentMapa);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                ParticipanteDAO dao = new ParticipanteDAO(ListaParticipantesActivity.this);

                dao.deletar(participante);
                dao.close();

                carregarListaParticipantes();

                Snackbar.make(listaParticipantes, participante.getNome()
                        + " deletado com Sucesso!", Snackbar.LENGTH_LONG).show();
                return false;
            }
        });

        MenuItem menuGit = menu.add("Usuário no GIT");
        menuGit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                WebClient web = new WebClient();
                String resposta = web.get(participante.getNome());

                Toast.makeText(ListaParticipantesActivity.this, resposta, Toast.LENGTH_LONG).show();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void exportarListaJSON(){
        ParticipanteDAO dao = new ParticipanteDAO(this);
        List<Participante> lista = dao.getLista();
        dao.close();

        ParticipanteConverter conversor = new ParticipanteConverter();
        String json = conversor.converterParaJSON(lista);

        Toast.makeText(this, json, Toast.LENGTH_LONG).show();
    }
}
