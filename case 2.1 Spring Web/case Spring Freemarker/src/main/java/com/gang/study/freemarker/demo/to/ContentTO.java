package com.gang.study.freemarker.demo.to;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author ant-black
 * @since 2020-05-10
 */
public class ContentTO {

    private static final long serialVersionUID = 1L;

    private String contentBody;

    private String contentCode;

    private String contentTitle;

    private String contentInfo;

    private String contentForeword;

    private String contentOrder;

    private String contentBodyType;

    private String contentStart;

    private String contentPath;

    private String contentFolder;

    private String contentHot;

    private String contentExt;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createDate;

    /**
     * ant_blog_tag
     */
    private String blogTag;

    /**
     * ant_blog_template
     */
    private String blogTemplate;

    /**
     * ant_blog_type
     */
    private String blogType;


    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getContentForeword() {
        return contentForeword;
    }

    public void setContentForeword(String contentForeword) {
        this.contentForeword = contentForeword;
    }

    public String getContentOrder() {
        return contentOrder;
    }

    public void setContentOrder(String contentOrder) {
        this.contentOrder = contentOrder;
    }

    public String getContentBodyType() {
        return contentBodyType;
    }

    public void setContentBodyType(String contentBodyType) {
        this.contentBodyType = contentBodyType;
    }

    public String getContentStart() {
        return contentStart;
    }

    public void setContentStart(String contentStart) {
        this.contentStart = contentStart;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContentFolder() {
        return contentFolder;
    }

    public void setContentFolder(String contentFolder) {
        this.contentFolder = contentFolder;
    }

    public String getContentHot() {
        return contentHot;
    }

    public void setContentHot(String contentHot) {
        this.contentHot = contentHot;
    }

    public String getContentExt() {
        return contentExt;
    }

    public void setContentExt(String contentExt) {
        this.contentExt = contentExt;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(String blogTag) {
        this.blogTag = blogTag;
    }

    public String getBlogTemplate() {
        return blogTemplate;
    }

    public void setBlogTemplate(String blogTemplate) {
        this.blogTemplate = blogTemplate;
    }

    public String getBlogType() {
        return blogType;
    }

    public void setBlogType(String blogType) {
        this.blogType = blogType;
    }
}
