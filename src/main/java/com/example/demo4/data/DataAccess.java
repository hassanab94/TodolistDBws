package com.example.demo4.data;

import com.example.demo4.model.Todo;
import com.example.demo4.model.Urgence;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.sql.*;

import static com.mysql.cj.protocol.a.MysqlTextValueDecoder.getTime;

public class DataAccess {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist?connectTimeout=3000&useSSL=false&allowPublicKeyRetrieval=true", "root", "Mysql94");

            //System.out.println("Connexion réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return conn;
    }

    public static String todoparuser(int userId) {
        StringBuilder str = new StringBuilder();
        try (Connection conn = getConnection()) {
            PreparedStatement prs = conn.prepareStatement(
                    "SELECT * FROM todolist.todo WHERE idUser = ?"
            );

            prs.setInt(1, userId);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                str.append(String.format(
                        "todo Id : %d, name todo : %s, description : %s, date : %s, idurg : %s\n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public static String todourgence(String Urnom) {
        StringBuilder st = new StringBuilder();
        try (
                Connection conn = getConnection()) {
            PreparedStatement prs = conn.prepareStatement(
                    "SELECT t.idTodo, t.nom, t.description, t.date, u.nom\n" +
                            "FROM todolist.todo AS t, todolist.urgence AS u\n" +
                            "WHERE u.nom = ? \n" +
                            "AND t.idUrgence = u.idUrgence;"
            );
            prs.setString(1, String.valueOf(Urnom));
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                st.append(String.format(
                        "Todo Id : %d, Todo name : %s, Todo description : %s, Todo date : %s, Urgence name :%s\n",
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getTimestamp(4),
                        rs.getString(5)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return st.toString();
    }

    public static String delurgence( int urgId) {
        try {
            Connection conn = getConnection();
            PreparedStatement prs = conn.prepareStatement("SELECT COUNT(*) FROM todo WHERE idUrgence = ?");
            prs.setInt(1, urgId);
            ResultSet counturg = prs.executeQuery();
            counturg.next();
            int count = counturg.getInt(1);
            if (count > 0) {
                // s'il reste encore des todo liées à l'urgence
                return Response.status(409).entity("Impossible de supprimer l'urgence car il reste des tâches liées.").build().toString();
            } else {
                // s'il n'y a pas de todo liée à l'urgence, on la supprime
                PreparedStatement del = conn.prepareStatement("DELETE FROM urgence WHERE idUrgence = ?");
                del.setInt(1, urgId);
                int delurg = del.executeUpdate();
                if (delurg == 0) {
                    // aucune urgence trouvée
                    return Response.status(404).entity("L'urgence n'a pas été trouvée.").build().toString();
                } else {
                    return Response.ok("L'urgence a été supprimée.").build().toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static String inserttodouser(int userId, Todo todo) {
        try {
            Connection conn = getConnection();
            PreparedStatement prs = conn.prepareStatement("SELECT COUNT(*)\n" +
                    "FROM Todo\n" +
                    "WHERE idUser = ?");
            prs.setInt(1, userId);
            ResultSet usercount = prs.executeQuery();
            usercount.next();
            int count = usercount.getInt(1);
            if (count > 0) {
                PreparedStatement insert = conn.prepareStatement("INSERT INTO Todo (nom, description, date, idUrgence, idUser) VALUES (?, ?, ?, ?, ?)");
                insert.setString(1, todo.getNom());
                insert.setString(2, todo.getDescription());
                insert.setObject(3, todo.getDate());
                insert.setInt(4, todo.getIdUrgence());
                insert.setInt(5, userId);
                insert.executeUpdate();
                return Response.ok("La todo a été ajoutée : " + todo.toString()).build().toString();
            } else {
                return Response.status(404).entity("L'utilisateur n'existe pas").build().toString();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
public static String insertodoparnom(String nomuser, String prenomuser, Todo todo) throws SQLException {
        try{
            Connection conn = getConnection();
            PreparedStatement prs = conn.prepareStatement("SELECT COUNT(*)\n" +
                    "FROM Todo AS t, USER AS u\n" +
                    "WHERE u.nom = ? \n" +
                    "AND u.prenom = ? \n" +
                    "AND t.idUser = u.idUser;");
            prs.setString(1, nomuser);
            prs.setString(2, prenomuser);
            ResultSet result = prs.executeQuery();
            result.next();
            int count = result.getInt(1);
            if (count == 0){
                return Response.status(404).entity("l'utilisateur n'existe pas dans la BD").build().toString();
            }else {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO todo (nom,description,date,idUrgence,idUser)" +
                        "VALUES (?,?,?,?,?)");
                ps.setString(1, todo.getNom());
                ps.setString(2, todo.getDescription());
                ps.setObject(3, todo.getDate());
                ps.setInt(4, todo.getIdUrgence());
                ps.setInt(5, todo.getIdUser());
                ps.executeUpdate();
                return Response.ok("la todo a été ajoutée dans la DB").build().toString();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}
public static String deluserdeltodo(int userid, Todo todo){
        try{
            Connection conn = getConnection();
            // Ajout d'abord dans la base de donnée 'ON DELETE CASCADE'
            PreparedStatement prs = conn.prepareStatement("DELETE FROM user where idUser = ?");
            prs.setInt(1, userid);
            int Dele = prs.executeUpdate();
            if(Dele == 0){
                return " Aucun utilisateur trouvé avec cet identifiant :"+ userid;
            }else {
                return Response.ok("la todo a été suprimée avec succé").build().toString();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
}

}







