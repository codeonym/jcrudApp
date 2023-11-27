package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models;

import java.util.ArrayList;
import java.util.List;

public class StudentExtraModel {

    // DECLARING ATTRIBUTES
    private String cne;
    private List<ModuleStatModel> more = new ArrayList<>();

    public StudentExtraModel(){

        // DEFAULT CONSTRUCTOR
        super();
    }
    public StudentExtraModel(String cne, List<ModuleStatModel> more){

        // SETTING THE DATA
        this.cne = cne;
        this.more = more;
    }

    // GETTERS AND SETTERS
    public String getCne() {
        return cne;
    }

    public void setCne(String cne) {
        this.cne = cne;
    }

    public List<ModuleStatModel> getMore() {
        return more;
    }

    public void setMore(List<ModuleStatModel> more) {
        this.more = more;
    }
}
