class Simplesso {
  public static void main(String[] args) {
    Matrice matrice = new Matrice();
    boolean trovatoMassimo = false;
    boolean illimitata = false;
    boolean troppiCicli = false;
    int counter = 0;

    matrice.inizializzoMatrice();
    matrice.stampaMatrice();

    trovatoMassimo = matrice.trovaVariabileEntrante() == -1;

    while (!trovatoMassimo && !illimitata && !troppiCicli) {
        illimitata = matrice.trovaVariabileUscente()[0] == -1;

        trovatoMassimo = matrice.trovaVariabileEntrante() == -1;
        troppiCicli = ++counter == 2;
    }

    if (trovatoMassimo) System.out.println("trovata la soluzione");
    else if (illimitata) System.out.println("il sistema é illimitato");
    else if (troppiCicli) System.out.println("errore!");
  }
}
