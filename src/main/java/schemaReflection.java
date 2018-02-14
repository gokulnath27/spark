import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;


public class schemaReflection {
    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.some.config.option", "some-value")
                .master("local")
                .getOrCreate();
        JavaRDD<creatingDataset> reflection = spark.read()
                                               .textFile("/Users/gokul-pt1831/Downloads/test.txt")
                                               .javaRDD()
                                               .map(line -> {
                                                  String[] parts = line.split(" ");
                                                  creatingDataset person = new creatingDataset();
                                                  person.setName(parts[0]);
                                                  person.setAge(Integer.parseInt(parts[1].trim()));
                                                  return person;
                                               });

        Dataset<Row> peopleDF = spark.createDataFrame(reflection, creatingDataset.class);
        peopleDF.createOrReplaceTempView("people");
        Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
        Encoder<String> stringEncoder = Encoders.STRING();
        Dataset<String> teenagerNamesByIndexDF = teenagersDF.map(
                (MapFunction<Row, String>) row -> "Name: " + row.getString(0),
                stringEncoder);
        teenagerNamesByIndexDF.show();
    }
}
