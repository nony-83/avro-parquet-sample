name := "parquet-sample"

version := "0.1"

scalaVersion := "2.11.11"

lazy val parquetversion = "1.9.0"
libraryDependencies += "org.apache.parquet" % "parquet-hadoop-bundle" % parquetversion
libraryDependencies += "org.apache.parquet" % "parquet-avro" % parquetversion
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "2.7.5"
libraryDependencies += "org.apache.hadoop" % "hadoop-hdfs" % "2.7.5"
