package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class TableClients
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtListeTousClients;

    private final Connexion cx;

    public TableClients(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection().prepareStatement("select * from client where idclient = ?");
        this.stmtInsert = cx.getConnection()
                .prepareStatement("insert into client (idclient, nom, prenom, age) "
                        + "values (?,?,?,?)");
        this.stmtUpdate = cx.getConnection()
                .prepareStatement("update client set nom = ?, prenom = ?, age = ? " + "where idclient = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from client where idclient = ?");
        this.stmtListeTousClients = cx.getConnection().prepareStatement("select idclient, nom, prenom, age from client");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un client existe.
     */
    public boolean existe(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        boolean clientExiste = rset.next();
        rset.close();
        return clientExiste;
    }

    /**
     * Lecture d'un Client.
     */
    public TupleClient getClient(int idClient) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleClient tupleClient = new TupleClient();
            tupleClient.setIdClient(idClient);
            tupleClient.setNom(rset.getString(2));
            tupleClient.setPrenom(rset.getString(3));
            tupleClient.setAge(rset.getInt(4));

            rset.close();
            return tupleClient;
        }
        else
            return null;
    }

    /**
     * Ajout d'un nouveau Client dans la base de données.
     */
    public void ajouter(int idClient, String nom, String prenom, int age) throws SQLException
    {
        /* Ajout d'un client-canard. */
        stmtInsert.setInt(1, idClient);
        stmtInsert.setString(2, nom);
        stmtInsert.setString(3, prenom);
        stmtInsert.setInt(4, age);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression  d'un client.
     */
    public int supprimer(int idClient) throws SQLException
    {
        /* Suppression d'un client-canard. */
        stmtDelete.setInt(1, idClient);
        return stmtDelete.executeUpdate();
    }
}
