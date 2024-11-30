// ARVORE BINARIA RED BLACK //

public class Main{
    public static void main(String args[]){
        Btree a = new Btree();

        // Exemplo de árvore RED BLACK
        a.adicionar(10);
        a.adicionar(5);
        a.adicionar(12);
        a.adicionar(3);
        a.adicionar(8);
        a.adicionar(15);
        a.adicionar(18);

        a.mostrar();
    }
}