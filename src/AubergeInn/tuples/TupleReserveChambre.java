package AubergeInn.tuples;

import java.sql.Date;

public class TupleReserveChambre
{
    private int idClient;
    private int idChambre;
    private Date dateDebut;
    private Date dateFin;

    public TupleReserveChambre()
    {
    }

    public TupleReserveChambre(int idClient, int idChambre, Date dateDebut, Date dateFin)
    {
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public int getIdClient()
    {
        return idClient;
    }

    public void setIdClient(int idClient)
    {
        this.idClient = idClient;
    }

    public int getIdChambre()
    {
        return idChambre;
    }

    public void setIdChambre(int idChambre)
    {
        this.idChambre = idChambre;
    }

    public Date getDateDebut()
    {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut)
    {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin()
    {
        return dateFin;
    }

    public void setDateFin(Date dateFin)
    {
        this.dateFin = dateFin;
    }
}
