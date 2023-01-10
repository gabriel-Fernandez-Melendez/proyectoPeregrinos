/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import conexionBD.ConexPeregrino;
import dao.ParadaDAO;
import static entidades.Parada.LectorParadas;
import static entidades.Parada.LectoresNacionalidades;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import static lectores.Lectores.LectoresParadas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import validaciones.Validadores;

/**
 *
 * @author gabof
 */
public class Peregrino {

    private long id;
    private String nombre;
    private String contraseña;
    private String nacionalidad;
    //y un objeto carnet ya que todo peregrino cuenta con un carnet
    private Carnet carnet;
    //coleccion de la conexion con estancia
    private ArrayList<Estancia> estancias = new ArrayList<Estancia>();
    //coleccion con la conexion de parada
    private ArrayList<Parada> paradas = new ArrayList<Parada>();
    //esto es para poder poner el perfil en credenciales 
    private Perfil perfil;
    
    public Peregrino() {

    }

    public Peregrino(long id, String nombre, String contraseña, String nacionalidad, Carnet carnet) {
        this.id = id;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.carnet = carnet;
    }

    public Peregrino(long id, String nombre, String contraseña, String nacionalidad, ArrayList<Estancia> estancias, ArrayList<Parada> paradas) {
        this.id = id;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.estancias = estancias;
        this.paradas = paradas;
    }


    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    
    
    
    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public ArrayList<Estancia> getEstancias() {
        return estancias;
    }

    public void setEstancias(ArrayList<Estancia> estancias) {
        this.estancias = estancias;
    }

