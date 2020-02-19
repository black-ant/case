package com.gang.study.adtest.demo.to;

import lombok.Data;

/**
 * @Classname SearchTO
 * @Description TODO
 * @Date 2019/11/13 17:09
 * @Created by zengzg
 */
@Data
public class SearchTO<T, E> {

    protected PageTO<E> pageTO;

    protected T key;

    protected T parentId;

    protected Boolean fetchChild;

    protected String version;

    public SearchTO build(E page, E size) {
        PageTO<E> thispageTO = this.pageTO;
        if (null == pageTO) {
            thispageTO = new PageTO<>(page, size);
            this.pageTO = thispageTO;
        } else {
            this.pageTO.setPage(page);
            this.pageTO.setSize(size);
        }
        return this;
    }

    public E getSearchPage() {
        return pageTO == null ? null : pageTO.getPage();
    }

    public E getSearchSize() {
        return pageTO == null ? null : pageTO.getSize();
    }

    public <I> I getSearchInfo() {
        return null;
    }

    public <I> void setSearchInfo(I i) {
    }

}
