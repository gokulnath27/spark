import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;
public class streamingDataSet {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("JavaStructuredNetworkWordCount")
                .master("local")
                .getOrCreate();
        Dataset<Row> socketDF = spark
                .readStream()
                .format("socket")
                .option("host", "localhost")
                .option("port", 8080)
                .load();
        socketDF.isStreaming();
        socketDF.printSchema();
        StructType userSchema = new StructType().add("name", "string").add("age", "integer");
        Dataset<Row> csvDF = spark
                .readStream()
                .option("sep", ";")
                .schema(userSchema)
                .csv("/Users/gokul-pt1831/Downloads/sample_input.csv");
        csvDF.printSchema();

    }
}
