package tech.synelia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Score {
    private Integer id;
    private Date date;
    private int nbre_bne_rpse;
    private int nbre_mvse_rpse;
    private Utilisateur utilisateur;

    public Score(int id, Date date, int nbre_bne_rpse, int nbre_mvse_rpse, Utilisateur utilisateur){
        this.id = id;
        this.date = date;
        this.nbre_bne_rpse = nbre_bne_rpse;
        this.nbre_mvse_rpse = nbre_mvse_rpse;
        this.utilisateur = utilisateur;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return this.date;
    }

    public void setNbre_bne_rpse(int nbre_bne_rpse){
        this.nbre_bne_rpse = nbre_bne_rpse;
    }
    public int getNbre_bne_rpse(){
        return this.nbre_bne_rpse;
    }

    public void setNbre_mvse_rpse(int nbre_mvse_rpse){
        this.nbre_mvse_rpse = nbre_mvse_rpse;
    }
    public int getNbre_mvse_rpse(){
        return this.nbre_mvse_rpse;
    }

    public void setUtilisateur(Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }
    public Utilisateur getUtilisateur(){
        return this.utilisateur;
    }

    public Score toEntity(ResultSet rs) throws SQLException {
        Score myEntity = new Score(
          rs.getInt("id"),
          rs.getDate("date"),
          rs.getInt("nbre_bne_rpse"),
          rs.getInt("nbre_mvse_rpse"),
          recupererUtilisateur(rs.getInt("utilisateur_id"))
        );
        return myEntity;
    }

    public List<Score> toEntities(ResultSet rs) throws SQLException {
        List<Score> entities = new ArrayList<>();
        while (rs.next()){
        Score myEntity = toEntity(rs);
        entities.add(myEntity);
        }
        return entities;
    }

    private Utilisateur recupererUtilisateur(int utilisateur_id) throws SQLException {
        JdbcConnector jdbcConnector = new JdbcConnector(
                "jdbc:postgresql://localhost:5432/JeuDeCapitale",
                "admin",
                "secret123"
        );
        jdbcConnector.connect();
        Statement stmt = jdbcConnector.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur WHERE id = "+utilisateur_id);
        List<Utilisateur> utilisateur = Utilisateur.toEntities(rs);
        jdbcConnector.disconnect();

        return utilisateur.isEmpty() ? null : utilisateur.get(0);
    }
}
