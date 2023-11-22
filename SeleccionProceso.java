import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SeleccionProceso {
  static int algoritmo, seleccion;
  static Stack<Proceso> pilaLIFO = new Stack<>();
  static Stack<Proceso> pilaLIFOSec = new Stack<>();
  static Queue<Proceso> colaFIFO = new LinkedList<>();

  public static void FIFO(Proceso[] memoria, Proceso[] memoriaSecundaria, Proceso procesoNuevo) {
    Proceso p = null;
    if (memoria.length>=memoriaSecundaria.length) {
      p = colaFIFO.poll();
    }
    else
      SeleccionProceso.pilaLIFOSec.push(p);
    
    eliminarProceso(memoria, p);
    switch (algoritmo) {
      case 1:
        primerAjuste(memoriaSecundaria, p);
        break;
      case 2:
        mejorAjuste(memoriaSecundaria, p);
        break;
      case 3:
        peorAjuste(memoriaSecundaria, p);
        break;
      default:
        System.out.println("Algoritmo no valido");
        break;
    }
  }

  public static void LIFO(Proceso[] memoria, Proceso[] memoriaSecundaria, Proceso procesoNuevo) {
    Proceso p = null;
    if (memoria.length>=memoriaSecundaria.length) {
      p = pilaLIFO.pop();
    }
    else
      p = pilaLIFOSec.pop();

    eliminarProceso(memoria, p);
    switch (algoritmo) {
      case 1:
        primerAjuste(memoriaSecundaria, p);
        Algoritmo.primerAjuste(memoria, procesoNuevo, memoriaSecundaria);
        break;
      case 2:
        mejorAjuste(memoriaSecundaria, p);
        mejorAjuste(memoria, procesoNuevo);
        break;
      case 3:
        peorAjuste(memoriaSecundaria, p);
        peorAjuste(memoria, procesoNuevo);
        break;
      default:
        System.out.println("Algoritmo no valido");
        break;
    }
  }

  public static void eliminarProceso(Proceso[] memoria, Proceso p){
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        memoria[i] = null;
      }
    }
  }

  public static void primerAjuste(Proceso[] memoriaSecundaria, Proceso p) {

    for (int i = 0; i < memoriaSecundaria.length; i++) {
      if (memoriaSecundaria[i] == null) {
        if (p.tamano <= memoriaSecundaria.length - i) {
          // Allocate the process in the memory
          if (Algoritmo.comprobarEspacio(memoriaSecundaria, i, p.tamano)) {
            System.out.println("a");
            for (int j = 0; j < p.tamano; j++) {
              memoriaSecundaria[i + j] = p;
            }
            System.out.println("Proceso " + p.id + " con tamanio:" + p.tamano + " insertado en memoria sec");
            pilaLIFOSec.push(p);
            return;
          }
        }
      }

    }
    System.out.println("No hay espacio suficiente en memoria para el proceso "+p);

  }

  public static void mejorAjuste(Proceso[] memoriaSecundaria, Proceso p) {
    int indice = Algoritmo.buscarEspacioPequeno(memoriaSecundaria, p.tamano);
    if (indice != -1) {
      for (int i = 0; i < p.tamano; i++) {
        memoriaSecundaria[indice + i] = p;
      }
      System.out.println("Proceso " + p.id + " con tamaño:" + p.tamano + " insertado en memoria sec");
    } else {
      System.out.println("No hay espacio suficiente en memoria para el proceso. Espacio: " + p.tamano);
    }
  }

  public static void peorAjuste(Proceso[] memoriaSecundaria, Proceso p) {
    int indice = Algoritmo.buscarEspacioGrande(memoriaSecundaria, p.tamano);

    if (indice != -1) {
      for (int i = 0; i < p.tamano; i++) {
        memoriaSecundaria[indice + i] = p;
      }
      System.out.println("Proceso " + p.id + " con tamaño:" + p.tamano + " insertado en memoria sec");
    } else {
      System.out.println("No hay espacio suficiente en memoria para el proceso " + p.tamano);
    }
  }

}