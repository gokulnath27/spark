import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.functions;

public class anamolyDetection{
    public static void main(String[] args) {



        SparkSession spark = SparkSession
                .builder()
                .appName("JavaStructured")
                .master("local")
                .getOrCreate();

        StructType userSchema = new StructType().add("timestamp", "Timestamp").add("name", "String");
        Dataset<Row> words = spark
                .readStream()

                .schema(userSchema)
                .csv("/Users/gokul-pt1831/Downloads/test_folder");
        words.printSchema();

        Dataset<Row> windowedCounts = words.groupBy(
                functions.window(words.col("timestamp"), "10 minutes", "5 minutes"),
                words.col("name")
        ).count();

    }

}