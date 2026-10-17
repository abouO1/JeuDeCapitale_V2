package tech.synelia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;

    public Utilisateur(int id, String nom, String prenom, String email){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
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

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public String getPrenom(){
        return this.prenom;
    }

    public void setEmail(String eamil){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }

    public static Utilisateur toEntity(ResultSet rs) throws SQLException {
     Utilisateur myEntity = new Utilisateur(
             rs.getInt("id"),
             rs.getString("nom"),
             rs.getString("prenom"),
             rs.getString("email")
     );
     return myEntity;
    }

    public static List<Utilisateur> toEntities(ResultSet rs) throws SQLException {
        List<Utilisateur> entities = new ArrayList<>();
        while (rs.next()){
            Utilisateur myEntity = toEntity(rs);
            entities.add(myEntity);
        }
        return entities;
    }
}
