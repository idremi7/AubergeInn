package AubergeInn.tables;


import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TupleReserveChambre;

import java.util.*;

import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableChambres
{
    private final TypedQuery<TupleChambre> stmtExiste;
//    private final TypedQuery<TupleChambre> stmtChambreReserve;
//    private final TypedQuery<TupleChambre> stmtChambresLibre;
//    private final TypedQuery<TupleChambre> stmtIsChambreLibre;
//    private final TypedQuery<TupleChambre> stmtChambresCommodites;

    private final Connexion cx;

    public TableChambres(Connexion cx)
    {
        this.cx = cx;
        stmtExiste = cx.getConnection().createQuery("select ch from TupleChambre ch where ch.idChambre = :idchambre", TupleChambre.class);

//        this.stmtChambreReserve = cx.getConnection()
//                .prepareStatement("select t2.idreservation, t2.idclient ,t2.idchambre, t2.datedebut, t2.datefin\n" +
//                        "from  reservechambre t2, chambre t3\n" +
//                        "where t3.idchambre = t2.idchambre;");
//
//        this.stmtChambresLibre = cx.getConnection()
//                .prepareStatement(
//                        "Select ch.idchambre, ch.nom, ch.type , (ch.prixbase + coalesce(SUM(c.prix),0)) as prixLocation " +
//                                "from chambre ch " +
//                                "LEFT JOIN reservechambre r on ch.idchambre = r.idchambre " +
//                                "LEFT JOIN possedecommodite p on ch.idchambre = p.idchambre " +
//                                "LEFT JOIN commodite c on p.idcommodite = c.idcommodite " +
//                                "where r.idchambre IS NULL OR now() NOT between r.datedebut and r.datefin " +
//                                "GROUP BY ch.idchambre;");
//
//        this.stmtIsChambreLibre = cx.getConnection().prepareStatement("Select ch.idchambre, ch.nom, ch.type , (ch.prixbase + coalesce(SUM(c.prix),0)) as prixLocation\n" +
//                "                    from chambre ch\n" +
//                "                    LEFT JOIN reservechambre r on ch.idchambre = r.idchambre\n" +
//                "                    LEFT JOIN possedecommodite p on ch.idchambre = p.idchambre\n" +
//                "                    LEFT JOIN commodite c on p.idcommodite = c.idcommodite\n" +
//                "                    where ch.idchambre = ? AND r.idchambre IS NULL OR now() NOT between r.datedebut and r.datefin\n" +
//                "                    GROUP BY ch.idchambre;");
//
//
//        this.stmtChambresCommodites = cx.getConnection()
//                .prepareStatement("select c.*\n" +
//                        "from chambre t1\n" +
//                        "JOIN possedecommodite p on t1.idchambre = p.idchambre\n" +
//                        "JOIN commodite c on c.idcommodite = p.idcommodite\n" +
//                        "WHERE t1.idchambre = ?;");
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
    public boolean existe(int idChambre) throws SQLException
    {
        stmtExiste.setParameter("idchambre", idChambre);
        return  !stmtExiste.getResultList().isEmpty();
    }

//    /**
//     * Vérifie si une chambre est libre.
//     */
//    public boolean isChambreLibre(int idChambre) throws SQLException
//    {
//        stmtIsChambreLibre.setInt(1, idChambre);
//        ResultSet rset = stmtIsChambreLibre.executeQuery();
//        boolean chambreExiste = rset.next();
//        rset.close();
//        return chambreExiste;
//    }
//
//    /**
//     * Lecture d'une chambre.
//     */
//    public TupleChambre getChambre(int idChambre) throws SQLException
//    {
//        stmtExiste.setInt(1, idChambre);
//        ResultSet rset = stmtExiste.executeQuery();
//        if (rset.next())
//        {
//            TupleChambre tupleChambre = new TupleChambre();
//            tupleChambre.setIdChambre(idChambre);
//            tupleChambre.setNom(rset.getString(2));
//            tupleChambre.setType(rset.getString(3));
//            tupleChambre.setPrixBase(rset.getInt(4));
//
//            rset.close();
//            return tupleChambre;
//        } else
//            return null;
//    }
//
//    /**
//     * Ajout d'une nouvelle chambre dans la base de données.
//     */
//    public void ajouter(int idChambre, String nom, String type, float prixBase) throws SQLException
//    {
//        /* Ajout d'une chambre. */
//        stmtInsert.setInt(1, idChambre);
//        stmtInsert.setString(2, nom);
//        stmtInsert.setString(3, type);
//        stmtInsert.setFloat(4, prixBase);
//        stmtInsert.executeUpdate();
//    }
//
//    /**
//     * Suppression  d'une chambre.
//     */
//    public int supprimer(int idChambre) throws SQLException
//    {
//        /* Suppression d'une chambre. */
//        stmtDelete.setInt(1, idChambre);
//        return stmtDelete.executeUpdate();
//    }
//
//    /**
//     * Trouve toutes les commodites d'une chambre
//     */
//    public List<TupleCommodite> listerCommodites(int idChambre) throws SQLException
//    {
//        stmtChambresCommodites.setInt(1, idChambre);
//        ResultSet rset = stmtChambresCommodites.executeQuery();
//        List<TupleCommodite> listCommodite = new ArrayList<>();
//        while (rset.next())
//        {
//            TupleCommodite commodite = new TupleCommodite(rset.getInt(1),     //id
//                    rset.getString(2),  //description
//                    rset.getFloat(3));  //prix
//
//            listCommodite.add(commodite);
//        }
//        rset.close();
//        return listCommodite;
//    }
//
//    /**
//     * Trouve toutes les infos d'une chambre libre avec le prix de base + les commodites
//     */
//    public List<TupleChambre> listerChambresLibres() throws SQLException
//    {
//        ResultSet rset = stmtChambresLibre.executeQuery();
//        List<TupleChambre> listChambreLibre = new ArrayList<>();
//        while (rset.next())
//        {
//            TupleChambre chambre = new TupleChambre(rset.getInt(1),     //idchambre
//                    rset.getString(2),  //nom
//                    rset.getString(3),  //type
//                    rset.getFloat(4));  //prix de location
//
//            listChambreLibre.add(chambre);
//        }
//        rset.close();
//        return listChambreLibre;
//    }
//
//    /**
//     * Liste toute les réservations
//     */
//    public List<TupleReserveChambre> listerReservations() throws SQLException
//    {
//        ResultSet rset = stmtChambreReserve.executeQuery();
//        List<TupleReserveChambre> listReservation = new ArrayList<>();
//        while (rset.next())
//        {
//            TupleReserveChambre reservation = new TupleReserveChambre(rset.getInt(1),     //idreservation
//                    rset.getInt(2),    // idclient
//                    rset.getInt(3),    // idchambre
//                    rset.getDate(4),   // datedebut
//                    rset.getDate(5));  // datefin
//
//            listReservation.add(reservation);
//        }
//        rset.close();
//        return listReservation;
//    }
}
