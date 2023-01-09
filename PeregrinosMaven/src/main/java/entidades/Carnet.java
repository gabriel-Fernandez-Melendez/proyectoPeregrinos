/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDate;

/**
 *
 * @author gabof
 */
public class Carnet {
    private long idPeregrino;
    private LocalDate fechaExp;
    private double distancia;
    private int nVips;
    private Parada paradainit;
    
    public Carnet(){
        
    }

    public Carnet(long idPeregrino, LocalDate fechaExp, double distancia, int nVipss,Parada paradainit) {
        this.idPeregrino = idPeregrino;
        this.fechaExp = fechaExp;
        this.distancia = distancia;
        this.nVips = nVips;
        this.paradainit=paradainit;
      
    }

    public long getIdPeregrino() {
        return idPeregrino;
    }

    public LocalDate getFechaExp() {
        return fechaExp;
    }

    public double getDistancia() {
        return distancia;
    }

    public int getnVips() {
        return nVips;
    }

    public void setIdPeregrino(long idPeregrino) {
        this.idPeregrino = idPeregrino;
    }

    public void setFechaExp(LocalDate fechaExp) {
        this.fechaExp = fechaExp;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public void setnVips(int nVips) {
        this.nVips = nVips;
    }

 

    public Parada getParada() {
        return paradainit;
    }

    public void setParada(Parada parada) {
        this.paradainit = parada;
    }
    
    

    @Override
    public String toString() {
        return "Carnet{" + "idPeregrino=" + idPeregrino + ", fechaExp=" + fechaExp + ", distancia=" + distancia + ", nVips=" + nVips + '}';
    }
    
    //metodo para la creacion del carnet del peregrino (deberia poder quitar la parada del argumento)
    public static Carnet NuevoCarnet(Peregrino p){
        Carnet ret=new Carnet();
        ret.setIdPeregrino(p.getId());
        ret.setParada(p.getParadas().get(5));
        LocalDate fecha=fechaDeHoy();
        ret.setFechaExp(fecha);
        return ret;
    }
    
    //este metodo devuelve la fecha de hoy cuando se reguistra el carnet
    public static LocalDate fechaDeHoy(){
        LocalDate fecha=LocalDate.now();
        return fecha;
    }
}
