package AubergeInn.tables;


import AubergeInn.Connexion;
import AubergeInn.tuples.TupleChambre;
import AubergeInn.tuples.TupleClient;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableChambres
{
    private final PreparedStatement stmtExiste;
    private final PreparedStatement stmtInsert;
    private final PreparedStatement stmtUpdate;
    private final PreparedStatement stmtDelete;

    private final PreparedStatement stmtChambreReserve;
    private final PreparedStatement stmtChambresLibre;

    private final Connexion cx;

    public TableChambres(Connexion cx) throws SQLException
    {
        this.cx = cx;
        this.stmtExiste = cx.getConnection().prepareStatement("select idchambre, nom, type, prixbase from chambre where idchambre = ?");
        this.stmtInsert = cx.getConnection().prepareStatement("insert into chambre (idchambre, nom, type, prixbase) "
                                + "values (?,?,?,?)");
        this.stmtUpdate = cx.getConnection().prepareStatement("update chambre set nom = ?, type = ?, prixbase = ? " + "where idchambre = ?");
        this.stmtDelete = cx.getConnection().prepareStatement("delete from chambre where idchambre = ?");

        this.stmtChambreReserve = cx.getConnection()
            .prepareStatement("select t1.idclient, t1.nom, t1.prenom, "
                      + "t1.age, " + "t2.idclient, t2.idchambre, t3.prixbase, t2.datedebut, t2.datefin "
                      + "from client t1, reservechambre t2, chambre t3 "
                      + "where  t1.idclient = t2.idclient and t3.idchambre = t2.idchambre ");

        this.stmtChambresLibre = cx.getConnection()
            .prepareStatement("select * "
                    + "from reservechambre "
                    + "where idchambre = ? ");
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
    public boolean existe(int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        boolean chambreExiste = rset.next();
        rset.close();
        return chambreExiste;
    }

    /**
     * Lecture d'une chambre.
     */
    public TupleChambre getChambre(int idChambre) throws SQLException
    {
        stmtExiste.setInt(1, idChambre);
        ResultSet rset = stmtExiste.executeQuery();
        if (rset.next())
        {
            TupleChambre tupleChambre = new TupleChambre();
            tupleChambre.setIdChambre(idChambre);
            tupleChambre.setNom(rset.getString(2));
            tupleChambre.setType(rset.getString(3));
            tupleChambre.setPrixBase(rset.getInt(4));

            rset.close();
            return tupleChambre;
        }
        else
            return null;
    }

    /**
     * Ajout d'une nouvelle chambre dans la base de données.
     */
    public void ajouterChambre(int idChambre, String nom, String type, float prixBase) throws SQLException
    {
        /* Ajout d'une chambre. */
        stmtInsert.setInt(1, idChambre);
        stmtInsert.setString(2, nom);
        stmtInsert.setString(3, type);
        stmtInsert.setFloat(4, prixBase);
        stmtInsert.executeUpdate();
    }

    /**
     * Suppression  d'une chambre.
     */
    public void supprimerChambre(int idChambre) throws SQLException
    {
        /* Suppression d'une chambre. */
        stmtDelete.setInt(1, idChambre);
        stmtDelete.executeUpdate();
    }
}
