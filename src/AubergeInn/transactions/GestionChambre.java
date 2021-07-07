package AubergeInn.transactions;


import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambres;
import AubergeInn.tables.TableCommodites;
import AubergeInn.tables.TableReserveChambre;
import AubergeInn.tuples.*;

import java.net.StandardSocketOptions;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class GestionChambre
{
    private TableChambres chambres;
    private TableReserveChambre reservations;
    private TableCommodites commodites;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionChambre(TableChambres chambre, TableReserveChambre reservation) throws IFT287Exception
    {
        this.cx = chambre.getConnexion();
        if (chambre.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les collections d'objets n'utilisent pas la même connexion au serveur");
        this.chambres = chambre;
        this.reservations = reservation;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterChambre(int idChambre, String nom, String type, float prixBase)
            throws IFT287Exception, Exception
    {
        try
        {
            cx.demarreTransaction();

            TupleChambre chambre = new TupleChambre(idChambre, nom, type, prixBase);

            // Vérifie si la chambre existe déja
            if (chambres.existe(idChambre))
                throw new IFT287Exception("Chambre existe déjà: " + idChambre);

            // Ajout d'une chambre dans la table des chambres
            chambres.ajouter(chambre);

            // Commit
            cx.commit();
        } catch (Exception e)
        {
            cx.rollback();
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
            //demarre transaction
            cx.demarreTransaction();
            // Validation
            TupleChambre chambre = chambres.getChambre(idChambre);
            if (chambre == null)
                throw new IFT287Exception("Chambre inexistant: " + idChambre);

            // Suppression du chambre.
            if (!chambres.supprimer(chambre))
                throw new IFT287Exception("Chambre " + idChambre + " inexistant");

            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }


    /**
     * Cette commande obtiens une chambre
     */
    public TupleChambre getChambre(int idChambre) throws SQLException
    {
        try
        {
            cx.demarreTransaction();
            TupleChambre uneChambre = chambres.getChambre(idChambre);
            cx.commit();
            return uneChambre;
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }

    }

    public List<TupleCommodite> ListerCommodites(int idChambre) throws SQLException
    {
        try
        {
            List<TupleCommodite> commodites = chambres.listerCommodites(idChambre);
            cx.commit();
            return commodites;
        } catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }



    /**
     * Affiche les chambres libres
     */
    public void AfficherChambresLibres()
    {
        cx.demarreTransaction();
        List<TupleChambre> listeChambre = chambres.listerChambresLibre();
        for (TupleChambre ch : listeChambre)
        {
                ch.afficherInfosChambre();
        }
        cx.commit();
    }

    /**
     * Affiche tous les Chambre de la BD
     */
    public void ListerChambres()
    {
        cx.demarreTransaction();

        List<TupleChambre> listeChambre = chambres.listerChambres();

        for (TupleChambre ch : listeChambre)
        {
            System.out.println(ch.getIdChambre() + " " + ch.getNom() + " " + ch.getType() + " " + ch.getPrixBase());
        }

        cx.commit();
    }
}
