package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableCommodites;
import AubergeInn.tables.TablePossedeCommodite;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TuplePossedeCommodite;

import java.sql.SQLException;

public class GestionCommodite
{
    private TableCommodites commodite;
    private TablePossedeCommodite commoditeChambre;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionCommodite(TableCommodites commodite, TablePossedeCommodite commoditeChambre) throws IFT287Exception
    {
        this.cx = commodite.getConnexion();
        this.cx = commoditeChambre.getConnexion();

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
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la commodite existe déja
            if (commodite.existe(idCommodite))
                throw new IFT287Exception("Chambre existe déjà: " + idCommodite);

            // Ajout d'une commodite dans la table des commodite
            commodite.ajouter(idCommodite, description, prix);

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Supprimer une commodite de la bd (pour tester seulement) non necessaire pour le programme.
     */
    public void supprimerCommodite(int idCommodite) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            TupleCommodite tupleCommodite = commodite.getCommodite(idCommodite);
            if (tupleCommodite == null)
                throw new IFT287Exception("Client inexistant: " + idCommodite);

//            if (possedeCommodite.getCommodite(idCommodite) != null)
//                throw new IFT287Exception("Commodite #" + idCommodite + " est lié a une ou plusieurs chambres");

            // Suppression d'une commodite.
            int nb = commodite.supprimer(idCommodite);
            if (nb == 0)
                throw new IFT287Exception("Commodite " + idCommodite + " inexistant");

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Ajout d'une nouvelle commodite dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void InclureCommodite(int idChambre, int idCommodite)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si la commodite existe déja
            if (commoditeChambre.existe(idCommodite, idChambre))
                throw new IFT287Exception("le lien de la commodite : " + idCommodite + "avec la chambre " + idChambre + "existe déjà!");

            // Ajout d'une commoditeChambre dans la table possedeCommodite
            commoditeChambre.ajouter(idCommodite, idChambre);

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Enlever une commoditer d'une chambre
     */
    public void enleverCommodite(int idChambre, int idCommodite) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            TuplePossedeCommodite tuplePossedeCommodite = commoditeChambre.getCommoditeChambre(idCommodite, idChambre);
            if (tuplePossedeCommodite == null)
                throw new IFT287Exception("Lien inexistant pour la commodite: " + idCommodite + " et la chambre: " + idChambre);

            // Suppression d'une commodite pour une chambre.
            int nb = commoditeChambre.supprimer(idCommodite, idChambre);
            if (nb == 0)
                throw new IFT287Exception("Lien Commodite-Chambre " + idCommodite + "-" + idChambre + " inexistant");

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }
}
