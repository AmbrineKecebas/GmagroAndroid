package com.example.gmagro_webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gmagro_webservice.beans.Intervenant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

public class Connexion extends AppCompatActivity {
    private Button connexion;
    public static int REQUEST_LISTER_INTERV = 1;
    public static Intervenant moi ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion);

        connexion = (Button) findViewById(R.id.connexion);

        connexion.setOnClickListener(view -> {
            String login = ((EditText) (findViewById(R.id.login))).getText().toString();

            String mdp = ((EditText) (findViewById(R.id.mdp))).getText().toString();

            createAndExecuteConnexionIntervenant(login, mdp);
        });
    }

    private void createAndExecuteConnexionIntervenant(String login, String mdp) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                super.onPostExecute(s);
                traiterRetourGetConnexionIntervenant(s);
            }

        };
        ws.execute("uc=connexion&mail=" + login + "&mdp=" + mdp);
    }

    private void traiterRetourGetConnexionIntervenant(WSResponse s) {
        Log.d("TRAITER-RETOUR-CONNEXION-INTERVENANT", s.toString());

            if (s.isSuccess()) {
                ObjectMapper oM = new ObjectMapper();
                try {
                    moi = oM.readValue(s.getResult(), Intervenant.class);
                    Intent i = new Intent(this, ListeInterv.class);
                    startActivityForResult(i, REQUEST_LISTER_INTERV);
                    Toast.makeText(this, "Connexion", Toast.LENGTH_SHORT).show();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Mauvais login ou mdp", Toast.LENGTH_SHORT).show();

            }

    }


}