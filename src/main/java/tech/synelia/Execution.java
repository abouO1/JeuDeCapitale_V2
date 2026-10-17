package tech.synelia;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.sql.*;

public class Execution {
    public static void main(String[] args) throws SQLException {
        List<Capitale> paysCapitale = null;
        Capitale paysAleatoire;
        String reponse;
        String decision = "";
        List<Capitale> paysCites = new ArrayList<>();
        String url = "jdbc:postgresql://localhost:5432/JeuDeCapitale";
        String user = "admin";
        String password = "secret123";
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
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connexion à la bd réussie !");
            Statement stmt = conn.createStatement();
            Statement stmt_config = conn.createStatement();
            Statement stmt_user = conn.createStatement();
            Statement stmt_score = conn.createStatement();
            /*ResultSet rs = stmt.executeQuery(" SELECT * FROM capitale ;");
            //ResultSet rs_config = stmt_config.executeQuery("SELECT * FROM configuration WHERE cle = 'nbre_de_question'");
            paysCapitale = Capitale.toEntities(rs);
            paysAleatoire = paysCapitale.get(rand.nextInt(paysCapitale.size()));*/
            System.out.println("=======================================");
            System.out.println("           JEU DE CAPITALE");
            System.out.println("=======================================");
            System.out.println("10 questions par partie.");
            System.out.println("Majuscule ou miniscule, peu importe.");
            System.out.println("1 point par bonne réponse et 0 point par mauvaise réponse.");
            System.out.println("Saisisez les réponses avec les accents !");
            do {
                int bne_rpse = 0;
                int mvse_rpse = 0;
                ResultSet rs = stmt.executeQuery(" SELECT * FROM capitale ;");
                //ResultSet rs_config = stmt_config.executeQuery("SELECT * FROM configuration WHERE cle = 'nbre_de_question'");
                paysCapitale = Capitale.toEntities(rs);
                paysAleatoire = paysCapitale.get(rand.nextInt(paysCapitale.size()));
                ResultSet rs_config = stmt_config.executeQuery("SELECT * FROM configuration WHERE cle = 'nbre_de_question'");
                final int nbreQuestion;
                Score score;
                configuration = Configuration.toEntities(rs_config);
                if (configuration.isEmpty()) {
                    throw new IllegalStateException("Aucune configuration trouvée !");
                }
                nbreQuestion = Integer.parseInt(configuration.getFirst().getValeur());
                question = 0;
                paysCites = new ArrayList<>();
                System.out.println("----------------------------------------------------");
                System.out.print("Veuillez entrer votre nom : ");
                nom_user = sc.nextLine();
                System.out.print("Veuillez entrer votre prenom : ");
                prenom_user = sc.nextLine();
                System.out.print("Veuillez entrer votre username : ");
                username_user = sc.nextLine();
                System.out.print("Veuillez entrer votre email : ");
                email_user = sc.nextLine();
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO utilisateur(nom, prenom, username, email) VALUES (?, ?, ?, ?) " +
                        "ON CONFLICT (username) DO UPDATE SET username = EXCLUDED.username RETURNING id");
                pstmt.setString(1, nom_user);
                pstmt.setString(2, prenom_user);
                pstmt.setString(3, username_user);
                pstmt.setString(4, email_user);
                ResultSet rs_user = pstmt.executeQuery();
                rs_user.next();
                int utilisateur_id;
                utilisateur_id = rs_user.getInt("id");
                pstmt.close();
                System.out.println("Bienvenue "+username_user);
                System.out.println("----------------------------------------------------");
                while (question < nbreQuestion) {
                    question++;
                    do {
                        paysAleatoire = paysCapitale.get(rand.nextInt(paysCapitale.size()));
                    } while (paysCites.contains(paysAleatoire));
                    paysCites.add(paysAleatoire);
                    System.out.println("Question " + question + "/"+nbreQuestion);
                    //System.out.print("Quelle est la capitale de " + paysAleatoire.getPays().getNom() + " ? ");
                    do{
                        System.out.print("Quelle est la capitale de " + paysAleatoire.getPays().getNom() + " ? ");
                        reponse = sc.nextLine();
                        if(!reponse.isBlank()){
                            break;
                        }
                    }while(reponse.isBlank());
                    if (reponse.equalsIgnoreCase(paysAleatoire.getNom())) {
                        System.out.println("bonne réponse");
                        bne_rpse++;
                    } else {
                        System.out.println("Mauvaise réponse. La réponse correcte est : " + paysAleatoire.getNom());
                        mvse_rpse++;
                    }
                    //nbreQuestion++;
                    System.out.println("----------------------------------------");
                }
                System.out.println("Jeu terminé. Votre score est de " + bne_rpse + "/"+nbreQuestion);
                PreparedStatement pstmt_score = conn.prepareStatement("INSERT INTO score(date, nbre_bne_rpse, nbre_mvse_rpse, utilisateur_id) " +
                        "VALUES (CURRENT_DATE, ?, ?, ?)");
                pstmt_score.setInt(1, bne_rpse);
                pstmt_score.setInt(2, mvse_rpse);
                pstmt_score.setInt(3, utilisateur_id);
                pstmt_score.executeUpdate();
                pstmt_score.close();
                do {
                    System.out.println("");
                    System.out.print("Voulez-vous rejouer ? oui ou non ? ");
                    decision = sc.nextLine();
                    if (!decision.equalsIgnoreCase("oui") && !decision.equalsIgnoreCase("non")) {
                        System.out.print("Veuillez répondre par oui ou non : ");
                    }
                } while (!decision.equalsIgnoreCase("oui") && !decision.equalsIgnoreCase("non"));
            } while (decision.equalsIgnoreCase("oui"));
            sc.close();
            System.out.println();
            System.out.println("-----------------------------------------------------");
            System.out.println("Jeu terminé. Merci d'avoir joué et à très bientôt !!!");
            System.out.println("-----------------------------------------------------");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //IO.println(paysCapitale.get("Côte d'Ivoire"));
            //IO.println(paysAleatoire);
        }
    }
}
