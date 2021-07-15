package AubergeInn.tables;


import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TupleReserveChambre;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TableChambres
{
    private Connexion cx;
    private MongoCollection<Document> chambresCollection;

    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;

    private final PreparedStatement stmtChambreReserve;
    private final PreparedStatement stmtChambresLibre;
    private final PreparedStatement stmtIsChambreLibre;
    private final PreparedStatement stmtChambresCommodites;

    private final Connexion cx;

    public TableChambres(Connexion cx) throws SQLException
    {
        this.cx = cx;
        chambresCollection = cx.getDatabase().getCollection("Chambres");

        this.stmtExiste = cx.getConnection().prepareStatement("select idchambre, nom, type, prixbase from chambre where idchambre = ?");
        this.stmtInsert = cx.getConnection().prepareStatement("insert into chambre (idchambre, nom, type, prixbase) "
                + "values (?,?,?,?)");
        this.stmtUpdate = cx.getConnection().prepareStatement("update chambre set nom = ?, type = ?, prixbase = ? " + "where idchambre = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from chambre where idchambre = ?");

        this.stmtChambreReserve = cx.getConnection()
                .prepareStatement("select t2.idreservation, t2.idclient ,t2.idchambre, t2.datedebut, t2.datefin\n" +
                        "from  reservechambre t2, chambre t3\n" +
                        "where t3.idchambre = t2.idchambre;");

        this.stmtChambresLibre = cx.getConnection()
                .prepareStatement(
                        "Select ch.idchambre, ch.nom, ch.type , (ch.prixbase + coalesce(SUM(c.prix),0)) as prixLocation " +
                                "from chambre ch " +
                                "LEFT JOIN reservechambre r on ch.idchambre = r.idchambre " +
                                "LEFT JOIN possedecommodite p on ch.idchambre = p.idchambre " +
                                "LEFT JOIN commodite c on p.idcommodite = c.idcommodite " +
                                "where r.idchambre IS NULL OR now() NOT between r.datedebut and r.datefin " +
                                "GROUP BY ch.idchambre;");

        this.stmtIsChambreLibre = cx.getConnection().prepareStatement("Select ch.idchambre, ch.nom, ch.type , (ch.prixbase + coalesce(SUM(c.prix),0)) as prixLocation\n" +
                "                    from chambre ch\n" +
                "                    LEFT JOIN reservechambre r on ch.idchambre = r.idchambre\n" +
                "                    LEFT JOIN possedecommodite p on ch.idchambre = p.idchambre\n" +
                "                    LEFT JOIN commodite c on p.idcommodite = c.idcommodite\n" +
                "                    where ch.idchambre = ? AND r.idchambre IS NULL OR now() NOT between r.datedebut and r.datefin\n" +
                "                    GROUP BY ch.idchambre;");


        this.stmtChambresCommodites = cx.getConnection()
                .prepareStatement("select c.*\n" +
                        "from chambre t1\n" +
                        "JOIN possedecommodite p on t1.idchambre = p.idchambre\n" +
                        "JOIN commodite c on c.idcommodite = p.idcommodite\n" +
                        "WHERE t1.idchambre = ?;");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si une chambre existe.
     */
    public boolean existe(int idChambre)
    {
        return chambresCollection.find(eq("idChambre", idChambre)).first() != null;
    }

    /**
     * Vérifie si une chambre est libre.
     */
    public boolean isChambreLibre(int idChambre) throws SQLException
    {
        stmtIsChambreLibre.setInt(1, idChambre);
        ResultSet rset = stmtIsChambreLibre.executeQuery();
        boolean chambreExiste = rset.next();
        rset.close();
        return chambreExiste;
    }

    /**
     * Lecture d'une chambre.
     */
    public TupleChambre getChambre(int idChambre)
    {
        Document l = chambresCollection.find(eq("idChambre", idChambre)).first();
        if(l != null)
        {
            return new TupleChambre(l);
        }
        return null;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public void ajouter(int idChambre, String nom, String type, Float prixBase)
    {
        TupleChambre l = new TupleChambre(idChambre, nom, type, prixBase);

        // Ajout du livre.
        chambresCollection.insertOne(l.toDocument());
    }

    /**
     * Suppression  d'une chambre.
     */
    public boolean supprimer(int idChambre) throws SQLException
    {
        /* Suppression d'une chambre. */
        return chambresCollection.deleteOne(eq("idChambre", idChambre)).getDeletedCount() > 0;
    }

    /**
     * Trouve toutes les commodites d'une chambre
     */
    public List<TupleCommodite> listerCommodites(int idChambre) throws SQLException
    {
        stmtChambresCommodites.setInt(1, idChambre);
        ResultSet rset = stmtChambresCommodites.executeQuery();
        List<TupleCommodite> listCommodite = new ArrayList<>();
        while (rset.next())
        {
            TupleCommodite commodite = new TupleCommodite(rset.getInt(1),     //id
                    rset.getString(2),  //description
                    rset.getFloat(3));  //prix

            listCommodite.add(commodite);
        }
        rset.close();
        return listCommodite;
    }

    /**
     * Trouve toutes les infos d'une chambre libre avec le prix de base + les commodites
     */
    public List<TupleChambre> listerChambresLibres() throws SQLException
    {
        ResultSet rset = stmtChambresLibre.executeQuery();
        List<TupleChambre> listChambreLibre = new ArrayList<>();
        while (rset.next())
        {
            TupleChambre chambre = new TupleChambre(rset.getInt(1),     //idchambre
                    rset.getString(2),  //nom
                    rset.getString(3),  //type
                    rset.getFloat(4));  //prix de location

            listChambreLibre.add(chambre);
        }
        rset.close();
        return listChambreLibre;
    }

    /**
     * Liste toute les réservations
     */
    public List<TupleReserveChambre> listerReservations() throws SQLException
    {
        ResultSet rset = stmtChambreReserve.executeQuery();
        List<TupleReserveChambre> listReservation = new ArrayList<>();
        while (rset.next())
        {
            TupleReserveChambre reservation = new TupleReserveChambre(rset.getInt(1),     //idreservation
                    rset.getInt(2),    // idclient
                    rset.getInt(3),    // idchambre
                    rset.getDate(4),   // datedebut
                    rset.getDate(5));  // datefin

            listReservation.add(reservation);
        }
        rset.close();
        return listReservation;
    }
}
