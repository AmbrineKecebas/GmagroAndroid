package com.example.gmagro_webservice;

public class WSResponse {
    private boolean success ;
    private String result ;

    public WSResponse(boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "WSResponse{" +
                "success=" + success +
                ", result='" + result + '\'' +
                '}';
    }
}
