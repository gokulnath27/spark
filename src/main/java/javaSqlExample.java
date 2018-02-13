import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.*;
import static org.apache.spark.sql.functions.col;

public class javaSqlExample {
    public static void main(String[] args) throws AnalysisException {


        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.some.config.option", "some-value")
                .master("local")
                .getOrCreate();
        Dataset<Row> df = spark.read().json("/Users/gokul-pt1831/Downloads/spark-2.2.1-bin-hadoop2.7/examples/src/main/resources/people.json");
        df.show();
        df.printSchema();
        df.select("name").show();
        df.select(col("name"), col("age").plus(1)).show();
        df.filter(col("age").gt(21)).show();
        df.groupBy("age").count().show();
        df.createOrReplaceTempView("people");
        Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
        sqlDF.show();
        try {
            df.createGlobalTempView("people");
        } catch (AnalysisException e) {
            e.printStackTrace();
        }
        spark.sql("SELECT * FROM global_temp.people").show();
        spark.newSession().sql("SELECT * FROM global_temp.people").show();

    }
}