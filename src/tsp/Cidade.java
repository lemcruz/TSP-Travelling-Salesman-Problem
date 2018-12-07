package tsp;

public class Cidade {
	private String nome;
	private int ID;
	private boolean foiVisitada;


	public Cidade(String name, int ID, boolean visited) {
		this.nome = name;
		this.ID = ID;
		this.foiVisitada = visited;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public boolean isFoiVisitada() {
		return foiVisitada;
	}

	public void setFoiVisitada(boolean foiVisitada) {
		this.foiVisitada = foiVisitada;
	}

	@Override
	public String toString() {
		return "Cidade{" + "nome=" + nome + ", ID=" + ID + 
                            ", foiVistada=" + foiVisitada + '}';
	}
}