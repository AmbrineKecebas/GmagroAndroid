package com.example.gmagro_webservice.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Intervenant {
    private int id;
    private String nom;
    private String prenom;
    private String password;
    private String mail;
    private String site_uai ;
    private String role_code;


    public Intervenant() {
    }

    public Intervenant(int id, String nom, String prenom, String mdp, String mail, String site_uai, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = mdp;
        this.mail = mail;
        this.site_uai = site_uai;
        this.role_code = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return password;
    }

    public void setMdp(String mdp) {
        this.password = mdp;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getSite_uai() {
        return site_uai;
    }

    public void setSite_uai(String site_uai) {
        this.site_uai = site_uai;
    }

    public String getRole_code() {
        return role_code;
    }

    public void setRole_code(String role) {
        this.role_code = role;
    }

    @Override
    public String toString() {
        {
            return nom + " " + prenom;
        }
    }
}
