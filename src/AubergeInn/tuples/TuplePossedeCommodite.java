package AubergeInn.tuples;

import javax.persistence.*;

@Entity
public class TuplePossedeCommodite
{
    @Id
    @GeneratedValue
    private long id;

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

}
