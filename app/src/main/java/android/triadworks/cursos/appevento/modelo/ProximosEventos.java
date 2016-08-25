package android.triadworks.cursos.appevento.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by handersonbf on 25/08/16.
 */
public class ProximosEventos implements Serializable{

    private String evento;
    private String local;
    private String data;
    private List<String> palestras;

    public ProximosEventos(String evento, String data, String local){
        this.evento = evento;
        this.data = data;
        this.local = local;
    }


    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getPalestras() {
        return palestras;
    }

    public void setPalestras(List<String> palestras) {
        this.palestras = palestras;
    }

    @Override
    public String toString() {
        return "Evento:" + evento + " - " + data;
    }
}
