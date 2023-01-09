/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import conexionBD.ConexPeregrino;
import dao.CarnetDAO;
import dao.PeregrinoDAO;
import static entidades.Carnet.NuevoCarnet;
import static entidades.Parada.DevolverParadaPeregrino;
import static entidades.Peregrino.mostrarPeregrino;
import static entidades.Peregrino.registrarPeregrino;
import static entidades.Peregrino.settearCarnet;
import static entidades.Peregrino.xmlPeregrino;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;
import static lectores.Lectores.ExportarParadaADMIN;
import static lectores.Lectores.Login;
import static lectores.Lectores.LoginAdmin;
import static lectores.Lectores.Loginparada;
import static lectores.Lectores.loginBDAdmin;
import static lectores.Lectores.loginBDparada;
import static lectores.Lectores.loginBDper;
import static lectores.Lectores.seleccionadordeparadaGeneral;
import static lectores.Lectores.validarNuevoUsuario;
import validaciones.Validadores;

/**
 *
 * @author gabof
 */
public class Menus {

    Connection conex = ConexPeregrino.getCon();

    ArrayList<Peregrino> inscrito = new ArrayList<Peregrino>();

    //esta variable guarda el valor del peregrino para exportarla al xml
    static Peregrino Guardado = new Peregrino();

    public Menus() {

    }

    public ArrayList<Peregrino> getInscrito() {
        return inscrito;
    }

    public void setInscrito(ArrayList<Peregrino> inscrito) {
        this.inscrito = inscrito;
    }

    public void menuInicial() {
        boolean validador = true;
        do {
            System.out.println("bienvenido al menu de invitado,que desea hacer : ");
            System.out.println("1- registrarse");
            System.out.println("2- logearte");
            System.out.println("0- salir del programa cerrando la sesion actual.");

            int elecc;
            Scanner scan = new Scanner(System.in);
            elecc = scan.nextInt();

            switch (elecc) {
                case 1:
                    validador = false;
                    do {
                        //el usuario decide si se va a registrar (nuevo peregrino vale para registrar cualquier tipo de usuario)
                        Peregrino p = new Peregrino();
                        Parada parada = new Parada();
                        Carnet c = new Carnet();
                        Perfil perfil;
                        do {
                            p = registrarPeregrino();
                            //y con estas dos instruciones le meto su tipo de perfil para la distincion en la base de datos
                            perfil = TipoDeRegistro();
                            p.setPerfil(perfil);
                            //si es valido se ingresa en las tablas pertinentes                        
                            validador = validarNuevoUsuario(p);
                        } while (!validador);
                        if (validador) {
                            //se inserta en peregrino y credenciales si el usuario es valido 
                            PeregrinoDAO P = PeregrinoDAO.singlePeregrino(conex);
                            long peregrinoid = P.insertarSinID(p);
                            System.out.println("el id de usted es: " + peregrinoid);
                            Peregrino temp = new Peregrino();
                            temp = P.buscarPorID(peregrinoid);
                            Long idtemp = temp.getId();
                            // una vez setteado el id es cuando llamamos al metodo para insertar el carnet
                            p.setId(idtemp);
                            c = NuevoCarnet(p);
                            settearCarnet(p, c);
                            CarnetDAO C = CarnetDAO.singleCarnet(conex);
                            Long idcarnet = C.insertarSinID(c);
                            System.out.println("el id de su carnet es: " + idcarnet);
                            mostrarPeregrino(p, c);
//                          //ha sido eliminado el codigo que ponia aqui el xml y en su lugar una variable guarda su valor para ejecutarlo mas tarde!
                            inscrito.add(p);

                            break;
                        } else {
                            System.out.println("  ");
                            System.out.println("esas credenciales ya son usadas , debe escojer otras, intentelo de nuevo ");
                            System.out.println("  ");
                            validador = false;
                        }
                    } while (!validador);
                case 2:
                    validador = true;
                    //el usuario inicia sesion como peregrino y en el futuro como administrador de parada
                    menuprincipal();
                    break;
                case 0:
                    System.out.println("seguro que quiere salir del programa?");
                    validador = Validadores.leerBoolean();
                    if (validador) {
                        System.exit(0);
                        validador = true;
                        break;
                    }
                //añadi el default para avisar de su error al usuario
                default:
                    System.out.println("Valor incorrecto para el menú , porfavor introduzca una de las opciones");
                    validador = false;
                    break;

            }
        } while (!validador);
        System.out.println("ha sido exitosamente registrado!");
        menuprincipal();
    }

    public void menuprincipal() {
        boolean validador = true;
        do {
            System.out.println("bienvenido al menu principal: ");
            System.out.println("1- inicio de sesion del peregrino");
            System.out.println("2- iniciar sesion como administrador de parada");
            System.out.println("3- iniciar sesion como administrador de paradas general ");
            System.out.println("0- ir al menu principal.");

            int elecc;
            Scanner scan = new Scanner(System.in);
            elecc = scan.nextInt();

            switch (elecc) {
                case 1:
                    validador = true;
                    //el usuario decide logearse como peregrino
                    boolean valido = false;
                    Peregrino per = new Peregrino();
                    do {
                        per = Login();
                        valido = loginBDper(per);
                    } while (!valido);
                    Menus me = new Menus();
                    me.menuPeregrino(per);
                    break;
                case 2:
                    validador = true;
                    //el usuario inicia sesion como adminostrador de paradas(no disponible aun)
                    valido = false;
                    do {
                        //cabiamos el boolean por un peregrino
                        per = Loginparada();
                        per = loginBDparada(per);
                        System.out.println("como desea operar el dia de hoy Administrador de parada: ");
                        if(per != null){
                            valido=true;
                        }
                    } while (!valido);
                    menuAdministrador(per);
                    break;
                case 3:
                    validador = true;
                    valido = false;
                    do {
                        per = LoginAdmin();
                        valido = loginBDAdmin(per);
                    } while (!valido);
                    System.out.println("como desea operar el dia de hoy Administrador general: ");
                    //se  pasa desde estos login  el objeto  como  argumento para que llega la info del usuario a las funcionalidades
                    adminGeneralmenu(per);
                    break;
                case 0:
                    Menus m = new Menus();
                    m.menuInicial();
                    validador = true;
                    break;

                //el defauld logra captar todo dato que no este contenplado en el menu 
                default:
                    System.out.println("Valor incorrecto para el menú , porfavor introduzca una de las opciones");
                    validador = false;
                    break;

            }
        } while (!validador);
    }

