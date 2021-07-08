package AubergeInn;

import AubergeInn.tables.*;
import AubergeInn.transactions.GestionChambre;
import AubergeInn.transactions.GestionClient;
import AubergeInn.transactions.GestionCommodite;
import AubergeInn.transactions.GestionReservation;

import java.sql.SQLException;

public class GestionAubergeInn
{
    private Connexion cx;
    private TableClients client;
    private TableChambres chambre;
    private TableCommodites commodite;
    private TableReserveChambre reservation;

    private GestionClient gestionClient;
    private GestionChambre gestionChambre;
    private GestionCommodite gestionCommodite;
    private GestionReservation gestionReservation;

    /**
     * Ouvre une connexion avec la BD relationnelle et alloue les gestionnaires
     * de transactions et de tables.
     *
     * @param serveur  SQL
     * @param bd       nom de la bade de données
     * @param user     user id pour établir une connexion avec le serveur SQL
     * @param password mot de passe pour le user id
     */
    public GestionAubergeInn(String serveur, String bd, String user, String password)
            throws IFT287Exception, SQLException
    {
        // Allocation des objets pour le traitement des transactions
        cx = new Connexion(serveur, bd, user, password);

        client = new TableClients(cx);
        chambre = new TableChambres(cx);
        commodite = new TableCommodites(cx);
        reservation = new TableReserveChambre(cx);

        setGestionClient(new GestionClient(client, reservation));
        setGestionChambre(new GestionChambre(chambre, reservation));
        setGestionCommodite(new GestionCommodite(commodite, chambre));
        setGestionReservation(new GestionReservation(chambre, client, reservation));
    }

    public void fermer() throws SQLException
    {
        // Fermeture de la connexion
        cx.fermer();
    }

    /**
     * Rollback
     */
    public void rollback() throws SQLException
    {
        cx.rollback();
    }

    public GestionClient getGestionClient()
    {
        return gestionClient;
    }

    public void setGestionClient(GestionClient gestionClient)
    {
        this.gestionClient = gestionClient;
    }

    public GestionChambre getGestionChambre()
    {
        return gestionChambre;
    }

    public void setGestionChambre(GestionChambre gestionChambre)
    {
        this.gestionChambre = gestionChambre;
    }

    public GestionCommodite getGestionCommodite()
    {
        return gestionCommodite;
    }

    public void setGestionCommodite(GestionCommodite gestionCommodite)
    {
        this.gestionCommodite = gestionCommodite;
    }

    public GestionReservation getGestionReservation()
    {
        return gestionReservation;
    }

    public void setGestionReservation(GestionReservation gestionReservation)
    {
        this.gestionReservation = gestionReservation;
    }
}
