package com.company;

import java.util.*;

/**
 * Driver of my program
 */

public class Main {
    //global world for the fist task

    static AdjacencyMatrixGraph < String, Integer > matrixGraph = new AdjacencyMatrixGraph < > ();

    //global world for the second task

    static AdjacencyMatrixGraph <Integer,Integer> matrixIntegerGraph = new AdjacencyMatrixGraph<>();

    //to start execute the dfs

    static String firstChar = new String();

    /**
     * Main method
     * @param args
     */

    public static void main(String[] args) {
        driveInput2();
        //driveInput1();

    }

    /**
     * Dijkstra's algorithm implementation
     * @param source From which vertex we want to find the path
     * @param target End of path that we want to find
     */

    public static void  dijkstra(Vertex<Integer> source, Vertex<Integer> target){
        //Initialize array with initial distance between nodes

        int[] dijkstraArray = new int[matrixIntegerGraph.listOfVertex.size()];

        for (int i = 0; i < matrixIntegerGraph.listOfVertex.size(); i++) {
            dijkstraArray[i] = Integer.MAX_VALUE;
        }

        //Initializing array to return the path between nodes

        int[] path = new int[matrixIntegerGraph.listOfVertex.size()];

        //Checker that responsible for visiting of each vertices and contains boolean vals

        boolean[] isVisited = new boolean[matrixIntegerGraph.listOfVertex.size()];

        //Integer index
        int start = matrixIntegerGraph.indexOf(source);

        dijkstraArray[start] = 0;

        //implementation by queue
        Queue<Vertex<Integer>> queue = new LinkedList<>();

        queue.add(source);

        while (!queue.isEmpty()){

            //Extracting from queue

            Vertex<Integer> startingPoint = queue.poll();

            //Updating distance between between nodes

            for(int i = 0; i < matrixIntegerGraph.listOfVertex.size();++i ){
                if( matrixIntegerGraph.adjacencyMatrix.get(start).get(i).weight != null
                        && dijkstraArray[start] + (int)matrixIntegerGraph.adjacencyMatrix.get(start).get(i).weight < dijkstraArray[i]) {
                    dijkstraArray[i] = dijkstraArray[start] + (int)matrixIntegerGraph.adjacencyMatrix.get(start).get(i).weight;
                    path[i] = start;
                }
            }

            //Putting bool value true means that this node is already visited

            isVisited[start] = true;

            int minDist = Integer.MAX_VALUE;

            //Search current minimum distance to selecting and pushing to the queue

            for (int i = 0; i < matrixIntegerGraph.listOfVertex.size(); i++) {
                if(minDist > dijkstraArray[i] && !isVisited[i]){
                    minDist = dijkstraArray[i];
                    start = i;
                }
            }

            //Check that is didn't participate at our path

            if(!isVisited[start])
                queue.add(matrixIntegerGraph.findVertex(start));


        }

        //Integer index of target vertex

        int targetIndex = matrixIntegerGraph.indexOf(target);

        //Checking conditions

        if(dijkstraArray[targetIndex] == Integer.MAX_VALUE){
            System.out.println("IMPOSSIBLE");
        }

        //Returning the answer path

        else{
            int old_start = matrixIntegerGraph.indexOf(source);

            int j = targetIndex;
            ArrayList<Integer> ans = new ArrayList<>();
            j = targetIndex;
            while(j != old_start){
                ans.add(j);
                j = path[j];
            }
            ans.add(j);
            System.out.print(ans.size()+" "+ dijkstraArray[targetIndex] + " ");
            int minBandwidth = Integer.MAX_VALUE;

            for(int i = ans.size() - 2; i >=0;i--){

                Edge<Integer> edge = matrixIntegerGraph.findEdge(ans.get(i+1),ans.get(i));
                if(minBandwidth > edge.bandwidth){
                    minBandwidth = edge.bandwidth;
                }
            }
            System.out.println(minBandwidth);
            for(int i = ans.size() - 1; i >=0;i--){
                System.out.print(ans.get(i) + " ");
            }
        }
    }

    /**
     * This function right to read from input of task 2
     */

