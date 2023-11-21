package ma.fso.masterscolarite.m2i_tp5_bouarour_ayoub_crudapp.models;

import java.util.ArrayList;
import java.util.List;

public class StudentsMoreModel {

    // DECLARING ATTRIBUTES
    private String cne;
    private List<ModulesModel> more = new ArrayList<>();

    public StudentsMoreModel(){

        // DEFAULT CONSTRUCTOR
        super();
    }
    public StudentsMoreModel(String cne, List<ModulesModel> more){

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

    public List<ModulesModel> getMore() {
        return more;
    }

    public void setMore(List<ModulesModel> more) {
        this.more = more;
    }
}
