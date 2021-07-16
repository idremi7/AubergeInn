package AubergeInn.tuples;

import org.bson.Document;

import java.util.Date;

public class TupleReserveChambre
{
    private int idClient;
    private int idChambre;
    private Date dateDebut;
    private Date dateFin;
    private float prixTotal;

    public TupleReserveChambre()
    {
    }

    public TupleReserveChambre(Document d)
    {
        this.idClient = d.getInteger("idClient");
        this.idChambre = d.getInteger("idChambre");
        this.dateDebut = d.getDate("dateDebut");
        this.dateFin  = d.getDate("dateFin");
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

    public float getPrixTotal()
    {
        return prixTotal;
    }

    public void setPrixTotal(float prixTotal)
    {
        this.prixTotal = prixTotal;
    }

    public Document toDocument()
    {
        return new Document().append("idClient", idClient)
                .append("idChambre", idChambre)
                .append("dateDebut", dateDebut)
                .append("dateFin", dateFin);
    }
}
