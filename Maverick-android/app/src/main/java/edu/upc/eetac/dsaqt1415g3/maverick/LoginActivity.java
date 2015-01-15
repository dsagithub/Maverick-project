package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.User;

/**
 * Created by david on 02/01/2015.
 */

public class LoginActivity extends Activity {
    private final static String TAG = LoginActivity.class.getName();
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences prefs = getSharedPreferences("maverick-profile",
                Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String password = prefs.getString("userpass", null);



        if ((username != null) && (password != null)) {
            Intent intent = new Intent(this, MaverickMainActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_maverick_main);
    }

    public void signIn(View v) throws AppException {
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etPassword = (EditText) findViewById(R.id.etPassword);

        final String username = etUsername.getText().toString();
        final String password = etPassword.getText().toString();

        //Se debería acceder a la API y comprobar que las credenciales son correctas

// Launch a background task to check if credentials are correct
// If correct, store username and password and start uTroll activity
// else, handle error

        (new checkLoginTask()).execute(username, password);

// I'll suppose that u/p are correct:
    }

    private void startMaverickActivity() {
        Intent intent = new Intent(this, MaverickMainActivity.class);
        startActivity(intent);
        finish();

        //String urlUser = user.getLinks().get("self").getTarget();
//System.out.println("miramos" + urlUser);
        //Intent intent = new Intent(this, MaverickMainActivity.class);
        //intent.putExtra("url", urlUser);
        //startActivity(intent);
        //finish(); //Si no a
    }

    private void evaluateLogin(Boolean loginOK) {
        if (loginOK) {
            EditText etUsername = (EditText) findViewById(R.id.etUsername);
            EditText etPassword = (EditText) findViewById(R.id.etPassword);

            final String username = etUsername.getText().toString();
            final String password = etPassword.getText().toString();
           // System.out.println(username);

            SharedPreferences prefs = getSharedPreferences("maverick",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.putString("username", username);
            editor.putString("password", password);

            boolean done = editor.commit();
            if (done)
                Log.d(TAG, "preferences set");
            else
                Log.d(TAG, "preferences not set. THIS A SEVERE PROBLEM");

            startMaverickActivity();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Usuario o contraseña incorrectos";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private class checkLoginTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog pd;

       @Override
        protected Boolean doInBackground(String... params) {
            Boolean correctLogin = false;
            try {
                correctLogin = MaverickAPI.getInstance(LoginActivity.this)
                        .checkLogin(params[0], params[1]);

            } catch (AppException e) {
                e.printStackTrace();
            }
            return correctLogin;
        }


        @Override
        protected void onPostExecute(Boolean loginOK) {
            evaluateLogin(loginOK);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(LoginActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }
}