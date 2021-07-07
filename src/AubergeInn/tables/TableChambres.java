package AubergeInn.tables;


import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TupleReserveChambre;

import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TableChambres
{
    private final TypedQuery<TupleChambre> stmtExiste;
    private final TypedQuery<TupleChambre> stmtListeTousChambres;
    private final TypedQuery<TupleReserveChambre> stmtListeTousReservation;
    private final TypedQuery<TupleReserveChambre> stmtListeReservationChambre;

    private final Connexion cx;

    public TableChambres(Connexion cx)
    {
        this.cx = cx;

        stmtExiste = cx.getConnection()
                .createQuery("select ch from TupleChambre ch where ch.idChambre = :idchambre", TupleChambre.class);
        stmtListeTousChambres = cx.getConnection()
                .createQuery("select ch from TupleChambre ch", TupleChambre.class);
        stmtListeTousReservation = cx.getConnection()
                .createQuery("select r from TupleReserveChambre r", TupleReserveChambre.class);
        this.stmtListeReservationChambre = cx.getConnection()
                .createQuery("select r from TupleReserveChambre r where r.chambre = :chambre", TupleReserveChambre.class);
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si une chambre existe.
     */
    public boolean existe(int idChambre)
    {
        stmtExiste.setParameter("idchambre", idChambre);
        return !stmtExiste.getResultList().isEmpty();
    }


    /**
     * Lecture d'une chambre.
     */
    public TupleChambre getChambre(int idChambre) throws SQLException
    {
        stmtExiste.setParameter("idchambre", idChambre);
        List<TupleChambre> chambres = stmtExiste.getResultList();
        if (!chambres.isEmpty())
        {
            return chambres.get(0);
        } else
        {
            return null;
        }
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public TupleChambre ajouter(TupleChambre chambre) throws SQLException
    {
        //ajout d'une chambre
        cx.getConnection().persist(chambre);

        return chambre;
    }

    /**
     * Suppression  d'une chambre.
     */
    public boolean supprimer(TupleChambre chambre)
    {
        /* Suppression d'une chambre. */
        if (chambre != null)
        {
            cx.getConnection().remove(chambre);
            return true;
        }
        return false;
    }

    /**
     * Trouve toutes les commodites d'une chambre
     */
    public List<TupleCommodite> listerCommodites(int idChambre)
    {
        stmtExiste.setParameter("idchambre", idChambre);
        List<TupleChambre> chambres = stmtExiste.getResultList();
        if (!chambres.isEmpty())
        {
            return chambres.get(0).getCommodites();
        } else
        {
            return null;
        }
    }

    /**
     * Lecture de la première reservation d'un chambre.
     */
    public TupleReserveChambre getReservationChambre(TupleChambre chambre)
    {
        stmtListeReservationChambre.setParameter("chambre", chambre);
        List<TupleReserveChambre> reservations = stmtListeReservationChambre.getResultList();
        if(!reservations.isEmpty())
        {
            return reservations.get(0);
        }
        return null;
    }

    /**
     * Trouve toutes les infos d'une chambre
     */
    public List<TupleChambre> listerChambresLibre()
    {

        List<TupleChambre> ListeChambreLibre = new ArrayList<>();
        List<TupleChambre> listeChambre = stmtListeTousChambres.getResultList();

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        for (TupleChambre ch : listeChambre)
        {
            TupleReserveChambre r = getReservationChambre(ch);
            if (r == null || r.getDateDebut().compareTo(calendar.getTime()) * calendar.getTime().compareTo(r.getDateFin()) >= 0)
            {
                ListeChambreLibre.add(ch);
            }
        }
        return ListeChambreLibre;
    }

    /**
     * Trouve toutes les infos d'une chambre
     */
    public List<TupleChambre> listerChambres()
    {
        return stmtListeTousChambres.getResultList();
    }

    /**
     * Liste toute les réservations
     */
    public List<TupleReserveChambre> listerReservations()
    {
        return stmtListeTousReservation.getResultList();
    }
}
