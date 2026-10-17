package tech.synelia;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pays {
    private Integer id;
    private String nom;
    private BigDecimal superficie;

    public Pays(int id, String nom, BigDecimal superficie){
        this.id = id;
        this.nom = nom;
        this.superficie = superficie;
    }

    void setId(int id){
        this.id = id;
    }
    int getId(){
        return this.id;
    }

    void setNom(String nom){
        this.nom = nom;
    }
    String getNom(){
        return this.nom;
    }

    void setSuperficie(BigDecimal superficie){
        this.superficie = superficie;
    }
    BigDecimal getSuperficie(){
        return this.superficie;
    }

    public static Pays toEntity(ResultSet rs) throws SQLException {
        Pays myEntity = new Pays(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getBigDecimal("superficie")
        );
        return myEntity;
    }

    public static List<Pays> toEntities(ResultSet rs) throws SQLException {
        List<Pays> entities = new ArrayList<>();
        while (rs.next()){
            Pays myEntity = toEntity(rs);
            entities.add(myEntity);
        }
        return entities;
    }

}
