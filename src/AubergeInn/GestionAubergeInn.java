package AubergeInn;

import AubergeInn.collections.*;
import AubergeInn.transactions.GestionChambre;
import AubergeInn.transactions.GestionClient;
import AubergeInn.transactions.GestionCommodite;
import AubergeInn.transactions.GestionReservation;

import java.sql.SQLException;

public class GestionAubergeInn
{
    private Connexion cx;
    private CollectionClients client;
    private CollectionChambres chambre;
    private CollectionCommodites commodite;
    private CollectionReserveChambre reservation;
    private CollectionPossedeCommodite commoditeChambre;
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

        client = new CollectionClients(cx);
        chambre = new CollectionChambres(cx);
        commodite = new CollectionCommodites(cx);
        commoditeChambre = new CollectionPossedeCommodite(cx);
        reservation = new CollectionReserveChambre(cx);

        setGestionClient(new GestionClient(client, reservation));
        setGestionChambre(new GestionChambre(chambre, reservation, commodite, commoditeChambre));
        setGestionCommodite(new GestionCommodite(commodite, commoditeChambre));
        setGestionReservation(new GestionReservation(chambre, client, reservation));
    }

    public void fermer() throws SQLException
    {
        // Fermeture de la connexion
        cx.fermer();
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
