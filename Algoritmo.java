public class Algoritmo {
  static int seleccion;

  public static boolean comprobarEspacio(Proceso[] memoria, int indice, int tamano) {
    for (int i = 0; i < tamano; i++) {
      if (memoria[indice + i] != null)
        return false;
    }
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

  public static void primerAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
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
    } 
    else if (aux == 2) {
      System.out.println("Proceso " + p.id + " solicitado");
      SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
      primerAjuste(memoriaSecundaria, p,  memoria);
      restarTiempo(memoria, p);
    } 
    else {
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
                  if (memoria.length>=memoriaSecundaria.length) {
                    SeleccionProceso.pilaLIFO.push(p);
                  }
                  else
                    SeleccionProceso.pilaLIFOSec.push(p);
                }
                else if (seleccion == 2)
                  SeleccionProceso.colaFIFO.add(p);
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
      switch (seleccion) {
      case 1:
        SeleccionProceso.LIFO(memoria, memoriaSecundaria, p);
        break;
      case 2:
        SeleccionProceso.FIFO(memoria, memoriaSecundaria, p);
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