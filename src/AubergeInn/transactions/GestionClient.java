package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableClients;
import AubergeInn.tables.TableReserveChambre;
import AubergeInn.tuples.TupleClient;

import java.sql.SQLException;

public class GestionClient
{
    private TableClients client;
    private TableReserveChambre reservation;
    private Connexion cx;

    /**
     * Creation d'une instance
     */
    public GestionClient(TableClients client, TableReserveChambre reservation) throws IFT287Exception
    {
        this.cx = client.getConnexion();
        if (client.getConnexion() != reservation.getConnexion())
            throw new IFT287Exception("Les instances de client et de chambre n'utilisent pas la même connexion au serveur");
        this.client = client;
        this.reservation = reservation;
    }

    /**
     * Ajout d'un nouveau client dans la base de données. S'il existe déjà, une
     * exception est levée.
     */
    public void ajouterClient(int idClient, String nom, String prenom, int age)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifie si le livre existe déja
            if (client.existe(idClient))
                throw new IFT287Exception("Livre existe déjà: " + idClient);

            // Ajout du livre dans la table des livres
            client.ajouter(idClient, nom, prenom, age);

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Supprimer un client.
     */
    public void supprimerClient(int idClient) throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Validation
            TupleClient tupleClient = client.getClient(idClient);
            if (tupleClient == null)
                throw new IFT287Exception("Client inexistant: " + idClient);

            if (reservation.getReservationClient(idClient) != null)
                throw new IFT287Exception("Client #" + idClient + " a une ou plusieurs réservations ");

            // Suppression du client.
            int nb = client.supprimer(idClient);
            if (nb == 0)
                throw new IFT287Exception("Client " + idClient + " inexistant");

            // Commit
            cx.commit();
        }
        catch (Exception e)
        {
            cx.rollback();
            throw e;
        }
    }

    /**
     * Affiche toute les informations sur un clients de la BD
     */
    public void afficherClient(int idClient) throws SQLException
    {

        try{
            TupleClient unClient = client.getClient(idClient);
            cx.commit();
            System.out.println(unClient);
        }
        catch (Exception e){
            cx.rollback();
            throw e;
        }

    }
}
