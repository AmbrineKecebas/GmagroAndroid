package com.example.gmagro_webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gmagro_webservice.beans.Intervention;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListeInterv extends AppCompatActivity {
    private Button deconnexion ;
    private Button ajoutInterv ;
    private List<Intervention> lesInterventions = new ArrayList<>( ) ;
    private ArrayAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_interv);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,lesInterventions) ;
        ((ListView) findViewById(R.id.lvInterventionsNterm)).setAdapter(adapter);

        createAndLaunchASWSGetIntervNTerm();



        deconnexion = (Button) findViewById(R.id.deconnexion);
        deconnexion.setOnClickListener(view -> {
            createAndLaunchASWSDecInterv();
            finish();
        });
        ajoutInterv = (Button) findViewById(R.id.ajoutInterv);
        ajoutInterv.setOnClickListener(view -> {
            Intent i = new Intent(this, AddInterventions.class);
            startActivity(i);
        });
        ((TextView) (findViewById(R.id.tvNomInterv))).setText("Vous êtes connecté en tant que" + " " +Connexion.moi.getNom()+" "+ Connexion.moi.getPrenom()) ;

    }

    private void createAndLaunchASWSDecInterv() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourDecInterv(s);
            }
        };
        ws.execute("uc=deconnexion");
    }

    private void traiterRetourDecInterv(WSResponse s) {
        Log.d("TRAITER-RETOUR-DECMEMBRES", s.toString());


            if (s.isSuccess()) {
                Toast.makeText(this, "Vous êtes déconnecté.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Connexion.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
            }

    }


    private void createAndLaunchASWSGetIntervNTerm() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetInterventionsNTerm(s);
            }
        };
        ws.execute("uc=listerInterventionsNonTerm&site_uai="+ Connexion.moi.getSite_uai());
    }

    private void traiterRetourGetInterventionsNTerm(WSResponse wsr) {
       if( wsr.isSuccess()){
           lesInterventions.clear();
           String s = wsr.getResult() ;
           ObjectMapper mapper = new ObjectMapper() ;
           try {
               Arrays.asList(mapper.readValue(s, Intervention[].class)).forEach(i->lesInterventions.add(i) ) ;
                adapter.notifyDataSetChanged();
           } catch (JsonProcessingException e) {
               e.printStackTrace();
           }
       }
    }
}