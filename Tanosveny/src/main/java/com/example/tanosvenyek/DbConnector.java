package com.example.tanosvenyek;
import java.sql.*;
import java.util.ArrayList;

public class DbConnector {
    private Connection conn;

    public DbConnector() {connect();}

    public void connect() {
        try {
            // db parameters
            String url = "jdbc:sqlite::resource:adatbazis.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private String generateSqlString(String utnev, String telepules, Boolean vezetes, Integer ido) {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        sb.append("select ut.utnev, telepules.telnev from ut inner join telepules on ut.telepulesid = telepules.telid inner join np on np.parkid = telepules.npid ");
        if (utnev != null || telepules != null || vezetes != null || ido != null) {sb.append("where ");}
        if (utnev != null && !utnev.isBlank()) {
            sb.append("ut.utnev = '").append(utnev).append("'");
            first = false;
        }
        if (telepules != null && !telepules.isBlank()) {
            if (!first) {
                sb.append(" AND ");
            }
            sb.append("telepules.telnev = '").append(telepules).append("'");
            first = false;
        }
        if (vezetes != null) {
            if (!first) {
                sb.append(" AND ");
            }
            sb.append("ut.vezetes = ").append(vezetes ? "0" : "1");
            first = false;
        }
        if (ido != null) {
            if (!first) {
                sb.append(" AND ");
            }
            sb.append("ut.ido = '").append(ido).append("'");
            first = false;
        }

        System.out.println("Generated sql script:");
        System.out.println(sb.toString());

        return sb.toString();
    }

    public ArrayList<ReadData> getReadDataFromDb(String utnev, String telepules, Boolean vezetes, Integer ido) {
        ArrayList<ReadData> res = null;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(generateSqlString(utnev,telepules,vezetes, ido));
            res = new ArrayList<>();
            while(rs.next()) {
                res.add(new ReadData(rs));
            }

        } catch (SQLException ex) {
            System.out.println("failed to create Statement in getReadDataFromDb");
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return res;
    }

    public ArrayList<TelepulesData> getAllUniqueTelepules() {
        Statement stmt = null;
        ArrayList<TelepulesData> res = null;
        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select distinct * from telepules");

            res = new ArrayList<>();

            while (rs.next()) {
                res.add(new TelepulesData(rs));
                System.out.println("EZ: " + res.toString());
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return res;
    }

    public ArrayList<UtData> getAllUniqueUt() {
        Statement stmt = null;
        ArrayList<UtData> res = null;
        try {
            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select distinct * from ut");

            res = new ArrayList<>();

            while (rs.next()) {
                res.add(new UtData(rs));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return res;
    }

    public boolean insertLocation(TelepulesData cinema, UtData movie) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            stmt.executeUpdate("insert into hely values (" + movie.getutid() + ", " + cinema.telid + ")");


        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean updateTelepules(TelepulesData telepules) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            stmt.executeUpdate(generateupdateTelepulesString(telepules));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try{
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String generateupdateTelepulesString(TelepulesData telepules) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        sb.append("update telepules SET ");
        if (telepules.telid != null) {
            if (telepules.telnev != null && !telepules.telnev.isBlank()) {
                if (!first) {
                    sb.append(" , ");
                }
                sb.append("telnev = '" + telepules.getTelnev() + "'");
                first = false;
            }
        }
        sb.append(" where telid = " + telepules.gettelid().toString());

        System.out.println("generated sql string:");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public boolean deleteUtById(Integer utid) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            stmt.executeUpdate("delete from ut where utid = " + utid.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            try{
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
