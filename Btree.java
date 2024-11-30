public class Btree {
	// atributos da classe
	Bnode raiz;

	// construtor
	public Btree(){
		raiz = null;
	}

	public Bnode getRaiz() {
		return raiz;
	}

	public void setRaiz(Bnode raiz) {
		this.raiz = raiz;
	}

	public void adicionar(int valor){
		if(raiz!=null) raiz.adicionar(valor, this);
		else{
			raiz = new Bnode(null, valor, false);
			definirAtr();
			System.out.println("-----------------------------");
		}
	}

	public void mostrar(){
		if(raiz!=null) raiz.mostrar();
		else System.out.println("Arvore Vazia!!! ");
	}

	public int tamanho(){
		if(raiz!=null) return raiz.tamanho();
		return 0;
	}

	public void nivel(){
		if(raiz!=null) raiz.nivel(0);
		else System.out.println("Arvore Vazia!!! ");
	}

	public int altura(){
		if (raiz != null) return raiz.altura();
		return 0;
	}

	public int alturaNegra(){
		if (raiz != null) return raiz.alturaNegra();
		return 0;
	}

	public int soma(){
		if(raiz!=null) return raiz.soma();
		return 0;
	}

	public void definirAtr(){
		if(raiz!=null) raiz.definirAtr(1);
		else System.out.println("Arvore Vazia!!! ");
	}
}