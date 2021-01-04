package com.chapterJPA.demo.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Departments {
    private String deptno;
    private String deptname;

    @Id
    @Column(name = "deptno")
    public String getDeptno() {
        return deptno;
    }

    public void setDeptno(String deptno) {
        this.deptno = deptno;
    }

    @Basic
    @Column(name = "deptname")
    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departments that = (Departments) o;
        return Objects.equals(deptno, that.deptno) &&
                Objects.equals(deptname, that.deptname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(deptno, deptname);
    }
}
