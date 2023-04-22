package com.example.gmagro_webservice.beans;

public class IntervenantTpsPassee {
    private Intervenant intervenant;
    private String temps_passee;

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public String getTemps_passee() {
        return temps_passee;
    }

    public void setTemps_passee(String temps_passee) {
        this.temps_passee = temps_passee;
    }

    @Override
    public String toString() {
        return intervenant.getNom() + " " + intervenant.getPrenom() +" "+ temps_passee;
    }
}
