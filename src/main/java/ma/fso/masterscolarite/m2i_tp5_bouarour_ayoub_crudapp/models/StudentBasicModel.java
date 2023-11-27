package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models;

import java.sql.Date;
import java.util.List;

public class StudentBasicModel {

    // DECLARING STUDENT MODEL ATTRS
    private int id ;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String cne;
    private String adresse;
    private List<ModuleStatModel> modules;

    public StudentBasicModel(){

        // ADDING DEFAULT CONSTRUCTOR TO STICK TO THE JAVABEANS REQUIREMENTS
        super();
    }

    public StudentBasicModel(int id, String nom, String prenom, Date dateNaissance, String cne, String adresse, List <ModuleStatModel> modules){

        // CONSTRUCTING THE STUDENT MODEL FROM DATA
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.cne = cne;
        this.dateNaissance = dateNaissance;
        this.modules = modules;

    }

    // GETTERS AND SETTERS
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public java.sql.Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public List<ModuleStatModel> getModules() {
        return modules;
    }

    public void setModules(List<ModuleStatModel> modules) {
        this.modules = modules;
    }

}
