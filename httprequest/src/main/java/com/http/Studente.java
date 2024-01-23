package com.http;
public class Studente {
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private int eta;
    private String classe;

    public Studente(){}

    public Studente(String n, String c, String cf, int e, String cl){
        nome = n;
        cognome = c;
        codiceFiscale = cf;
        eta = e;
        classe = cl;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public String getCodiceFiscale(){
        return codiceFiscale;
    }

    public int getEta(){
        return eta;
    }

    public String getClasse(){
        return classe;
    }

    public void setNome(String n){
        nome = n;
    }

    public void setCognome(String c){
        cognome = c;
    }

    public void setCodiceFiscale(String cf){
        codiceFiscale = cf;
    }

    public void setEta(int e){
        eta = e;
    }

    public void setClasse(String cl){
        classe = cl;
    }
}
