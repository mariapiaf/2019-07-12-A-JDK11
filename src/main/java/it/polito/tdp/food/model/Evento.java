package it.polito.tdp.food.model;

public class Evento {

	public enum EventType{
		INIZIO_PREPARAZIONE,
		FINE_PREPARAZIONE,
	}
	
	public Double tempoPreparazione;
	public EventType tipo;
	public Food food;
	public Stazione stazione;
	
	public Evento(Double tempoPreparazione, EventType tipo, Food food, Stazione stazione) {
		super();
		this.tempoPreparazione = tempoPreparazione;
		this.tipo = tipo;
		this.food = food;
		this.stazione = stazione;
	}

	public Double getTempoPreparazione() {
		return tempoPreparazione;
	}

	public void setTempoPreparazione(Double tempoPreparazione) {
		this.tempoPreparazione = tempoPreparazione;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Stazione getStazione() {
		return stazione;
	}

	public void setStazione(Stazione stazione) {
		this.stazione = stazione;
	}
	
	
	
}
