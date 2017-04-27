package uk.ac.qub.leaderelectiongame.async;

import android.content.Context;
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

import uk.ac.qub.leaderelectiongame.R;
import uk.ac.qub.leaderelectiongame.consts.Consts;
import uk.ac.qub.leaderelectiongame.game.CipherSocket;

/**
 * Class responsible for communicating with game server, extends AsyncTask.
 */
public class GameConnectionAsyncTask extends AsyncTask<Void, String, Void> {

    /**
     * Listening server IP.
     */
    private static final String SERVER_IP = "54.245.51.53";
    //private static final String SERVER_IP = "10.0.2.2";

    /**
     * Listening server port.
     */
    private static final int SERVER_PORT = 2222;

    /**
     * Server connection timeout.
     */
    private static final int TIMEOUT_MS = 3000;

    private static final String TAG = "GameConnectionAsyncTask";

    /**
     * Context.
     */
    private Context context;

    /**
     * Constructor.
     * @param context
     */
    public GameConnectionAsyncTask(Context context){
        this.context = context;
    }

    @Override
    /**
     * Method to perform a game server communication on a background thread.
     */
    protected Void doInBackground(Void... voids) {
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            Log.i(TAG, serverAddr.toString());
            Log.i(TAG, "C: Connecting...");
            CipherSocket socket = new CipherSocket(Consts.CIPHER_KEY);
            try {
                socket.connect(new InetSocketAddress(serverAddr, SERVER_PORT), TIMEOUT_MS);
                Log.i(TAG, SERVER_IP);
                Log.i(TAG, "C: Sent.");
                Log.i(TAG, "C: Done.");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    if (isCancelled()) {
                        break;
                    }   //if
                    String serverMessage = in.readLine();
                    if (!TextUtils.isEmpty(serverMessage)) {
                        Log.i(TAG, "S: Received Message: '" + serverMessage + "'");
                        if (serverMessage.equals(Consts.MSG_CLOSE_CONNECTION)) {
                            Log.i(TAG, "S: Disconnecting...");
                            publishProgress("Bye!");
                            break;
                        }   //if
                        if (serverMessage.equals(Consts.MSG_ALGORITHM_FINISHED)) {
                            Log.i(TAG, "S: Disconnecting...");
                            publishProgress(serverMessage);
                            publishProgress("Bye!");
                            break;
                        }   //if
                        publishProgress(serverMessage);
                    }   //if
                }
            } catch (SocketTimeoutException ex) {
                publishProgress(String.format(context.getString(R.string.game_server_connection_error) , SERVER_IP, SERVER_PORT));
                Log.e(TAG, "S: Error", ex);
            } catch (IOException ex) {
                publishProgress("Error occurred during the connection with server...");
                Log.e(TAG, "S: Error", ex);
            } catch (Exception ex) {
                publishProgress("Error occurred...");
                Log.e(TAG, "S: Error", ex);
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
