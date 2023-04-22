package com.example.gmagro_webservice.beans;

import java.util.Date;

public class Machine {
    private String code ;
    private Date date_fabrication;
    private int numero_serie;
    private int site_uai ;
    private int type_machine_code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDate_fabrication() {
        return date_fabrication;
    }

    public void setDate_fabrication(Date date_fabrication) {
        this.date_fabrication = date_fabrication;
    }

    public int getNumero_serie() {
        return numero_serie;
    }

    public void setNumero_serie(int numero_serie) {
        this.numero_serie = numero_serie;
    }

    public int getSite_uai() {
        return site_uai;
    }

    public void setSite_uai(int site_uai) {
        this.site_uai = site_uai;
    }

    public int getType_machine_code() {
        return type_machine_code;
    }

    public void setType_machine_code(int type_machine_code) {
        this.type_machine_code = type_machine_code;
    }
}
