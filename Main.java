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
    System.out.println("Ingrese tamano de memoria principal");
    int tamanoMemoriaPrincipal = sc.nextInt();
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
    ////// CREACION DE LOS PROCESOS
    for (int j = 0; j < 10; j++) {
      tamanoProceso = random.nextInt(3) + 1;
      quantum = random.nextInt(5) + 1;
      procesos.add(new Proceso(tamanoProceso, quantum, j));
    }
    int i = 0;
    while (i < 40) {

      switch (algoritmo) {
        case 1:
          id = random.nextInt(11);
          Algoritmo.inicioPrimerAjuste(memoriaPrincipal, procesos.get(id), memoriaSecundaria);
          break;
        case 2:
          id = random.nextInt(11);
          Algoritmo.inicioMejorAjuste(memoriaPrincipal, procesos.get(id), memoriaSecundaria);
          break;
        case 3:
          id = random.nextInt(11);
          Algoritmo.inicioPeorAjuste(memoriaPrincipal, procesos.get(id), memoriaSecundaria);
          break;
        default:
          System.out.println("Algoritmo no valido");
          break;
      }
      System.out.println("Memoria pricipal:");
      imprimirArreglo(memoriaPrincipal);
      System.out.println("Memoria Secundaria:");
      imprimirArreglo(memoriaSecundaria);
      System.out.println();
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