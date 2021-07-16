package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleClient;
import AubergeInn.tuples.TuplePossedeCommodite;
import AubergeInn.tuples.TupleReserveChambre;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class TablePossedeCommodite
{
//    private final PreparedStatement stmtExiste;
//    private final PreparedStatement stmtInsert;
//    private final PreparedStatement stmtUpdate;
//    private final PreparedStatement stmtDelete;

    private final Connexion cx;
    private MongoCollection<Document> possedeCommoditeCollection;

    public TablePossedeCommodite(Connexion cx)
    {
        this.cx = cx;
        possedeCommoditeCollection = cx.getDatabase().getCollection("PossedeCommodite");
//        this.stmtExiste = cx.getConnection().prepareStatement("select * from possedecommodite where idcommodite = ? and idchambre = ?");
//        this.stmtInsert = cx.getConnection()
//                .prepareStatement("insert into possedecommodite (idcommodite, idchambre) " + "values (?,?)");
//        this.stmtUpdate = cx.getConnection()
//                .prepareStatement("update possedecommodite " +
//                        "set idcommodite = ?, idchambre = ? " + "where idcommodite = ? and idchambre = ?");
//        this.stmtDelete = cx.getConnection().prepareStatement("delete from possedecommodite where idcommodite = ? and idchambre = ?");
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un lien chambre commodité existe.
     */
    public boolean existe(int idCommodite, int idChambre) throws SQLException
    {
        return possedeCommoditeCollection
                .find(and(eq("idCommodite", idCommodite), eq("idChambre", idChambre))).first() != null;
    }

    /**
     * Lecture de la commodite d'une chambre
     */
    public TuplePossedeCommodite getCommoditeChambre(int idCommodite, int idChambre)
    {
        Document p = possedeCommoditeCollection
                .find(and(eq("idCommodite", idCommodite), eq("idChambre", idChambre))).first();
        if(p != null)
        {
            return new TuplePossedeCommodite(p);
        }
        return null;
    }

    /**
     * Ajout d'un lien commodite-chambre dans la base de données.
     */
    public void ajouter(int idCommodite, int idChambre)
    {
        /* Ajout d'une commodite-canard. */
        TuplePossedeCommodite p = new TuplePossedeCommodite(idCommodite, idChambre);
        possedeCommoditeCollection.insertOne(p.toDocument());
    }

    /**
     * Suppression d'un lien commodite-chambre dans la base de données.
     */
    public boolean supprimer(int idCommodite, int idChambre)
    {
        /* Suppression d'un commodite-chambre. */
        return possedeCommoditeCollection
                .deleteOne(and(eq("idCommodite", idCommodite), eq("idChambre", idChambre))).getDeletedCount() > 0;
    }
}
