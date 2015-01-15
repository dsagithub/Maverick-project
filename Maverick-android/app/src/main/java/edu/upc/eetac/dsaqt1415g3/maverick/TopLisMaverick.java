package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by david on 12/01/2015.
 */
public class TopLisMaverick extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.top_list_menu);



    }

    public void startListActivityRock(View v){
        Intent intent = new Intent(this, ListarGeneroMaverick.class);
        startActivity(intent);

    }
    public void startListActivityIndie(View v){
        Intent intent = new Intent(this, indie.class);
        startActivity(intent);

    }
    public void startListActivityPop(View v){
        Intent intent = new Intent(this, pop.class);
        startActivity(intent);

    }
    public void startListActivityPachangeo(View v){
        Intent intent = new Intent(this, pachangeo.class);
        startActivity(intent);

    }
    public void startListActivityedm(View v){
        Intent intent = new Intent(this, edm.class);
        startActivity(intent);

    }
    public void startListActivityOtros(View v){
        Intent intent = new Intent(this, otros.class);
        startActivity(intent);

    }
    public void startListActivityLikes(View v){
        Intent intent = new Intent(this, toplist.class);
        startActivity(intent);

    }




}
