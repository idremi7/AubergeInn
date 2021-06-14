package AubergeInn.tables;

import AubergeInn.Connexion;
import AubergeInn.tuples.TupleClient;
import AubergeInn.tuples.TupleCommodite;
import AubergeInn.tuples.TuplePossedeCommodite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TableCommodites
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;

    private final Connexion cx;

    public TableCommodites(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection()
                .prepareStatement("select idcommodite, description, prix from commodite where idcommodite = ?");
        this.stmtInsert = cx.getConnection().prepareStatement("insert into commodite (idcommodite, description, prix) "
                + "values (?,?,?)");
        this.stmtUpdate = cx.getConnection().prepareStatement("update commodite set description = ?, prix = ? " + "where idcommodite = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from commodite where idcommodite = ?");
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
        stmtExiste.setInt(1, idCommodite);
        ResultSet rset = stmtExiste.executeQuery();
        boolean commoditeExiste = rset.next();
        rset.close();
        return commoditeExiste;
    }

    /**
     * Ajout d'une nouvelle Commodite dans la base de données.
     */
    public void ajouter(int idCommodite, String description, float prix) throws SQLException
    {
        /* Ajout d'une commodite-canard. */
        stmtInsert.setInt(1, idCommodite);
        stmtInsert.setString(2, description);
        stmtInsert.setFloat(3, prix);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression  d'une commodite.
     */
    public int supprimer(int idCommodite) throws SQLException
    {
        /* Suppression d'une commodite. */
        stmtDelete.setInt(1, idCommodite);
        return stmtDelete.executeUpdate();
    }

    /**
     * Lecture d'une commodite.
     */
    public TupleCommodite getCommodite(int idCommodite) throws SQLException
    {
        stmtExiste.setInt(1, idCommodite);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleCommodite tupleCommodite = new TupleCommodite();
            tupleCommodite.setIdCommodite(idCommodite);
            tupleCommodite.setDescription(rset.getString(2));
            tupleCommodite.setPrix(rset.getFloat(3));

            rset.close();
            return tupleCommodite;
        }
        else
            return null;
    }





}
