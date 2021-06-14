// Travail fait par :
//   Pierre-Daniel Godfrey - 17179777
//   Rémi Létourneau - 19112826

package AubergeInn;

import AubergeInn.tables.*;
import AubergeInn.transactions.GestionChambre;
import AubergeInn.transactions.GestionClient;
import AubergeInn.transactions.GestionCommodite;
import AubergeInn.transactions.GestionReservation;

import java.io.*;
import java.util.StringTokenizer;
import java.sql.*;

/**
 * Fichier de base pour le TP2 du cours IFT287
 *
 * <pre>
 * 
 * Vincent Ducharme
 * Universite de Sherbrooke
 * Version 1.0 - 7 juillet 2016
 * IFT287 - Exploitation de BD relationnelles et OO
 * 
 * Ce programme permet d'appeler des transactions d'un systeme
 * de gestion utilisant une base de donnees.
 *
 * Paramètres du programme
 * 0- site du serveur SQL ("local" ou "dinf")
 * 1- nom de la BD
 * 2- user id pour etablir une connexion avec le serveur SQL
 * 3- mot de passe pour le user id
 * 4- fichier de transaction [optionnel]
 *           si non spécifié, les transactions sont lues au
 *           clavier (System.in)
 *
 * Pré-condition
 *   - La base de donnees doit exister
 *
 * Post-condition
 *   - Le programme effectue les mises à jour associees à chaque
 *     transaction
 * </pre>
 */
public class AubergeInn
{
    private GestionAubergeInn gestionAubergeInn;
    private static Connexion cx;
    private static TableClients client;
    private static TableChambres chambre;
    private static TableCommodites commodite;
    private static TablePossedeCommodite commoditeChambre;
    private static TableReserveChambre reservation;
    private static GestionClient gestionClient;
    private static GestionChambre gestionChambre;
    private static GestionCommodite gestionCommodite;
    private GestionReservation gestionReservation;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception
    {
        if (args.length < 4)
        {
            System.out.println("Usage: java AubergeInn.AubergeInn <serveur> <bd> <user> <password> [<fichier-transactions>]");
            return;
        }

        //AubergeInn aubergeInnInstance = null;
        Connexion cx = null;

        try
        {
            // Il est possible que vous ayez à déplacer la connexion ailleurs.
            // N'hésitez pas à le faire!
            //aubergeInnInstance = new AubergeInn(args[0], args[1], args[2], args[3]);
            cx = new Connexion(args[0], args[1], args[2], args[3]);
            client = new TableClients(cx);
            chambre = new TableChambres(cx);
            commodite = new TableCommodites(cx);
            commoditeChambre = new TablePossedeCommodite(cx);
            reservation = new TableReserveChambre(cx);

            gestionClient = new GestionClient(client, reservation);
            gestionChambre = new GestionChambre(chambre);
            gestionCommodite = new GestionCommodite(commodite, commoditeChambre);

            BufferedReader reader = ouvrirFichier(args);

            String transaction = lireTransaction(reader);
            while (!finTransaction(transaction))
            {
                executerTransaction(transaction);
                transaction = lireTransaction(reader);
            }
        }
        finally
        {
            if (cx != null)
                cx.fermer();
        }

    }

    public AubergeInn(String serveur, String bd, String user, String password) throws Exception
    {

    }

    public void fermer() throws Exception
    {
        cx.fermer();
    }

