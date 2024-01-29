package com.example.tanosvenyek;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadData {
    private String telnev;
    private String utnev;

    public ReadData(ResultSet rs) throws SQLException {
        this.telnev = rs.getString("telnev");
        this.utnev = rs.getString("utnev");
    }

    public String getTelnev() {
        return telnev;
    }

    public void setTelnev(String telnev) {
        this.telnev = telnev;
    }

    public String getUtnev() {
        return utnev;
    }

    public void setUtnev(String utnev) {
        this.utnev = utnev;
    }
}
