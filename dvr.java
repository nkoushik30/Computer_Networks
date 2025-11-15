import java.util.*;

public class rrr {

    static final int MAX = 10;
    static final int INF = 9999;

    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        int nodes, i, j, k, count;
        int[][] distance = new int[MAX][MAX];
        int[][] via = new int[MAX][MAX];

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        nodes = sc.nextInt();

        System.out.printf("Enter the cost/delay adjacency matrix (%d for no link/infinite delay):\n", INF);
        for (i = 0; i < nodes; i++) {
            for (j = 0; j < nodes; j++) {
                distance[i][j] = sc.nextInt();
                if (i != j && distance[i][j] == 0) {
                    distance[i][j] = INF;
                }
                via[i][j] = j;
            }
        }

        // This is the core algorithm loop structure you asked about
        do {
            count = 0;
            for (i = 0; i < nodes; i++) {
                for (j = 0; j < nodes; j++) {
                    for (k = 0; k < nodes; k++) {
                        if (distance[i][j] > distance[i][k] + distance[k][j]) {
                            distance[i][j] = distance[i][k] + distance[k][j];
                            via[i][j] = k;
                            count++;
                        }
                    }
                }
            }
        } while (count != 0);

        // Display routing tables
        for (i = 0; i < nodes; i++) {
            System.out.printf("\nRouting table for node %d:\n", i);
            System.out.println("Destination\tNext Hop\tTotal Cost");
            for (j = 0; j < nodes; j++) {
                if (i == j) {
                    System.out.printf("%d\t\t-\t\t0\n", j);
                } else {
                    System.out.printf("%d\t\t%d\t\t%d\n", j, via[i][j], distance[i][j]);
                }
            }
        }
        
        sc.close();
    }
}
