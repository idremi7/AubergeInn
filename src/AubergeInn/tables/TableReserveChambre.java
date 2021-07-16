package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleReserveChambre;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;

public class TableReserveChambre
{
//    private final PreparedStatement stmtExiste;
//    private final PreparedStatement stmtInsert;
//    private final PreparedStatement stmtUpdate;
//    private final PreparedStatement stmtDelete;
//    private final PreparedStatement stmtListeReservationClient;
//    private final PreparedStatement stmtListeReservationPrixClient;
//    private final PreparedStatement stmtListeReservationChambre;
//    private final PreparedStatement stmtListeTousReservation;
//    private final PreparedStatement stmtChambreReserve;

    private MongoCollection<Document> reservationsCollection;
    private Connexion cx;

    public TableReserveChambre(Connexion cx) throws SQLException
    {
        this.cx = cx;
        reservationsCollection = cx.getDatabase().getCollection("ReserveChambre");

//        this.stmtExiste = cx.getConnection()
//                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idclient = ? and idchambre = ?");
//        this.stmtInsert = cx.getConnection()
//                .prepareStatement("insert into reservechambre (idclient, idchambre, datedebut, datefin) " + "values (?,?,?,?)");
//        this.stmtUpdate = cx.getConnection()
//                .prepareStatement("update reservechambre " +
//                        "set idclient = ?, idchambre = ?, datedebut = ?, datefin = ? " + "where idclient = ? and idchambre = ?");
//        this.stmtDelete = cx.getConnection().prepareStatement("delete from reservechambre where idreservation = ? ");
//
//        this.stmtListeReservationClient = cx.getConnection()
//                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idclient = ?");
//
//        this.stmtListeReservationChambre = cx.getConnection()
//                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idchambre = ?");
//
//        this.stmtListeTousReservation = cx.getConnection()
//                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre");
//
//        this.stmtListeReservationPrixClient = cx.getConnection()
//                .prepareStatement("Select  r.idreservation, r.idclient, r.idchambre, r.datedebut, r.datefin,(c.prixbase + coalesce(SUM(co.prix),0)) as prixTotal\n" +
//                        "from reservechambre r\n" +
//                        "JOIN chambre c on r.idchambre = c.idchambre\n" +
//                        "LEFT JOIN possedecommodite p on c.idchambre = p.idchambre\n" +
//                        "LEFT JOIN commodite co on co.idcommodite = p.idcommodite\n" +
//                        "where r.idclient = ?\n" +
//                        "GROUP BY c.prixbase, r.idreservation;");
//
//        this.stmtChambreReserve = cx.getConnection()
//                .prepareStatement("select t2.idreservation, t2.idclient ,t2.idchambre, t2.datedebut, t2.datefin\n" +
//                        "from  reservechambre t2, chambre t3\n" +
//                        "where t3.idchambre = t2.idchambre;");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Verifie si une reservation existe.
     */
    public boolean existe(int idClient, int idChambre)
    {
        return reservationsCollection.find(and(eq("idClient", idClient), eq("idChambre", idChambre))).first() != null;
    }

    /**
     * Lecture d'une reservation.
     */
    public TupleReserveChambre getReservation(int idClient, int idChambre)
    {
        Document r = reservationsCollection.find(and(eq("idClient", idClient), eq("idChambre", idChambre))).first();
        if(r != null)
        {
            return new TupleReserveChambre(r);
        }
        return null;
    }

    /**
     * Lecture de la première reservation d'un client.
     */
    public TupleReserveChambre getReservationClient(int idClient)
    {
        Document l = reservationsCollection.find(eq("idClient", idClient)).first();
        if(l != null)
        {
            return new TupleReserveChambre(l);
        }
        return null;
    }


//    /**
//     * Lecture des reservation avec prix total d'un client
//     */
//    public List<TupleReserveChambre> getReservationPrixClient(int idClient) throws SQLException
//    {
//        stmtListeReservationPrixClient.setInt(1, idClient);
//        ResultSet rset = stmtListeReservationPrixClient.executeQuery();
//
//        List<TupleReserveChambre> listReserveChambres = new ArrayList<>();
//        while (rset.next())
//        {
//            TupleReserveChambre tupleReservation = new TupleReserveChambre();
//            tupleReservation.setIdReservation(rset.getInt(1));
//            tupleReservation.setIdClient(rset.getInt(2));
//            tupleReservation.setIdChambre(rset.getInt(3));
//            tupleReservation.setDateDebut(rset.getDate(4));
//            tupleReservation.setDateFin(rset.getDate(5));
//            tupleReservation.setPrixTotal(rset.getFloat(6));
//
//            listReserveChambres.add(tupleReservation);
//        }
//        rset.close();
//        return listReserveChambres;
//    }

    /**
     * Lecture de la première reservation d'un chambre.
     */
    public TupleReserveChambre getReservationChambre(int idChambre)
    {
        Document d = reservationsCollection.find(eq("idChambre", idChambre)).first();
        if(d != null)
        {
            return new TupleReserveChambre(d);
        }
        return null;
    }

    /**
     * Réservation d'une chambre.
     */
    public void reserver(int idClient, int idChambre, Date dateDebut, Date dateFin)
    {
        TupleReserveChambre r = new TupleReserveChambre(idClient, idChambre, dateDebut, dateFin);
        reservationsCollection.insertOne(r.toDocument());
    }

    /**
     * Suppression d'une reservation.
     */
    public boolean annulerRes(int idReservation)
    {
        return reservationsCollection.deleteOne(eq("_id", idReservation)).getDeletedCount() > 0;
    }

    /**
     * Liste toute les réservations
     */
    public List<TupleReserveChambre> listerReservations()
    {
        List<TupleReserveChambre> liste = new LinkedList<TupleReserveChambre>();
        MongoCursor<Document> reservations = reservationsCollection.find().iterator();
        try
        {
            while (reservations.hasNext())
            {
                liste.add(new TupleReserveChambre(reservations.next()));
            }
        }
        finally
        {
            reservations.close();
        }

        return liste;
    }
}
