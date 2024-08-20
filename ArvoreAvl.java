public class ArvoreAvl {
    class No{
        int valor;
        int altura;    //mesmo algoritmo da atividade anterior
        No esquerda;
        No direita;
        int balancear;

        No(int valor){
            this.valor = valor;
            this.altura = 1;
            this.balancear = 0;
        }
    }
    No raiz;

    int altura(No no){
        if(no == null){
            return 0;
        }else{
            return no.altura;
        }
    }
    int balancear(No no){
        if(no == null){
            return 0;
        }
        else{
            return altura(no.direita)- altura(no.esquerda);
        } 
    }

    No girarDireita(No y){
        No x = y.esquerda;
        No T2 = x.direita;

        x.direita = y;
        y.esquerda = T2;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita))+ 1;
        y.balancear = balancear(y);
        x.balancear = balancear(x);

        return x;
    }

    No girarEsquerda(No x){
        No y = x.direita;
        No T2 = y.esquerda;
        y.esquerda = x;
        x.direita = T2;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) +1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.balancear = balancear(x);
        y.balancear = balancear(y);

        return y;
    }

    No inserir(No no, int valor){
        if(no == null){
            return new No(valor);
        }
        if(valor < no.valor){
            no.esquerda = inserir(no.esquerda, valor);
        } 
        else if(valor > no.valor){
            no.direita = inserir(no.direita, valor); 
        } 
        else{
            return no;
        }
        
        no.altura = 1 +Math.max(altura(no.esquerda), altura(no.direita));
        no.balancear = balancear(no);

        if(no.balancear > 1 && valor> no.direita.valor){
            return girarEsquerda(no);
        }
        if(no.balancear < -1 && valor < no.esquerda.valor){
            return girarDireita(no);
        }
        if(no.balancear > 1 && valor< no.direita.valor){
            no.direita = girarDireita(no.direita);
            return girarEsquerda(no);
        }
        if(no.balancear < -1 && valor> no.esquerda.valor){
            no.esquerda = girarEsquerda(no.esquerda);
            return girarDireita(no);
        }
        return no;
    }

    No valorMin(No no){
        No atual = no;
        while (atual.esquerda != null){
            atual = atual.esquerda;
        }  
        return atual;
    }

    No remover(No raiz, int valor){
        if(raiz == null){
            return raiz;
        }
        if(valor< raiz.valor){
            raiz.esquerda = remover(raiz.esquerda, valor);
        } 
        else if(valor > raiz.valor){
            raiz.direita = remover(raiz.direita, valor);
        }  
        else{
            if((raiz.esquerda == null)|| (raiz.direita == null)) {
                No temp = null;
                if(temp == raiz.esquerda){
                    temp = raiz.direita;
                }
                else{
                    temp = raiz.esquerda;
                }
                if(temp == null){
                    temp = raiz;
                    raiz = null;
                }else{
                    raiz = temp;
                }
            }else{
                No temp = valorMin(raiz.direita);
                raiz.valor = temp.valor;
                raiz.direita = remover(raiz.direita, temp.valor);
            }
        }
        if (raiz == null){
            return raiz;
        }
            
        raiz.altura = Math.max(altura(raiz.esquerda), altura(raiz.direita)) +1;
        raiz.balancear = balancear(raiz);

        if(raiz.balancear > 1 && balancear(raiz.direita) >=0){
            return girarEsquerda(raiz);
        }
        if(raiz.balancear > 1&& balancear(raiz.direita) < 0){
            raiz.direita = girarDireita(raiz.direita);
            return girarEsquerda(raiz);
        }
        if(raiz.balancear <-1 && balancear(raiz.esquerda) <= 0){
            return girarDireita(raiz);
        }
        if(raiz.balancear < -1 && balancear(raiz.esquerda)> 0){
            raiz.esquerda = girarEsquerda(raiz.esquerda);
            return girarDireita(raiz);
        }
        return raiz;
    }

    void preOrdem(No no){
        if(no != null){
            System.out.println("valor: "+no.valor+" fator de balanceamento: "+no.balancear);
            preOrdem(no.esquerda);
            preOrdem(no.direita);
        }
    }

    boolean eArvoreAvl(No no){
        if(no == null){
            return true;
        }

        int fBalancear = balancear(no);
        if(fBalancear > 1 || fBalancear <-1){
            return false;
        }
        return eArvoreAvl(no.esquerda) && eArvoreAvl(no.direita);
    }

    int contarOcorrencias(No no, int valor){
        if(no == null){
            return 0;
        }
        if(valor < no.valor){
            return contarOcorrencias(no.esquerda, valor);
        }else if (valor > no.valor){
            return contarOcorrencias(no.direita, valor);
        }else{
            return 1;
        }
    }
}