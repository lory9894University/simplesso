import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;
import static java.util.stream.Collectors.toList;

class Simplesso {
    public static void main(String[] args) {
        final int RIGA = 0;
        final int COLONNA = 1;

        boolean debug = true;

        Matrice matrice;
        boolean illimitata = false;
        boolean troppiCicli = false;
        int counter = 0;

        if (args.length == 0) {
            System.out.println("non è stato specificato alcun argomento, procedo  tramite riga di comando\n");
            matrice = new Matrice();
        } else {
            if (args[0].equals("-h")) {
                System.out.println("inserire come argomento il path del file contenente:\n" +
                        "coefficienti della funzione obbiettivo\nmatrice dei coefficienti\n\n" +
                        "Esempio:\n2 1\n1 1 1 0 0 1\n1 3 0 1 0 6\n1 0 0 0 1 4\n");
            }
            matrice = fileParsing(args[0]);

        }
        matrice.stampaMatrice();

        boolean trovatoMassimo = matrice.trovaVariabileEntrante() == -1;

        if (debug) {
            System.out.println("colonna variabile entrante " + matrice.trovaVariabileEntrante());
            System.out.println("colonna " + matrice.pivot(COLONNA));
            System.out.println("riga " + matrice.pivot(RIGA));
            System.out.println("elemento " + matrice.pivot());
        }

        while (!trovatoMassimo && !illimitata && !troppiCicli) {
            illimitata = matrice.pivot(RIGA) == -1;

            trovatoMassimo = matrice.trovaVariabileEntrante() == -1;
            troppiCicli = ++counter == 2;
        }

        if (trovatoMassimo) System.out.println("trovata la soluzione");
        else if (illimitata) System.out.println("il sistema é illimitato");
        else System.out.println("errore!");

    }

    static public Matrice fileParsing(String file) {
        List<String> lines = null;
        int i = 0;
        String[] sub;
        double[] knownCoeff = new double[3];
        double[] targetFunction = new double[0];
        double[][] matrice = new double[3][5];

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            lines = in.lines().collect(toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        if (lines == null) {
            System.out.println("non riesco a leggere il file\n");
            exit(1);
        }

        for (String line : lines) {
            if (i == 0)
                targetFunction = Arrays.stream(line.split(" ")).map(Double::valueOf).mapToDouble(Double::doubleValue).toArray();
            else {
                sub = line.split(" ");
                for (int j = 0; j < 5; j++)
                    matrice[i - 1][j] = Double.parseDouble(sub[j]);
                knownCoeff[i - 1] = Double.parseDouble(sub[5]);
            }
            i++;
        }

        if (i != 4) {
            System.out.println("file male formattato:\ncoefficienti della funzione obbiettivo\n" +
                    "matrice dei coefficienti\n\n" +
                    "Esempio:\n2 1\n1 1 1 0 0 1\n1 3 0 1 0 6\n1 0 0 0 1 4\n");
            exit(1);
        }

        return new Matrice(matrice, knownCoeff, targetFunction);
    }
}
