package com.example.tanosvenyek;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtData {
    private Integer utid;
    private String utnev;
    private Integer vezetes;
    private Integer allomas;
    private Integer ido;
    private Integer hossz;

    public UtData() {
    }

    public UtData(ResultSet rs) throws SQLException {
        this.utid = rs.getInt("utid");
        this.utnev =  rs.getString("utnev");
        this.vezetes =  rs.getInt("vezetes");
        this.allomas =  rs.getInt("allomas");
        this.ido =  rs.getInt("ido");
        this.hossz =  rs.getInt("hossz");
    }

    @Override
    public String toString() {
        return "UtData{" +
                "utid=" + utid +
                ", utnev='" + utnev + '\'' +
                ", vezetes=" + vezetes +
                ", allomas='" + allomas + '\'' +
                ", ido='" + ido + '\'' +
                ", hossz=" + hossz +
                '}';
    }

    public Integer getutid() {
        return utid;
    }

    public void setutid(Integer utid) {
        this.utid = utid;
    }

    public String getutnev() {
        return utnev;
    }

    public void setutnev(String utnev) {
        this.utnev = utnev;
    }

    public Integer getvezetes() {
        return vezetes;
    }

    public void setvezetes(Integer vezetes) {
        this.vezetes = vezetes;
    }

    public Integer getallomas() {
        return allomas;
    }

    public void setallomas(Integer allomas) {
        this.allomas = allomas;
    }

    public Integer getido() {
        return ido;
    }

    public void setido(Integer ido) {
        this.ido = ido;
    }

    public Integer getHossz() {
        return hossz;
    }

    public void setHossz(Integer hossz) {
        this.hossz = hossz;
    }
}
