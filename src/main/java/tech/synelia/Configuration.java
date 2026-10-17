package tech.synelia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private String cle;
    private String valeur;

    public Configuration(String cle, String valeur){
        this.cle = cle;
        this.valeur = valeur;
    }

    public void setCle(String cle){this.cle = cle;}
    public String getCle(){return this.cle;}

    public void setValeur(String valeur){this.valeur = valeur;}
    public String getValeur(){return this.valeur;}

    public static Configuration toEntity(ResultSet rs) throws SQLException {
        Configuration myEntity = new Configuration(
                rs.getString("cle"),
                rs.getString("valeur"));
        return myEntity;
    }



    public static List<Configuration> toEntities(ResultSet rs) throws SQLException {
        List<Configuration> entities = new ArrayList<>();
        while (rs.next()){
            Configuration myEntity = toEntity(rs);
            entities.add(myEntity);
        }
        return entities;
    }

}
