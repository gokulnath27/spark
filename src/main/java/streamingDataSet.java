import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.*;
import org.apache.spark.sql.streaming.DataStreamWriter;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;


public class streamingDataSet {
    public static void main(String[] args) throws StreamingQueryException {

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaStructuredNetworkWordCount")
                .master("local")
                .getOrCreate();
        Dataset<Row> socketDF = spark
                .readStream()
                .format("socket")
                .option("host", "localhost")
                .option("port", 9999)
                .load();
        socketDF.isStreaming();
        socketDF.printSchema();
        StructType userSchema = new StructType().add("timestamp", "Timestamp").add("name", "String");
        Dataset<Row> csvDF = spark
                .readStream()

                .schema(userSchema)
                .csv("/Users/gokul-pt1831/Downloads/test_folder");
        csvDF.printSchema();


        StreamingQuery query = csvDF.writeStream()

                .format("console")
                .start();
        query.awaitTermination();







    }
}
