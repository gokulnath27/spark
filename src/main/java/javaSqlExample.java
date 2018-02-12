import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.*;
import static org.apache.spark.sql.functions.col;

public class javaSqlExample {
    public static void main(String[] args) {


        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.some.config.option", "some-value")
                .master("local")
                .getOrCreate();
        Dataset<Row> df = spark.read().json("/Users/gokul-pt1831/Downloads/spark-2.2.1-bin-hadoop2.7/examples/src/main/resources/people.json");
        df.show();
        df.printSchema();
    }
}