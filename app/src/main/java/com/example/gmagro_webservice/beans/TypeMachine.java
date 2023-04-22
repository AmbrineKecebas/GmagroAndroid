package com.example.gmagro_webservice.beans;

public class TypeMachine {
    private String code;
    private String nom ;
    private int site_uai ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSite_uai() {
        return site_uai;
    }

    public void setSite_uai(int site_uai) {
        this.site_uai = site_uai;
    }

    @Override
    public String toString() {
        return  nom ;
    }
}
