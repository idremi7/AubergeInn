package AubergeInn.tuples;

import org.bson.Document;

public class TuplePossedeCommodite
{
    private int idCommodite;
    private int idChambre;

    public TuplePossedeCommodite()
    {
    }

    public TuplePossedeCommodite(Document d)
    {
        this.idCommodite = d.getInteger("idCommodite");
        this.idChambre = d.getInteger("idChambre");
    }

    public TuplePossedeCommodite(int idCommodite, int idChambre)
    {
        this.idCommodite = idCommodite;
        this.idChambre = idChambre;
    }

    public int getIdCommodite()
    {
        return idCommodite;
    }

    public void setIdCommodite(int idCommodite)
    {
        this.idCommodite = idCommodite;
    }

    public int getIdChambre()
    {
        return idChambre;
    }

    public void setIdChambre(int idChambre)
    {
        this.idChambre = idChambre;
    }

    public Document toDocument()
    {
        return new Document().append("idCommodite", idCommodite)
                .append("idChambre", idChambre);
    }
}
