import avroschema.Data
import org.apache.avro.Schema
import org.apache.avro.generic.GenericData
import org.apache.commons.io.IOUtils
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.parquet.avro.AvroParquetReader
import org.apache.parquet.avro.AvroParquetWriter
import org.apache.parquet.hadoop.ParquetReader
import org.apache.parquet.hadoop.ParquetWriter
import org.apache.parquet.hadoop.metadata.CompressionCodecName
import org.apache.parquet.schema.MessageType

object Main {

  def main(args:Array[String]): Unit = {
    val a = new avroschema.Data.DataA(1,"A")
    val subs: Array[avroschema.Data.DataB] = Array(
      new avroschema.Data.DataB(1,"b1"),
      new avroschema.Data.DataB(2,"b2")
    )

    val recordA:GenericData.Record = new GenericData.Record(a.getSchema())
    recordA.put("id",a.id)
    recordA.put("name",a.name)
    recordA.put("sub_id", "1,2")
    val storeA = new avroschema.DataStore("/avro/" + a.getClass().getSimpleName())
    storeA.writeToParquet(List(recordA),a.getSchema())

    val recordB:GenericData.Record = new GenericData.Record(subs(0).getSchema())
    val list = scala.collection.mutable.ListBuffer.empty[GenericData.Record]
    subs.foreach( b => {
      recordB.put("id",b.id)
      recordB.put("name",b.name)
      list += recordB
    })
    val storeB = new avroschema.DataStore("/avro/" + subs(0).getClass().getSimpleName())
    storeB.writeToParquet(list.toList, subs(0).getSchema())

    storeA.readFromParquet()
    storeB.readFromParquet()
  }

}