    public ArrayList<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(ArrayList<Parada> paradas) {
        this.paradas = paradas;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Carnet getCarnet() {
        return carnet;
    }

    public void setCarnet(Carnet carnet) {
        this.carnet = carnet;
    }

    @Override
    public String toString() {
        return "Peregrino{" + "id=" + id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad +"pass: "+contraseña+ '}';
    }

   

    public static Peregrino registrarPeregrino() {
        Peregrino ret = new Peregrino();
        //pedir nombre contraseña nacionalidad y parada inicial  
        //se settea aqui  un  valor de if ya que este no este vacio pero que tampoco se le pase como argumento a la base de datoa
        long id = 1;
        String nombre;
        String contraseña;
        String nacionalidad;
        Parada parada;
        Scanner scan = new Scanner(System.in);
        boolean validador = false;
        System.out.println("bienvenido al proceso de registro como peregrino o administrador!");
        System.out.println("porfavor sigue las instrucciones de nuestro programa: ");
        ret.setId(id);
        do {
            System.out.println("introduce tu nombre: ");
            nombre = scan.nextLine();
            validador = Validadores.validarNombre(nombre);
            if (validador) {
                ret.setNombre(nombre);
            }
        } while (!validador);
        validador = false;
        do {
            System.out.println("introduce tu contraseña: ");
            contraseña = scan.nextLine();
            validador = Validadores.validarNombre(contraseña);
            if (validador) {
                ret.setContraseña(contraseña);
            }
        } while (!validador);
        validador = false;
        do {
            ArrayList<String> nacionalidades = new ArrayList<String>();
            //aqui guardo en la coleccion el valor de todas las nacionalidades
            System.out.println("selecciona tu nacionalidad: ");
            nacionalidades = LectoresNacionalidades();
            int elecc = scan.nextInt();
            System.out.println("tu nacionalidad es: " + nacionalidades.get(elecc - 1) + " es esto correcto? ");
            validador = Validadores.leerBoolean();
            if (validador) {
                nacionalidad = nacionalidades.get(elecc - 1);
                ret.setNacionalidad(nacionalidad);
            }
        } while (!validador);
        do {
            //implementado que venga de la base de datos!
            ArrayList<Parada> paradas = new ArrayList<Parada>();
            Connection c=ConexPeregrino.getCon();
            ParadaDAO p=ParadaDAO.singleParada(c);
            paradas = (ArrayList<Parada>) p.buscarTodos();
            System.out.println("introduce tu parada actual en la que inicias el viaje: ");
            int i = 1;
            //ahora el bucle enseña de forma correcta las paradas de la base de datos
            for (Parada pa : paradas) {
                System.out.println(i + pa.toString());
                i++;
            }
            int elecc = scan.nextInt();
            System.out.println("tu parada es: " + paradas.get(elecc - 1) + "es esto correcto? ");
            validador = Validadores.leerBoolean();
            if (validador) {
                parada = paradas.get(elecc - 1);
                paradas.add(parada);
                ret.setParadas(paradas);
            }
        } while (!validador);
        return ret;
    }

    //en este metodo puedo meter la introduccion a la base de datos 
    public static void settearCarnet(Peregrino p, Carnet c) {
        p.setCarnet(c);
    }

    //este metod le muestra la informacion al  ususario 
    public static void mostrarPeregrino(Peregrino p, Carnet c) {
        System.out.println(" ");
        System.out.println("usted se ha registrado como peregrino con los siguientes datos: ");
        System.out.println("id del peregrino/carnet: " + p.getId());
        System.out.println("nombre del peregrino: " + p.getNombre());
        System.out.println("nacionalidad: " + p.getNacionalidad());
        System.out.println("fecha de expedicion del carnet: " + c.getFechaExp());
        System.out.println("nombre de la parada: " + c.getParada().getNombre() + "Region: " + c.getParada().getRegion());
        System.out.println(" ");

    }

    
    //puede que tenga que mirar algo  por auqi ded com o va la informacion 
    public static String xmlPeregrino(Peregrino p) {

        try {
            //creamos los objetos necesarios para la creacion del fichero xml 
            DocumentBuilderFactory fabric = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = fabric.newDocumentBuilder();
            DOMImplementation inplant = constructor.getDOMImplementation();

            //declaramos el objeto dumento cuyo atributo es el nombre del elemento raiz
            Document documento = inplant.createDocument(null, "carnet", null);
            //indicamos la version del documento xml
            documento.setXmlVersion("1.0");

            //comenzamos a crear otras etiquetas a insertar en la raiz carnet
            Element id = documento.createElement("id");
            Element fechaexp = documento.createElement("fechaexp");
            Element expedicionen = documento.createElement("expedicionen");
            Element peregrino = documento.createElement("peregrino");
            Element nombre = documento.createElement("nombre");
            Element nacionalidad = documento.createElement("nacionalidad");
            Element hoy = documento.createElement("hoy");
            Element distanciatotal = documento.createElement("distanciatotal");
            Element estancias = documento.createElement("estancias");
            Element estancia = documento.createElement("estancia");
            Element idestancia = documento.createElement("idestancia");
            Element fechaest = documento.createElement("fechaest");
            Element parada = documento.createElement("parada");
            Element vip = documento.createElement("vip");

            //y le añadimos el texto haciendo las dististas transformaciones necesarias a cadenas de texto
            //elemento id
            String s = Long.toString(p.getId());
            Text idtxt = documento.createTextNode(s);

            id.appendChild(idtxt);
//            documento.appendChild(id);

            //elemento fecha
            LocalDate fechaCadena = p.getCarnet().getFechaExp();
            String fechatxt = fechaCadena.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
            Text Datetxt = documento.createTextNode(fechatxt);
            fechaexp.appendChild(Datetxt);

            //elemento expedicion en 
            String paradat = p.getParadas().toString();
            Text paradatxt = documento.createTextNode(paradat);
            expedicionen.appendChild(paradatxt);

            //elemento peregrino
            //elementos nombre y nacionalidad de peregrino(hay que hacer dos hijos)
            String nomP = p.getNombre();
            Text nomtxt = documento.createTextNode(nomP);
            nombre.appendChild(nomtxt);
            peregrino.appendChild(nombre);

            String nacion = p.getNacionalidad();
            Text naciontxt = documento.createTextNode(nacion);
            nacionalidad.appendChild(naciontxt);
            peregrino.appendChild(nacionalidad);

            //fecha del dia de hoy
            LocalDate diadehoy = LocalDate.now();
            String diadehoytxt = diadehoy.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
            Text diahoy = documento.createTextNode(diadehoytxt);
            hoy.appendChild(diahoy);

            //distancia(que aun  no esta implementado)
            String distanciat = "por implementar";
            Text distanciatxt = documento.createTextNode(distanciat);
            distanciatotal.appendChild(distanciatxt);

            //estancias 
            estancias.appendChild(estancia);

            //estancia
            String est = "xxxxxx";
            Text esttxt = documento.createTextNode(est);
            idestancia.appendChild(esttxt);
            estancia.appendChild(idestancia);

            //correccion en como sacaba la fecha del xml 
            LocalDate fech=p.getCarnet().getFechaExp();
            String fechtxt=fech.toString();
            fechaest.appendChild(esttxt);
            Text fechtec = documento.createTextNode(fechtxt);
            estancia.appendChild(fechtec);

            parada.appendChild(esttxt);
            estancia.appendChild(parada);

            //mejorado el vip 
            String vipp = "false";
            Text viptxt = documento.createTextNode(vipp);
            vip.appendChild(viptxt);
            estancia.appendChild(vip);

            documento.getDocumentElement().appendChild(peregrino);
            documento.getDocumentElement().appendChild(estancias);

            Source raiz = new DOMSource(documento);
            Result resultado = new StreamResult(new File("ficherosPeregrino\\" + p.getNombre() + "_peregrino.xml"));

            Transformer transformador = TransformerFactory.newInstance().newTransformer();
            transformador.transform(raiz, resultado);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Peregrino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Peregrino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(Peregrino.class.getName()).log(Level.SEVERE, null, ex);
        }

        String mngs = "se ha importado el peregrino con su nombre en la carpeta del proyecto! ";
        return mngs;

    }

    public static Peregrino almacenarPeregrino(Peregrino p) {
        Peregrino guardar = p;

        return guardar;
    }
}
