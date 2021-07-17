package AubergeInn.transactions;


import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambres;
import AubergeInn.tables.TableCommodites;
import AubergeInn.tables.TablePossedeCommodite;
import AubergeInn.tables.TableReserveChambre;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TuplePossedeCommodite;
import AubergeInn.tuples.TupleReserveChambre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GestionChambre
{
    private TableChambres chambre;
    private TableCommodites commodite;
    private TablePossedeCommodite commoditeChambre;
    private TableReserveChambre reservation;

    /**
     * Creation d'une instance
     */
    public GestionChambre(TableChambres chambre, TableReserveChambre reservation, TableCommodites commodite, TablePossedeCommodite commoditeChambre) throws IFT287Exception
    {
        if (chambre.getConnexion() != reservation.getConnexion() || reservation.getConnexion() != commodite.getConnexion() ||
                commodite.getConnexion() != commoditeChambre.getConnexion())
            throw new IFT287Exception("Les instances de chambre et de reservation n'utilisent pas la même connexion au serveur");
        this.chambre = chambre;
        this.reservation = reservation;
        this.commodite = commodite;
        this.commoditeChambre = commoditeChambre;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterChambre(int idChambre, String nom, String type, float prixBase)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la chambre existe déja
            if (chambre.existe(idChambre))
                throw new IFT287Exception("Chambre existe déjà: " + idChambre);

            // Ajout d'une chambre dans la table des chambres
            chambre.ajouter(idChambre, nom, type, prixBase);

        } catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Supprimer une chambre.
     */
    public void supprimerChambre(int idChambre) throws IFT287Exception, Exception
    {
        try
        {
            // Validation
            TupleChambre tupleChambre = chambre.getChambre(idChambre);
            if (tupleChambre == null)
                throw new IFT287Exception("Chambre inexistant: " + idChambre);

            // Suppression du chambre.
            if (!chambre.supprimer(idChambre))
                throw new IFT287Exception("Chambre " + idChambre + " inexistant");

        } catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Cette commande obtiens une chambre
     */
    public TupleChambre getChambre(int idChambre)
    {
        try
        {
            return chambre.getChambre(idChambre);
        } catch (Exception e)
        {
            throw e;
        }

    }

    /**
     * Trouve toutes les chambres libre
     */
    public List<TupleChambre> listerChambresLibre()
    {
        List<TupleChambre> listeChambreLibre = new ArrayList<>();
        List<TupleChambre> listeChambre = chambre.calculerListeChambre();

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        for (TupleChambre ch : listeChambre)
        {
            TupleReserveChambre r = reservation.getReservationChambre(ch.getIdChambre());
            if (r == null || r.getDateDebut().compareTo(calendar.getTime()) * calendar.getTime().compareTo(r.getDateFin()) >= 0)
            {
                listeChambreLibre.add(ch);
            }
        }

        return listeChambreLibre;
    }

    public double calculerPrixLocation(int idChambre){

        TupleChambre uneChambre = chambre.getChambre(idChambre);
        double prixtotal = uneChambre.getPrixBase();

        List<TuplePossedeCommodite> listePossedeCommodite = commoditeChambre.listerCommoditeChambre(idChambre);

        for (TuplePossedeCommodite p : listePossedeCommodite)
        {
            TupleCommodite c = commodite.getCommodite(p.getIdCommodite());
            prixtotal += c.getPrix();
        }

        return  prixtotal;
    }
}