    /**
     * Decodage et traitement d'une transaction
     */
    static void executerTransaction(String transaction) throws Exception, IFT287Exception
    {
        try
        {
            System.out.print(transaction);
            // Decoupage de la transaction en mots
            StringTokenizer tokenizer = new StringTokenizer(transaction, " ");
            if (tokenizer.hasMoreTokens())
            {
                String command = tokenizer.nextToken();

                // *******************
                // HELP
                // *******************
                if (command.equals("aide"))
                {
                    afficherAide();
                }
                // *******************
                // ajouter un client
                // *******************
                else if (command.equals("ajouterClient"))
                {
                    // Lecture des parametres
                    int idclient = readInt(tokenizer);
                    String nom = readString(tokenizer);
                    String prenom = readString(tokenizer);
                    int age = readInt(tokenizer);
                    // Appel de la methode des gestionnaires qui traite la transaction specifique
                    gestionClient.ajouterClient(idclient, nom, prenom, age);

                }
                // *******************
                // supprimer un client
                // *******************
                else if (command.equals("supprimerClient"))
                {
                    // Lecture des parametres
                    int idclient = readInt(tokenizer);
                    //appel methode traitement pour la transaction
                    gestionClient.supprimerClient(idclient);
                }
                // **********************
                // ajouter une chambre
                // ***********************
                else if (command.equals("ajouterChambre"))
                {
                    // Lecture des parametres
                    int idChambre = readInt(tokenizer);
                    String nom = readString(tokenizer);
                    String type = readString(tokenizer);
                    float prix = Float.parseFloat(readString(tokenizer));
                    //appel methode traitement pour la transaction
                    gestionChambre.ajouterChambre(idChambre, nom, type, prix);
                }
                // ***********************
                // supprimer une chambre
                // ***********************
                else if (command.equals("supprimerChambre"))
                {
                    // Lecture des parametres
                    int idChambre = readInt(tokenizer);
                    //appel methode traitement pour la transaction
                    gestionChambre.supprimerChambre(idChambre);
                }
                // ***********************
                // ajouterCommodite : Cette commande ajoute un nouveau service offert par l’entreprise.
                // ***********************
                else if (command.equals("ajouterCommodite"))
                {
                    // Lecture des parametres
                    int idCommodite = readInt(tokenizer);
                    String description = readString(tokenizer);
                    float prix =  Float.parseFloat(readString(tokenizer));

                    //appel methode traitement pour la transaction
                    gestionCommodite.ajouterCommodite(idCommodite, description, prix);
                }
                // ***********************
                // inclureCommodite : Cette commande ajoute une commodité à une chambre.
                // ***********************
                else if (command.equals("inclureCommodite"))
                {
                    // Lecture des parametres
                    int idChambre = readInt(tokenizer);
                    int idCommodite = readInt(tokenizer);
                    //appel methode traitement pour la transaction
                    gestionCommodite.InclureCommodite(idChambre, idCommodite);
                }
                else
                {
                    System.out.println(" : Transaction non reconnue. Essayer \"aide\"");
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(" " + e.toString());
            // Ce rollback est ici seulement pour vous aider et éviter des problèmes lors de la correction
            // automatique. En théorie, il ne sert à rien et ne devrait pas apparaître ici dans un programme
            // fini et fonctionnel sans bogues.
            cx.rollback();
        }
    }

    /** Affiche le menu des transactions acceptées par le système */
    private static void afficherAide()
    {
        System.out.println();
        System.out.println("Chaque transaction comporte un nom et une liste d'arguments");
        System.out.println("separes par des espaces. La liste peut etre vide.");
        System.out.println(" Les dates sont en format yyyy-mm-dd.");
        System.out.println("");
        System.out.println("Les transactions sont:");
        System.out.println("  aide");
        System.out.println("  quitter");
        System.out.println("  ajouterClient <idClient> <nom> <prenom> <age>");
        System.out.println("  supprimerClient <idClient>");
        System.out.println("  ajouterChambre <idChambre> <nom de la chambre> <type de lit> <prix de base>");
        System.out.println("  supprimerChambre <idChambre>");
        System.out.println("  ajouterCommodite <idCommodite> <description> <surplus prix>");
        System.out.println("  inclureCommodite <idChambre> <idCommodite>");
        System.out.println("  enleverCommodite <idChambre> <idCommodite>");
        System.out.println("  afficherChambresLibres");
        System.out.println("  afficherClient <idClient>");
        System.out.println("  afficherChambre <idChambre>");
        System.out.println("  reserver <idClient> <idChambre> <dateDebut> <dateFin>");
    }
    
    // ****************************************************************
    // *   Les methodes suivantes n'ont pas besoin d'etre modifiees   *
    // ****************************************************************

    /**
     * Ouvre le fichier de transaction, ou lit à partir de System.in
     */
    public static BufferedReader ouvrirFichier(String[] args) throws FileNotFoundException
    {
        if (args.length < 5)
            // Lecture au clavier
            return new BufferedReader(new InputStreamReader(System.in));
        else
            // Lecture dans le fichier passe en parametre
            return new BufferedReader(new InputStreamReader(new FileInputStream(args[4])));
    }

    /**
     * Lecture d'une transaction
     */
    static String lireTransaction(BufferedReader reader) throws IOException
    {
        return reader.readLine();
    }

    /**
     * Verifie si la fin du traitement des transactions est atteinte.
     */
    static boolean finTransaction(String transaction)
    {
        // fin de fichier atteinte
        return (transaction == null || transaction.equals("quitter"));
    }

    /** Lecture d'une chaine de caracteres de la transaction entree a l'ecran */
    static String readString(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
            return tokenizer.nextToken();
        else
            throw new Exception("Autre parametre attendu");
    }

    /**
     * Lecture d'un int java de la transaction entree a l'ecran
     */
    static int readInt(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Integer.valueOf(token).intValue();
            }
            catch (NumberFormatException e)
            {
                throw new Exception("Nombre attendu a la place de \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

    static Date readDate(StringTokenizer tokenizer) throws Exception
    {
        if (tokenizer.hasMoreElements())
        {
            String token = tokenizer.nextToken();
            try
            {
                return Date.valueOf(token);
            }
            catch (IllegalArgumentException e)
            {
                throw new Exception("Date dans un format invalide - \"" + token + "\"");
            }
        }
        else
            throw new Exception("Autre parametre attendu");
    }

}
