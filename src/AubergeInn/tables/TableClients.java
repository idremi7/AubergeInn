package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TableClients
{
    private final Connexion cx;
    private MongoCollection<Document> clientsCollection;

    public TableClients(Connexion cx)
    {
        this.cx = cx;
        clientsCollection = cx.getDatabase().getCollection("Client");
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
        return clientsCollection.find(eq("idClient", idClient)).first() != null;
    }

    /**
     * Lecture d'un Client.
     */
    public TupleClient getClient(int idClient) throws SQLException
    {
        Document cl = clientsCollection.find(eq("idClient", idClient)).first();
        if(cl != null)
        {
            return new TupleClient(cl);
        }
        return null;
    }

    /**
     * Ajout d'un nouveau Client dans la base de données.
     */
    public void ajouter(int idClient, String nom, String prenom, int age)
    {
        /* Ajout d'un client-canard. */
        TupleClient cl = new TupleClient(idClient, nom, prenom, age);
        clientsCollection.insertOne(cl.toDocument());
    }

    /**
     * Suppression  d'un client.
     */
    public boolean supprimer(int idClient)
    {
        /* Suppression d'un client. */
        return clientsCollection.deleteOne(eq("idClient", idClient)).getDeletedCount() > 0;
    }
}
