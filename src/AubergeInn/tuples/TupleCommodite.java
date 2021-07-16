package AubergeInn.tuples;

import org.bson.Document;

public class TupleCommodite
{
    private int idCommodite;
    private String description;
    private float prix;

    public TupleCommodite()
    {
    }

    public TupleCommodite(Document d)
    {
        idCommodite = d.getInteger("idChambre");
        description = d.getString("nom");
        prix = Float.parseFloat(d.getString("prixBase"));
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

    public Document toDocument()
    {
        return new Document().append("idCommodite", idCommodite)
                .append("description", description)
                .append("prix", prix);
    }
}
