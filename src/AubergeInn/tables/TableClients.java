package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleClient;

import javax.persistence.TypedQuery;
import java.util.List;

public class TableClients
{
    private final TypedQuery<TupleClient> stmtExiste;
    private final TypedQuery<TupleClient> stmtListeTousClients;
    private final Connexion cx;

    public TableClients(Connexion cx)
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection().createQuery("select c from TupleClient c where c.idClient = :idClient", TupleClient.class);
        this.stmtListeTousClients = cx.getConnection().createQuery("select c from TupleClient c", TupleClient.class);
    }

    /**
     * Retourner la connexion associée.
     */
    public Connexion getConnexion()
    {
        return cx;
    }

    /**
     * Vérifie si un client existe.
     */
    public boolean existe(int idClient)
    {
        stmtExiste.setParameter("idClient", idClient);
        return !stmtExiste.getResultList().isEmpty();
    }

    /**
     * Lecture d'un Client.
     */
    public TupleClient getClient(int idClient)
    {
        stmtExiste.setParameter("idClient", idClient);
        List<TupleClient> clients = stmtExiste.getResultList();
        if (!clients.isEmpty())
        {
            return clients.get(0);
        } else
        {
            return null;
        }
    }

    /**
     * Ajout d'un nouveau Client dans la base de données.
     */
    public TupleClient ajouter(TupleClient client)
    {
        /* Ajout d'un client-canard. */
        cx.getConnection().persist(client);
        return client;
    }

    /**
     * Suppression  d'un client.
     */
    public Boolean supprimer(TupleClient client)
    {
        /* Suppression d'une chambre. */
        if (client != null)
        {
            cx.getConnection().remove(client);
            return true;
        }
        return false;
    }
}
