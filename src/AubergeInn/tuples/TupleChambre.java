package AubergeInn.tuples;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "chambres", cascade = CascadeType.ALL)
    private List<TupleCommodite> commodites;

    public TupleChambre()
    {
    }

    public TupleChambre(int idChambre, String nom, String type, float prixBase)
    {
        this.idChambre = idChambre;
        this.nom = nom;
        this.type = type;
        this.prixBase = prixBase;
        this.commodites = new ArrayList<>();
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

    public List<TupleCommodite> getCommodites()
    {
        return commodites;
    }

    public void setCommodites(List<TupleCommodite> commodites)
    {
        this.commodites = commodites;
    }

    public int getNbCommodite()
    {
        return commodites.size();
    }

    public void ajouteCommodite(TupleCommodite c)
    {
        commodites.add(c);
    }

    public void supprimerCommodite(TupleCommodite c)
    {
        commodites.remove(c);
    }

    public float calculerPrixTotal(){

        float total = prixBase;
        for (TupleCommodite c : commodites)
        {
            total += c.getPrix();
        }
        return total;
    }

    public void afficherInfosChambre()
    {
        System.out.println("\nid nom type prixLocation");
        System.out.println(idChambre + " " + nom + " " + type + " " + calculerPrixTotal());
    }
}
