package AubergeInn.tuples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TupleReserveChambre
{
    @Id
    @GeneratedValue
    private long id;

    private long idReservation;
    private TupleClient client;
    private TupleChambre chambre;
    private Date dateDebut;
    private Date dateFin;

    public TupleReserveChambre()
    {
    }

    public TupleReserveChambre(int idReservation, TupleClient client, TupleChambre chambre, Date dateDebut, Date dateFin)
    {
        this.idReservation = idReservation;
        this.client = client;
        this.chambre = chambre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public TupleReserveChambre(TupleClient client, TupleChambre chambre, Date dateDebut, Date dateFin)
    {
        this.client = client;
        this.chambre = chambre;
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

    public TupleClient getClient()
    {
        return client;
    }

    public void setClient(TupleClient client)
    {
        this.client = client;
    }

    public TupleChambre getChambre()
    {
        return chambre;
    }

    public void setChambre(TupleChambre chambre)
    {
        this.chambre = chambre;
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
