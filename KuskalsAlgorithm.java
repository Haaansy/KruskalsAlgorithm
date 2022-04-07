import java.util.*;

class Graph {
    private boolean adjMatrix[][];
    private int numVertices, numEdges;
    Edge edge[];

    class Edge implements Comparable<Edge>{
        int src, dest, weight;

        public int compareTo(Edge compareEdge){
            return this.weight - compareEdge.weight;
        }
    };

    class subset{
        int parent, rank;
    }
  
    public Graph(int v, int e){
      this.numVertices = v;
      this.numEdges = e;
      edge = new Edge[e];
      for(int i = 0; i < e; ++i){
          edge[i] = new Edge();
      }
      adjMatrix = new boolean[numVertices][numVertices];
    }

    int find(subset subsets[], int i){
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }

    void Union(subset subsets[], int x, int y){
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
 
        if (subsets[xroot].rank
            < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank
                 > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
 
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    void KruskalAlgorithm(){
        Edge result[] = new Edge[numVertices];
        int e = 0;
       
        int i = 0;
        for (i = 0; i < numVertices; ++i)
            result[i] = new Edge();
 
        Arrays.sort(edge);
 
        subset subsets[] = new subset[numVertices];
        for (i = 0; i < numVertices; ++i){
            subsets[i] = new subset();
        }
 
        for (int v = 0; v < numVertices; ++v){
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0;
 
        while (e < numVertices - 1){
            Edge next_edge = edge[i++];
 
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);
 
            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
        System.out.println("Constructed MST: ");
        int minimumCost = 0;
        for (i = 0; i < e; ++i)
        {
            System.out.println(result[i].src + " -- "
                               + result[i].dest
                               + " == " + result[i].weight);
            minimumCost += result[i].weight;
        }
        System.out.println("Minimum Cost Spanning Tree "
                           + minimumCost);
    }
  
    // Add edges
    public void addEdge(int i, int j) {
      adjMatrix[i][j] = true;
      adjMatrix[j][i] = true;
    }
  
    // Remove edges
    public void removeEdge(int i, int j) {
      adjMatrix[i][j] = false;
      adjMatrix[j][i] = false;
    }
  
    // Print the matrix
    public String toString() {
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < numVertices; i++){
        s.append(i + ": ");
        for(boolean j : adjMatrix[i]){
          s.append((j ? 1 : 0) + " ");
        }
        s.append("\n");
      }
      return s.toString();
    }

    public static void Driver(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of Vertices: ");
        int vertices = sc.nextInt();
        System.out.println("Enter number of Edges: ");
        int edges = sc.nextInt();
        Graph g = new Graph(vertices, edges);

        for(int c = 0; c <= vertices; ++c){
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            System.out.println("Enter source node: ");
            g.edge[c].src = sc.nextInt();
            System.out.println("Enter Destination node: ");
            g.edge[c].dest = sc.nextInt();
            System.out.println("Enter Weight: ");
            g.edge[c].weight = sc.nextInt();
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            g.addEdge(g.edge[c].src, g.edge[c].dest);
        }
        System.out.println("Adjacent Matrix:");
        System.out.print(g.toString());
        g.KruskalAlgorithm();
        int selection;
        do{
            System.out.println("DO YOU WANT TO TRY AGAIN?");
            System.out.println("[1] YES");
            System.out.println("[2] NO");
            selection = sc.nextInt();
            switch(selection){
                case 1: Driver();
                        break;
                case 2: System.exit(0);
                        break;
            }
        }while(selection > 2);
    }
  
    public static void main(String[]args) {
        Driver();
    }
  }