    public static void driveInput2(){
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();

        int m = in.nextInt();

        for(int i = 0; i < n + 1;++i){
            matrixIntegerGraph.addVertex(i);

        }

        ArrayList<Edge<Integer>> listOfEdge = new ArrayList<>();

        for(int i = 0; i < m;++i ){

            int from = in.nextInt();

            int to = in.nextInt();

            int weight = in.nextInt();

            int bandwidth = in.nextInt();

            Edge<Integer> tempEdge = new Edge<>(weight,bandwidth);

            tempEdge.i = from;

            tempEdge.j = to;

            tempEdge.source = matrixIntegerGraph.findVertex(from);

            tempEdge.target = matrixIntegerGraph.findVertex(to);

            listOfEdge.add(tempEdge);
        }

        int source = in.nextInt();

        int target = in.nextInt();

        int minBandWidth = in.nextInt();

        //Adding only those that has bandwidth >= minBandWidth

        for(int i = 0; i < listOfEdge.size();++i){
            if(listOfEdge.get(i).bandwidth >= minBandWidth ){
                matrixIntegerGraph.addEdge(listOfEdge.get(i).source,listOfEdge.get(i).target,listOfEdge.get(i).weight,listOfEdge.get(i).bandwidth);
            }
        }

        Vertex<Integer> src = matrixIntegerGraph.findVertex(source);

        Vertex<Integer> trg = matrixIntegerGraph.findVertex(target);

        //Executing algorithm

        dijkstra(src,trg);

    }

    /**
     * This function right to read from input of task 1
     */

    public static void driveInput1(){

        Scanner in = new Scanner(System.in);

        String n = in .nextLine();

        input1(n);

        while ( in.hasNextLine()) {

            n = in .nextLine();

            input1(n);

        }
    }

    /**
     * That function recognize actions from input
     * @param n
     */

    public static void input1(String n) {


        String[] words = n.split(" ");

        switch (words[0]) {

            case ("ADD_VERTEX"):

                firstChar = words[1];

                boolean l = true;

                for(int i = 0; i < matrixGraph.listOfVertex.size();++i){

                    if(firstChar.equals(matrixGraph.listOfVertex.get(i).name))
                        l = false;

                }

                if(l)
                    matrixGraph.addVertex(words[1]);

                break;

            case ("ADD_EDGE"):

                int weight = Integer.parseInt(words[3]);

                Vertex < String > first = matrixGraph.findVertex(words[1]);

                Vertex < String > second = matrixGraph.findVertex(words[2]);

                matrixGraph.addEdge(first, second, weight, 0);

                break;

            case ("HAS_EDGE"):

                boolean t = matrixGraph.hasEdge(matrixGraph.findVertex(words[1]), matrixGraph.findVertex(words[2]));

                if (t)
                    System.out.println("TRUE");

                else
                    System.out.println("FALSE");

                break;

            case ("REMOVE_EDGE"):

                matrixGraph.removeEdge(matrixGraph.findEdge(words[1], words[2]));

                break;

            case ("REMOVE_VERTEX"):

                matrixGraph.removeVertex(matrixGraph.findVertex(words[1]));

                break;

            case ("IS_ACYCLIC"):

                ArrayList < Vertex < String >> visited = new ArrayList < > ();

                Stack < Vertex < String >> stack = new Stack < > ();

                boolean result = matrixGraph.isAcyclic(stack);

                if (result)
                    System.out.println("ACYCLIC");

                 else {

                    int index = -1;

                    for (int i = 0; i <stack.size(); ++i) {

                        if (stack.get(i).equals(matrixGraph.closer)) {

                            index = i;

                            break;
                        }

                    }

                    int sumWeight = 0;

                    for (int i = index; i < stack.size() - 1; i++) {

                        if(index!=stack.size() - 1)
                            sumWeight += matrixGraph.findEdge(stack.get(i).name, stack.get(i + 1).name).weight;

                    }

                    sumWeight+=matrixGraph.findEdge(stack.get(stack.size() - 1).name, stack.get(index).name).weight;

                    System.out.print(sumWeight + " ");

                    for (int i = index; i <stack.size(); i++)
                        System.out.print(stack.get(i).name + " ");

                    System.out.println();
                }

                break;

            case ("TRANSPOSE"):

                matrixGraph.transpose();

                break;

            default:

                System.out.println("There is no such program");

                break;
        }
    }
}

/**
 * Interface for graph
 * @param <V> generic type for Vertex
 * @param <E> generic type for Edge
 */

interface GraphADT < V, E > {
    Vertex < V > addVertex(V value);

    Vertex < V > removeVertex(Vertex < V > vertex);

    Edge < E > addEdge(Vertex < V > from, Vertex < V > to, E weight, E bandwidth);

    Edge < E > removeEdge(Edge < E > edge);

    ArrayList < Edge < E >> edgesFrom(Vertex < V > vertex);

