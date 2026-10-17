package tech.synelia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Execution2 {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/JeuDeCapitale";
        String user = "admin";
        String password = "secret123";

        JdbcConnector jdbc = new JdbcConnector(url, user, password);
        jdbc.connect();

        try {
            Connection conn = jdbc.getConnection();

            if (conn == null) {
                System.out.println("Connexion impossible.");
                return;
            }

            // TEST 1 : Pays.toEntity()
            System.out.println("\n=== TEST Pays.toEntity() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT * FROM pays ORDER BY id LIMIT 1")) {

                if (rs.next()) {
                    Pays pays = Pays.toEntity(rs);

                    System.out.println("ID : " + pays.getId());
                    System.out.println("Nom : " + pays.getNom());
                    System.out.println("Superficie : " + pays.getSuperficie());
                } else {
                    System.out.println("Aucun pays trouvé.");
                }
            }

            // TEST 2 : Pays.toEntities()
            System.out.println("\n=== TEST Pays.toEntities() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM pays")) {

                List<Pays> paysList = Pays.toEntities(rs);

                for (Pays p : paysList) {
                    System.out.println(
                            p.getId() + " - " + p.getNom()
                                    + " - " + p.getSuperficie()
                    );
                }

                System.out.println("Nombre de pays : " + paysList.size());
            }

            // TEST 3 : Utilisateur.toEntity()
            System.out.println("\n=== TEST Utilisateur.toEntity() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT * FROM utilisateur ORDER BY id LIMIT 1")) {

                if (rs.next()) {
                    Utilisateur u = Utilisateur.toEntity(rs);

                    System.out.println("ID : " + u.getId());
                    System.out.println("Nom : " + u.getNom());
                    System.out.println("Prénom : " + u.getPrenom());
                    System.out.println("Email : " + u.getEmail());
                } else {
                    System.out.println("Aucun utilisateur trouvé.");
                }
            }

            // TEST 4 : Utilisateur.toEntities()
            System.out.println("\n=== TEST Utilisateur.toEntities() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM utilisateur")) {

                List<Utilisateur> utilisateurs =
                        Utilisateur.toEntities(rs);

                for (Utilisateur u : utilisateurs) {
                    System.out.println(
                            u.getId() + " - " + u.getNom()
                                    + " " + u.getPrenom()
                                    + " - " + u.getEmail()
                    );
                }

                System.out.println(
                        "Nombre d'utilisateurs : " + utilisateurs.size()
                );
            }

            // TEST 5 : Capitale.toEntity()
            System.out.println("\n=== TEST Capitale.toEntity() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT * FROM capitale ORDER BY id LIMIT 1")) {

                if (rs.next()) {
                    Capitale mapper = new Capitale(0, "", "", null);
                    Capitale capitale = mapper.toEntity(rs);

                    System.out.println("ID : " + capitale.getId());
                    System.out.println("Nom : " + capitale.getNom());
                    System.out.println(
                            "Type : " + capitale.getType_capitale()
                    );

                    if (capitale.getPays() != null) {
                        System.out.println(
                                "Pays : " + capitale.getPays().getNom()
                        );
                    } else {
                        System.out.println("Pays associé introuvable.");
                    }
                } else {
                    System.out.println("Aucune capitale trouvée.");
                }
            }

            // TEST 6 : Capitale.toEntities()
            System.out.println("\n=== TEST Capitale.toEntities() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT * FROM capitale ORDER BY id")) {

                Capitale mapper = new Capitale(0, "", "", null);
                List<Capitale> capitales = mapper.toEntities(rs);

                for (Capitale c : capitales) {
                    String nomPays = c.getPays() != null
                            ? c.getPays().getNom()
                            : "Pays inconnu";

                    System.out.println(
                            c.getId() + " - " + c.getNom()
                                    + " - " + c.getType_capitale()
                                    + " - " + nomPays
                    );
                }

                System.out.println(
                        "Nombre de capitales : " + capitales.size()
                );
            }

            // TEST 7 : Score.toEntity()
            System.out.println("\n=== TEST Score.toEntity() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT * FROM score ORDER BY id LIMIT 1")) {

                if (rs.next()) {
                    Score mapper = new Score(0, null, 0, 0, null);
                    Score score = mapper.toEntity(rs);

                    System.out.println("ID : " + score.getId());
                    System.out.println("Date : " + score.getDate());
                    System.out.println(
                            "Bonnes réponses : "
                                    + score.getNbre_bne_rpse()
                    );
                    System.out.println(
                            "Mauvaises réponses : "
                                    + score.getNbre_mvse_rpse()
                    );

                    if (score.getUtilisateur() != null) {
                        System.out.println(
                                "Utilisateur : "
                                        + score.getUtilisateur().getNom()
                                        + " "
                                        + score.getUtilisateur().getPrenom()
                        );
                    } else {
                        System.out.println(
                                "Utilisateur associé introuvable."
                        );
                    }
                } else {
                    System.out.println("Aucun score trouvé.");
                }
            }

            // TEST 8 : Score.toEntities()
            System.out.println("\n=== TEST Score.toEntities() ===");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT * FROM score ORDER BY id")) {

                Score mapper = new Score(0, null, 0, 0, null);
                List<Score> scores = mapper.toEntities(rs);

                for (Score s : scores) {
                    String nomUtilisateur =
                            s.getUtilisateur() != null
                                    ? s.getUtilisateur().getNom()
                                    + " " + s.getUtilisateur().getPrenom()
                                    : "Utilisateur inconnu";

                    System.out.println(
                            s.getId()
                                    + " - " + s.getDate()
                                    + " - Bonnes réponses : "
                                    + s.getNbre_bne_rpse()
                                    + " - Mauvaises réponses : "
                                    + s.getNbre_mvse_rpse()
                                    + " - " + nomUtilisateur
                    );
                }

                System.out.println("Nombre de scores : " + scores.size());
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        } finally {
            jdbc.disconnect();
        }
    }

}
