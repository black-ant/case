package com.gang.study.stream.stram;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StramApplicationTests {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<String> listCollect;
    private Set<String> setCollect;
    private Map<String, String> mapCollect;

    @Before
    public void dateInit() {
        listCollect = Arrays.asList("AAA", "BBBB", "CCC", "DDD", "GGGG", "FFFF");
    }

    @Test
    public void contextLoads() {


        CreateStream createStream = new CreateStream();
//        createStream.collectToStream(listCollect);

        ExchangeSteam exchangeSteam = new ExchangeSteam();
        exchangeSteam.exchangeToArray(listCollect);
//        exchangeSteam.exchangeToSet(listCollect);

        BaseStream baseStream = new BaseStream();
//        baseStream.baseFunction("1");
//        baseStream.doubleColon(listCollect);
    }

    @Test
    public void givenTwoCollections_whenStreamedInParallel_thenCheckOutputDifferent() {
        List<String> list = Arrays.asList("B", "A", "C", "D", "F");
        Set<String> set = new TreeSet<>(list);

        Object[] listOutput = list.stream().parallel().toArray();
        Object[] setOutput = set.stream().parallel().toArray();

        Assert.assertEquals("[B, A, C, D, F]", Arrays.toString(listOutput));
        Assert.assertEquals("[A, B, C, D, F]", Arrays.toString(setOutput));
    }


    @Test
    public void test() {
        List<String> list = Arrays.asList("B", "A", "C", "D", "F");
        list.stream().parallel().forEachOrdered(e -> logger.info(e));
    }

    @Test
    public void givenSameCollection_whenStreamCollected_checkOutput() {
        List<String> list = Arrays.asList("B", "A", "C", "D", "F");

        List<String> collectionList = list.stream().parallel().collect(Collectors.toList());
        Set<String> collectionSet = list.stream().parallel()
                .collect(Collectors.toCollection(TreeSet::new));

        Assert.assertEquals("[B, A, C, D, F]", collectionList.toString());
        Assert.assertEquals("[A, B, C, D, F]", collectionSet.toString());
    }

    @Test
    public void givenList_whenCollectedtoLinkedHashMap_thenCheckOrderMaintained() {
        List<String> list = Arrays.asList("A", "BB", "CCC");

        Map<String, Integer> linkedHashMap = list.stream().collect(Collectors.toMap(
                Function.identity(),
                String::length,
                (u, v) -> u,
                LinkedHashMap::new
        ));

        Object[] keySet = linkedHashMap.keySet().toArray();

        Assert.assertEquals("[A, BB, CCC]", Arrays.toString(keySet));
    }

}
