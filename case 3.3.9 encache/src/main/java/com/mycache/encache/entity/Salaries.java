package com.mycache.encache.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author 10169
 * @Description TODO
 * @Date 2019/3/9 21:53
 * @Version 1.0
 **/
@Entity
public class Salaries {
    @Id
    @Column(name = "empno")
    private Integer empno;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "fromdate")
    private Date fromdate;
    @Column(name = "todate")
    private Date todate;

    public Salaries() {
    }

    public Salaries(Integer empno, Integer salary, Date fromdate, Date todate) {
        this.empno = empno;
        this.salary = salary;
        this.fromdate = fromdate;
        this.todate = todate;
    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Date getFromdate() {
        return fromdate;
    }

    public void setFromdate(Date fromdate) {
        this.fromdate = fromdate;
    }

    public Date getTodate() {
        return todate;
    }

    public void setTodate(Date todate) {
        this.todate = todate;
    }
}
