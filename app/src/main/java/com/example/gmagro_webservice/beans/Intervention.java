package com.example.gmagro_webservice.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intervention {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dh_debut;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dh_fin;
    private String commentaire = "";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dh_creation;
    private String temps_arret;
    private int changement_organe;
    private int perte;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dh_derniere_maj;
    private int intervenant_id;
    private String activite_code;
    private String machine_code;
    private String cause_defaut_code;
    private String cause_objet_code;
    private String symptome_defaut_code;
    private String symptome_objet_code;
    private String libelle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDh_debut() {
        return dh_debut;
    }

    public void setDh_debut(Date dh_debut) {
        this.dh_debut = dh_debut;
    }

    public Date getDh_fin() {
        return dh_fin;
    }

    public void setDh_fin(Date dh_fin) {
        this.dh_fin = dh_fin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDh_creation() {
        return dh_creation;
    }

    public void setDh_creation(Date dh_creation) {
        this.dh_creation = dh_creation;
    }

    public String getTemps_arret() {
        return temps_arret;
    }

    public void setTemps_arret(String temps_arret) {
        this.temps_arret = temps_arret;
    }

    public int getChangement_organe() {
        return changement_organe;
    }

    public int getPerte() {
        return perte;
    }

    public void setChangement_organe(int changement_organe) {
        this.changement_organe = changement_organe;
    }

    public void setPerte(int perte) {
        this.perte = perte;
    }

    public Date getDh_derniere_maj() {
        return dh_derniere_maj;
    }

    public void setDh_derniere_maj(Date dh_derniere_maj) {
        this.dh_derniere_maj = dh_derniere_maj;
    }

    public int getIntervenant_id() {
        return intervenant_id;
    }

    public void setIntervenant_id(int intervenant_id) {
        this.intervenant_id = intervenant_id;
    }

    public String getActivite_code() {
        return activite_code;
    }

    public void setActivite_code(String activite_code) {
        this.activite_code = activite_code;
    }

    public String getMachine_code() {
        return machine_code;
    }

    public void setMachine_code(String machine_code) {
        this.machine_code = machine_code;
    }

    public String getCause_defaut_code() {
        return cause_defaut_code;
    }

    public void setCause_defaut_code(String cause_defaut_code) {
        this.cause_defaut_code = cause_defaut_code;
    }

    public String getCause_objet_code() {
        return cause_objet_code;
    }

    public void setCause_objet_code(String cause_objet_code) {
        this.cause_objet_code = cause_objet_code;
    }

    public String getSymptome_defaut_code() {
        return symptome_defaut_code;
    }

    public void setSymptome_defaut_code(String symptome_defaut_code) {
        this.symptome_defaut_code = symptome_defaut_code;
    }

    public String getSymptome_objet_code() {
        return symptome_objet_code;
    }

    public void setSymptome_objet_code(String symptome_objet_code) {
        this.symptome_objet_code = symptome_objet_code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return id + " " + formatageDate(dh_debut) + " " + libelle + " " + machine_code;
    }

    private String formatageDate(Date date) {
        String pattern = "dd-M-yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
