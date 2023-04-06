package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PracticalTest01Var04Service extends Service {
    public PracticalTest01Var04Service() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String name = intent.getStringExtra("name");
        String group = intent.getStringExtra("group");

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                Intent intent1 = new Intent("android.intent.action.broadcast");
                intent1.putExtra("name", name);
                intent1.putExtra("group", group);
                sendBroadcast(intent1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}