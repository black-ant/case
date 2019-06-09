# Git 规范及开发流程



```
version : 1.0
lastUpateTime : 2019-6-4
```



### 一  .   区域介绍

```sequence
workspace->stage: add
stage->repository: commit
repository->remote: push
remote->repository: fetch / clone
repository-->workspace :checkout
remote-->workspace:pull
```



> 简述 ： 
> stage 为缓存区 ，是临时库（用于存放提交的快照） 
> repository （history）为本地库 
> remote 为远程库 
> workspace 就是我们的工作区 



### 二  .  要点

#### fetch

- 取回远端更新 ，但是不对本地执行 merge 操作  

#### merge and rebase

```
- merge :  将获取到的远程仓库代码和本地代码进行合并
- rebase :  复位基底 ，会将2个分支融合为线性




```



#### pull

- 作用 ： pull = fetch + merge



 #### git rest

- 作用 ： 将一个分支的末端指向之前的 commit , 再下次垃圾回收后清除该commit 之后的commit
  - -mixed : 影响到暂存区和历史记录区
  - -soff : 只影响历史记录区
  - -hard : 影响工作区 ， 暂存区 ，和历史记录区

#### git checkout

- 作用 ： 将 HEAD 开启新的分支，并且更新工作目录

#### git revert 

- 作用 ： 同样是回退 ，但是该方法会创建新的commit , 从而实现保留之前的commit

#### git cherry -pick commitID

- 作用 ： 对个别 commit 进行合并 （首先 gitlog 获取提交的 commit id） 



#### git add

```java
// 作用 ：git add 用于将工作区的修改提交到缓冲区

> git add .
> git add [dir]
> git add [file]
```

### git commit

```java
// 作用 ： commit 用于将 缓冲区对象提交到本地仓库

> git commit -m [message]
> git commit [file1] -m [message]

//使用一次新的commit，替代上一次提交
> git commit --amend -m [message]


```



### 三 . 重点分析

#### 项目提交

```
> Git 保存的是不同时刻的快照 ，并不会保存文件的变化和差异

> Git 提交时 ， Git 会先计算每一个子目录的校验和 ，并且将其保存为一个树对象，随后创建提交对象，提交对象包含作者的部分信息 ，以及指向树对象的指针 。
  |-> 树对象 中重要的是tree 和 parent : tree 是自身的key , parent 是上一个提交的父对象的key
```



#### 分支处理

```
> master 分支
  |-> master 同样是一个分支 ，被默认创建
  
> HEAD 指针
  |-> HEAD 是一个指针 ，指向当前所在的本地分支

> 分支实现方式 ？
  |-> 分支通过创建一个可移动的动态指针来实现
  
> 分支的新建和合并
  |-> git brance [branchname] : 新建分支
  |-> git checkout [branchname] ： 切换到指定的分支
  |-> change something
  |-> git merge [branchname] ： 合并指定分支
  |-? Fast-forward : 当master 为当前分支的直接上游的时候 ，通过简单的指针移动来修改当前master 进度 
  |-> merge 后 ，因为master 指针已经指向了修改后的 ，所以可将之前的分支删除
  |-> 三方合并 ： 当master 和当前的分支处在两条线上时（1线 2线从 节点 A1 分开，而master 被指定到 A1 后 1 线的 A2 节点 ），会整合A1 ,A2 以及当前处在的2线的A3 节点的资源 ，组合为 A4节点，回归主线
  
> origin 分支
  |-> 和 master 一样 ，origin 并没有特殊含义 ，只是普遍被作为远程分支名
```



#### Git 变基

```java
Git 变基的关键是 merge 和 rebase 命令 ，我们假设有2条线
> merge : 将2条线最后的结果进行合并 ，形成新的commit
> rebase : 以当前分支为基准 ，将需要合并的分支作为补丁 依次 打入现有分支、

> 变基的核心在于 ： 基准 ， 补丁 ，逐个
> 用一个通俗的案例来说 ： 
  |-> 1 主线创建并且提交过 C0 - C1
  |-> 2 主线创建了分支,并且提交 C0 - C1 - C2 - C3  /  C0 - C1 -C4
    //  可以看到 2 者在 C1 处进行了分叉
  |-> merge : C3 + C4 = C5 
      // C0 - C1 - C2 - C3 - C5
      // C0 - C1 -C4 - C5
  |-> rebase : 依次打补丁
      // C0 - C1 - C2 - C3 - C4'
      // C0 - C1 - C4 - C2'- C3'

> 变基的陷阱
  |-> 变基有一个准则 ：不要对在你的仓库外有副本的分支执行变基
  	  // 当变基后的提交被其他客户端拉取修改后 ，会导致与现在的修改重复且混乱
```



### 附录一 ： 常用命令

``` 
> 创建版本库
 |-> 初始化 Git 仓库 --- git init
 |-> 克隆远程版本库 ---  git clone <url> 
 |-> 添加远程版本库 --- git remote add <remote> <url>
> 修改和提交
 |-> 查看状态 ---  git status 
 |-> 查看变更内容	 --- git diff 
 |-> 添加到stage          
 |-> 基于本地分支创建新分支 --- git branch <branchname>
 |-> 检出分支 --- git checkout <branchname>
 |-> 合并并提交 --- git merge <branchname>
 |-> 撤销合并 --- git checkout head
 |-> 解决冲突（可视化工具） --- git merge tool
 |-> 删除当前分支 --- git branch -d <branchname>
 |-> 更新远程分支更新 --- git fetch <remotename>
 |-> 删除远程分支 --- git push origin --delete <branchname>
 |-> 设置别名 --- git config --global A B
 |-> 
 |->  
 |-> 
 |-> 
 |->  
 |->  
> 查看
 |-> 列出本地分支   ---- git branch
 |-> 列出所有的本地分支  --- git branch -a
 |-> 查看分支最后一次提交 --- git branch -v
 |-> 查看所有的跟踪分支 --- git branch -vv 
 |-> 查看未合并分支 --- git branch --no-merge
 |-> 查看已合并分支 --- git branch --merge
 |-> 查看当前状态 --- git status
 |-> 查看历史记录 --- git log
 |-> 查看当前分支历史记录 --- gitk
 |-> 查看指定分支历史记录 --- gitk <branchname>
 |-> 查看远程版本库信息 --- git remote -v
 |-> 查看指定远程版本库信息 --- git remote show <remote> 
 |-> 
 |-> 
 |->  
 |-> 
 |-> 
 |->  
 |-> 
 |-> 
 |->  
 |-> 
 |-> 
 |->  
 |-> 
 |-> 
 |->  
 |-> 
 |-> 
 |->   
```

### 附录二  ： 补充知识点

```java
> git 协议 ： 
 |-> 本地协议（Local）
 |-> HTTP 协议 
 |-> SSH (Secure Shell) 协议
 |-> Git 协议    
```

参考文档 ：

[官方文档](https://git-scm.com/book/zh/v2)

<https://www.jianshu.com/p/4079284dd970>