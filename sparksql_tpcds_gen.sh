
################################################################################
## DO NOT NEED TO EDIT BELOW PARAS!!!
################################################################################
bench_root=$( cd $( dirname ${BASH_SOURCE[0]} ) && pwd )
bench_conf=${bench_root}/conf
hiveTestBench_root=${bench_root}/hive-testbench
sparksqlPerf_root=${bench_root}/spark-sql-perf
sparksql_conf="${bench_root}/conf/sparksql.conf"
sparksqlPerfJar="${sparksqlPerf_root}/target/scala-2.11/spark-sql-perf_2.11-0.5.1-SNAPSHOT.jar"
tpcds_gen_conf="${bench_conf}/tpcds_gen.scala"



spark-shell  --properties-file ${sparksql_conf} --jars ${sparksqlPerfJar} -I ${tpcds_gen_conf}
