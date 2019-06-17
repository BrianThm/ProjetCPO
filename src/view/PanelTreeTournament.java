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
	private static int x=1000;
	private static int y=250;
	
	public PanelTreeTournament(Controller controller, Tournament t) {
		
		this.controller = controller;
		
		mxGraph graph = new mxGraph();
		//Object parent = graph.getDefaultParent();
		//ArrayList<Object> tab_match = new ArrayList();
		
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
			graph.setCellsMovable(true);
			graph.setCellsEditable(false);
		}
	
	private void gauche(mxGraph graph, int xpere , int ypere, ArrayList<Match> list, int indice){
		if (indice< list.size()) {
			graph.insertVertex(graph.getDefaultParent(), null, list.get(indice), xpere-(x/5), ypere+(y/6), 80, 30);
			gauche(graph, xpere-(x/5), ypere+(y/3),list,indice*2+1);
			droite(graph, xpere-(x/5), ypere+(y/3),list,indice*2);
		}
	}
	
	private void droite(mxGraph graph, int xpere , int ypere, ArrayList<Match> list, int indice){
		if (indice< list.size()) {
			graph.insertVertex(graph.getDefaultParent(), null, list.get(indice), xpere - (x/5), ypere-(y/6), 80, 30);
			gauche(graph, xpere-(x/5), ypere-(y/3),list,indice*2+1);
			droite(graph, xpere-(x/5), ypere-(y/3),list,indice*2);
		}
	}
	
	private void premierMatch(mxGraph graph, ArrayList<Match> list) {
		graph.insertVertex(graph.getDefaultParent(), null, list.get(1), x, y, 80, 30);
		gauche(graph, x, y+(y/6),list,3);
		droite(graph, x, y-(y/6),list,2);
	}
}