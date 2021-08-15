package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleClient;
import AubergeInn.tuples.TupleReserveChambre;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class TableReserveChambre
{
    private final TypedQuery<TupleReserveChambre> stmtExiste;

    private final TypedQuery<TupleReserveChambre> stmtListeReservationClient;

    private final TypedQuery<TupleReserveChambre> stmtListeTousReservation;


    private final Connexion cx;

    public TableReserveChambre(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection().createQuery("select r from TupleReserveChambre r where r.idReservation = :idReservation", TupleReserveChambre.class);

        this.stmtListeReservationClient = cx.getConnection().createQuery("select r from TupleReserveChambre r where r.client = :client", TupleReserveChambre.class);


        this.stmtListeTousReservation = cx.getConnection().createQuery("select r from TupleReserveChambre r", TupleReserveChambre.class);

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
    public boolean existe(int idReservation)
    {
        stmtExiste.setParameter("idReservation", idReservation);
        return !stmtExiste.getResultList().isEmpty();
    }

    /**
     * Lecture d'une reservation.
     */
    public TupleReserveChambre getReservation(TupleClient client, TupleChambre chambre)
    {
        stmtExiste.setParameter("client", client);
        stmtExiste.setParameter("chambre", chambre);
        List<TupleReserveChambre> reservations = stmtExiste.getResultList();
        if (!reservations.isEmpty())
        {
            return reservations.get(0);
        }
        return null;
    }

    /**
     * Lecture de la première reservation d'un client.
     */
    public TupleReserveChambre getReservationClient(TupleClient client)
    {
        stmtListeReservationClient.setParameter("client", client);
        List<TupleReserveChambre> reservations = stmtListeReservationClient.getResultList();
        if (!reservations.isEmpty())
        {
            return reservations.get(0);
        }
        return null;
    }

    /**
     * Lecture de la première reservation d'un client.
     */
    public List<TupleReserveChambre> getReservationsClient(TupleClient client)
    {
        stmtListeReservationClient.setParameter("client", client);
        return stmtListeReservationClient.getResultList();
    }

    /**
     * Trouve toutes les infos d'une reservation
     */
    public List<TupleReserveChambre> listerReservation()
    {
        return stmtListeTousReservation.getResultList();
    }

    /**
     * Réservation d'une chambre.
     */
    public void reserver(TupleReserveChambre r)
    {
        cx.getConnection().persist(r);
    }

    /**
     * Suppression d'une reservation.
     */
    public boolean annulerRes(TupleReserveChambre r) throws SQLException
    {
        if (r != null)
        {
            cx.getConnection().remove(r);
            return true;
        }
        return false;
    }

}
