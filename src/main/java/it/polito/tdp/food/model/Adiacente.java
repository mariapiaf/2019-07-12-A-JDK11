package it.polito.tdp.food.model;

public class Adiacente implements Comparable<Adiacente>{

	Food f1;
	Double calorieCongiunte;
	
	public Adiacente(Food f1, Double calorieCongiunte) {
		super();
		this.f1 = f1;
		this.calorieCongiunte = calorieCongiunte;
	}

	public Food getF1() {
		return f1;
	}

	public void setF1(Food f1) {
		this.f1 = f1;
	}

	public Double getCalorieCongiunte() {
		return calorieCongiunte;
	}

	public void setCalorieCongiunte(double calorieCongiunte) {
		this.calorieCongiunte = calorieCongiunte;
	}

	@Override
	public String toString() {
		return f1.getDisplay_name()+" "+calorieCongiunte;
	}

	@Override
	public int compareTo(Adiacente o) {
		return this.calorieCongiunte.compareTo(calorieCongiunte);
	}
	
	
	
}
