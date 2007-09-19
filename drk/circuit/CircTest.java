package drk.circuit;
public class CircTest
{
	public static void main(String [] argin)
	{
		char i;
		
		OutputSystem root;
		LogicInput[] lin=new LogicInput[4];
		for(int is=0;is < lin.length;is++) lin[is]=new LogicInput(false);

		//root=new Multiplexor(lin);
		
		root=new BinaryXor(new BinaryAnd(new BinaryAnd(new Inverter(lin[0]),lin[1]),lin[2]),lin[3]);

		//(((not a) and b) and c) xor d
		
		OutputSystem.printTruthTable(System.out,lin,root);
	}

}
