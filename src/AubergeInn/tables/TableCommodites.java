package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleCommodite;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class TableCommodites
{
    private final Connexion cx;
    private MongoCollection<Document> commoditesCollection;

    public TableCommodites(Connexion cx)
    {
        this.cx = cx;
        commoditesCollection = cx.getDatabase().getCollection("Commodite");
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
}
