package AubergeInn.tuples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TupleCommodite
{
    @Id
    @GeneratedValue
    private long id;

    private int idCommodite;
    private String description;
    private float prix;

    public TupleCommodite()
    {
    }

    public TupleCommodite(int idCommodite, String description, float prix)
    {
        this.idCommodite = idCommodite;
        this.description = description;
        this.prix = prix;
    }

    public int getIdCommodite()
    {
        return idCommodite;
    }

    public void setIdCommodite(int idCommodite)
    {
        this.idCommodite = idCommodite;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public float getPrix()
    {
        return prix;
    }

    public void setPrix(float prix)
    {
        this.prix = prix;
    }
}
