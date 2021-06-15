package AubergeInn.tuples;

import java.sql.Date;

public class TupleReserveChambre
{
    private long idReservation;
    private int idClient;
    private int idChambre;
    private Date dateDebut;
    private Date dateFin;
    private float prixTotal;

    public TupleReserveChambre()
    {
    }

    public TupleReserveChambre(int idReservation, int idClient, int idChambre, Date dateDebut, Date dateFin)
    {
        this.idReservation = idReservation;
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public long getIdReservation()
    {
        return idReservation;
    }

    public void setIdReservation(long idReservation)
    {
        this.idReservation = idReservation;
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

    public float getPrixTotal()
    {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal)
    {
        this.prixTotal = prixTotal;
    }
}
