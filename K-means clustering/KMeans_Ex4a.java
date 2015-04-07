/**
*
*/
import java.util.*;
public class KMeans_Ex4a{
    private static final int NUM_CLUSTERS        = 2;    // Total clusters.
    private static final int TOTAL_DATA          = 7;      // Total data points.
    private static final double SAMPLES[][]      = new double[][] {{1.0, 1.0}, 
                                                                {1.5, 2.0}, 
                                                                {3.0, 4.0}, 
                                                                {5.0, 7.0}, 
                                                                {3.5, 5.0}, 
                                                                {4.5, 5.0}, 
                                                                {3.5, 4.5}};
    private static ArrayList<Data> dataSet       = new ArrayList<Data>();
    private static ArrayList<Centroid> centroids = new ArrayList<Centroid>();
    private static void initialize(){
        System.out.println("Centroids initialized at: ");
        centroids.add(new Centroid(1.0, 1.0)); // lowest set.
        centroids.add(new Centroid(5.0, 7.0)); // highest set.
        System.out.println("     (" + centroids.get(0).X() + ", " + centroids.get(0).Y() + ") ");
        System.out.println("     (" + centroids.get(1).X() + ", " + centroids.get(1).Y() + ") ");
        System.out.print("\n");
    }
    private static void kMeanCluster(){
        final double bigNumber = Math.pow(10, 10);// some big number that's sure to be larger than our data range.
        double minimum         = bigNumber;       // The minimum value to beat. 
        double distance        = 0.0;             // The current minimum value.
        int sampleNumber       = 0;
        int cluster            = 0;
        boolean isStillMoving  = true;
        Data newData           = null;
        // Add in new data, one at a time, recalculating distances to centroids with each new one. 
        while(dataSet.size() < TOTAL_DATA){
            newData = new Data( SAMPLES[sampleNumber][0], SAMPLES[sampleNumber][1] );
            dataSet.add( newData );
            minimum = bigNumber;
            for(int i = 0; i < NUM_CLUSTERS; i++){
                distance = dist( newData, centroids.get(i) ); //euclidian distance
                if(distance < minimum){
                    minimum = distance;
                    cluster = i;
                }
            }
            newData.cluster(cluster);
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++){
                int totalX         = 0;
                int totalY         = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++){
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            sampleNumber++;
        }
        //keep shifting centroids until equilibrium occurs.
        while(isStillMoving){
            // calculate new centroids.
            for(int i = 0; i < NUM_CLUSTERS; i++){
                int totalX         = 0;
                int totalY         = 0;
                int totalInCluster = 0;
                for(int j = 0; j < dataSet.size(); j++){
                    if(dataSet.get(j).cluster() == i){
                        totalX += dataSet.get(j).X();
                        totalY += dataSet.get(j).Y();
                        totalInCluster++;
                    }
                }
                if(totalInCluster > 0){
                    centroids.get(i).X(totalX / totalInCluster);
                    centroids.get(i).Y(totalY / totalInCluster);
                }
            }
            // Assign all data to the new centroids
            isStillMoving = false;
            for(int i = 0; i < dataSet.size(); i++){
                Data tempData = dataSet.get(i);
                minimum       = bigNumber;
                for(int j = 0; j < NUM_CLUSTERS; j++){
                    distance = dist(tempData, centroids.get(j));
                    if(distance < minimum){
                        minimum = distance;
                        cluster = j;
                    }
                }
                tempData.cluster(cluster);
                if(tempData.cluster() != cluster){
                    tempData.cluster(cluster);
                    isStillMoving = true;
                }
            }
        }
    }
    private static double dist(Data d, Centroid c){
        return Math.sqrt(Math.pow((c.Y() - d.Y()), 2) + Math.pow((c.X() - d.X()), 2));
    }    
    public static void main( String[] args ){
        initialize();
        kMeanCluster();
        // Print out clustering results.
        for(int i = 0; i < NUM_CLUSTERS; i++){
            System.out.println("Cluster " + i + " includes:");
            for(int j = 0; j < TOTAL_DATA; j++){
                if(dataSet.get(j).cluster() == i){
                    System.out.println("     (" + dataSet.get(j).X() + ", " + dataSet.get(j).Y() + ")");
                }
            } 
            System.out.println();
        } 
        //centroit
        System.out.println("Centroids finalized at:");
        for(int i = 0; i < NUM_CLUSTERS; i++){
            System.out.println("     (" + centroids.get(i).X() + ", " + centroids.get(i).Y());
        }
        System.out.print("\n");
    }
}
