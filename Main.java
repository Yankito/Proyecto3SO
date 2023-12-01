import java.util.Scanner;
import java.util.Stack;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

  public static void main(String[] args) {

    ArrayList<Proceso> procesos = new ArrayList<>();
    Random random = new Random();
    int algoritmo, seleccion, tamanoProceso, quantum, id;

    Scanner sc = new Scanner(System.in);
    // System.out.println("Ingrese tamano de memoria principal");
    int tamanoMemoriaPrincipal = 5;
    // System.out.println("Ingrese tamano de memoria secundaria");
    int tamanoMemoriaSecundaria = 5;

    Proceso memoriaPrincipal[] = new Proceso[tamanoMemoriaPrincipal];
    Proceso memoriaSecundaria[] = new Proceso[tamanoMemoriaSecundaria];

    System.out.println(
        "Escoja Algoritmo:\n1)Primer Ajuste//Solo este funciona(de momento UwU)\n2)Mejor Ajuste\n3)Peor Ajuste\nOpcion:");
    algoritmo = sc.nextInt();
    System.out.println("Escoja Algoritmo de seleccion:\n1)LIFO\n2)FIFO");
    seleccion = sc.nextInt();
    SeleccionProceso.algoritmo = algoritmo;
    SeleccionProceso.seleccion = seleccion;
    Algoritmo.seleccion = seleccion;
    // System.out.println("Ingrese tamano del proceso a insertar");

    for (int j = 0; j < 5; j++) {
      tamanoProceso = random.nextInt(3) + 1;
      // quantum = random.nextInt(2) + 1;
      quantum = 5;
      procesos.add(new Proceso(tamanoProceso, quantum, j));
    }
    int i = 0;
    while (i < 20) {

      switch (algoritmo) {
        case 1:
          id = random.nextInt(5);
          Algoritmo.primerAjuste(memoriaPrincipal, procesos.get(id), memoriaSecundaria, Algoritmo.pilaLIFO,
          Algoritmo.pilaLIFOSec, Algoritmo.colaFIFO, Algoritmo.colaLIFOSec);
          System.out.println("Primer Ajuste con memoria de " + tamanoMemoriaPrincipal);
          break;
        case 2:
          id = random.nextInt(5);
          System.out.println("Mejor Ajuste con memoria de " + tamanoMemoriaPrincipal);
          Algoritmo.mejorAjuste(memoriaPrincipal, procesos.get(id), memoriaSecundaria);
          break;
        case 3:
          id = random.nextInt(5);
          Algoritmo.peorAjuste(memoriaPrincipal, procesos.get(id), memoriaSecundaria);
          System.out.println("Peor Ajuste con memoria de " + tamanoMemoriaPrincipal);
          break;
        default:
          System.out.println("Algoritmo no valido");
          break;
      }
      System.out.println("Memoria pricipal:");
      imprimirArreglo(memoriaPrincipal);
      System.out.println("Memoria Secundaria:");
      imprimirArreglo(memoriaSecundaria);
      i++;
    }
  }

  /*
   * int j=0;
   * while (j<5) {
   * restarTiempo(memoriaPrincipal);
   * imprimirArreglo(memoriaPrincipal);
   * System.out.println();
   * j++;
   * }
   */

  static void imprimirArreglo(Proceso[] memoria) {
    for (int i = 0; i < memoria.length; i++) {
      System.out.println(memoria[i] + " ");
    }
    System.out.println();
  }

}