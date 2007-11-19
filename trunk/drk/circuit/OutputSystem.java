package drk.circuit;
import java.io.*;
import drk.maze.MazeItem;
import drk.game.KarnaughGame;
import drk.game.KarnaughOverlays;

public abstract class OutputSystem extends MazeItem
{
	public abstract boolean evaluate();
	public abstract int getNumInputs();
	public abstract OutputSystem getInput(int i);
	public abstract OutputSystem setInput(OutputSystem os,int i);
	
	
	public String toString()
	{
		boolean e=evaluate();
		if(e)	return "1";
		else	return "0";
	}
	
	public static void printTruthTable(PrintStream out,LogicInput[] lin,OutputSystem root)
	{
		int i,j;
		for(j=lin.length-1;j>=0;j--) System.out.print(j+" ");
		System.out.println("| r");
		for(j=lin.length-1;j>=0;j--) System.out.print("--");
		System.out.println("---");
		for(i=0;i<(1<<lin.length);i++)
		{
			for(j=0;j<lin.length;j++) lin[j].setValue(((i >> j) & 0x01) == 1);
			for(j=lin.length-1;j>=0;j--) System.out.print(lin[j]+" ");
			System.out.println("| "+root);
		}
	}
	
/*	public void render(GL gl)
	{
		super.render(gl);
	}*/
	
	
	public void onMazeItemHighlighted(KarnaughGame k){
		super.onMazeItemHighlighted(k);
		
		if(k.leftClick || k.rightClick){
		if(!k.hasWire){
			k.hasWire = true;
			k.overlays.currentCursor = k.overlays.wireHand;
		}
		else{
			k.overlays.currentCursor = k.overlays.cursor;
			k.hasWire = false;
		}
		}
		
		else{
			if(!k.hasWire){
		  k.overlays.currentCursor = k.overlays.interactHand;}
		}
		
		}
	

	
	
	public static boolean checkTruthTable(int[] table,LogicInput[] lin,OutputSystem root)
	{
		for(int i=0;i<(1<<lin.length) && i < table.length;i++)
		{
			for(int j=0;j<lin.length;j++) lin[j].setValue(((i >> j) & 0x01) == 1);
			//for(int j=lin.length-1;j>=0;j--) System.out.print(lin[j]+" ");
			//System.out.println("| "+root);
			if(
				(table[i] == 0 || table[i] == 1) 
				&& (root.evaluate() != (table[i]==1))
			) return false;			
		}
		return true;
	}
}
