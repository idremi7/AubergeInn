package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleReserveChambre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableReserveChambre
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;
    private final PreparedStatement stmtListeReservationClient;
    private final PreparedStatement stmtListeTousReservation;

    private final Connexion cx;

    public TableReserveChambre(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection().prepareStatement("select * from reservechambre where idclient = ? and idchambre = ?");
        this.stmtInsert = cx.getConnection()
                .prepareStatement("insert into reservechambre (idclient, idchambre, datedebut, datefin) "+ "values (?,?,?,?)");
        this.stmtUpdate = cx.getConnection()
                .prepareStatement("update reservechambre " +
                        "set idclient = ?, idchambre = ?, datedebut = ?, datefin = ? " + "where idclient = ? and idchambre = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from reservechambre where idclient = ? and idchambre = ?");
        this.stmtListeReservationClient = cx.getConnection()
                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre where idclient = ?");
        this.stmtListeTousReservation = cx.getConnection()
                .prepareStatement("select idclient, idchambre, datedebut, datefin from reservechambre");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
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
}
