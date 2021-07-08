package AubergeInn.tuples;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<TupleCommodite> commodites;

    public TupleChambre()
    {
        this.commodites = new ArrayList<>();
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

    public boolean isCommoditeExiste(TupleCommodite c){
        if (commodites.contains(c)){
            return true;
        }else{
            return false;
        }
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

        float total = 0;
        for (TupleCommodite c : commodites)
        {
            total += c.getPrix();
        }
        return total + prixBase;
    }

    public void afficherInfosChambre()
    {
        System.out.println("\nid nom type prixLocation");
        System.out.println(idChambre + " " + nom + " " + type + " " + calculerPrixTotal());
    }
}
