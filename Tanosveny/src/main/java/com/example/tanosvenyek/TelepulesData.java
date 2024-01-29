package com.example.tanosvenyek;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TelepulesData {
    Integer telid;
    String telnev;

    public TelepulesData() {
    }

    public TelepulesData(ResultSet rs) throws SQLException {
        this.telid = rs.getInt("telid");
        this.telnev = rs.getString("telnev");
    }

    @Override
    public String toString() {

        return "TelepulesData{" +
                "telid=" + telid +
                ", telnev='" + telnev + '\'' +
                '}';
    }

    public String getTelnev() {
        return telnev;
    }

    public void setTelnev(String telnev) {
        this.telnev = telnev;
    }

    public Integer gettelid() {
        return telid;
    }

    public void settelid(Integer telid) {
        this.telid = telid;
    }

}
