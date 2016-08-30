package android.triadworks.cursos.appevento.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by handersonbf on 30/08/16.
 */
public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Opá, chegou SMS :D", Toast.LENGTH_LONG).show();
    }
}
