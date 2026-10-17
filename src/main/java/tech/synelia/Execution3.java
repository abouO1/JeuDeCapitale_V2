package tech.synelia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;

public class Execution3 {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/JeuDeCapitale";
        String user = "admin";
        String password = "secret123";
        JdbcConnector jdbcConnector = new JdbcConnector(url,user,password);
        List<Capitale> paysCapitale = null;
        Capitale paysAleatoire;
        String reponse;
        String decision = "";
        List<Capitale> paysCites = new ArrayList<>();
        String nom_user;
        String prenom_user;
        String username_user;
        String email_user;
        int question;
        List<Configuration> configuration = new ArrayList<>();
        //List<Configuration> configuration;
        Utilisateur utilisateur;
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        try{
            jdbcConnector.connect();
            System.out.println("Connexion à la bd réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //IO.println(paysCapitale.get("Côte d'Ivoire"));
            //IO.println(paysAleatoire);
        }
    }
}
