package AubergeInn.collections;

import AubergeInn.Connexion;
import AubergeInn.tuples.TuplePossedeCommodite;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

public class CollectionPossedeCommodite
{
    private final Connexion cx;
    private final MongoCollection<Document> possedeCommoditeCollection;

    public CollectionPossedeCommodite(Connexion cx)
    {
        this.cx = cx;
        possedeCommoditeCollection = cx.getDatabase().getCollection("PossedeCommodite");
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
     * Lecture d'un lien commodite-chambre avec un idCommodite
     */
    public TuplePossedeCommodite getCommoditeChambre(int idCommodite)
    {
        Document p = possedeCommoditeCollection
                .find(and(eq("idCommodite", idCommodite))).first();
        if(p != null)
        {
            return new TuplePossedeCommodite(p);
        }
        return null;
    }

    /**
     * Liste toute les réservations
     */
    public List<TuplePossedeCommodite> listerCommoditeChambre(int idChambre)
    {
        List<TuplePossedeCommodite> liste = new LinkedList<>();
        MongoCursor<Document> commoditeChambre = possedeCommoditeCollection.find(eq("idChambre", idChambre)).iterator();
        try
        {
            while (commoditeChambre.hasNext())
            {
                liste.add(new TuplePossedeCommodite(commoditeChambre.next()));
            }
        }
        finally
        {
            commoditeChambre.close();
        }

        return liste;
    }
}