    public void menuPeregrino(Peregrino per) {
        boolean validador = true;
        do {
            System.out.println("bienvenido al menu de peregrino,escoja una opcion:  ");
            System.out.println("1- exportar fichero xml");
            System.out.println("2- ir atras!");
            int elecc;
            Scanner scan = new Scanner(System.in);
            elecc = scan.nextInt();

            switch (elecc) {
                case 1:
                    validador = true;
                    //aqui tiene que ir la llamada a la exportacion del fichero 
                    System.out.println("desea exportar el xml del carnet ? ");
                    validador = Validadores.leerBoolean();
                    if (validador) {
                        CarnetDAO C = CarnetDAO.singleCarnet(conex);
                        Carnet carnetper = C.buscarPorID(per.getId());
                        per.setCarnet(carnetper);

                        String mensaje = xmlPeregrino(per);
                        System.out.println(mensaje);
                    }
                    break;
                case 2:
                    validador = true;
                    //aqui tiene que ir el logout que es una llamada al metodo de manu principal 
                    System.out.println("desea salir del menu de administrador ? ");
                    validador = Validadores.leerBoolean();
                    if (validador) {
                        Menus m = new Menus();
                        m.menuInicial();
                    }
                    break;
                //añadi el default para avisar de su error al usuario
                default:
                    System.out.println("Valor incorrecto para el menú , porfavor introduzca una de las opciones");
                    validador = false;
                    break;
            }
        } while (validador);
    }

    public static void menuAdministrador(Peregrino per) {
        boolean validador = true;
        do {
            System.out.println("bienvenido al menu de peregrino,escoja una opcion:  ");
            System.out.println("1- exportar datos de parada ");
            System.out.println("2-desea registrar el paso/alojamiento de un peregrino");
            System.out.println("3- logout");
            int elecc;
            Scanner scan = new Scanner(System.in);
            elecc = scan.nextInt();

            switch (elecc) {
                case 1:
                    Carnet carnet= new Carnet();
                    validador = true;
                    //es te if para hacer la distincion entre un administrador general  y un admin de parada
                    if (per.getPerfil().equals(Perfil.AdministradordeParadas)) {
                        ExportarParadaADMIN(per);
                    } else {
                         Parada parada;
                         parada=seleccionadordeparadaGeneral();  
                         carnet.setParada(parada);
                         per.setCarnet(carnet);
                          ExportarParadaADMIN(per);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    validador = true;
                    //aqui tiene que ir el logout que es una llamada al metodo de manu principal 
                    System.out.println("desea salir del menu de administrador ? ");
                    validador = Validadores.leerBoolean();
                    Menus m = new Menus();
                    m.menuInicial();
                    break;
                //añadi el default para avisar de su error al usuario
                default:
                    System.out.println("Valor incorrecto para el menú , porfavor introduzca una de las opciones");
                    validador = false;
                    break;
            }
        } while (validador);
    }

    //el menus esta el tipo de registro 
    public static Perfil TipoDeRegistro() {
        //este menos 1 hace que la eleccion del usuario salga correcta! importante recordarlo 
        int elecc = -1;
        Perfil cadena;
        Perfil perfil;
        boolean validador = false;
        do {
            System.out.println("selecciona el tipo de cuenta que vas a registrar: ");
            int i = 1;
            for (Perfil p : Perfil.values()) {
                System.out.println(i + "- " + p.getTipodeperfil());
                i++;
            }
            Scanner scan = new Scanner(System.in);
            elecc = scan.nextInt();
            if (elecc >= 1 && elecc <= Perfil.values().length) {
                System.out.println("escojio la opcion: " + elecc + "es eso correcto ? ");
                validador = Validadores.leerBoolean();
            } else {
                System.out.println("el valor introducido no el valido! intente de nuevo ");
                validador = false;
            }

        } while (!validador);
        perfil = Perfil.values()[elecc - 1];
        System.out.println("va a crear un perfil de tipo: " + perfil);
        return perfil;
    }

    //importante esto se pa usado en las funcionalidades de la tarea 3
    public void adminGeneralmenu(Peregrino per) {
        System.out.println("1- quiere operar como peregrino");
        System.out.println("2- quiere operar como administrador de paradas");
        Scanner scan = new Scanner(System.in);
        int elecc = scan.nextInt();
        switch (elecc) {
            case 1:
                System.out.println("usted va a operar como peregrino: ");
                menuPeregrino(per);
                break;
            case 2:
                System.out.println("usted va a operar como administrador de parada!");
                menuAdministrador(per);
                break;

        }
    }
    
    
}
