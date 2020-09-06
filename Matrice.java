import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

class Matrice {
    private final boolean debugIsOn = false;

    private double[][] matrice = new double[3][5];
    private double[] coefficientiNoti = new double[3];
    private double[] funzioneObiettivo = new double[5];
    private int[] posizionePivot = new int[2];
    final NumberFormat nf = new DecimalFormat("##.###");

    public Matrice(double[][] matrice, double[] coefficientiNoti, double[] funzioneObiettivo) {
        this.matrice = matrice;
        this.coefficientiNoti = coefficientiNoti;
        this.funzioneObiettivo = funzioneObiettivo;
    }

    public Matrice() {
        Scanner keyboard = new Scanner(System.in);

        System.out.println("inizializzo funzione obiettivo");
        System.out.print("x₁:\t");
        funzioneObiettivo[0] = keyboard.nextInt();
        System.out.print("x₂:\t");
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

    public void cambioBase() {
    /*if (matrice[rigaPivot][colonnaPivot]==0){
      FIXME:caso possibile ma mai incontrato in un esercizio. se lo trovo lo includo
    }*/
        int rigaPivot=posizionePivot[0];
        int colonnaPivot=posizionePivot[1];
        double valoreMoltiplicativo;
        double valorePivot = matrice[rigaPivot][colonnaPivot];

        if (matrice[rigaPivot][colonnaPivot] < 0) {
            for (int i = 0; i < matrice[rigaPivot].length; i++) {
                matrice[rigaPivot][i] = -matrice[rigaPivot][i];
            }
        }

        //riduzione della riga del pivot in modo che quest'ultimo sia 1
        if (matrice[rigaPivot][colonnaPivot] != 1) {
            for (int i = 0; i < matrice[rigaPivot].length; i++) {
                matrice[rigaPivot][i] = matrice[rigaPivot][i] == 0 ? 0 : matrice[rigaPivot][i] / valorePivot;
                coefficientiNoti[rigaPivot] = coefficientiNoti[rigaPivot] == 0 ? 0 : coefficientiNoti[rigaPivot] / valorePivot;
            }
        }

        // riduzione delle altre 2 righe per ottenere un vettore linearmente indipendente (porto a zero i valori aventi
        // la stessa colonna del pivot)
        for (int i = 0; i < matrice.length; i++) {
            if (i == rigaPivot) //questo procedimento non va ovviamente eseguito per la riga del pivot
                continue;

            valoreMoltiplicativo = matrice[i][colonnaPivot] / 1; // diviso 1 per sapere se mettere il segno meno
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = matrice[i][j] - valoreMoltiplicativo * matrice[rigaPivot][j];
                coefficientiNoti[i] = coefficientiNoti[i] - valoreMoltiplicativo * coefficientiNoti[rigaPivot];
            }
        }

    }

    public void aggiornaFunzioneObbiettivo() {

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

        if (debugIsOn) {
            if (result > -1) System.out.println("result " + result);
            else System.out.println("end!");
        }

        return result;
    }

    public void trovaVariabileUscente() {
        int colonna = trovaVariabileEntrante();
        int riga = -1;

        if (colonna > -1) {
            double minimo = Double.MAX_VALUE;

            for (int i = 0; i < matrice.length; i++) {
                double elementoIJ =  coefficientiNoti[i] / matrice[i][colonna];
                if (elementoIJ > 0 && elementoIJ < minimo) {
                    minimo = elementoIJ;
                    riga = i;
                }
            }
        }

        posizionePivot[0] = riga;
        posizionePivot[1] = colonna;
    }

    public int pivot(int indice) {
        trovaVariabileUscente();
        return posizionePivot[indice];
    }

    public double pivot() {
        trovaVariabileUscente();
        return matrice[posizionePivot[0]][posizionePivot[1]];
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
