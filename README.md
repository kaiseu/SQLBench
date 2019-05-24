# SQLBench
Tools for easier running SQL on Hive/Hadoop/Spark, e.g. TPC-DS, TPC-H

## Settings for hive-testbench
hive-testbench can be used for running TPC-H and TPC-DS queries with Map-Reduce, Spark, or Sparksql engine.

### Setting files introduction
All the setting files are located in ${hiveTestBench_root}/sample-queries-${BENCHMARK}/conf, where ${hiveTestBench_root} is the absolute path of hive-testbench on your machine, and ${BENCHMARK} is the benchmark type, which can be 'tpcds' or 'tpch' currently for TPC-DS and TPC-H benchmark respectively. Below is the explaination of configuration file under conf:
- ${Benchmark}.sql: Global settings for the benchark itself(e.g. tpcds or tpch), this is reserved for configuring the benchmark itself.
- ${ENGINE}.sql: Used to configure the specific engine used for the benchmark, the engine can be 'mr', 'spark', 'sparksql'. This is the global setting file for the all the benchmark queries, meaning that the settings in this file will be applied to all the queries.
- ${ENGINE}/populate.sql: This is the configuration for load the table. It's for mr and spark engine only as sparksql engine will use spark-sql-perf tool to generate the data and do the table loading.
- ${ENGINE}/q${i}.sql: Setting files for the specific query, e.g. q1.sql is the settings for query1 specifically. 
- sparksql.conf: Configuration file for SparkSQL engine, the settings in this file will be applied to all the queries.
- sparksql/q${i}.sql: if using SparkSQL engine, the query specific settings will be in this file, e.g. q1.sql is the settings for query1 specifically. As spark-sql command line can only accept one properties-file, this benchmark tool will combine the query specific settings with the engine settings(sparksql.conf) and benchmark settings(${Benchmark}.sql), and make all the configurations in the tthree files in to one and pass it to the spark-sql command line. It will do this automatically even though there's no query specific settings for a query as it will try to generate one. If there's some configuration conflict in the three files, the priority order is as below: q${i}.sql > sparksql.conf > ${Benchmark}.sql, that means the configuration in q${i}.sql will override the settings in sparksql.conf and ${Benchmark}.sql. 
