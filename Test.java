import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.exit;
import static java.util.stream.Collectors.toList;

class Test {
    public static void main(String[] args) {
        Matrice matrice= fileParsing("prova.txt");
        matrice.stampaMatrice();
        matrice.trovaVariabileEntrante();
        matrice.trovaVariabileUscente();
        matrice.cambioBase();
        matrice.aggiornaFunzioneObbiettivo();
        matrice.stampaMatrice();

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
