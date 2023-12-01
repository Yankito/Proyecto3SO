import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Algoritmo {
  static int seleccion;
  static Stack<Proceso> pilaLIFO = new Stack<>();
  static Stack<Proceso> pilaLIFOSec = new Stack<>();
  static Queue<Proceso> colaFIFO = new LinkedList<>();
  static Queue<Proceso> colaLIFOSec = new LinkedList<>();


  public static boolean comprobarEspacio(Proceso[] memoria, int indice, int tamano) {
    for (int i = 0; i < tamano; i++) {
      if (memoria[indice + i] != null)
        return false;
    }
    return true;
  }

  public static boolean comprobarEspacioDisponibleEnMemorias(Proceso[] memoria, Proceso[] memoriaSecundaria, int tamano) {
    int contador=0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] == null)
        contador++;
    }
    for (int i = 0; i < memoriaSecundaria.length; i++) {
      if (memoriaSecundaria[i] == null)
        contador++;
    }
    if(contador<tamano)
      return false;
    else
      return true;

  }


  public static int buscarEspacioGrande(Proceso[] memoria, int tamano) {
    int indice = -1;
    int tamanoLibreActual = 0;
    int tamanoLibreMaximo = 0;

    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] == null) {
        tamanoLibreActual++;
      } else {
        // Si encontramos una partición ocupada, comprobamos si es la más grande hasta
        // ahora
        if (tamanoLibreActual > tamanoLibreMaximo) {
          tamanoLibreMaximo = tamanoLibreActual;
          indice = i - tamanoLibreActual; // Guardamos el índice de inicio de la partición libre más grande
        }
        tamanoLibreActual = 0;
      }
    }

    // Comprobamos también la última partición en caso de que sea la más grande
    if (tamanoLibreActual > tamanoLibreMaximo) {
      tamanoLibreMaximo = tamanoLibreActual;
      indice = memoria.length - tamanoLibreActual; // Guardamos el índice de inicio de la última partición libre más
                                                   // grande
    }

    // Devolvemos el índice de inicio de la partición libre más grande si es
    // suficientemente grande para el proceso
    return (tamanoLibreMaximo >= tamano) ? indice : -1;
  }

  public static int buscarEspacioPequeno(Proceso[] memoria, int tamano) {
    int indice = -1;
    int espacioMenor = 0;
    int espacio = 0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] == null) {
        espacio++;
      } else if (memoria[i] != null || i == memoria.length - 1) {
        if (espacio >= tamano) {
          if (indice == -1) {
            espacioMenor = espacio;
          } else {
            if (espacio < espacioMenor) {
              indice = i;
              espacio = 0;
            }
          }
        }
      }
    }
    return indice;
  }

  public static void primerAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria, Stack<Proceso> pilaLIFO,
      Stack<Proceso> pilaLIFOSec, Queue<Proceso> colaFIFO, Queue<Proceso> colaLIFOSec) {
        try {
          Thread.sleep(500);
      } catch (InterruptedException e) {
          e.printStackTrace();
      }

        //System.out.println("Pila\n"+pilaLIFO);
    int aux = 0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        aux = 1;
        break;
      }
    }
    for (int i = 0; i < memoriaSecundaria.length; i++) {
      if (memoriaSecundaria[i] != null && memoriaSecundaria[i].id == p.id) {
        aux = 2;
        break;
      }
    }

    if(aux !=1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano))
      return;

    System.out.println("Proceso " + p.id + " solicitado");
    if (aux == 1) {
      System.out.println("Proceso en memoria principal");
      restarTiempo(memoria, p);
    } 
    else if (aux == 2) {
      System.out.println("Proceso en memoria secundaria");
      //System.out.println("Proceso " + p.id + " solicitado");
      //System.out.println("Elimina de memoria secundaria");
      //SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
      //Main.imprimirArreglo(memoriaSecundaria);
      //primerAjuste(memoriaSecundaria, p,  memoria, pilaLIFOSec, pilaLIFO, colaLIFOSec, colaFIFO);
      Proceso nuevo = null;
      if(pilaLIFO.isEmpty())
        return;
      else
         nuevo = pilaLIFO.pop();

      switch (seleccion) {
      case 1:
        pilaLIFOSec.remove(p);
        pilaLIFOSec.push(p);
        SeleccionProceso.LIFO(memoriaSecundaria, memoria, nuevo, pilaLIFOSec, pilaLIFO);
        break;
      case 2:
        SeleccionProceso.FIFO(memoria, memoriaSecundaria, p, colaFIFO);
      default:
        break;
      }
      restarTiempo(memoria, p);
    } 
    else {
      System.out.println("Inserta en memoria");
      for (int i = 0; i < memoria.length; i++) {
        if (memoria[i] == null) { 
          if (p.tamano <= memoria.length - i) {
            // Allocate the process in the memory
            if (comprobarEspacio(memoria, i, p.tamano)) {

              for (int j = 0; j < p.tamano; j++) {
                memoria[i + j] = p;
              }
              System.out.println("Proceso " + p.id + " con tamanio:" + p.tamano + " insertado en memoria");
                if (seleccion == 1){
                  //if (memoria.length>=memoriaSecundaria.length) {
                    pilaLIFO.push(p);
                  //}
                  //else
                   // pilaLIFOSec.push(p);
                }
                else if (seleccion == 2)
                  colaFIFO.add(p);
              return;
            }
          }
          /*
           * else {
           * System.out.println("Espacio insuficiente en memoria para el proceso "+p.
           * tamano);
           * return;
           * }
           */
        }
      }
      System.out.println("No hay espacio suficiente en memoria para el proceso "+p);
      if(pilaLIFO.isEmpty())
        return;
      switch (seleccion) {
      case 1:
        SeleccionProceso.LIFO(memoria, memoriaSecundaria, p, pilaLIFO, pilaLIFOSec);
        break;
      case 2:
        SeleccionProceso.FIFO(memoria, memoriaSecundaria, p, colaFIFO);
      default:
        break;
      }
    }

  }

  public static void mejorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
    int aux = 0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        aux = 1;
        break;
      }
    }
    for (int i = 0; i < memoriaSecundaria.length; i++) {
      if (memoriaSecundaria[i] != null && memoriaSecundaria[i].id == p.id) {
        aux = 2;
        break;
      }
    }
    if (aux == 1) {
      System.out.println("Proceso " + p.id + " solicitado");
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      System.out.println("Proceso " + p.id + " solicitado");

      //restarTiempo(memoria, p);
    } else {
      int indice = buscarEspacioPequeno(memoria, p.tamano);
      if (seleccion == 1)
        pilaLIFO.push(p);
      else if (seleccion == 2)
        colaFIFO.add(p);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        System.out.println("Proceso " + p.id + " con tamaño:" + p.tamano + " insertado en memoria");
      } else {
        switch (seleccion) {
          case 1:
            SeleccionProceso.LIFO(memoria, memoriaSecundaria, p);
            break;
          case 2:
            SeleccionProceso.FIFO(memoria, memoriaSecundaria, p);
          default:
            break;
        }
        pilaLIFO.push(p);
        System.out.println("No hay espacio suficiente en memoria para el proceso. Espacio: " + p.tamano);
      }
    }
  }

  public static void peorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
    int aux = 0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        aux = 1;
        break;
      }
    }
    if (aux == 1) {
      System.out.println("Proceso " + p.id + " solicitado");
      restarTiempo(memoria, p);
    } else {
      int indice = buscarEspacioGrande(memoria, p.tamano);
      if (seleccion == 1)
        SeleccionProceso.pilaLIFO.push(p);
      else if (seleccion == 2)
        SeleccionProceso.colaFIFO.add(p);

      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        System.out.println("Proceso " + p.id + " con tamaño:" + p.tamano + " insertado en memoria");
      } else {
        switch (seleccion) {
          case 1:
            SeleccionProceso.LIFO(memoria, memoriaSecundaria, p);
            break;
          case 2:
            SeleccionProceso.FIFO(memoria, memoriaSecundaria, p);
          default:
            break;
        }
        System.out.println("No hay espacio suficiente en memoria para el proceso " + p.tamano);
      }
    }
  }

  static void restarTiempo(Proceso[] memoria, Proceso p) {
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        memoria[i].quantum--;
        if (memoria[i].quantum <= 0) {
          for (int j = i; j < p.tamano + i; j++) {
            memoria[j] = null;
          }
          p.quantum = 2;
        }
        break;
      }
    }
  }

}