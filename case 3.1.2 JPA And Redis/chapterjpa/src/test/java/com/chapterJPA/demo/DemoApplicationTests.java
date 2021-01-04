package com.chapterJPA.demo;

import com.chapterJPA.demo.entity.Departments;
import com.chapterJPA.demo.entity.repository.DepartmentsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private DepartmentsRepository userRepository;

    @Test
    public void testAll() throws Exception {
        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
        Assert.assertEquals(10, userRepository.findAll().size());
        List<Departments> list =userRepository.findAll();
        for (Departments x : list){
            System.out.print(x.getDeptno());
        }
    }

    @Test
    public void test() throws Exception {


        // 测试findAll, 查询所有记录
        Assert.assertEquals(10, userRepository.findAll().size());

        // 测试findByName, 查询姓名为FFF的User
        Assert.assertEquals(60, userRepository.findByDeptname("aaaa").getDeptno());

        // 测试findUser, 查询姓名为FFF的User
        Assert.assertEquals(60, userRepository.findDepartments("aaaa").getDeptno());

        // 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
        Assert.assertEquals("aaaa", userRepository.findByDeptnameAndDeptno("aaaa", "d008").getDeptname());

        // 测试删除姓名为AAA的User
        userRepository.delete(userRepository.findByDeptname("aaaa"));

        // 测试findAll, 查询所有记录, 验证上面的删除是否成功
        Assert.assertEquals(9, userRepository.findAll().size());

    }

}
