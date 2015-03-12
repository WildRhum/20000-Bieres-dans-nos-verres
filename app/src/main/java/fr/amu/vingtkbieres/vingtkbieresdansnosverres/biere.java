package fr.amu.vingtkbieres.vingtkbieresdansnosverres;

/**
 * Created by Hikyon on 11/03/2015.
 */
public class biere {

    private String m_id;
    private String m_nom;
    private String m_paysOrigine;
    private String m_style;
    private String m_degreAlcool;
    private String m_noteGen;
    private String m_noteStyle;
    private String m_localisation;
    private String m_noteEtoile = null;
    private String m_commentairePerso = null;
    private String m_notePerso = null;


    public biere(String m_id, String m_nom, String m_paysOrigine,
                 String m_style, String m_degreAlcool, String m_noteGen,
                 String m_noteStyle, String m_localisation,
                 String m_noteEtoile, String m_commentairePerso,
                 String m_notePerso) {

        this.m_id = m_id;
        this.m_nom = m_nom;
        this.m_paysOrigine = m_paysOrigine;
        this.m_style = m_style;
        this.m_degreAlcool = m_degreAlcool;
        this.m_noteGen = m_noteGen;
        this.m_noteStyle = m_noteStyle;
        this.m_localisation = m_localisation;
        this.m_noteEtoile = m_noteEtoile;
        this.m_commentairePerso = m_commentairePerso;
        this.m_notePerso = m_notePerso;
    }

    public String getM_nom() {
        return m_nom;
    }

    public void setM_nom(String m_nom) {
        this.m_nom = m_nom;
    }

    public String getM_id() {
        return m_id;
    }

    public void setM_id(String m_id) {
        this.m_id = m_id;
    }

    public String getM_paysOrigine() {
        return m_paysOrigine;
    }

    public void setM_paysOrigine(String m_paysOrigine) {
        this.m_paysOrigine = m_paysOrigine;
    }

    public String getM_style() {
        return m_style;
    }

    public void setM_style(String m_style) {
        this.m_style = m_style;
    }

    public String getM_noteEtoile() {
        return m_noteEtoile;
    }

    public void setM_noteEtoile(String m_noteEtoile) {
        this.m_noteEtoile = m_noteEtoile;
    }

    public String getM_notePerso() {
        return m_notePerso;
    }

    public void setM_notePerso(String m_notePerso) {
        this.m_notePerso = m_notePerso;
    }

    public String getM_commentairePerso() {
        return m_commentairePerso;
    }

    public void setM_commentairePerso(String m_commentairePerso) {
        this.m_commentairePerso = m_commentairePerso;
    }

    public String getM_localisation() {
        return m_localisation;
    }

    public void setM_localisation(String m_localisation) {
        this.m_localisation = m_localisation;
    }

    public String getM_noteStyle() {
        return m_noteStyle;
    }

    public void setM_noteStyle(String m_noteStyle) {
        this.m_noteStyle = m_noteStyle;
    }

    public String getM_noteGen() {
        return m_noteGen;
    }

    public void setM_noteGen(String m_noteGen) {
        this.m_noteGen = m_noteGen;
    }

    public String getM_degreAlcool() {
        return m_degreAlcool;
    }

    public void setM_degreAlcool(String m_degreAlcool) {
        this.m_degreAlcool = m_degreAlcool;
    }
}
