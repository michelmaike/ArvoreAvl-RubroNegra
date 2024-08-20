import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class ComparacaoArvores {
    public static void main(String[] args){
        ArvoreAvl avl = new ArvoreAvl();
        ArvoreRubroNegra rubroNegra = new ArvoreRubroNegra();
        String caminho = "C:/Users/Michel-PC/Downloads/dados100_mil.txt";

        try{
            File dados = new File(caminho);
            Scanner scan = new Scanner(dados);
            long inicioAvl = System.nanoTime(); //estava dando erro na leitura dos dados, utilizei IA com os try catchs e conseguir corrigir os erros

            while(scan.hasNextLine()) {
                String linha = scan.nextLine().trim();
                try{
                    int numero = Integer.parseInt(linha);
                    avl.raiz = avl.inserir(avl.raiz, numero);
                }catch (NumberFormatException e){
                    System.err.println("erro ao ler"+linha);
                }
            }

            long fimAVL = System.nanoTime();
            scan.close();
            scan = new Scanner(dados);
            long inicioRN = System.nanoTime();

            while (scan.hasNextLine()) {
                String linha = scan.nextLine().trim();
                try{
                    int numero = Integer.parseInt(linha);
                    rubroNegra.inserir(numero);
                }catch (NumberFormatException e) {
                    System.err.println("erro ao ler o dados"+linha);
                }
            }

            long fimRN = System.nanoTime();
            scan.close();
            long tempoExecucaoAVL = fimAVL - inicioAvl;
            long tempoExecucaoRN = fimRN - inicioRN;
            System.out.println("tempo para inserir na avl: "+formatarTempo(tempoExecucaoAVL));
            System.out.println("tempo para inserir na rubro negra: "+formatarTempo(tempoExecucaoRN));

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Random random = new Random();
        long inicioOperacaoAVL = System.nanoTime();

        for(int i = 0; i< 50000; i++){
            int sorteio = random.nextInt(19999)- 9999;

            if(sorteio % 3 == 0){
                avl.raiz = avl.inserir(avl.raiz,sorteio);
            }else if(sorteio% 5 == 0){
                avl.raiz = avl.remover(avl.raiz, sorteio);
            }else{
                avl.contarOcorrencias(avl.raiz, sorteio);
            }
        }
        long fimAvl = System.nanoTime();
        long inicioAvl = System.nanoTime();

        for(int i = 0; i < 50000; i++){
            int sorteio = random.nextInt(19999) - 9999;

            if(sorteio % 3 == 0){
                rubroNegra.inserir(sorteio);
            } else if(sorteio % 5 == 0){
                rubroNegra.remover(sorteio);
            }
        }
        long fimOperacaoRN = System.nanoTime();
        long tempoExeAvl = fimAvl- inicioOperacaoAVL;
        long tempoExeRubroNe = fimOperacaoRN - inicioAvl;
        System.out.println("tempo de execução avl: "+formatarTempo(tempoExeAvl));
        System.out.println("tempo de execução rubro negra: "+formatarTempo(tempoExeRubroNe));
    }

    private static String formatarTempo(long tempoNano) {
        long tempoMillis = tempoNano / 1000000;
        long minutos = tempoMillis / 60000;
        long segundos = (tempoMillis % 60000) / 1000;
        long milissegundos = tempoMillis % 1000;
        return String.format(minutos+" minutos, "+segundos+" segundos e "+milissegundos+"milissegundos");
    }
}
