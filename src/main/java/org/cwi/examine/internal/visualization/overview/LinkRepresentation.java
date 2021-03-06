package org.cwi.examine.internal.visualization.overview;

import com.sun.javafx.collections.ObservableSetWrapper;
import org.cwi.examine.internal.data.HNode;

import org.cwi.examine.internal.data.Network;
import org.cwi.examine.internal.molepan.dataread.DataRead;
import org.cwi.examine.internal.graphics.PVector;
import org.cwi.examine.internal.graphics.StaticGraphics;
import org.cwi.examine.internal.graphics.draw.Representation;
import org.cwi.examine.internal.visualization.Parameters;
import org.cwi.examine.internal.visualization.Visualization;
import org.cwi.examine.internal.data.HAnnotation;
import org.cwi.examine.internal.data.Network;
import org.jgrapht.graph.DefaultEdge;

import org.jgrapht.graph.*;
import org.jgrapht.Graph.*;


import java.io.*;
import java.util.*;
import java.lang.Object;


import org.jgrapht.*;


import java.awt.*;
import java.util.HashSet;
import java.util.Set;


// Link representation.
public class LinkRepresentation extends Representation<LinkRepresentation.Link> {
    private final Visualization visualization;
    public final DefaultEdge edge;  // Underlying edge.
    public final PVector[] cs;
     private Network network;
    
    // public  String col = "" ;
    
    Map<String, String> collision = new HashMap<>();
     
     int baba = 0;
   
  
   //  public final PVector[] ca;
    // public int est;
    //public final LineString ls;     // Curve coordinates.

    public LinkRepresentation(final Visualization visualization,
            DefaultEdge edge,
                              HNode node1,
                              HNode node2,
                              PVector[] cs,
                              Network network) {
                              
        super(new Link(node1, node2));
        this.visualization = visualization;
        this.edge = edge;
        this.cs = cs;
        this.network = network;
        //this.node1 = node1;
        // List<HNode> collision;
        
		
        //this.ls = Util.circlePiece(cs[0], cs[1], cs[2], LINK_SEGMENTS);
    }

    @Override
    public PVector dimensions() {
        return PVector.v();
    }

    @Override
    public void draw() {
        boolean highlight = visualization.model.highlightedInteractions.get().contains(edge);
        
        StaticGraphics.picking();

        // Halo.
        double haloWeight = highlight ? Parameters.LINK_WIDTH + 4f : Parameters.LINK_WIDTH + 2f;
        StaticGraphics.color(org.cwi.examine.internal.graphics.draw.Parameters.backgroundColor);
        StaticGraphics.strokeWeight(haloWeight);
        drawLink();
        
        // Actual edge.
        double edgeWeight = highlight ? Parameters.LINK_WIDTH + 2f : Parameters.LINK_WIDTH;
        StaticGraphics.color(highlight ? Color.BLACK: org.cwi.examine.internal.graphics.draw.Parameters.textColor);
        StaticGraphics.strokeWeight(edgeWeight);
        drawLink();
    }
    
    private void drawLink() {
    
    
      //  StaticGraphics.circleArc(cs[0], cs[0], cs[2]); // Here Bond Type!
      String bondtype;
      
      
       
       
      if(  !element.node1.toString().contains("H") 
      	&& !element.node2.toString().contains("H") 
      	&& network.graph.degreeOf(element.node1) < 4 
      	&& network.graph.degreeOf(element.node2) < 4
      	&& ( !DataRead.col.contains(element.node1.toString())  // ai not in list, or ai and aj in list
      		 || DataRead.col.contains(" "  + element.node1.toString() 
      		 							   + element.node2.toString() )
      		 || DataRead.col.contains(		 element.node1.toString() 
      		 							   + element.node2.toString() 
      		 							   + " " ) 
      		 							    
      		)  
      															  
   /*   	&& ( !DataRead.col.contains(element.node2.toString())  
      		 || DataRead.col.contains(" "  + element.node1.toString() 
      		 							   + element.node2.toString() ) 
      		 || DataRead.col.contains(		 element.node1.toString() 
      		 							   + element.node2.toString() 
      		 							   + " " ) 
      		)  */
      	
     			 //	&& collision.containsValue(element.node1.toString())
      			//	&& collision.containsValue(element.node2.toString())
      	) {
      			//if(baba==0){
      			//col = "bla"; // + element.node1.toString() + element.node2.toString();
      			//baba ++;
      			//}
      			//else{
      		if( !DataRead.col.contains(element.node1.toString()+element.node2.toString()) /* && !DataRead.col.contains(element.node2.toString()+element.node1.toString()) */)
      		 DataRead.col += " " + element.node1.toString() + element.node2.toString();
      	
      			//}
      			//	System.out.println(DataRead.col);
      		
      			//	collision.put("1", element.node1.toString());
      			//	collision.put("1", element.node2.toString());
      			
      		bondtype = "double";
      	
      	}
      
      	else{
      	
      		bondtype = "single";
      		
      	}
      	
  		//System.out.println(DataRead.col);
  		
      StaticGraphics.drawLine(cs[0],cs[2], bondtype);
      
    	//  if(DataRead.atomPl==DataRead.atomNo-1) 
     	// 	DataRead.col = "";      
       
        
        //network.graph.degreeOf(element.node1);
        
      	//   StaticGraphics.circleArc(cs[2], cs[0], cs[2]);
        
         // StaticGraphics.circleArc(cs[0], cs[0], cs[1]);
          
        
        //StaticGraphics.circleArc(cs[0], cs[1], cs[2]);
        
       // StaticGraphics.circleArc(cs[0], cs[1], cs[2]);
        //drawLineString(ls);
        //drawLine(cs[0], cs[1]);
        //drawLine(cs[1], cs[2]);
    }

    @Override
    public void beginHovered() {
        // Highlight proteins term intersection.
        Set<HNode> hP = new HashSet<HNode>();
        hP.add(element.node1);
        hP.add(element.node2);
        visualization.model.highlightedProteins.set(new ObservableSetWrapper<>(hP));
        
        // Highlight interactions.
        Set<DefaultEdge> hI = new HashSet<DefaultEdge>();
        hI.add(edge);
        visualization.model.highlightedInteractions.set(new ObservableSetWrapper<>(hI));
        
        // Intersect annotation annotations.
        Set<HAnnotation> hT = new HashSet<HAnnotation>();
        hT.addAll(element.node1.annotations);
        hT.retainAll(element.node2.annotations);
        visualization.model.highlightedSets.set(new ObservableSetWrapper<>(hT));
    }

    @Override
    public void endHovered() {
        visualization.model.highlightedProteins.clear();
        visualization.model.highlightedInteractions.clear();
        visualization.model.highlightedSets.clear();
    }

    
    public static class Link {
        public HNode node1, node2;

        public Link(HNode node1, HNode node2) {
            this.node1 = node1;
            this.node2 = node2;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + (this.node1 != null ? this.node1.hashCode() : 0);
            hash = 29 * hash + (this.node2 != null ? this.node2.hashCode() : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Link other = (Link) obj;
            if (this.node1 != other.node1 && (this.node1 == null || !this.node1.equals(other.node1))) {
                return false;
            }
            if (this.node2 != other.node2 && (this.node2 == null || !this.node2.equals(other.node2))) {
                return false;
            }
            return true;
        }
    }
}
