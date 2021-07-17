package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleReserveChambre;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class TableReserveChambre
{
    private MongoCollection<Document> reservationsCollection;
    private Connexion cx;

    public TableReserveChambre(Connexion cx) throws SQLException
    {
        this.cx = cx;
        reservationsCollection = cx.getDatabase().getCollection("ReserveChambre");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Verifie si une reservation existe.
     */
    public boolean existe(int idClient, int idChambre)
    {
        return reservationsCollection.find(and(eq("idClient", idClient), eq("idChambre", idChambre))).first() != null;
    }

    /**
     * Lecture d'une reservation.
     */
    public TupleReserveChambre getReservation(int idClient, int idChambre)
    {
        Document r = reservationsCollection.find(and(eq("idClient", idClient), eq("idChambre", idChambre))).first();
        if(r != null)
        {
            return new TupleReserveChambre(r);
        }
        return null;
    }

    /**
     * Lecture de la première reservation d'un client.
     */
    public TupleReserveChambre getReservationClient(int idClient)
    {
        Document l = reservationsCollection.find(eq("idClient", idClient)).first();
        if(l != null)
        {
            return new TupleReserveChambre(l);
        }
        return null;
    }

    /**
     * Lecture de la première reservation d'un chambre.
     */
    public TupleReserveChambre getReservationChambre(int idChambre)
    {
        Document d = reservationsCollection.find(eq("idChambre", idChambre)).first();
        if(d != null)
        {
            return new TupleReserveChambre(d);
        }
        return null;
    }

    /**
     * Réservation d'une chambre.
     */
    public void reserver(int idClient, int idChambre, Date dateDebut, Date dateFin)
    {
        TupleReserveChambre r = new TupleReserveChambre(idClient, idChambre, dateDebut, dateFin);
        reservationsCollection.insertOne(r.toDocument());
    }

    /**
     * Suppression d'une reservation.
     */
    public boolean annulerRes(int idReservation)
    {
        return reservationsCollection.deleteOne(eq("_id", idReservation)).getDeletedCount() > 0;
    }

    /**
     * Liste toute les réservations
     */
    public List<TupleReserveChambre> listerReservations()
    {
        List<TupleReserveChambre> liste = new LinkedList<TupleReserveChambre>();
        MongoCursor<Document> reservations = reservationsCollection.find().iterator();
        try
        {
            while (reservations.hasNext())
            {
                liste.add(new TupleReserveChambre(reservations.next()));
            }
        }
        finally
        {
            reservations.close();
        }

        return liste;
    }

    /**
     * Liste toute les réservations
     */
    public List<TupleReserveChambre> calculerlisteReservationsClient(int idClient)
    {
        List<TupleReserveChambre> liste = new LinkedList<TupleReserveChambre>();
        MongoCursor<Document> reservations = reservationsCollection.find(eq("idClient", idClient)).iterator();
        try
        {
            while (reservations.hasNext())
            {
                liste.add(new TupleReserveChambre(reservations.next()));
            }
        }
        finally
        {
            reservations.close();
        }

        return liste;
    }
}
