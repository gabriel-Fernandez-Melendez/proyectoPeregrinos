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
public class Estancia {


    public long id;
    public LocalDate fecha;
    private boolean vip =false;
    private Parada parada;
    //tengo que meterlo en constructor(para la tabla va el id)
    private Peregrino peregrino;
    
    public Estancia(){
        
    }

    //contructor con solo el id para la BD
    public Estancia(long id) {
        this.id = id;
    }

    public Estancia(long id, LocalDate fecha,boolean vip,Parada parada,Peregrino peregrino) {
        this.id = id;
        this.fecha = fecha;
        this.vip=vip;
        this.parada=parada;
        this.peregrino=peregrino;
    }

    
    public void setParada(Parada parada) {
        this.parada = parada;
    }

    public Parada getParada() {
        return parada;
    }
    
    public long getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean isVip() {
        return vip;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public Peregrino getPeregrino() {
        return peregrino;
    }

    public void setPeregrino(Peregrino peregrino) {
        this.peregrino = peregrino;
    }

    @Override
    public String toString() {
        return "Estancia{" + "id=" + id + ", fecha=" + fecha + ", vip=" + vip + '}';
    }
    
    
    
}