    ArrayList < Edge < E >> edgesTo(Vertex < V > vertex);

    Vertex < V > findVertex(V value);

    Edge < E > findEdge(V from_value, V to_value);

    boolean hasEdge(Vertex < V > v, Vertex < V > u);

}

/**
 * Class edge
 * @param <T> generic type for almost all features of edge
 */

class Edge < T > {

    T weight;

    T bandwidth;

    boolean isEmpty;

    //Coordinates in adjacency matrix

    int i;

    int j;

    //Coordinates in adjacency matrix expressed by Vertex

    Vertex source;

    Vertex target;

    //Default constructor

    Edge() {
        this.isEmpty = true;
    }

    //Copy constructor

    public Edge(Edge < T > obj) {
        this.weight = obj.weight;
        this.bandwidth = obj.bandwidth;
        this.isEmpty = false;
    }

    //Conversion constructor

    Edge(T weight, T bandwidth) {

        this.weight = weight;

        this.bandwidth = bandwidth;

        this.isEmpty = false;
    }

}

/**
 * Class vertex
 * @param <T> generic type for almost all features of vertex
 */

class Vertex < T > {

    T name;

    //Conversion constructor

    Vertex(T name) {

        this.name = name;

    }

    //Copy constructor

    Vertex(Vertex < T > obj) {

        this.name = obj.name;
    }
}

/**
 * Class AdjacencyMatrixGraph
 * @param <V> generic type for vertex
 * @param <E> generic type for edge
 */

class AdjacencyMatrixGraph < V, E > implements GraphADT < V, E > {

    ArrayList < ArrayList < Edge < E >>> adjacencyMatrix;

    ArrayList < Vertex < V >> listOfVertex;

    Stack < Vertex < V >> visitedVertices;

    Vertex < V > closer;

    //Default constructor

    AdjacencyMatrixGraph() {

        adjacencyMatrix = new ArrayList < > ();

        listOfVertex = new ArrayList < > ();

    }

    //To determine the position in the matrix

    public int indexOf(Vertex < V > vertex) {

        if (vertex != null) {

            for (int i = 0; i < listOfVertex.size(); i++) {

                if (vertex.name.equals(listOfVertex.get(i).name))
                    return i;
            }

        }

        return -1;
    }

    @Override

    //Adding vertex to the list of vertex and matrix by creating new row and column

    public Vertex < V > addVertex(V value) {

        Vertex < V > temp = new Vertex < V > (value);

        listOfVertex.add(temp);

        ArrayList < Edge < E >> newRow = new ArrayList < > ();
        for (int i = 0; i < listOfVertex.size(); ++i) {
            Edge < E > empty = new Edge < > ();
            newRow.add(empty);
        }
        adjacencyMatrix.add(newRow);
        for (int i = 0; i < listOfVertex.size() - 1; ++i) {
            Edge < E > empty = new Edge < > ();
            adjacencyMatrix.get(i).add(empty);
        }

        return temp;
    }

    @Override

    //Removing vertex from all staff that contain this vertex

    public Vertex < V > removeVertex(Vertex < V > vertex) {

        int x = indexOf(vertex);

        adjacencyMatrix.remove(x);

        for (int i = 0; i < listOfVertex.size() - 1; ++i)
            adjacencyMatrix.get(i).remove(x);

        listOfVertex.remove(vertex);

        return vertex;
    }

    @Override

    //Adding edge to the matrix

    public Edge < E > addEdge(Vertex < V > from, Vertex < V > to, E weight, E bandwidth) {
        int x = indexOf(from);
        int y = indexOf(to);
        if (x != -1 && y != -1) {
            Edge < E > edge = adjacencyMatrix.get(x).get(y);
            edge.isEmpty = false;
            edge.weight = weight;
            edge.bandwidth = bandwidth;
            return edge;
        }
        return null;
    }

    @Override

    //Removing edge

    public Edge < E > removeEdge(Edge < E > edge) {

        edge.isEmpty = true;

        edge.weight = null;

        return edge;
    }

    @Override

    //Returning the list of outgoing edges

    public ArrayList < Edge < E >> edgesFrom(Vertex < V > vertex) {

        int x = indexOf(vertex);

        if (x == -1)
            return null;

        ArrayList < Edge < E >> listOfSource = new ArrayList < > ();

        for (int i = 0; i < listOfVertex.size(); ++i) {

            if (!adjacencyMatrix.get(x).get(i).isEmpty)

                listOfSource.add(adjacencyMatrix.get(x).get(i));
        }

        return listOfSource;
    }

