package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleClient;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TuplePossedeCommodite;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class TableCommodites
{
//    private final PreparedStatement stmtExiste;
//    private final PreparedStatement stmtInsert;
//    private final PreparedStatement stmtUpdate;
//    private final PreparedStatement stmtDelete;
//    private final PreparedStatement stmtChambresCommodites;

    private final Connexion cx;
    private MongoCollection<Document> commoditesCollection;

    public TableCommodites(Connexion cx)
    {
        this.cx = cx;
        commoditesCollection = cx.getDatabase().getCollection("Commodite");
//        this.stmtExiste = cx.getConnection()
//                .prepareStatement("select idcommodite, description, prix from commodite where idcommodite = ?");
//        this.stmtInsert = cx.getConnection().prepareStatement("insert into commodite (idcommodite, description, prix) "
//                + "values (?,?,?)");
//        this.stmtUpdate = cx.getConnection().prepareStatement("update commodite set description = ?, prix = ? " + "where idcommodite = ?");
//        this.stmtDelete = cx.getConnection().prepareStatement("delete from commodite where idcommodite = ?");
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
     * Vérifie si une commodité existe.
     */
    public boolean existe(int idCommodite)
    {
        return commoditesCollection.find(eq("idCommodite", idCommodite)).first() != null;
    }

    /**
     * Ajout d'une nouvelle Commodite dans la base de données.
     */
    public void ajouter(int idCommodite, String description, float prix)
    {
        TupleCommodite c = new TupleCommodite(idCommodite, description, prix);
        /* Ajout d'une commodite. */
        commoditesCollection.insertOne(c.toDocument());
    }

    /**
     * Suppression  d'une commodite.
     */
    public boolean supprimer(int idCommodite)
    {
        /* Suppression d'une commodite. */
        return commoditesCollection.deleteOne(eq("idCommodite", idCommodite)).getDeletedCount() > 0;
    }

    /**
     * Lecture d'une commodite.
     */
    public TupleCommodite getCommodite(int idCommodite)
    {
        Document co = commoditesCollection.find(eq("idCommodite", idCommodite)).first();
        if(co != null)
        {
            return new TupleCommodite(co);
        }
        return null;
    }

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
}
