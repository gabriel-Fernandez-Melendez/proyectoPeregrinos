/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entidades;

/**
 *
 * @author gabof
 */
//clase de los perfiles que se pueden tomar en el menu 
public enum Perfil {
    
    
   Peregrino(1,"Peregrino"),AdministradordeParadas(2,"AdministradorDeParadas"),Invitado(3,"invitado"),AdministradorGeneral(4,"AdministradorGeneral");
    
    private int id;
    private String tipodeperfil;

    private Perfil(int id, String tipodeperfil) {
        this.id = id;
        this.tipodeperfil = tipodeperfil;
    }

    public static Perfil getPeregrino() {
        return Peregrino;
    }

    public static Perfil getAdministradorDeParadas() {
        return AdministradordeParadas;
    }

    public int getId() {
        return id;
    }

    public String getTipodeperfil() {
        return tipodeperfil;
    }

    public static Perfil getInvitado() {
        return Invitado;
    }

    public static Perfil getAdministradordeParadas() {
        return AdministradordeParadas;
    }

    public static Perfil getAdministradorGeneral() {
        return AdministradorGeneral;
    }

    

    
    
    //solo devuelve el string del tipo de perfil que es lo que se ingresa en la BD
    @Override
    public String toString() {
        return ""+tipodeperfil+"";
    }
    
    
}
