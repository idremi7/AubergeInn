package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambres;
import AubergeInn.tables.TableCommodites;
import AubergeInn.tables.TablePossedeCommodite;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TuplePossedeCommodite;
import AubergeInn.tuples.TupleReserveChambre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GestionCommodite
{
    private TableCommodites commodite;
    private TablePossedeCommodite commoditeChambre;

    /**
     * Creation d'une instance
     */
    public GestionCommodite(TableCommodites commodite, TablePossedeCommodite commoditeChambre) throws IFT287Exception
    {
        if (commodite.getConnexion() != commoditeChambre.getConnexion())
            throw new IFT287Exception("Les instances de commodite et de commoditeChambre n'utilisent pas la même connexion au serveur");
        this.commodite = commodite;
        this.commoditeChambre = commoditeChambre;
    }

    /**
     * Ajout d'une nouvelle commodite dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterCommodite(int idCommodite, String description, float prix)
            throws IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la commodite existe déja
            if (commodite.existe(idCommodite))
                throw new IFT287Exception("Chambre existe déjà: " + idCommodite);

            // Ajout d'une commodite dans la table des commodite
            commodite.ajouter(idCommodite, description, prix);

        } catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Ajout d'une nouvelle commodite dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void InclureCommodite(int idChambre, int idCommodite)
            throws IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la commodite existe déja
            if (commoditeChambre.existe(idCommodite, idChambre))
                throw new IFT287Exception("le lien de la commodite : " + idCommodite + "avec la chambre " + idChambre + "existe déjà!");

            // Ajout d'une commoditeChambre dans la table possedeCommodite
            commoditeChambre.ajouter(idCommodite, idChambre);

        } catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Enlever une commoditer d'une chambre
     */
    public void enleverCommodite(int idChambre, int idCommodite) throws IFT287Exception, Exception
    {
        try
        {
            // Validation
            TuplePossedeCommodite tuplePossedeCommodite = commoditeChambre.getCommoditeChambre(idCommodite, idChambre);
            if (tuplePossedeCommodite == null)
                throw new IFT287Exception("Lien inexistant pour la commodite: " + idCommodite + " et la chambre: " + idChambre);

            // Suppression d'une commodite pour une chambre.
            if (!commoditeChambre.supprimer(idCommodite, idChambre))
                throw new IFT287Exception("Lien Commodite-Chambre " + idCommodite + "-" + idChambre + " inexistant");

        } catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * Trouve toutes les commodites d'une chambre
     */
    public List<TupleCommodite> calculerListeCommodites(int idChambre)
    {
        List<TupleCommodite> liste = new ArrayList<>();
        List<TuplePossedeCommodite> listePossedeCommodite = commoditeChambre.listerCommoditeChambre(idChambre);

        for (TuplePossedeCommodite p : listePossedeCommodite)
        {
            TupleCommodite c = commodite.getCommodite(p.getIdCommodite());
            liste.add(c);
        }

        return liste;
    }

}
