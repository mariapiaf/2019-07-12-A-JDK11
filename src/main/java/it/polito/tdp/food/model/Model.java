package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private FoodDao dao;
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Map<Integer, Food> idMap;
	
	public Model() {
		dao = new FoodDao();
		idMap = new HashMap<>();
	}
	
	public void creaGrafo(int porzioni) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.listAllFoods(idMap);
		
		// aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getVertici(porzioni, idMap));
		
		// aggiungo archi
		for(Arco a: this.dao.getArchi(grafo, idMap)) {
			Graphs.addEdge(this.grafo, a.getF1(), a.getF2(), a.getPeso());
		}
		
	}

	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}
	
	public Set<Food> getVertici(){
		return grafo.vertexSet();
	}
	
	public List<Adiacente> getCibiCongiunti(Food partenza) {
		List<Adiacente> result = new ArrayList<>();
		
		for(DefaultWeightedEdge edge: this.grafo.edgesOf(partenza)) {
			result.add(new Adiacente(Graphs.getOppositeVertex(grafo, edge, partenza), grafo.getEdgeWeight(edge)));
		}
		
		Collections.sort(result);
		return result;
	}
}
