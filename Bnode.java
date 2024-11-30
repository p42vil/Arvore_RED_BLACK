public class Bnode {
	//atributos
	private int x; // valor armazenado
	private int h; // altura
	private int nivel;
	private Bnode esq, dir; // esquerda, direita
	private Bnode pai; // nó pai
	private boolean cor;
	// true = VERMELHO || false = PRETO

	//construtor
	public Bnode(Bnode pai, int valor){
		this.pai = pai; // adicionado o atributo pai, para ser menos confuso de programar
		x = valor;
		esq = null;
		dir = null;
		cor = true;
	}

	public Bnode(Bnode pai, int valor, boolean cor){
		this.pai = pai;
		this.cor = cor;
		x = valor;
		esq = null;
		dir = null;
	}

	public void adicionar(int valor, Btree arvore){ // adiciona o pai e o valor toda vez que cria
		if(valor > x){
			if(dir!=null) dir.adicionar(valor, arvore);
			else{
				Bnode newNode = new Bnode(this, valor);
				dir = newNode;
				balanceamento(newNode, arvore);
			}
		}
		else if(valor < x){
			if(esq!=null) esq.adicionar(valor, arvore);
			else{
				Bnode newNode = new Bnode(this, valor);
				esq = newNode;
				balanceamento(newNode, arvore);
			}
		}
		else{
			int eh = 0;
			int dh = 0;

			if(esq!=null) eh = esq.altura();
			if(dir!=null) dh = dir.altura();

			if (eh > dh){
				if(dir!=null) dir.adicionar(valor, arvore);
				else{
					Bnode newNode = new Bnode(this, valor);
					dir = newNode;
					balanceamento(newNode, arvore);
				}
			}
			else{
				if(esq!=null) esq.adicionar(valor, arvore);
				else{
					Bnode newNode = new Bnode(this, valor);
					esq = newNode;
					balanceamento(newNode, arvore);
				}
			}
		}
	}

	public void mostrar(){
		if (cor) System.out.println(x + " [PRETO]");
		else System.out.println(x + " [VERMELHO]");

		if(esq!=null) esq.mostrar();
		if(dir!=null) dir.mostrar();
	}

	public int tamanho(){
		int e = 0, d = 0;
		if(esq!=null) e = esq.tamanho();
		if(dir!=null) d = dir.tamanho();
		return 1 + e + d;
	}

	public void nivel(int val){
		if (esq != null) esq.nivel(val + 1);
		if (dir != null) dir.nivel(val + 1);

		nivel = val;
	}

	public int altura(){
		int qe = 0, qd = 0;

		if (esq != null) qe = esq.altura();
		if (dir != null) qd = dir.altura();

		if (qe > qd){
			return 1 + qe;
		}
		else{
			return 1 + qd;
		}
	}

	public int alturaNegra(){
		int val;

		int qe = 0, qd = 0;
		if (esq != null) qe = esq.alturaNegra();
		if (dir != null) qd = dir.alturaNegra();

		if (qe > qd) val = qe;
		else val = qd;

		if (cor == false) val++;
		if (esq == null && dir == null) val++;

		return val;
	}

	public int soma(){
		int e = 0, d = 0;
		if(esq!=null) e = esq.soma();
		if(dir!=null) d = dir.soma();
		return x + e + d;
	}

	public void definirAtr(int val){ // define altura
		h = val;

		if (esq != null) esq.definirAtr(val + 1);
		if (dir != null) dir.definirAtr(val + 1);

		if (cor) System.out.println("Valor: " + x + " | Altura: " + h + " | Cor: VERMELHO");
		else System.out.println("Valor: " + x + " | Altura: " + h + " | Cor: PRETO");

	}

	//        ( C )            ( C )
	//         /                  \
	//      ( B )                ( B )
	//       /                      \
	//    ( A )                    ( A )

	public void rotacaoEsq(Bnode A, Bnode B, Bnode C, Btree arvore){
		if (B.dir == null && B.esq != null) {
			// ficar reto, para fazer rotação
			// A e B não trocam de posição, só vals
			B.dir = B.esq;
			B.esq = null;

			int reg_val = A.x;
			A.x = B.x;
			B.x = reg_val;
		}

		if (B.esq == null) { // se o B não tiver valor do lado
			B.esq = C;
			C.dir = null;
		} else { // se tiver
			Bnode reg_node = B.esq;
			B.esq = C;
			C.dir = reg_node;
		}

		// RECOLORAÇÃO
		B.cor = false;
		C.cor = true;

		// consertar herança
		if (C.pai == null) arvore.setRaiz(B);
		else{
			Bnode paiC = C.pai;
			C.pai = B;
			B.pai = paiC;

			if (paiC.esq == C) paiC.esq = B;
			else if (paiC.dir == C) paiC.dir = B;
		}
	}

	public void rotacaoDir(Bnode A, Bnode B, Bnode C, Btree arvore){
		if (B.esq == null && B.dir != null) {
			// ficar reto, para fazer rotação
			// A e B não trocam de posição, só vals
			B.esq = B.dir;
			B.dir = null;

			int reg_val = A.x;
			A.x = B.x;
			B.x = reg_val;
		}

		if (B.dir == null) { // se o B não tiver valor do lado
			B.dir = C;
			C.esq = null;
		} else { // se tiver
			Bnode reg_node = B.dir;
			B.dir = C;
			C.esq = reg_node;
		}

		// RECOLORAÇÃO
		B.cor = false;
		C.cor = true;

		// consertar herança
		if (C.pai == null) arvore.setRaiz(B);
		else{
			Bnode paiC = C.pai;
			C.pai = B;
			B.pai = paiC;

			if (paiC.esq == C) paiC.esq = B;
			else if (paiC.dir == C) paiC.dir = B;
		}
	}

	public void balanceamento(Bnode atual, Btree arvore){
		System.out.println("Antes do balanceamento:");
		arvore.definirAtr();
		System.out.println("-----------------------------");

		if (atual.pai.cor == true){ // se pai for vermelho
			if (atual.pai.pai != null) { // se avo existe
				Bnode avo = atual.pai.pai;

				if (avo.esq == atual.pai) { // confere que lado está o pai
					if (avo.dir == null || avo.dir.cor == false) { // se tio é null ou preto
						rotacaoDir(atual, atual.pai, atual.pai.pai, arvore);
					} else { // se tio for vermelho
						// RECOLORAÇÃO
						avo.esq.cor = false;
						avo.dir.cor = false;

						// se coloração deixa dois vermelhos juntos, RECOLORAÇÃO dnv
						if (avo.cor == false && avo != arvore.getRaiz()){
							avo.cor = true;

							if (avo.pai.cor == true && avo.pai != arvore.getRaiz() && avo.pai != null){
								avo.balanceamento(avo, arvore);
							}
						}
					}
				} else if (avo.dir == atual.pai) {
					if (avo.esq == null || avo.esq.cor == false) { // se tio é null ou preto
						rotacaoEsq(atual, atual.pai, atual.pai.pai, arvore);
					} else { // se tio for vermelho
						// RECOLORAÇÃO
						avo.esq.cor = false;
						avo.dir.cor = false;

						// se coloração deixa dois vermelhos juntos, RECOLORAÇÃO dnv
						if (avo.cor == false && avo != arvore.getRaiz()){
							avo.cor = true;

							if (avo.pai.cor == true && avo.pai != arvore.getRaiz() && avo.pai != null){
								avo.balanceamento(avo, arvore);
							}
						}
					}
				}
			}
		}

		System.out.println("Pós balanceamento:");
		arvore.definirAtr();
		System.out.println("-----------------------------");
	}
}