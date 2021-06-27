package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public void listAllFoods(Map<Integer, Food> idMap){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;

			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					if(!idMap.containsKey(res.getInt("food_code"))) {
						Food f = new Food(res.getInt("food_code"),res.getString("display_name"));
						idMap.put(res.getInt("food_code"), f);
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> getVertici(int n, Map<Integer, Food> idMap){
		String sql = "SELECT f.food_code, f.display_name, COUNT(*) AS porzioni "
				+ "FROM `portion` p, food f "
				+ "WHERE p.food_code = f.food_code "
				+ "GROUP BY f.food_code, f.display_name "
				+ "HAVING porzioni = ?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, n);
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(idMap.containsKey(res.getInt("f.food_code"))) {
					list.add(idMap.get(res.getInt("f.food_code")));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Arco> getArchi(Graph<Food, DefaultWeightedEdge> grafo, Map<Integer, Food> idMap) {
		String sql = "SELECT f1.food_code AS id1, f2.food_code AS id2, AVG(c.condiment_calories) AS peso "
				+ "FROM food_condiment f1, food_condiment f2, condiment c "
				+ "WHERE f1.condiment_code = f2.condiment_code AND f1.food_code> f2.food_code "
				+ "	AND f1.condiment_code = c.condiment_code "
				+ "GROUP BY f1.food_code, f2.food_code"; 
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Arco> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Food f1 = idMap.get(res.getInt("id1"));
				Food f2 = idMap.get(res.getInt("id2"));
				if(grafo.containsVertex(f1) && grafo.containsVertex(f2)) {
					list.add(new Arco(f1, f2, res.getDouble("peso")));
					
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
}
