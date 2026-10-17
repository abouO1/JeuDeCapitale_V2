package tech.synelia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Capitale {
    private Integer id;
    private String nom;
    private String type_capitale;
    private Pays pays;

    public Capitale(int id, String nom, String type_capitale, Pays pays){
        this.id = id;
        this.nom = nom;
        this.type_capitale = type_capitale;
        this.pays = pays;
    }

    public void setId(int id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNom(){
        return this.nom;
    }

    public void setType_capitale(String type_capitale){
        this.type_capitale = type_capitale;
    }
    public String getType_capitale(){
        return this.type_capitale;
    }

    public void setPays(Pays pays){
        this.pays = pays;
    }
    public Pays getPays(){
        return this.pays;
    }

    public static Capitale toEntity(ResultSet rs) throws SQLException {
        Capitale myEntity = new Capitale(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("type_capitale"),
                recupererPays(rs.getInt("pays_id"))
        );
        return myEntity;
    }

    private static Pays recupererPays(int pays_id) throws SQLException {
        JdbcConnector jdbcConnector = new JdbcConnector(
                "jdbc:postgresql://localhost:5432/JeuDeCapitale",
                "admin",
                "secret123"
        );
        jdbcConnector.connect();
        Statement stm = jdbcConnector.getConnection().createStatement();
        ResultSet rs = stm.executeQuery("SELECT * FROM pays WHERE id = "+pays_id);
        List<Pays> pays = Pays.toEntities(rs);
        jdbcConnector.disconnect();

        return pays.isEmpty() ? null : pays.get(0);
    }

    public static List<Capitale> toEntities(ResultSet rs) throws SQLException {
        List<Capitale> entities = new ArrayList<>();
        while (rs.next()){
            Capitale myEntity = toEntity(rs);
            entities.add(myEntity);
        }
        return entities;
    }
}
