package avroschema

import java.io.IOException

import org.apache.parquet.avro.{AvroParquetReader, AvroParquetWriter}
import org.apache.avro.generic.GenericData
import org.apache.avro.Schema
import org.apache.hadoop.fs.Path
import org.apache.hadoop.conf.Configuration
import org.apache.parquet.hadoop.metadata.CompressionCodecName

class DataStore(pathToHDFSFile:String) {
  val rootPath = "hdfs://localhost:9000"
  val path = new Path(pathToHDFSFile)
  val conf = new Configuration()
  val configPath = "/opt/hadoop/hadoop-2.7.5/etc/hadoop"
  conf.addResource(s"${configPath}/core-site.xml")
  conf.addResource(s"${configPath}/hdfs-site.xml")
  conf.set("fs.defaultFS", rootPath)
  println(conf.toString())

  @throws[IOException]
  def readFromParquet(): Unit = {
    try {
      val reader = AvroParquetReader.builder[GenericData.Record](path)
        .withConf(conf)
        .build
      try {
        val record = reader.read()
        if (record != null) {
          println(record)
        }
      } finally if (reader != null) reader.close()
    }
  }

  @throws[IOException]
  def writeToParquet(recordsToWrite: List[GenericData.Record], schema: Schema)  {
    //    https://github.com/MaxNevermind/Hadoop-snippets/blob/master/src/main/java/org/maxkons/hadoop_snippets/parquet/ParquetReaderWriterWithAvro.java

    try {
      val writer = AvroParquetWriter.builder[GenericData.Record](path)
        .withSchema(schema)
        .withConf(conf)
        .withCompressionCodec(CompressionCodecName.SNAPPY)
        .build
      try {
        for (record <- recordsToWrite) {
          writer.write(record)
        }
      } finally if (writer != null) writer.close()
    }
  }

}
