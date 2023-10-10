package avengers;
/**
 * 
 * Using the Adjacency Matrix of n vertices and starting from Earth (vertex 0), 
 * modify the edge weights using the functionality values of the vertices that each edge 
 * connects, and then determine the minimum cost to reach Titan (vertex n-1) from Earth (vertex 0).
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * LocateTitanInputFile name is passed through the command line as args[0]
 * Read from LocateTitanInputFile with the format:
 *    1. g (int): number of generators (vertices in the graph)
 *    2. g lines, each with 2 values, (int) generator number, (double) funcionality value
 *    3. g lines, each with g (int) edge values, referring to the energy cost to travel from 
 *       one generator to another 
 * Create an adjacency matrix for g generators.
 * 
 * Populate the adjacency matrix with edge values (the energy cost to travel from one 
 * generator to another).
 * 
 * Step 2:
 * Update the adjacency matrix to change EVERY edge weight (energy cost) by DIVIDING it 
 * by the functionality of BOTH vertices (generators) that the edge points to. Then, 
 * typecast this number to an integer (this is done to avoid precision errors). The result 
 * is an adjacency matrix representing the TOTAL COSTS to travel from one generator to another.
 * 
 * Step 3:
 * LocateTitanOutputFile name is passed through the command line as args[1]
 * Use Dijkstra’s Algorithm to find the path of minimum cost between Earth and Titan. 
 * Output this number into your output file!
 * 
 * Note: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut (here, minCost represents the minimum cost to 
 *   travel from Earth to Titan):
 *     StdOut.setFile(outputfilename);
 *     StdOut.print(minCost);
 *  
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/LocateTitan locatetitan.in locatetitan.out
 * 
 * @author David Rahabi
 * 
 */

public class LocateTitan {
	
    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java LocateTitan <INput file> <OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        int a=StdIn.readInt();
    	int[] gen=new int[a];
        double[] fun=new double[a];

    for(int i=0;i<a;i++){
        gen[i]= StdIn.readInt();
        fun[i]= StdIn.readDouble();
    }
    int[][] enc=new int[a][a];
    for(int i=0;i<a;i++){
        for(int j=0;j<a;j++){
            enc[i][j]=StdIn.readInt();
        }
    }
    for(int i=0;i<a;i++){
        for(int j=0;j<a;j++){
            int e=enc[i][j];
            if(enc[i][j]!=0){
                double n=e/(fun[i]*fun[j]);
                int no= (int) n;
                enc[i][j]=no;
            }     
        }
    }
    int[] minCost=new int[a];
    boolean[] dijk= new boolean[a];
    for(int i=0;i<a;i++){
        if(i==0) {
            minCost[i]=0;
        }
        else{
        minCost[i]=Integer.MAX_VALUE;

        }
    }
    System.out.println();
    for(int i=0;i<a;i++){
        int currentSource=getMinCostNode(minCost, dijk);
        dijk[currentSource]=true;
        for(int j=0;j<a;j++){
            if(enc[currentSource][j]!=0 && dijk[j]==false && minCost[currentSource]!=Integer.MAX_VALUE && 
            minCost[j]>(minCost[currentSource] + enc[currentSource][j])){
                minCost[j]=minCost[currentSource] + enc[currentSource][j];
            }
        }
    }
    StdOut.setFile(args[1]);
    StdOut.println(minCost[minCost.length-1]);
    }
private static int getMinCostNode(int[] cost, boolean[] bool){
    int min=Integer.MAX_VALUE;
    int nod=0;
    for(int i=0; i<cost.length; i++){
        if(cost[i]<min && bool[i]==false ){
            min=cost[i];
            nod=i;
        }
    }
    return nod;
    }
}


