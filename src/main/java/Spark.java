import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;


public class Spark {
    public static void main(String[] args) {

        String logFile = "/Users/gokul-pt1831/Downloads/sample.txt"; // Should be some file on your system
        SparkSession spark = SparkSession.builder().appName("Simple Application").master("local").getOrCreate();
        Dataset<String> logData = spark.read().textFile(logFile).cache();

        long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
        long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();

        System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs);

        spark.stop();

    }
}
