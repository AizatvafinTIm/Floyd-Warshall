package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class FloydWarshallPathFinder {
    private int N;
    private int[][] D;
    private int[][] R;

    public FloydWarshallPathFinder(int NumberOfVertices, int[][] EdgesLengths) {
        N = NumberOfVertices;
        D = EdgesLengths;
        R = null;
    }
    public int[][] AddingVertex(){
        N++;
        R = new int[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; ++j)
            {
                R[i][j] = j;
            }
        }
        int[][] newD = new int[N][N];
        for(int i = 0; i < N;++i){
            for (int j = 0; j < N; j++) {
                if( (i == N-1 || j == N-1)&& i!=j)
                    newD[i][j] = 9999;


                else if(i == N-1 && i == j){
                    newD[i][j] = 0;
                }
                else{
                    newD[i][j] = D[i][j];
                }
            }
        }
        D = newD;
        return D;
    }
    public int[][] AddingEdge(int from,int to,int weight){
        R = new int[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; ++j)
            {
                R[i][j] = j;
            }
        }
        for(int i = 0; i < N;++i){
            for(int j = 0; j < N;++j){
                if (D[i][from] + D[to][j] + weight < D[i][j])
                {
                    D[i][j] = D[i][from] + D[to][j] + weight;
                    R[i][j] = R[i][to];
                }
            }
        }
        return D;
    }
    public int[][] FindAllPaths() {
        R = new int[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; ++j)
            {
                R[i][j] = j;
            }
        }

        for (int k = 0; k < N; k++)
        {
            for (int v = 0; v < N; v++)
            {
                for (int u = 0; u < N; u++)
                {
                    if (D[u][k] + D[k][v] < D[u][v])
                    {
                        D[u][v] = D[u][k] + D[k][v];
                        R[u][v] = R[u][k];
                    }
                }
            }
        }

        return R;
    }

    public ArrayList<Integer> FindShortestPath(int start, int end) {
        if (R == null) {
            FindAllPaths();
        }

        ArrayList<Integer> Path = new ArrayList<>();

        while (start != end)
        {
            Path.add(start);

            start = R[start][end];
        }

        Path.add(end);

        return Path;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        int N = 4;
        int[][] D = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int t = 0; t < N; t++) {
                if (i == t) {
                    D[i][t] = 0;
                } else {
                    D[i][t] = 9999;
                }
            }
        }

        D[0][2] = 1;
        D[1][0] = 7;
        D[1][2] = 6;
        D[2][3] = 5;
        D[3][1] = 2;

        FloydWarshallPathFinder pathFinder = new FloydWarshallPathFinder(N, D);

        int start = 0;
        int end = 1;

       
        System.out.print("Path: {0}" + " -> " + Arrays.toString(pathFinder.FindShortestPath(0, 3).toArray()));
        System.out.println();
        pathFinder.AddingEdge(0,3,4);
        System.out.print("Path: {0}" + " -> " + Arrays.toString(pathFinder.FindShortestPath(0, 3).toArray()));

    }
}