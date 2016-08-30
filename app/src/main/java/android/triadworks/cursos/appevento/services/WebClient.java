package android.triadworks.cursos.appevento.services;

import android.triadworks.cursos.appevento.converter.ParticipanteConverter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by handersonbf on 30/08/16.
 */
public class WebClient {

    public String get(String nome){
        try {


            URL url = new URL("https://api.github.com/search/users?q="
                    + nome.replace(" ", "%20"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            ParticipanteConverter converter = new ParticipanteConverter();

            return converter.converterParaObjeto(resposta);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
