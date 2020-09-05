import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

class Matrice {
  private double[][] matrice = new double[3][5];
  private double[] coefficientiNoti = new double[3];
  private double[] funzioneObiettivo = new double[5];
  NumberFormat nf = new DecimalFormat("##.###");

  public Matrice(double[][] matrice, double[] coefficientiNoti, double[] funzioneObiettivo) {
    this.matrice = matrice;
    this.coefficientiNoti = coefficientiNoti;
    this.funzioneObiettivo = funzioneObiettivo;
  }

  public Matrice(){
    Scanner keyboard = new Scanner(System.in);

    System.out.println("inizializzo funzione obiettivo");
    System.out.print("x1:\t");
    funzioneObiettivo[0] = keyboard.nextInt();
    System.out.print("x2:\t");
    funzioneObiettivo[1] = keyboard.nextInt();
    System.out.println();

    System.out.println("inizializzo la matrice");
    for (int i = 0; i < matrice.length; i++) {
      System.out.println("riga " + (i + 1));
      for (int j = 0; j < matrice[i].length; j++) {
        System.out.print("colonna: " + (j + 1) + "\t");
        matrice[i][j] = keyboard.nextInt();
      }
      System.out.print("coefficiente " + (i + 1) + "\t");
      coefficientiNoti[i] = keyboard.nextInt();
      System.out.println();

    }
  }

  public int trovaVariabileEntrante() {
    int result = -1;
    double max = -1;

    for (int i = 0; i < funzioneObiettivo.length; i++) {
      if (funzioneObiettivo[i] > 0 && funzioneObiettivo[i] > max) {
        max = funzioneObiettivo[i];
        result = i;
      }
    }

    if (result > -1) System.out.println("result " + result);
    else System.out.println("end!");

    return result;
  }

  public int[] trovaVariabileUscente() {
    System.out.println("uno");
    int[] elemento = {-1, trovaVariabileEntrante()};

    if (elemento[1] > -1) {
      System.out.println("colonna " + elemento[1]);

      double minimo = Double.MAX_VALUE;
      for (int i = 0; i < matrice.length && elemento[1] > -1; i++) {
        double valoreI =  matrice[i][elemento[1]] / coefficientiNoti[i];
        if (valoreI > 0 && valoreI < minimo) {
          System.out.println("i " + i + " valoreI " + valoreI);
          minimo = valoreI;
          elemento[1] = i;
        }
        System.out.println("minimo " + minimo);
      }
    }

    if (elemento[0] > -1 && elemento[1] > -1) {
      System.out.println("riga " + elemento[0] + " colonna " + elemento[1]);
      System.out.println("pivot " + matrice[elemento[0]][elemento[1]]);
    }

    return elemento;
  }

  public void stampaFunzioneObiettivo() {
    char[] c = {'₁', '₂', '₃', '₄', '₅'};

    System.out.println("funzione obiettivo");
    for (int i = 0; i < funzioneObiettivo.length; i++) {
      if (funzioneObiettivo[i] != 0) {
        System.out.print(nf.format(funzioneObiettivo[i]) + "x" + c[i] + " ");
      }
    }
    System.out.println("\n");
  }

  public void stampaMatrice() {
    stampaFunzioneObiettivo();

    System.out.println("stampo la matrice");
    for (int i = 0; i < matrice.length; i++) {
      for (int j = 0; j < matrice[i].length; j++) {
        System.out.print(nf.format(matrice[i][j]) + "\t");
      }
      System.out.print("| " + nf.format(coefficientiNoti[i]));
      System.out.println();
    }
  }
}
