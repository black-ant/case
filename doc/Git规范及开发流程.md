# Git 规范及开发流程

[TOC]



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

#### 添加 ignore 文件

```

```



### 附录一 ： 常用命令 

``` java
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
 |-> 拉取远程分支到本地指定分支 , 不合并 --- git fetch origin <origin>:<default> 
 |-> 删除远程分支 --- git push origin --delete <branchname>
 |-> 设置别名 --- git config --global A B
 |-> 拉取远程分支 --- git checkout -b 本地分支名 origin/远程分支名
 |-> 回退到指定的分支版本 --- git reset --hard 84cc59c7d9adbe44e0097197ce7e4796b0c73f21
 |-> 提交不生成记录 --- git commit --amend
 |-> 回退到上一个版本 --- git reset --hard HEAD^
 |-> 回退到上上版本 ---  git reset --hard HEAD^^ 
 |-> 回退到指定次数版本 --- git reset --hard HEAD~20
    git reset --hard 9576da5eda90cb3051b4574d0239d170f3939690
 |-> 解除与远程的关系 --- git remote remove gitlab
 |-> 添加额外的远程仓库 --- git remote add gitlab http://gitlab.paraview.cn/zengzg-example/case.git
 |-> 修改用户名 --- git config user.name 你的目标用户名；
     	-> git config user.name black-ant
     	-> git config user.name 曾志刚
 |-> 修改用户邮箱 --- git config user.email 你的目标邮箱名；
     	-> git config user.email 1016930479@qq.com
     	-> git config user.email zengzg@paraview.cn
 |-> 修改全局用户名 --- git config  --global user.name 你的目标用户名；
 |-> 修改全局用户邮箱 --- git config  --global user.email 你的目标邮箱名;
 |-> 修改当前项目用户名 --- git config user.name 你的目标用户名;
 |-> 合并多个commit --- git rebase -i HEAD~1  (改为squash)
 |-> 提交到远程分支 --- git push origin 本地:远程
     	-> git push -u origin ldap_search:ldap_search
     ext   -> git push -u origin hisense_cic:hisense_cic
 	server	-> git push -u origin master:Hisense
    feberation -> git push -u origin zhou_wsfederation:zhou_wsfederation   
    feberation server ->   git push -u origin federation:ws-federation
 |-> 强制提交 --- git push -f origin master
 |-> 查看远程仓库地址 --- git remote -v
 |-> 异常指定文件的版本管理 --- git rm [location]       
 // Tag           
 |-> 添加 tag --- git tag v0.1-loginok
        ->  git tag -a v1.0 -m "basic"
 |-> 添加 tag --- git tag -a v0.1 -m "loginok"
 |-> 把 tag 推送到远程 --- git push origin v1.0
 |-> 切换到 tag --- git checkout [tagname]
> 还原
 |->  测试所有本地修改 --- git checkout .
 |->  把所有的修改保存到暂存区 --- git stash
 |->  显示缓冲区改变 --- git stash show
 |->  显示缓存列表 --- git stash list
 |->  获取缓冲区的修改 --- git stash pop
> 查看
 |-> 列出本地分支   ---- git branch
 |-> 列出所有的本地分支  --- git branch -a
 |-> 查看分支最后一次提交 --- git branch -v
 |-> 查看所有的跟踪分支 --- git branch -vv 
 |-> 查看未合并分支 --- git branch --no-merge
 |-> 查看已合并分支 --- git branch --merge
 |-> 移除分支 --- git branch -d dev    
 |-> 查看当前状态 --- git status
 |-> 查看历史记录 --- git log
 |-> 查看时间点后log --- git log --since="2019-06-18" 
 |-> 查看时间点前log --- git log --before=
 |-> 获取时间点之后的commit --- git rev-list -n 2  --since=
 	// 2 -> 返回多少
 |-> 查看当前分支历史记录 --- gitk
 |-> 查看指定分支历史记录 --- gitk <branchname>
 |-> 查看远程版本库地址 --- git remote -v
 |-> 查看远程版本库信息 --- git remote show origin 
 |-> 查看指定远程版本库信息 --- git remote show <remote> 
 |-> 查看暂存区版本 --- git stash list
 |-> 查看当前config 配置 --- git config --list
 |-> 编辑config --- git config -e 
 |-> 查看提交树结构 --- git log --oneline --graph --decorate --all
 |-> 查看索引树结构 --- 
 	// Tag  
 |-> 查看 tag --- git tag
    // File
 |-> 查看 File 管理 --- git ls-files  
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



### 附录三  :  问题及解决

#### git ignore 添加后无效

```
> git root 路径下添加 .ignore 文件
> 如果添加后无效 : 可能因为git 旧索引对文件进行了索引 
	- 去掉 缓存  == git rm -r --cached .
	- 继续 add commit 查看效果
	
	
```

#### refusing to merge unrelated histories

```
git pull origin master --allow-unrelated-histories
```

### Received HTTP code 503 from proxy after CONNECT

```
> git config --global http.sslVerify "false"

> 注意环境变量
export
```



### 附录四 : Git 使用代理

```java
// 使用代理
// 注意 , 这里的 8118 是配置Linux proxy 时设置的
git config --global -l

git config --global http.https://github.com.proxy https://127.0.0.1:8118
git config --global https.https://github.com.proxy https://127.0.0.1:8118
git config --global http.https://github.com.proxy socks5://127.0.0.1:1086
git config --global https.https://github.com.proxy socks5://127.0.0.1:1086


git config --global http.proxy http://127.0.0.1:1080
git config --global https.proxy https://127.0.0.1:1080
git config --global http.proxy socks5://127.0.0.1:1080
git config --global https.proxy socks5://127.0.0.1:1080

// ->>>
git config http.proxy http://127.0.0.1:1080
git config https.proxy https://127.0.0.1:1080
    
git config --global --unset http.proxy
git config --global --unset https.proxy
git config --global --unset http.https://github.com.proxy
git config --global --unset https.https://github.com.proxy
```

### 附录五 : Git Config Linux 位置

```
/root/.gitconfig

```

### 附录六 : Git 修改提交过后得commit (慎用)

```java
> 配置 rebase 信息

> git rebase -i HEAD 
// git rebase -i HEAD~5

// 这里有几种命令
pick：保留该commit（缩写:p）
reword：保留该commit，但我需要修改该commit的注释（缩写:r）
edit：保留该commit, 但我要停下来修改该提交(不仅仅修改注释)（缩写:e）
squash：将该commit和前一个commit合并（缩写:s）
fixup：将该commit和前一个commit合并，但我不要保留该提交的注释信息（缩写:f）
exec：执行shell命令（缩写:x）
drop：我要丢弃该commit（缩写:d）

//修改名称前缀
pick c28a599 -> r c28a599    
    
// 修改后保存
git rebase --continue    
    
git rebase --abort 会放弃合并，回到rebase操作之前的状态，之前的提交的不会丢弃；
git rebase --skip 则会将引起冲突的commits丢弃掉（慎用！！）；
git rebase --continue 合并冲突，结合"git add 文件"命令一起用与修复冲突，提示开发者，一步一步地有没有解决冲突。    
```



参考文档 ：

[官方文档](https://git-scm.com/book/zh/v2)

<https://www.jianshu.com/p/4079284dd970>

[待学](https://blog.csdn.net/qq_16605855/article/details/78988400)