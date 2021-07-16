package AubergeInn.tuples;

import org.bson.Document;

public class TupleClient
{
    private int idClient;
    private String nom;
    private String prenom;
    private int age;

    public TupleClient()
    {
    }

    public TupleClient(Document d)
    {
        idClient = d.getInteger("idClient");
        nom = d.getString("nom");
        prenom = d.getString("prenom");
        age = d.getInteger("age");
    }

    public TupleClient(int idClient, String nom, String prenom, int age)
    {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public int getIdClient()
    {
        return idClient;
    }

    public void setIdClient(int idClient)
    {
        this.idClient = idClient;
    }

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Document toDocument()
    {
        return new Document().append("idClient", idClient)
                .append("nom", nom)
                .append("prenom", prenom)
                .append("age", age);
    }

    @Override
    public String toString()
    {
        return "TupleClient{" +
                "idClient=" + idClient +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                '}';
    }
}
