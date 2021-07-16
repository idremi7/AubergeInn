package AubergeInn.transactions;


import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambres;
import AubergeInn.tables.TableCommodites;
import AubergeInn.tuples.*;

import java.sql.SQLException;
import java.util.List;

public class GestionChambre
{
    private TableChambres chambre;
    private TableCommodites commodite;

    /**
     * Creation d'une instance
     */
    public GestionChambre(TableChambres chambre) throws IFT287Exception
    {
//        if (chambre.getConnexion() != commodite.getConnexion())
//            throw new IFT287Exception("Les instances de chambre et de reservation n'utilisent pas la même connexion au serveur");
        this.chambre = chambre;
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

//    public List<TupleChambre> ListerChambresLibres() throws SQLException
//    {
//        try
//        {
//            List<TupleChambre> chambres = chambre.listerChambresLibres();
//            cx.commit();
//            return chambres;
//        } catch (Exception e)
//        {
//            cx.rollback();
//            throw e;
//        }
//    }

}
