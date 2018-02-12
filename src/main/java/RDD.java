
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.sources.In;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.*;

public class RDD {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("RDDTesting").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("/Users/gokul-pt1831/Downloads/sample.txt");
        JavaRDD<Integer> lineLengths = lines.map(c -> c.length());
        int totalLength = lineLengths.reduce((a, b) -> a + b);

        JavaRDD<Integer> out = lines.map(new Function<String, Integer>() {
            public Integer call(String s){
                return s.length();
            }
        });
        JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(s, 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);

        Broadcast<int[]> broadcastVar = sc.broadcast(new int[] {1, 2, 3});

        int[] value = broadcastVar.getValue();
        int i = 0;
        while(i < value.length){
            System.out.println(value[i++]);


        }
    }
}
