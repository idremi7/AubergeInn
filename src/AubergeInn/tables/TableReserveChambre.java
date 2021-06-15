package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleReserveChambre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableReserveChambre
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtListeReservationClient;
    private final PreparedStatement stmtListeReservationPrixClient;
    private final PreparedStatement stmtListeReservationChambre;
    private final PreparedStatement stmtListeTousReservation;


    private final Connexion cx;

    public TableReserveChambre(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection()
                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idclient = ? and idchambre = ?");
        this.stmtInsert = cx.getConnection()
                .prepareStatement("insert into reservechambre (idclient, idchambre, datedebut, datefin) "+ "values (?,?,?,?)");
        this.stmtUpdate = cx.getConnection()
                .prepareStatement("update reservechambre " +
                        "set idclient = ?, idchambre = ?, datedebut = ?, datefin = ? " + "where idclient = ? and idchambre = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from reservechambre where idreservation = ? ");

        this.stmtListeReservationClient = cx.getConnection()
                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idclient = ?");

        this.stmtListeReservationChambre = cx.getConnection()
                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idchambre = ?");

        this.stmtListeTousReservation = cx.getConnection()
                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre");

        this.stmtListeReservationPrixClient = cx.getConnection()
                .prepareStatement("Select  r.idreservation, r.idclient, r.idchambre, r.datedebut, r.datefin,(c.prixbase + coalesce(SUM(co.prix),0)) as prixTotal\n" +
                        "from reservechambre r\n" +
                        "JOIN chambre c on r.idchambre = c.idchambre\n" +
                        "LEFT JOIN possedecommodite p on c.idchambre = p.idchambre\n" +
                        "LEFT JOIN commodite co on co.idcommodite = p.idcommodite\n" +
                        "where r.idclient = ?\n" +
                        "GROUP BY c.prixbase, r.idreservation;");
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
    public boolean existe(int idClient, int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        stmtExiste.setInt(2, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean reservationExiste = rset.next();
        rset.close();
        return reservationExiste;
    }

    /**
     * Lecture d'une reservation.
     */
    public TupleReserveChambre getReservation(int idClient, int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idClient);
        stmtExiste.setInt(2, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleReserveChambre tupleReserveChambre = new TupleReserveChambre();
            tupleReserveChambre.setIdClient(rset.getInt(1));
            tupleReserveChambre.setIdChambre(rset.getInt(2));
            tupleReserveChambre.setDateDebut(rset.getDate(3));
            tupleReserveChambre.setDateFin(rset.getDate(4));
            return tupleReserveChambre;
        }
        else
        {
            return null;
        }
    }

    /**
     * Lecture de la première reservation d'un client.
     */
    public TupleReserveChambre getReservationClient(int idClient) throws SQLException
    {
        stmtListeReservationClient.setInt(1, idClient);
        ResultSet rset = stmtListeReservationClient.executeQuery();
        if (rset.next())
        {
            TupleReserveChambre tupleReservation = new TupleReserveChambre();
            tupleReservation.setIdClient(rset.getInt(1));
            tupleReservation.setIdChambre(rset.getInt(2));
            tupleReservation.setDateDebut(rset.getDate(3));
            tupleReservation.setDateFin(rset.getDate(4));
            return tupleReservation;
        }
        else
        {
            return null;
        }
    }


    /**
     * Lecture des reservation avec prix total d'un client
     */
    public List<TupleReserveChambre> getReservationPrixClient(int idClient) throws SQLException
    {
        stmtListeReservationPrixClient.setInt(1, idClient);
        ResultSet rset = stmtListeReservationPrixClient.executeQuery();

        List<TupleReserveChambre> listReserveChambres = new ArrayList<>();
        while (rset.next())
        {
            TupleReserveChambre tupleReservation = new TupleReserveChambre();
            tupleReservation.setIdReservation(rset.getInt(1));
            tupleReservation.setIdClient(rset.getInt(2));
            tupleReservation.setIdChambre(rset.getInt(3));
            tupleReservation.setDateDebut(rset.getDate(4));
            tupleReservation.setDateFin(rset.getDate(5));
            tupleReservation.setPrixTotal(rset.getFloat(6));

            listReserveChambres.add(tupleReservation);
        }
       rset.close();
        return listReserveChambres;
    }

    /**
     * Lecture de la première reservation d'un chambre.
     */
    public TupleReserveChambre getReservationChambre(int idChambre) throws SQLException
    {
        stmtListeReservationChambre.setInt(1, idChambre);
        ResultSet rset = stmtListeReservationChambre.executeQuery();
        if (rset.next())
        {
            TupleReserveChambre tupleReservation = new TupleReserveChambre();
            tupleReservation.setIdClient(rset.getInt(1));
            tupleReservation.setIdChambre(rset.getInt(2));
            tupleReservation.setDateDebut(rset.getDate(3));
            tupleReservation.setDateFin(rset.getDate(4));
            return tupleReservation;
        }
        else
        {
            return null;
        }
    }

    /**
     * Réservation d'une chambre.
     */
    public void reserver(int idClient, int idChambre, Date dateDebut, Date dateFin) throws SQLException
    {
        stmtInsert.setInt(1, idClient);
        stmtInsert.setInt(2, idChambre);
        stmtInsert.setDate(3, dateDebut);
        stmtInsert.setDate(4, dateFin);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression d'une reservation.
     */
    public int annulerRes(int idReservation) throws SQLException
    {
        stmtDelete.setInt(1, idReservation);
        return stmtDelete.executeUpdate();
    }

}
