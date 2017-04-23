package uk.ac.qub.leaderelectiongame.async;

import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NetworkCheckAsyncTask extends AsyncTask<Void, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Void... voids) {
        InetAddress inetAddress = null;
        try {
            Future<InetAddress> future = Executors.newSingleThreadExecutor().submit(new Callable<InetAddress>() {
                @Override
                public InetAddress call() {
                    try {
                        return InetAddress.getByName("google.com");
                    } catch (UnknownHostException e) {
                        return null;
                    }
                }
            });
            inetAddress = future.get(10 * 1000, TimeUnit.MILLISECONDS);
            future.cancel(true);
        } catch (Exception ex) {
            //Log.d(TAG, ex.getMessage());
        }
        return inetAddress!=null && !inetAddress.equals("");
    }
}
