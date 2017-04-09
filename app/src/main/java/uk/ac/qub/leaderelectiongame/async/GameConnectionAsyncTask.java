package uk.ac.qub.leaderelectiongame.async;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class GameConnectionAsyncTask extends AsyncTask<Void, String, Void> {

    private static final String SERVER_IP = "54.245.51.53"; //ip from aws
    private static final int SERVER_PORT = 2222;
    private static final int TIMEOUT_MS = 3000;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.e("serverAddr", serverAddr.toString());
            Log.e("TCP Client", "C: Connecting...");
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(serverAddr, SERVER_PORT), TIMEOUT_MS);
                Log.e("TCP Server IP", SERVER_IP);
                Log.e("TCP Client", "C: Sent.");
                Log.e("TCP Client", "C: Done.");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    if (isCancelled())  {
                        break;
                    }   //if
                    if (in.ready()) {
                        String serverMessage = in.readLine();
                        if (!TextUtils.isEmpty(serverMessage)) {
                            publishProgress(serverMessage);
                            Log.e("RESPONSE FROM SERVER", "S: Received Message: '" + serverMessage + "'");
                        }   //if
                    }   //if
                }
            } catch (SocketTimeoutException ex) {
                publishProgress(String.format("Server did not respond properly - timeout... Please, check if these parameters are correct. IP address: %s, port: %d", SERVER_IP, SERVER_PORT));
                Log.e("TCP", "S: Error", ex);
            } catch (IOException ex) {
                publishProgress("Error occurred during the connection with server...");
                Log.e("TCP", "S: Error", ex);
            } catch (Exception ex) {
                publishProgress("Error occurred...");
                Log.e("TCP", "S: Error", ex);
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            publishProgress("Error occurred...");
            Log.e("TCP", "C: Error", e);
        }
        return null;
    }

}
