package AubergeInn.tables;

import AubergeInn.Connexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TablePossedeCommodite
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;

    private final Connexion cx;

    public TablePossedeCommodite(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection().prepareStatement("select * from possedecommodite where idcommodite = ? and idchambre = ?");
        this.stmtInsert = cx.getConnection()
                .prepareStatement("insert into possedecommodite (idcommodite, idchambre) "+ "values (?,?)");
        this.stmtUpdate = cx.getConnection()
                .prepareStatement("update possedecommodite " +
                        "set idcommodite = ?, idchambre = ? " + "where idcommodite = ? and idchambre = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from possedecommodite where idcommodite = ? and idchambre = ?");
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
        stmtExiste.setInt(1, idCommodite);
        stmtExiste.setInt(2, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean commoditeChambreExiste = rset.next();
        rset.close();
        return commoditeChambreExiste;
    }

    /**
     * Ajout d'un lien commodite dans la base de données.
     */
    public void ajouter(int idCommodite, int idChambre) throws SQLException
    {
        /* Ajout d'une commodite-canard. */
        stmtInsert.setInt(1, idCommodite);
        stmtInsert.setInt(2, idChambre);
        stmtInsert.executeUpdate();
    }
}
