package view;

import com.mxgraph.view.mxGraph;
import com.mxgraph.swing.mxGraphComponent;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.Controller;

import tournament.Match;
import tournament.Tournament;

/**
 * The ViewTreeTournament is the view the view that 
 * displays the tree of a tournament
 * @author Group
 * @version 1.0
 */

public class PanelTreeTournament extends JPanel {
	
	/** Pour éviter un warning venant du JFrame */
	public static final long serialVersionUID = -8123406571694511514L;

	private Controller controller;
	private static int deltaY=30; 
	private static int deltaX=120;
	
	public PanelTreeTournament(Controller controller, Tournament t) {
		
		this.controller = controller;
		
		mxGraph graph = new mxGraph();
		
		graph.getModel().beginUpdate();
		Match[] tab_temp = t.getMatchs();
		
		ArrayList<Match> tab_match = new ArrayList<Match>();
		for (int i = 0 ; i<tab_temp.length;i++) {
			tab_match.add(tab_temp[i]);
		}
		try {
			premierMatch(graph, tab_match);
		} finally {
			graph.getModel().endUpdate();
		}
			mxGraphComponent graphComponent = new mxGraphComponent(graph);
			this.add(graphComponent);
			graph.setCellsMovable(false);
			graph.setCellsEditable(false);
			graph.setAllowDanglingEdges(false);
		}
	
	private void gauche(mxGraph graph, int x , int y, ArrayList<Match> list, int indice, int ecart){
		if (indice< list.size()) {
			graph.insertVertex(graph.getDefaultParent(), null, list.get(indice), x, y, 100, 30);
			gauche(graph, x-deltaX, y+ecart*deltaY,list,indice*2+1, ecart/2);
			droite(graph, x-deltaX, y-ecart*deltaY,list,indice*2, ecart/2);
		}
	}
	
	private void droite(mxGraph graph, int x, int y, ArrayList<Match> list, int indice, int ecart){
		if (indice< list.size()) {
			graph.insertVertex(graph.getDefaultParent(), null, list.get(indice), x, y, 100, 30);
			gauche(graph, x-deltaX, y+ecart*deltaY, list,indice*2+1, ecart/2);
			droite(graph, x-deltaX, y-ecart*deltaY, list,indice*2, ecart/2);
		}
	}
	
	private void premierMatch(mxGraph graph, ArrayList<Match> list) {
		int nb_match_MAX_colonne = (int)(list.size()/2)+1;
		int x = (int) (Math.sqrt(nb_match_MAX_colonne ) *deltaX);
		int y = ((nb_match_MAX_colonne )*(deltaY+30))/2+15;
		graph.insertVertex(graph.getDefaultParent(), null, list.get(1), x, y, 100, 30);
		graph.addListener("Match", null);
		gauche(graph, x-deltaX, y+nb_match_MAX_colonne/2*deltaY,list,3, nb_match_MAX_colonne/4 );
		droite(graph, x-deltaX, y-nb_match_MAX_colonne/2*deltaY,list,2, nb_match_MAX_colonne/4 );
	}
}