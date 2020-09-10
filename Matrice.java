import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

class Matrice {

    private double[][] matrice = new double[3][5];
    private final int []indiciBase = new int[3];
    private double[] terminiNoti = new double[3];
    private final double[] funzioneObiettivo = new double[6];
    private final int[] posizionePivot = new int[2];
    private int indiceVaribileEntrante;
    private final NumberFormat nf = new DecimalFormat("##.###");
    private final char[] c = {'₁', '₂', '₃', '₄', '₅'};

    public Matrice(double[][] matrice, double[] terminiNoti, double[] funzioneObiettivo) {
        int i,nuovoIndice=0;
        int indipendenzaLineare;
        this.matrice = matrice;
        this.terminiNoti = terminiNoti;
        for (i = 0; i <funzioneObiettivo.length ; i++) {
            this.funzioneObiettivo[i]=funzioneObiettivo[i];
        }
        while (i<this.funzioneObiettivo.length){
            this.funzioneObiettivo[i]=0;
            i++;
        }
        for (int j=0;j<matrice[0].length;j++){
            for (i = 0, indipendenzaLineare=0; i <matrice.length ; i++) {
                if (matrice[i][j]!=0)
                    indipendenzaLineare++;
            }
            if (indipendenzaLineare == 1)
                indiciBase[nuovoIndice++]=j;
        }
        trovaVariabileEntrante();
    }

    public Matrice() {
        int i,nuovoIndice=0;
        int indipendenzaLineare;
        Scanner keyboard = new Scanner(System.in);

        System.out.println("inizializzo funzione obiettivo");
        System.out.print("x₁:\t");
        funzioneObiettivo[0] = keyboard.nextInt();
        System.out.print("x₂:\t");
        funzioneObiettivo[1] = keyboard.nextInt();
        System.out.println();

        System.out.println("inizializzo la matrice");

        for (i = 0; i < matrice.length; i++) {
            System.out.println("riga " + (i + 1));
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print("colonna: " + (j + 1) + "\t");
                matrice[i][j] = keyboard.nextInt();
            }
            System.out.print("coefficiente " + (i + 1) + "\t");
            terminiNoti[i] = keyboard.nextInt();
            System.out.println();

        }

        for (int j=0;j<matrice[0].length;j++){
            for (i = 0, indipendenzaLineare=0; i <matrice.length ; i++) {
                if (matrice[i][j]!=0)
                    indipendenzaLineare++;
            }
            if (indipendenzaLineare == 1)
                indiciBase[nuovoIndice++]=j;
        }
        trovaVariabileEntrante();
    }

    public char getUscente() {
        return c[indiciBase[posizionePivot[0]]];
    }

    public char getVaribileEntrante() {
        return c[indiceVaribileEntrante];
    }

    public void cambioBase(int rigaPivot,int colonnaPivot) {
    /*if (matrice[rigaPivot][colonnaPivot]==0){
      FIXME:caso possibile ma mai incontrato in un esercizio. se lo trovo lo includo
    }*/
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
            }
            terminiNoti[rigaPivot] = terminiNoti[rigaPivot] == 0 ? 0 : terminiNoti[rigaPivot] / valorePivot;
        }

        // riduzione delle altre 2 righe per ottenere un vettore linearmente indipendente (porto a zero i valori aventi
        // la stessa colonna del pivot)
        for (int i = 0; i < matrice.length; i++) {
            if (i == rigaPivot) //questo procedimento non va ovviamente eseguito per la riga del pivot
                continue;

            valoreMoltiplicativo = matrice[i][colonnaPivot] / 1; // diviso 1 per sapere se mettere il segno meno
            for (int j = 0; j < matrice[i].length; j++) {
                matrice[i][j] = matrice[i][j] - valoreMoltiplicativo * matrice[rigaPivot][j];
            }
            terminiNoti[i] = terminiNoti[i] - valoreMoltiplicativo * terminiNoti[rigaPivot];
        }

    }

    public void cambioBase() { //wrapper di cambioBase con parametri
        cambioBase(posizionePivot[0],posizionePivot[1]);
    }

    public void aggiornaFunzioneObbiettivo() {
        double costanteMoltiplicativa = funzioneObiettivo[indiceVaribileEntrante];
        funzioneObiettivo[indiceVaribileEntrante]=0;
        for (int i = 0; i < matrice[posizionePivot[0]].length; i++) {
            if (i!=posizionePivot[1])
                funzioneObiettivo[i]-=costanteMoltiplicativa*matrice[posizionePivot[0]][i];
        }
        funzioneObiettivo[5]+=costanteMoltiplicativa*terminiNoti[posizionePivot[0]];
    }

    public double trovaVariabileEntrante() {
        int result = -1;
        double max = -1;

        for (int i = 0; i < funzioneObiettivo.length; i++) {
            if (funzioneObiettivo[i] > 0 && funzioneObiettivo[i] > max && i!=5) {
                max = funzioneObiettivo[i];
                result = i;
            }
        }

        this.indiceVaribileEntrante=result;
        return max;
    }

    public boolean trovaVariabileUscente() {
        int colonna = indiceVaribileEntrante;
        int riga = -1;

        if (colonna > -1) {
            double minimo = Double.MAX_VALUE;

            for (int i = 0; i < matrice.length; i++) {
                double elementoIJ =  terminiNoti[i] / matrice[i][colonna];
                if (elementoIJ > 0 && elementoIJ < minimo) {
                    minimo = elementoIJ;
                    riga = i;
                }
            }
        }

        if (riga == posizionePivot[0] && colonna== posizionePivot[1])//il pivot non cambia, la funzione è massima
            return true;
        posizionePivot[0] = riga;
        posizionePivot[1] = colonna;

        return false;
    }

    public double pivot() {
        trovaVariabileUscente();
        return matrice[posizionePivot[0]][posizionePivot[1]];
    }

    public void stampaFunzioneObiettivo() {
        System.out.println("funzione obiettivo");
        for (int i = 0; i < funzioneObiettivo.length-1; i++) {
            if (funzioneObiettivo[i] != 0) {
                System.out.print(nf.format(funzioneObiettivo[i]) + "x" + c[i] + " ");
            }
        }
        if (funzioneObiettivo[5]!=0)
            System.out.print(nf.format(funzioneObiettivo[5]));
        System.out.println("\n");
    }

    public void stampaMatrice() {
        System.out.println("\tstampo la matrice");
        for (int i = 0; i < matrice.length; i++) {
            System.out.print("x" + c[indiciBase[i]]+ "\t");
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(nf.format(matrice[i][j]) + "\t");
            }
            System.out.print("| " + nf.format(terminiNoti[i]));
            System.out.println();
        }
        System.out.println();
        stampaFunzioneObiettivo();
    }

    public void printVariabiliInBase(){
        int virgole=2;

        System.out.print("base massima: (");
        for (int j : indiciBase) {
            System.out.print("x" + c[j]);
            if (virgole > 0) {
                System.out.print(",");
                virgole--;
            }
        }
        System.out.println(")\n");
    }
}
