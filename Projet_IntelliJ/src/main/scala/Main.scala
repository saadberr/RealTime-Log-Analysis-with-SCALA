import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.streaming
import org.apache.spark.sql.functions._
import org.apache.log4j._
import org.elasticsearch.spark.sql._

object Main {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().master("local[2]").appName("first")
      .config("spark.es.nodes", "localhost")
      .config("spark.es.port", "9200")
      .getOrCreate()
    Logger.getLogger("org").setLevel(Level.ERROR)

    //Data Structure
    val schema = StructType(List(
      StructField("Protocol", StringType, true),
      StructField("HTTP status", StringType, true),
      StructField("URL", StringType, true),
      StructField("Path", StringType, true),
      StructField("IP Adress", StringType, true)))

    //Create DataFrame
    val StreamDF = spark
      .read.option("delimiter", " ")
      .schema(schema)
      .csv("/home/hdsaad/Documents/simple/output")
    StreamDF.createOrReplaceTempView("SDF2")
    val outDF = spark.sql("select * from SDF2")


    //write DF to elasticSearch
    outDF.write
      .format("org.elasticsearch.spark.sql")
      .option("es.port", "9200")
      .option("es.nodes", "localhost")
      .mode("append")
      .save("logs_kibana/doc")

    //Write Stream to Console :
    // outDF.writeStream.format("console")
    // .outputMode("append")
    // .start()
    // .awaitTermination()

  }

}

