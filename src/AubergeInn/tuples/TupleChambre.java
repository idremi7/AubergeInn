package AubergeInn.tuples;

public class TupleChambre
{
    private int idChambre;
    private String nom;
    private String type;
    private float prixBase;

    public TupleChambre()
    {
    }

    public TupleChambre(int idChambre, String nom, String type, float prixBase)
    {
        this.idChambre = idChambre;
        this.nom = nom;
        this.type = type;
        this.prixBase = prixBase;
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
}
