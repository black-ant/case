package com.gang.study.hibernate.demo.entity;

import javax.persistence.Column;

/**
 * @Classname Student
 * @Description TODO
 * @Date 2020/5/22 23:04
 * @Created by zengzg
 */
@Entity
@Table(name = Tables.STUDENT)
public class Student implements Serializable {

    /**
     * 持久化参数
     */
    private static final long serialVersionUID = -8447170587360305408L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private Integer age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
