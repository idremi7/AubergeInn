package AubergeInn.tuples;

public class TuplePossedeCommodite
{
    private int idCommodite;
    private int idChambre;

    public TuplePossedeCommodite()
    {
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
}
