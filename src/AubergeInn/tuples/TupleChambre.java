package AubergeInn.tuples;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class TupleChambre
{
    @Id
    @GeneratedValue
    private long id;

    private int idChambre;
    private String nom;
    private String type;
    private float prixBase;
    private List<TupleCommodite> commodites;

    @ManyToMany
    @JoinTable(name="TupleClient")
    private TupleClient client;
    private Date dateDebut;
    private Date dateFin;

    public TupleChambre()
    {
    }

    public TupleChambre(int idChambre, String nom, String type, float prixBase)
    {
        this.idChambre = idChambre;
        this.nom = nom;
        this.type = type;
        this.prixBase = prixBase;
        this.commodites = null;
        this.client = null;
        this.dateDebut = null;
        this.dateFin = null;
    }

    public int getIdChambre()
    {
        return idChambre;
    }

    public void setIdChambre(int idChambre)
    {
        this.idChambre = idChambre;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public float getPrixBase()
    {
        return prixBase;
    }

    public void setPrixBase(float prixBase)
    {
        this.prixBase = prixBase;
    }

    public void reserverChambre(TupleClient c, Date dateDebut, Date dateFin)
    {
        this.client = c;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public TupleClient getClientChambre()
    {
        return this.client;
    }

    public Date getDateDebut()
    {
        return this.dateDebut;
    }

    public String toString()
    {
        StringBuffer s = new StringBuffer(getIdChambre() + " " + getNom() + " " + getType());
        if (getClientChambre() != null)
            s.append(" reserver a " + getClientChambre().getNom() + " " + getDateDebut());
        return s.toString();
    }
}
