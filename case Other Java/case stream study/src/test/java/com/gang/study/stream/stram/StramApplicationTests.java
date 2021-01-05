package com.gang.study.stream.stram;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StramApplicationTests {

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

}
