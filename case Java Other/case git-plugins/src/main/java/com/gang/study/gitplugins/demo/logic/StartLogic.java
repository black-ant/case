package com.gang.study.gitplugins.demo.logic;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * @Classname StartLogic
 * @Description TODO
 * @Date 2020/7/2 22:25
 * @Created by zengzg
 */
@Component
public class StartLogic implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String remotePath = "https://github.com/black-ant/mydoc.git";//远程库路径
    public String localPath = "D:\\file\\test";//下载已有仓库到本地路径
    public String initPath = "D:\\file\\test";//本地路径新建

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("------> git logic <-------");
        testClone();
    }

    /**
     * 克隆远程库
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public void testClone() throws IOException, GitAPIException {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("username", "password");

        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();

        Git git = cloneCommand.setURI(remotePath) //设置远程URI
                .setBranch("master") //设置clone下来的分支
                .setDirectory(new File(localPath)) //设置下载存放路径
                .setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                .call();

        System.out.print(git.tag());
    }

    /**
     * 本地新建仓库
     */
    public void testCreate() throws IOException {
        //本地新建仓库地址
        Repository newRepo = FileRepositoryBuilder.create(new File(initPath + "/.git"));
        newRepo.create();
    }

    /**
     * 本地仓库新增文件
     */
    public void testAdd() throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile.txt");
        myfile.createNewFile();
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));

        //添加文件
        git.add().addFilepattern("myfile").call();
    }

    /**
     * 本地提交代码
     */
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        //提交代码
        git.commit().setMessage("test jGit").call();
    }


    /**
     * 拉取远程仓库内容到本地
     */
    public void testPull() throws IOException, GitAPIException {

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("username", "password");
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        git.pull().setRemoteBranchName("master").
                setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }

    /**
     * push本地代码到远程仓库地址
     */
    public void testPush() throws IOException, JGitInternalException,
            GitAPIException {

        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
                UsernamePasswordCredentialsProvider("username", "password");
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        git.push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }
}
