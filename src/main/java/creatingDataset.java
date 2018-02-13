
import java.io.Serializable;
import java.util.Collections;
import java.util.Arrays;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.api.java.function.MapFunction;

public class creatingDataset implements Serializable {

    private String name;
    private int age;

    public void setName(String name){
        this.name = name;
    }

    public  void setAge(int age){
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.some.config.option", "some-value")
                .master("local")
                .getOrCreate();

        creatingDataset people = new creatingDataset();
        people.setAge(21);
        people.setName("gokul");
        Encoder<creatingDataset> encoder = Encoders.bean(creatingDataset.class);
        Dataset<creatingDataset> dataset = spark.createDataset(Collections.singletonList(people), encoder);
        dataset.show();
        Encoder<Integer> integerEncoder = Encoders.INT();
        Dataset<Integer> primitiveDS = spark.createDataset(Arrays.asList(1, 2, 3), integerEncoder);
        Dataset<Integer> transformedDS = primitiveDS.map(
                (MapFunction<Integer, Integer>) value -> value + 1,
                integerEncoder);
        transformedDS.show();
        String path = "/Users/gokul-pt1831/Downloads/spark-2.2.1-bin-hadoop2.7/examples/src/main/resources/people.json";
        Dataset<creatingDataset> peopleDS = spark.read().json(path).as(encoder);
        peopleDS.show();





    }
}
