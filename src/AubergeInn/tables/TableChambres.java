package AubergeInn.tables;


import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TupleReserveChambre;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TableChambres
{
    private Connexion cx;
    private MongoCollection<Document> chambresCollection;

//    private final PreparedStatement stmtExiste;
//    private final PreparedStatement stmtInsert;
//    private final PreparedStatement stmtUpdate;
//    private final PreparedStatement stmtDelete;
//
//    private final PreparedStatement stmtChambresLibre;
//    private final PreparedStatement stmtIsChambreLibre;



    public TableChambres(Connexion cx)
    {
        this.cx = cx;
        chambresCollection = cx.getDatabase().getCollection("Chambre");

//        this.stmtExiste = cx.getConnection().prepareStatement("select idchambre, nom, type, prixbase from chambre where idchambre = ?");
//        this.stmtInsert = cx.getConnection().prepareStatement("insert into chambre (idchambre, nom, type, prixbase) "
//                + "values (?,?,?,?)");
//        this.stmtUpdate = cx.getConnection().prepareStatement("update chambre set nom = ?, type = ?, prixbase = ? " + "where idchambre = ?");
//        this.stmtDelete = cx.getConnection().prepareStatement("delete from chambre where idchambre = ?");
//
//
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

    /**
     * Lecture d'une chambre.
     */
    public TupleChambre getChambre(int idChambre)
    {
        Document ch = chambresCollection.find(eq("idChambre", idChambre)).first();
        if(ch != null)
        {
            return new TupleChambre(ch);
        }
        return null;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public void ajouter(int idChambre, String nom, String type, Float prixBase)
    {
        TupleChambre ch = new TupleChambre(idChambre, nom, type, prixBase);

        // Ajout d'une chambre.
        chambresCollection.insertOne(ch.toDocument());
    }

    /**
     * Suppression  d'une chambre.
     */
    public boolean supprimer(int idChambre)
    {
        /* Suppression d'une chambre. */
        return chambresCollection.deleteOne(eq("idChambre", idChambre)).getDeletedCount() > 0;
    }



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


}
