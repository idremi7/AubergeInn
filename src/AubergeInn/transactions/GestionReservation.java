package AubergeInn.transactions;

import AubergeInn.Connexion;
import AubergeInn.IFT287Exception;
import AubergeInn.tables.TableChambres;
import AubergeInn.tables.TableClients;
import AubergeInn.tables.TableReserveChambre;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleClient;
import AubergeInn.tuples.TupleReserveChambre;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class GestionReservation
{
    private TableChambres chambre;
    private TableClients client;
    private TableReserveChambre reservation;
    private Connexion cx;

    /**
     * Creation d'une instance. La connection de l'instance de livre et de
     * membre doit être la même que cx, afin d'assurer l'intégrité des
     * transactions.
     */
    public GestionReservation(TableChambres chambre, TableClients client, TableReserveChambre reservation) throws IFT287Exception
    {
        if (chambre.getConnexion() != client.getConnexion() || reservation.getConnexion() != client.getConnexion())
            throw new IFT287Exception(
                    "Les instances de chambre, de client et de reservation n'utilisent pas la même connexion au serveur");
        this.cx = chambre.getConnexion();
        this.chambre = chambre;
        this.client = client;
        this.reservation = reservation;
    }

    /**
     * Réservation d'une chambre par un client. La chambre doit être libre.
     */
    public void reserver(int idChambre, int idClient, Date dateDebut, Date dateFin)
            throws SQLException, IFT287Exception, Exception
    {
        try
        {
            // Vérifier que la chambre existe
            TupleChambre tupleChambre = chambre.getChambre(idChambre);
            if (tupleChambre == null)
                throw new IFT287Exception("Chambre inexistant: " + idChambre);


            // Vérifier que le client existe
            TupleClient tupleClient = client.getClient(idClient);
            if (tupleClient == null)
                throw new IFT287Exception("Client inexistant: " + idClient);

            // Vérifier si dateDebut < date d'aujourd'hui
            java.util.Date currentDate = new java.util.Date();
            if ((dateDebut).before(currentDate))
                throw new IFT287Exception("Date de début inférieure ou égale à la date d'aujourd'hui");

            if ((dateFin).before(currentDate))
                throw new IFT287Exception("Date de fin inférieure ou égale à la date d'aujourd'hui");

            // Vérifier si la chambre existe dans les chambre libres
//            if(!chambre.isChambreLibre(idChambre))
//                throw new IFT287Exception("La chambre : " + idChambre +" n'est pas libre");
            List<TupleReserveChambre> listReservation = chambre.listerReservations();

            for (TupleReserveChambre res : listReservation)
            {
                if(res.getDateDebut().before(dateDebut) && res.getDateFin().after(dateFin) ){
                    throw new IFT287Exception("La chambre : " + idChambre +" n'est pas libre");
                }
                if(res.getDateDebut().before(dateFin) && res.getDateFin().after(dateDebut) ){
                    throw new IFT287Exception("La chambre : " + idChambre +" n'est pas libre");
                }
            }

            // Creation de la reservation
            reservation.reserver(idClient, idChambre, dateDebut, dateFin);

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
     * Trouve tous les reservation avec prix total d'un client de la BD
     */
    public List<TupleReserveChambre> listerToutesReservationClient(int idClient) throws SQLException
    {

        try{
            List<TupleReserveChambre> reserveChambres = reservation.getReservationPrixClient(idClient);
            cx.commit();
            return reserveChambres;
        }
        catch (Exception e){
            cx.rollback();
            throw e;
        }

    }
}
