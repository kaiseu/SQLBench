import com.databricks.spark.sql.perf.tpcds.TPCDSTables

// scaleFactor defines the size of the dataset to generate (in GB).
val scaleFactor = "1000"
// valid spark format like parquet "parquet".
val format = "parquet"
// int number, how many dsdgen partitions to run
val numPartitions = 100
// root directory of location to create data in.
val rootDir = s"/tmp/tpcds-generate/${scaleFactor}" 
// location of dsdgen
val dsdgenDir = "/tmp/tpcds-kit/tools"
// schemaType can be "partitioned" or "flat"
val schemaType = "partitioned"
// name of database to create.
val databaseName = s"tpcds_${schemaType}_${format}_${scaleFactor}" 

// RUN
val sqlContext = spark.sqlContext
val tables = new TPCDSTables(sqlContext,
    dsdgenDir = dsdgenDir, // location of dsdgen
    scaleFactor = scaleFactor,
    useDoubleForDecimal = false, // true to replace DecimalType with DoubleType
    useStringForDate = false) // true to replace DateType with StringType


tables.genData(
    location = rootDir,
    format = format,
    overwrite = true, // overwrite the data that is already there
    partitionTables = true, // create the partitioned fact tables 
    clusterByPartitionColumns = true, // shuffle to get partitions coalesced into single files. 
    filterOutNullPartitionValues = false, // true to filter out the partition with NULL key value
    tableFilter = "", // "" means generate all tables
    numPartitions = numPartitions) // how many dsdgen partitions to run - number of input tasks.

// Create the specified database
//sql(s"create database $databaseName")
// Create metastore tables in a specified database for your data.
// Once tables are created, the current database will be switched to the specified database.
tables.createExternalTables(rootDir, format, databaseName, overwrite = true, discoverPartitions = true)
// Or, if you want to create temporary tables
// tables.createTemporaryTables(location, format)

// For CBO only, gather statistics on all columns:
tables.analyzeTables(databaseName, analyzeColumns = true) 
System.exit(0)
