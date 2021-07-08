package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambres;
import AubergeInn.tables.TableCommodites;
import AubergeInn.tables.TablePossedeCommodite;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TuplePossedeCommodite;

import javax.persistence.Tuple;
import java.sql.SQLException;
import java.util.List;

public class GestionCommodite
{
    private TableCommodites commodite;
    private TableChambres chambre;
    //private TablePossedeCommodite commoditeChambre;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionCommodite(TableCommodites commodite, TableChambres chambre) throws IFT287Exception
    {
        this.cx = commodite.getConnexion();
        if (commodite.getConnexion() != chambre.getConnexion())
            throw new IFT287Exception("Les instances de commodite et de chambre n'utilisent pas la même connexion au serveur");
        this.commodite = commodite;
        this.chambre = chambre;
    }

    /**
     * Ajout d'une nouvelle commodite dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterCommodite(int idCommodite, String description, float prix)
            throws Exception
    {
        try
        {
            cx.demarreTransaction();

            TupleCommodite c = new TupleCommodite(idCommodite, description, prix);
            // Vérifie si la commodite existe déja
            if (commodite.existe(idCommodite))
                throw new IFT287Exception("Chambre existe déjà: " + idCommodite);

            // Ajout d'une commodite dans la table des commodite
            commodite.ajouter(c);

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
    public void supprimerCommodite(int idCommodite) throws Exception
    {
        try
        {
            // Validation
            TupleCommodite tupleCommodite = commodite.getCommodite(idCommodite);
            if (tupleCommodite == null)
                throw new IFT287Exception("Client inexistant: " + idCommodite);

            // Suppression d'une commodite.
            if (!commodite.supprimer(tupleCommodite))
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
            throws Exception
    {
        try
        {
            cx.demarreTransaction();

            TupleCommodite c = commodite.getCommodite(idCommodite);
            TupleChambre ch = chambre.getChambre(idChambre);

            if(c == null){
                throw new IFT287Exception("la commodite : " + idCommodite +  "existe pas!");
            }

            if(ch == null){
                throw new IFT287Exception("la chambre : " + idChambre +  "existe pas!");
            }

//            // Vérifie si la commodite existe déja
            if (ch.isCommoditeExiste(c))
                throw new IFT287Exception("la commodite : " + idCommodite + "avec la chambre " + idChambre + "existe déjà!");

            // Ajout d'une commodite dans la liste de commodite d'une chambre
            ch.ajouteCommodite(c);

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
    public void enleverCommodite(int idChambre, int idCommodite) throws IFT287Exception, Exception
    {
        try
        {
            cx.demarreTransaction();

            // Validation
            TupleCommodite c = commodite.getCommodite(idCommodite);
            TupleChambre ch = chambre.getChambre(idChambre);

            if (!ch.getCommodites().contains(c))
                throw new IFT287Exception("la commodite : " + idCommodite + "avec la chambre " + idChambre + "existe pas!");

            // Suppression d'une commodite pour une chambre.
            ch.supprimerCommodite(c);

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    public List<TupleCommodite> ListerTousCommodites()
    {
        try
        {
            cx.demarreTransaction();
            List<TupleCommodite> commodites = commodite.ListerCommodites();
            cx.commit();
            return commodites;
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

}
