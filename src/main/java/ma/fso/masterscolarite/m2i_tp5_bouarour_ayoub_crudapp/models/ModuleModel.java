package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models;

import java.util.ArrayList;

public class ModuleModel {

    // DECLARING ATTRIBUTES
    private int moduleId;
    private String moduleNom;
    public ModuleModel(){

        // DEFAULT CONSTRUCTOR
        super();
    }

    public ModuleModel(int moduleId, String moduleNom){

        // CONSTRUCTING DATA
        this.moduleId = moduleId;
        this.moduleNom = moduleNom;
    }

    // GETTERS AND SETTERS

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleNom() {
        return moduleNom;
    }

    public void setModuleNom(String moduleNom) {
        this.moduleNom = moduleNom;
    }


}
