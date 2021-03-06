
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.Arrays;


public class Streaming {

    public static void main(String[] args) throws StreamingQueryException {

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaStructured")
                .master("local")
                .getOrCreate();

        Dataset<Row> lines = spark
                .readStream()
                .format("socket")
                .option("host", "localhost")
                .option("port", 9999)
                .load();

// Split the lines into words
        Dataset<String> words = lines
                .as(Encoders.STRING())
                .flatMap((FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator(), Encoders.STRING());




        words.printSchema();

// Generate running word count
        Dataset<Row> wordCounts = words.groupBy("value").count();


        StreamingQuery query = wordCounts.writeStream()

                .outputMode("complete")
                .format("console")
                .start();




        query.awaitTermination();
    }
}
