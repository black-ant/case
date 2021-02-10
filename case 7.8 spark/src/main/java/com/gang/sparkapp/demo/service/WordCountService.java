package com.gang.sparkapp.demo.service;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Classname WordCountService
 * @Description TODO
 * @Date 2021/2/10 15:50
 * @Created by zengzg
 */
@Component
public class WordCountService implements Serializable {
    private static final Pattern SPACE = Pattern.compile(" ");

    @Autowired
    private transient JavaSparkContext sc;

    public Map<String, Integer> run() {
        Map<String, Integer> result = new HashMap<>();
        JavaRDD<String> lines = sc.textFile("C:\\Users\\bd2\\Downloads\\blsmy.txt").cache();

        lines.map(new Function<String, String>() {
            @Override
            public String call(String s) throws Exception {
                System.out.println(s);
                return s;
            }
        });

        System.out.println(lines.count());

        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {


            @Override
            public Iterable<String> call(String s) throws Exception {
                return Arrays.asList(SPACE.split(s));
            }
        });

        JavaPairRDD<String, Integer> ones = words.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            public Tuple2<String, Integer> call(String s) {
                return new Tuple2<String, Integer>(s, 1);
            }
        });

        JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {

            private static final long serialVersionUID = 1L;

            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });

        List<Tuple2<String, Integer>> output = counts.collect();
        for (Tuple2<String, Integer> tuple : output) {
            result.put(tuple._1(), tuple._2());

        }

        return result;

    }
}
