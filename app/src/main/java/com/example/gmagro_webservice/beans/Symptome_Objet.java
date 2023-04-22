package com.example.gmagro_webservice.beans;

public class Symptome_Objet {
    private String code ;
    private String libelle ;
    private int site_uai ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getSite_uai() {
        return site_uai;
    }

    public void setSite_uai(int site_uai) {
        this.site_uai = site_uai;
    }

    @Override
    public String toString() {
        return libelle ;
    }
}
