package AubergeInn.collections;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class CollectionChambres
{
    private Connexion cx;
    private MongoCollection<Document> chambresCollection;

    public CollectionChambres(Connexion cx)
    {
        this.cx = cx;
        chambresCollection = cx.getDatabase().getCollection("Chambre");
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

    /**
     * Liste toute les chambres
     */
    public List<TupleChambre> calculerListeChambre()
    {
        List<TupleChambre> liste = new LinkedList<TupleChambre>();
        MongoCursor<Document> chambre = chambresCollection.find().iterator();
        try
        {
            while (chambre.hasNext())
            {
                liste.add(new TupleChambre(chambre.next()));
            }
        }
        finally
        {
            chambre.close();
        }

        return liste;
    }
}
