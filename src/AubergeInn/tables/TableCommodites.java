package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleCommodite;

import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

public class TableCommodites
{
    private final TypedQuery<TupleCommodite> stmtExiste;
    private final TypedQuery<TupleCommodite> stmtListerCommodites;
    private final Connexion cx;

    public TableCommodites(Connexion cx)
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection()
                .createQuery("select c from TupleCommodite c where c.idCommodite = :idcommodite", TupleCommodite.class);

        this.stmtListerCommodites = cx.getConnection()
                .createQuery("select c from TupleCommodite c", TupleCommodite.class);
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
    public boolean existe(int idCommodite) throws SQLException
    {
        stmtExiste.setParameter("idcommodite", idCommodite);
        return !stmtExiste.getResultList().isEmpty();
    }

    /**
     * Ajout d'une nouvelle Commodite dans la base de données.
     */
    public TupleCommodite ajouter(TupleCommodite commodite)
    {
        /* Ajout d'une commodite. */
        cx.getConnection().persist(commodite);
        return commodite;
    }

    /**
     * Suppression  d'une commodite.
     */
    public boolean supprimer(TupleCommodite commodite) throws SQLException
    {
        /* Suppression d'une commodite. */
        if (commodite != null)
        {
            cx.getConnection().remove(commodite);
            return true;
        }
        return false;
    }

    /**
     * Lecture d'une commodite.
     */
    public TupleCommodite getCommodite(int idCommodite)
    {
        stmtExiste.setParameter("idcommodite", idCommodite);
        List<TupleCommodite> commodites = stmtExiste.getResultList();
        if (!commodites.isEmpty())
        {
            return commodites.get(0);
        } else
        {
            return null;
        }
    }

    /**
     * Lecture d'une commodite.
     */
    public List<TupleCommodite> ListerCommodites()
    {
       return stmtListerCommodites.getResultList();
    }

}
