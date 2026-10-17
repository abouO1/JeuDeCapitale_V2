package tech.synelia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnector {
    private String url;
    private String username;
    private String password;
    private Connection connection;

    public JdbcConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            //System.out.println("Connexion réussie à la base de données.");
        }catch (SQLException e){
            System.out.println("Une erreur s'est produite lors de la connexion à la base de données: " + e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Une erreur inattendue s'est produite lors de la connexion à la base de données.");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void disconnect() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
                //System.out.println("Déconnexion réussie de la base de données.");
            }
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la déconnexion de la base de données: " + e.getMessage());
        }
    }
}