    @Override

    //Returning the list of incoming edges

    public ArrayList < Edge < E >> edgesTo(Vertex < V > vertex) {

        int x = indexOf(vertex);

        if (x == -1)
            return null;

        ArrayList < Edge < E >> listOfSource = new ArrayList < > ();

        for (int i = 0; i < listOfVertex.size(); ++i) {

            if (!adjacencyMatrix.get(i).get(x).isEmpty)
                listOfSource.add(adjacencyMatrix.get(i).get(x));

        }

        return listOfSource;
    }

    @Override

    //Finding vertex by his value

    public Vertex < V > findVertex(V value) {

        for (int i = 0; i < listOfVertex.size(); ++i) {

            if (listOfVertex.get(i).name.equals(value)) {

                return listOfVertex.get(i);
            }
        }
        return null;
    }

    @Override

    //Finding edge by source and target

    public Edge < E > findEdge(V from_value, V to_value) {

        Vertex < V > ver1 = findVertex(from_value);

        Vertex < V > ver2 = findVertex(to_value);

        if (ver1 == null || ver2 == null)
            return null;
        int x = indexOf(ver1);

        int y = indexOf(ver2);

        if (x == -1 || y == -1)
            return null;

        return adjacencyMatrix.get(x).get(y);
    }

    @Override

    //Return boolean value that provide of existing edge

    public boolean hasEdge(Vertex < V > v, Vertex < V > u) {

        if (v == null || u == null)
            return false;

        int x = indexOf(v);

        int y = indexOf(u);

        if (x == -1 || y == -1)
            return false;


        if (adjacencyMatrix.get(x).get(y).isEmpty || adjacencyMatrix.get(x).get(y).weight == null)
            return false;


        return true;
    }

    //Function that prints matrix

    public ArrayList < ArrayList < Edge < E >>> printMatrix() {

        for (int i = 0; i < listOfVertex.size(); ++i) {

            for (int j = 0; j < listOfVertex.size(); ++j) {

                if (!adjacencyMatrix.get(i).get(j).isEmpty)

                    System.out.print(adjacencyMatrix.get(i).get(j).weight + " ");

                 else

                    System.out.print("- ");

            }

            System.out.println();

        }

        return adjacencyMatrix;
    }

    //Executing DFS for each node to check acyclicity of this graph

    public boolean isAcyclic(Stack < Vertex < V >> stack){

        Vertex<V> result;

        boolean t = true;

        for(Vertex <V> vert: listOfVertex ){

            stack.clear();

            ArrayList<Vertex<V>> newList = new ArrayList<>();

            result = isAcyclicObject(vert,newList,stack);

            if(result != null){

                closer = result;

                return false;

            }

        }
        return true;
    }

    //Implementation of dfs that traverse over this graph

    //If stack will remain non-empty therefore we have the cycle

    public Vertex < V > isAcyclicObject(Vertex < V > vertex, ArrayList < Vertex < V >> visited, Stack < Vertex < V >> stack) {

        visited.add(vertex);

        stack.push(vertex);

        for (Edge < E > edge: edgesFrom(vertex)) {

            Vertex < V > to;

            for (int i = 0; i < listOfVertex.size(); i++)

                for (int j = 0; j < listOfVertex.size(); j++)

                    if (edge.equals(adjacencyMatrix.get(i).get(j)) && edge.weight != null ) {

                        to = listOfVertex.get(j);

                        boolean temp = false;

                        for (Vertex < V > vert: visited)

                            if (to.name == vert.name) {

                                temp = true;

                                break;
                            }

                        if (!temp) {

                            Vertex < V > result = isAcyclicObject(to, visited, stack);

                            if (result != null)
                                return result;

                        }
                        else

                            for (Vertex < V > ver: stack)

                                if (to.name == ver.name) {

                                    visitedVertices = stack;

                                    return to;
                                }
                    }
        }

        stack.pop();

        return null;
    }

    //Transposing the matrix

    public void transpose() {

        for (int i = 0; i < listOfVertex.size(); ++i) {

            for (int j = i + 1; j < listOfVertex.size(); ++j) {

                Edge < E > temp1 = new Edge < E > (adjacencyMatrix.get(i).get(j));

                Edge < E > temp2 = new Edge < E > (adjacencyMatrix.get(j).get(i));

                adjacencyMatrix.get(i).set(j, temp2);

                adjacencyMatrix.get(j).set(i, temp1);

            }
        }
    }


}