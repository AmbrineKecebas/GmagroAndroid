package com.example.gmagro_webservice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.gmagro_webservice.beans.Activite;
import com.example.gmagro_webservice.beans.Cause_Defaut;
import com.example.gmagro_webservice.beans.Cause_Objet;
import com.example.gmagro_webservice.beans.Intervenant;
import com.example.gmagro_webservice.beans.IntervenantTpsPassee;
import com.example.gmagro_webservice.beans.Intervention;
import com.example.gmagro_webservice.beans.MachineSiteIntervenant;
import com.example.gmagro_webservice.beans.Symptome_Defaut;
import com.example.gmagro_webservice.beans.Symptome_Objet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddInterventions extends AppCompatActivity {
    private Button buttonDeconnexion;
    private Button buttonAnnuler;
    private ArrayAdapter adapterActivite;
    private List<Activite> lesActivites = new ArrayList<>();
    private EditText etDateHeureDebut;
    private EditText etDateHeureFin;
    private ArrayAdapter adapterMachine;
    private List<MachineSiteIntervenant> lesMachines = new ArrayList<>();

    private ArrayAdapter adapterSympObj;
    private List<Symptome_Objet> lesSO = new ArrayList<>();

    private ArrayAdapter adapterSympDef;
    private List<Symptome_Defaut> lesSD = new ArrayList<>();

    private ArrayAdapter adapterCauseDef;
    private List<Cause_Defaut> lesCD = new ArrayList<>();

    private ArrayAdapter adapterCauseObj;
    private List<Cause_Objet> lesCO = new ArrayList<>();

    private ArrayAdapter adapterTempsDarret;
    private List<String> TpsArrets = new ArrayList<>();

    private ArrayAdapter adapterTpsPassee;
    private List<String> tpsPassee = new ArrayList<>();

    private ArrayAdapter adapterIntervenants;
    private List<Intervenant> lesIntervenants = new ArrayList<>();

    private ArrayAdapter adapterIntervenantsTempsPassee;
    private List<IntervenantTpsPassee> lesIntervenantsTPS = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_interventions);

        buttonDeconnexion = (Button) findViewById(R.id.buttonDeconnexion);
        buttonDeconnexion.setOnClickListener(view -> {
            createAndLaunchASWSDecInterv();
            finish();
        });
        ((TextView) (findViewById(R.id.tvIntervConnecte))).setText(Connexion.moi.getNom() + " " + Connexion.moi.getPrenom());

        adapterActivite = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesActivites);
        ((Spinner) findViewById(R.id.spActivite)).setAdapter(adapterActivite);
        createAndLaunchASWSGetActivite();

        adapterMachine = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesMachines);
        ((Spinner) findViewById(R.id.spMachine)).setAdapter(adapterMachine);
        createAndLaunchASWSGetMachine();

        adapterSympObj = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesSO);
        ((Spinner) findViewById(R.id.spSymptObjet)).setAdapter(adapterSympObj);
        createAndLaunchASWSGetSO();

        adapterSympDef = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesSD);
        ((Spinner) findViewById(R.id.spSymptDefaut)).setAdapter(adapterSympDef);
        createAndLaunchASWSGetSD();

        adapterCauseDef = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesCD);
        ((Spinner) findViewById(R.id.spCauseDefaut)).setAdapter(adapterCauseDef);
        createAndLaunchASWSGetCD();

        adapterCauseObj = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesCO);
        ((Spinner) findViewById(R.id.spCauseObjet)).setAdapter(adapterCauseObj);
        createAndLaunchASWSGetCO();

        adapterIntervenants = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesIntervenants);
        ((Spinner) findViewById(R.id.spInterv)).setAdapter(adapterIntervenants);
        createAndLaunchASWSGetIntervenants();
        etDateHeureDebut = (EditText) findViewById(R.id.etDateHeureDebut);
        afficherCalendrier(etDateHeureDebut);

        adapterIntervenantsTempsPassee = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lesIntervenantsTPS);
        ((ListView) findViewById(R.id.lvIntervTpsPasse)).setAdapter(adapterIntervenantsTempsPassee);

        etDateHeureFin = (EditText) findViewById(R.id.etDateHeureFin);
        afficherCalendrier(etDateHeureFin);

        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 45; j = j + 15) {
                String minute = String.valueOf(j);
                if (j == 0) {
                    minute = "00";
                }
                String tps = "0" + i + ":" + minute;
                TpsArrets.add(tps);
                tpsPassee.add(tps);

            }

        }
        TpsArrets.add("08:00");
        tpsPassee.add("8:00");

        adapterTempsDarret = new ArrayAdapter(this, android.R.layout.simple_list_item_1, TpsArrets);
        ((Spinner) findViewById(R.id.spTempsArret)).setAdapter(adapterTempsDarret);
        adapterTempsDarret.notifyDataSetChanged();

        adapterTpsPassee = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tpsPassee);
        ((Spinner) findViewById(R.id.spTpsPasse)).setAdapter(adapterTpsPassee);
        adapterTpsPassee.notifyDataSetChanged();

        CheckBox cbMachineArretee = (CheckBox) findViewById(R.id.cbMachineArretee);
        cbMachineArretee.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Spinner tpsArrSp = ((Spinner) findViewById(R.id.spTempsArret));
                if (b == true) {

                    tpsArrSp.setVisibility(View.VISIBLE);
                } else {
                    tpsArrSp.setVisibility(View.INVISIBLE);
                }
            }


        });
        CheckBox cbIntervTerm = (CheckBox) findViewById(R.id.cbIntervTerm);
        cbIntervTerm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                EditText dhFin = ((EditText) findViewById(R.id.etDateHeureFin));
                if (b == true) {

                    dhFin.setVisibility(View.VISIBLE);
                } else {
                    dhFin.setVisibility(View.INVISIBLE);
                }
            }


        });

        Spinner spInt = (Spinner) findViewById(R.id.spInterv);
        Spinner spTpsPass = (Spinner) findViewById(R.id.spTpsPasse);
        spInt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Intervenant intervenant = (Intervenant) spInt.getSelectedItem();
                String nomInterv = "";
                if (intervenant != null) {
                    nomInterv = intervenant.getNom();
                }
                String tpsP = (String) spTpsPass.getSelectedItem();
                Button buttonIntervTpsPasse = (Button) findViewById(R.id.buttonIntervTpsPasse);
                buttonIntervTpsPasse.setText(nomInterv + " " + tpsP);

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spTpsPass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Intervenant intervenant = (Intervenant) spInt.getSelectedItem();
                String nomInterv = "";
                if (intervenant != null) {
                    nomInterv = intervenant.getNom();
                }
                String tpsP = (String) spTpsPass.getSelectedItem();
                Button buttonIntervTpsPasse = (Button) findViewById(R.id.buttonIntervTpsPasse);
                buttonIntervTpsPasse.setText(nomInterv + " " + tpsP);

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button buttonIntervTpsPasse = (Button) findViewById(R.id.buttonIntervTpsPasse);
        buttonIntervTpsPasse.setOnClickListener(view -> {
            Intervenant intervenant = (Intervenant) spInt.getSelectedItem();
            String tpsP = (String) spTpsPass.getSelectedItem();
            IntervenantTpsPassee intTempsPassee = new IntervenantTpsPassee();
            intTempsPassee.setIntervenant(intervenant);
            intTempsPassee.setTemps_passee(tpsP);
            lesIntervenantsTPS.add(intTempsPassee);
            adapterIntervenantsTempsPassee.notifyDataSetChanged();

        });


        Button buttonEnvoyer = (Button) findViewById(R.id.buttonEnvoyer);
        buttonEnvoyer.setOnClickListener(view -> {
            Spinner activiteSp = ((Spinner) findViewById(R.id.spActivite));
            Activite act = (Activite) activiteSp.getSelectedItem();
            Log.d("activite", act.getCode());
            Spinner tempsArretSp = ((Spinner) findViewById(R.id.spTempsArret));
            String tempsArret = (String) tempsArretSp.getSelectedItem();


            Spinner SpMachine = ((Spinner) findViewById(R.id.spMachine));
            MachineSiteIntervenant machine = (MachineSiteIntervenant) SpMachine.getSelectedItem();

            Spinner spSO = ((Spinner) findViewById(R.id.spSymptObjet));
            Symptome_Objet sO = (Symptome_Objet) spSO.getSelectedItem();

            Spinner spSD = ((Spinner) findViewById(R.id.spSymptDefaut));
            Symptome_Defaut sD = (Symptome_Defaut) spSD.getSelectedItem();

            Spinner spCD = ((Spinner) findViewById(R.id.spCauseDefaut));
            Cause_Defaut cD = (Cause_Defaut) spCD.getSelectedItem();

            Spinner spCO = ((Spinner) findViewById(R.id.spCauseObjet));
            Cause_Objet cO = (Cause_Objet) spCO.getSelectedItem();

            CheckBox changementOrgane = findViewById(R.id.cbChangementOrgane);
            boolean changementOrgCocher = changementOrgane.isChecked();

            CheckBox perte = findViewById(R.id.cbPertes);
            boolean perteCocher = perte.isChecked();

            String commentaire = ((EditText) (findViewById(R.id.etCommentaire))).getText().toString();
            String itps = "";
            for (IntervenantTpsPassee intervenantTpsPassee : lesIntervenantsTPS) {
                itps = itps + intervenantTpsPassee.getIntervenant().getId() + "|"
                        + intervenantTpsPassee.getTemps_passee() + "||";

            }
            Intervention interv = new Intervention();
            interv.setActivite_code(act.getCode());
            interv.setMachine_code(machine.getCode());
            interv.setSymptome_objet_code(sO.getCode());
            interv.setSymptome_defaut_code(sD.getCode());
            interv.setCause_defaut_code(cD.getCode());
            interv.setCause_objet_code(cO.getCode());
            interv.setCommentaire(commentaire);
            String dateD = ((EditText) (findViewById(R.id.etDateHeureDebut))).getText().toString();
            LocalDateTime LdateTime = LocalDateTime.parse(dateD, DateTimeFormatter.ofPattern("d-M-yyyy H:m"));
            Instant instant = LdateTime.atZone(ZoneId.systemDefault()).toInstant();
            interv.setDh_debut(Date.from(instant));
            interv.setDh_creation(new Date());
            interv.setTemps_arret(tempsArret);

            String dateF = ((EditText) (findViewById(R.id.etDateHeureFin))).getText().toString();
            LocalDateTime LdateTime2 = LocalDateTime.parse(dateF, DateTimeFormatter.ofPattern("d-M-yyyy H:m"));
            Instant instant2 = LdateTime2.atZone(ZoneId.systemDefault()).toInstant();
            interv.setDh_fin(Date.from(instant2));

            interv.setDh_derniere_maj(new Date());
            int intervenant = Connexion.moi.getId();
            interv.setIntervenant_id(intervenant);
            int chgtOrg = 0;
            if (changementOrgCocher == true) {
                chgtOrg = 1;
            }
            interv.setChangement_organe(chgtOrg);
            int pertes = 0;
            if (perteCocher == true) {
                pertes = 1;
            }
            interv.setPerte(pertes);


            createAndLaunchASWSIntervention(interv, itps);

        });
    }

    private void createAndLaunchASWSGetIntervenants() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetIntervenants(s);
            }
        };
        ws.execute("uc=listerIntervenants&site_uai=" + Connexion.moi.getSite_uai());
    }

    private void traiterRetourGetIntervenants(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesIntervenants.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, Intervenant[].class)).forEach(i -> lesIntervenants.add(i));
                adapterIntervenants.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }


    private void createAndLaunchASWSIntervention(Intervention i, String itps) {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourIntervention(s);
            }
        };
        String formatDhDeb = formatageDate(i.getDh_debut());
        String formatDhFin = formatageDate(i.getDh_fin());
        String formatDhCreation = formatageDate(i.getDh_creation());
        String formatDhDerniereMaj = formatageDate(i.getDh_derniere_maj());


        ws.execute("uc=addIntervention&activite=" + i.getActivite_code() + "&codemachine=" + i.getMachine_code() +
                "&symptomeobjet=" + i.getSymptome_objet_code() + "&symptomedefaut=" + i.getSymptome_defaut_code() +
                "&causeobjet=" + i.getCause_objet_code() + "&causedefaut=" + i.getCause_defaut_code() +
                "&commentaire=" + i.getCommentaire() + "&perte=" + i.getPerte() + "&changement_organe=" + i.getChangement_organe() +
                "&dh_debut=" + formatDhDeb + "&dh_fin=" + formatDhFin + "&dh_creation=" + formatDhCreation +
                "&dh_derniere_maj=" + formatDhDerniereMaj + "&intervenant_id=" + i.getIntervenant_id() + "&temps_arret=" + i.getTemps_arret()
                + "&listeIntervTps=" + itps
        );

    }

    private String formatageDate(Date date) {
        String pattern = "yyyy-M-dd HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    private void traiterRetourIntervention(WSResponse s) {
        Log.d("TRAITER-RETOUR-INTERVENTION", s.toString());


        if (s.isSuccess()) {
            Toast.makeText(this, "Intervention créé.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erreur", Toast.LENGTH_SHORT).show();
        }
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

    private void createAndLaunchASWSGetCO() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetCO(s);
            }
        };
        ws.execute("uc=listerCauseObj&id_intervenant=" + Connexion.moi.getId());
    }

    private void traiterRetourGetCO(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesCO.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, Cause_Objet[].class)).forEach(i -> lesCO.add(i));
                adapterCauseObj.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


    }

    private void createAndLaunchASWSGetCD() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetCD(s);
            }
        };
        ws.execute("uc=listerCauseDef&id_intervenant=" + Connexion.moi.getId());
    }


    private void traiterRetourGetCD(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesCD.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, Cause_Defaut[].class)).forEach(i -> lesCD.add(i));
                adapterCauseDef.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


    }


    private void createAndLaunchASWSGetSD() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetSD(s);
            }
        };
        ws.execute("uc=listerSymptDef&id_intervenant=" + Connexion.moi.getId());
    }

    private void traiterRetourGetSD(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesSD.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, Symptome_Defaut[].class)).forEach(i -> lesSD.add(i));
                adapterSympDef.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    private void createAndLaunchASWSGetSO() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetSO(s);
            }
        };
        ws.execute("uc=listerSymptObj&id_intervenant=" + Connexion.moi.getId());
    }

    private void traiterRetourGetSO(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesSO.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, Symptome_Objet[].class)).forEach(i -> lesSO.add(i));
                adapterSympObj.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    private void createAndLaunchASWSGetMachine() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetMachine(s);
            }
        };
        ws.execute("uc=listerMachines&id_intervenant=" + Connexion.moi.getId());
    }


    private void traiterRetourGetMachine(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesMachines.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, MachineSiteIntervenant[].class)).forEach(i -> lesMachines.add(i));
                adapterMachine.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }


    private void createAndLaunchASWSGetActivite() {
        WSConnexionHTTPS ws = new WSConnexionHTTPS() {
            @Override
            protected void onPostExecute(WSResponse s) {
                traiterRetourGetActivite(s);
            }
        };
        ws.execute("uc=listerActivites");
    }

    private void traiterRetourGetActivite(WSResponse wsr) {
        if (wsr.isSuccess()) {
            lesActivites.clear();
            String s = wsr.getResult();
            ObjectMapper mapper = new ObjectMapper();
            try {
                Arrays.asList(mapper.readValue(s, Activite[].class)).forEach(i -> lesActivites.add(i));
                adapterActivite.notifyDataSetChanged();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    private void afficherCalendrier(EditText et) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        int heure = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        et.setFocusable(false);
        et.setOnClickListener(v -> {

                    DatePickerDialog datePicker = new DatePickerDialog(AddInterventions.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int annee, int mois, int jour) {
                            TimePickerDialog timePicker = new TimePickerDialog(AddInterventions.this, new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker timePicker, int h, int m) {
                                    et.setText(jour + "-" + (mois + 1) + "-" + annee + " " + h + ":" + m);

                                }
                            }, heure, minute, true);

                            timePicker.show();
                        }
                    }, mYear, mMonth, mDay);
                    datePicker.show();
                }
        );

    }

}