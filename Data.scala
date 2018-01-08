package avroschema

import org.apache.avro.Schema

object Data {
  sealed abstract trait AsSchema {
    val schema: String
    def getSchema(): Schema = {
      return Schema.parse(schema)
    }
  }
  sealed trait AsTable {
  }

  case class DataA(id:Int,name:String) extends AsSchema with AsTable {
    val schema:String =
      """
        |{
        |    "type" : "record",
        |    "name" : "userInfo",
        |    "namespace" : "my.example",
        |    "fields" : [
        |     {"name" : "id", "type" : "int"},
        |     {"name" : "name", "type" : "string"},
        |     {"name" : "sub_id", "type" : "string"}
        |     ]
        |}
        |
      """.stripMargin
  }
  case class DataB(id:Int,name:String) extends AsSchema with AsTable {
    val schema:String =
      """
        |{
        |    "type" : "record",
        |    "name" : "subInfo",
        |    "namespace" : "my.example",
        |    "fields" : [
        |     {"name" : "id", "type" : "int"},
        |     {"name" : "name", "type" : "string"}
        |    ]
        |}
        |
      """.stripMargin
  }
}
