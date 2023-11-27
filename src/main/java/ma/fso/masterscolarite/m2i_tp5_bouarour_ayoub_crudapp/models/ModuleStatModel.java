package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models;

public class ModuleStatModel {

    // DECLARING ATTRS
    private int moduleAbsence;
    private String studentCne;
    private String moduleNom;
    private int moduleNote;
    public ModuleStatModel(){
        // ADDING DEFAULT CONSTRUCTOR TO STICK TO THE JAVABEANS REQUIREMENTS
        super();
    }
    public ModuleStatModel(int moduleAbsence, String studentCne, String moduleNom, int moduleNote){

        // CONSTRUCTING THE MODEL FROM GIVEN DATA
        this.moduleAbsence = moduleAbsence;
        this.moduleNom = moduleNom;
        this.studentCne = studentCne;
        this.moduleNote = moduleNote;
    }

    // GETTERS & SETTERS
    public int getModuleAbsence() {
        return moduleAbsence;
    }

    public void setModuleAbsence(int moduleAbsence) {
        this.moduleAbsence = moduleAbsence;
    }

    public String getStudentCne() {
        return studentCne;
    }

    public void setStudentCne(String studentCne) {
        this.studentCne = studentCne;
    }

    public String getModuleNom() {
        return moduleNom;
    }

    public void setModuleNom(String moduleNom) {
        this.moduleNom = moduleNom;
    }

    public int getModuleNote() { return moduleNote; }

    public void setModuleNote(int moduleNote) { this.moduleNote = moduleNote; }

}
