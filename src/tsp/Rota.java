package tsp;

import java.util.ArrayList;
import java.util.List;

public class Rota {
	private final Cidade cidadeComeco; 
	private Cidade cidadeAtual; 
	private List<Cidade> rota = new ArrayList<>(); 


	public Rota() {
		cidadeComeco = null;
	}

	public Rota(Cidade startCity) {
		this.cidadeComeco = startCity;
		this.cidadeAtual = startCity;
		this.rota.add(startCity);
	}



	public Cidade getStartCity() {
		return cidadeComeco;
	}

	public Cidade getCidadeAtual() {
		return cidadeAtual;
	}

	public void setCidadeAtual(Cidade cidadeAtual) {
		this.cidadeAtual = cidadeAtual;
	}

	public List<Cidade> getRota() {
		return rota;
	}

	public void setRota(List<Cidade> rota) {
		this.rota = rota;
	}

	@Override
	public String toString() {
		String r = "";
		if (!rota.isEmpty()) {
			// Short rota for easier display
			for (Cidade c : rota) {
				r += c.getNome() + ",";
			}

			// Remove trailing comma
			r = r.substring(0, r.length() - 1);
		}
		return "Rota{" + r + '}';
	}
}