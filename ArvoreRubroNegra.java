class ArvoreRubroNegra {
    private static final boolean vermelho = true;
    private static final boolean preto = false;

    class No {
        int valor;
        No esquerda, direita, pai;         //encontrei um algoritmo parecido em c no StackOverflow e passei para java
        boolean cor;

        No(int valor) {
            this.valor = valor;
            this.cor = vermelho;
        }
    }

    private No raiz;
    private void girarEsquerda(No no){
        No direita = no.direita;
        no.direita = direita.esquerda;

        if (direita.esquerda != null){
            direita.esquerda.pai = no;
        }
        direita.pai = no.pai;

        if(no.pai == null){
            raiz = direita;
        }else if(no == no.pai.esquerda){
            no.pai.esquerda = direita;
        }else{
            no.pai.direita = direita;
        }
        direita.esquerda = no;
        no.pai = direita;
    }

    private void girarDireita(No no){
        No esquerda = no.esquerda;
        no.esquerda = esquerda.direita;

        if(esquerda.direita != null){
            esquerda.direita.pai = no;
        }

        esquerda.pai = no.pai;

        if(no.pai == null){
            raiz = esquerda;
        }else if(no == no.pai.direita){
            no.pai.direita = esquerda;
        }else{
            no.pai.esquerda = esquerda;
        }
        esquerda.direita = no;
        no.pai = esquerda;
    }

    private void corrigirInsercao(No no){
        No pai, avo;

        while(no != raiz && no.cor != preto && no.pai.cor == vermelho){
            pai = no.pai;
            avo = pai.pai;

            if(pai == avo.esquerda){
                No tio = avo.direita;

                if(tio != null && tio.cor == vermelho){
                    avo.cor = vermelho;
                    pai.cor = preto;
                    tio.cor = preto;
                    no = avo;
                }else{
                    if(no == pai.direita){
                        girarEsquerda(pai);
                        no = pai;
                        pai = no.pai;
                    }

                    girarDireita(avo);
                    boolean temp = pai.cor;
                    pai.cor = avo.cor;
                    avo.cor = temp;
                    no = pai;
                }
            }else{
                No tio = avo.esquerda;
                if(tio != null && tio.cor == vermelho){
                    avo.cor = vermelho;
                    pai.cor = preto;
                    tio.cor = preto;
                    no = avo;
                }else{
                    if(no == pai.esquerda){
                        girarDireita(pai);
                        no = pai;
                        pai = no.pai;
                    }
                    girarEsquerda(avo);
                    boolean temp = pai.cor;
                    pai.cor = avo.cor;
                    avo.cor = temp;
                    no = pai;
                }
            }
        }
        raiz.cor = preto;
    }

    public void inserir(int valor) {
        No novoNo = new No(valor);
        No y = null;
        No x = raiz;

        while(x != null){
            y = x;
            if (novoNo.valor < x.valor) {
                x = x.esquerda;
            } else {
                x = x.direita;
            }
        }
        novoNo.pai = y;

        if(y == null){
            raiz = novoNo;
        }else if(novoNo.valor < y.valor){
            y.esquerda = novoNo;
        }else{
            y.direita = novoNo;
        }

        if(novoNo.pai == null){
            novoNo.cor = preto;
            return;
        }

        if(novoNo.pai.pai == null){
            return;
        }
        corrigirInsercao(novoNo);
    }

    private void corrigirRemocao(No x) {
        while(x != raiz && x.cor == preto){
            if(x == x.pai.esquerda){
                No w = x.pai.direita;
                if(w.cor == vermelho){
                    w.cor = preto;
                    x.pai.cor = vermelho;
                    girarEsquerda(x.pai);
                    w = x.pai.direita;
                }
                if(w.esquerda.cor == preto && w.direita.cor == preto){
                    w.cor = vermelho;
                    x = x.pai;
                }else{
                    if(w.direita.cor == preto){
                        w.esquerda.cor = preto;
                        w.cor = vermelho;
                        girarDireita(w);
                        w = x.pai.direita;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = preto;
                    w.direita.cor = preto;
                    girarEsquerda(x.pai);
                    x = raiz;
                }
            }else{
                No w = x.pai.esquerda;
                if(w.cor == vermelho){
                    w.cor = preto;
                    x.pai.cor = vermelho;
                    girarDireita(x.pai);
                    w = x.pai.esquerda;
                }
                if(w.direita.cor == preto && w.direita.cor == preto) {
                    w.cor = vermelho;
                    x = x.pai;
                }else{
                    if(w.esquerda.cor == preto){
                        w.direita.cor = preto;
                        w.cor = vermelho;
                        girarEsquerda(w);
                        w = x.pai.esquerda;
                    }
                    w.cor = x.pai.cor;
                    x.pai.cor = preto;
                    w.esquerda.cor = preto;
                    girarDireita(x.pai);
                    x = raiz;
                }
            }
        }
        x.cor = preto;
    }

    public void remover(int valor){
        No z = buscar(valor);
        if(z== null){
            return;
        }
        No y = z;
        No x;
        boolean ycorNormal = y.cor;

        if(z.esquerda == null){
            x = z.direita;
            transplantar(z, z.direita);
        }else if(z.direita == null){
            x = z.esquerda;
            transplantar(z, z.esquerda);
        }else{
            y = minimo(z.direita);
            ycorNormal = y.cor;
            x = y.direita;

            if(y.pai == z){
                if(x != null) x.pai = y;
            }else{
                transplantar(y, y.direita);
                y.direita = z.direita;
                y.direita.pai = y;
            }
            transplantar(z, y);
            y.esquerda = z.esquerda;
            y.esquerda.pai = y;
            y.cor = z.cor;
        }

        if(ycorNormal == preto){
            corrigirRemocao(x);
        }
    }

    private void transplantar(No u, No v) {
        if(u.pai == null){
            raiz = v;
        }else if(u == u.pai.esquerda){
            u.pai.esquerda = v;
        }else{
            u.pai.direita = v;
        }
        if(v != null){
            v.pai = u.pai;
        }
    }

    private No buscar(int valor){
        return buscar(raiz, valor);
    }

    private No buscar(No no, int valor){
        if(no == null || valor == no.valor){
            return no;
        }
        if(valor < no.valor){
            return buscar(no.esquerda, valor);
        }
        return buscar(no.direita, valor);
    }

    private No minimo(No no){
        while(no.esquerda != null){
            no = no.esquerda;
        }
        return no;
    }
}
