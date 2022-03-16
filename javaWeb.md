

# javaWeb

### 写在前面：依赖，错误，编码的一些解决办法

#### 1. 依赖

[Mvn仓库](https://mvnrepository.com/)

~~~xml
 <dependencies>

        <!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
        <!--Servlet依赖-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
            <scope>provided</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
        <!--jsp依赖-->
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>javax.servlet.jsp-api</artifactId>
            <version>2.3.3</version>
            <scope>provided</scope>
        </dependency>

        <!-- https://mvnrepository.com/artifact/javax.servlet.jsp.jstl/jstl-api -->
        <!--jstl表达式的依赖-->
        <dependency>
            <groupId>javax.servlet.jsp.jstl</groupId>
            <artifactId>jstl-api</artifactId>
            <version>1.2</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/taglibs/standard -->
        <!--standard标签库-->
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>
    </dependencies>
~~~

#### 2. 错误

使用maven部署的javaweb项目，在请求静态资源（图片，js，css...）的时候会被拦截，需要手动在web.xml中**激活Tomcat的defaultServlet来处理静态文件**,同时，在访问这些资源的时候建议使用${pageContext.request.contextPath}去获取路径

~~~jsp
<img src="${pageContext.request.contextPath}/img/500.jpg">
~~~

~~~xml
 <servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>*.jpg</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>*.png </url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>*.js</url-pattern>
  </servlet-mapping>
  <servlet-mapping>
    <servlet-name>default</servlet-name>
    <url-pattern>*.css</url-pattern>
  </servlet-mapping>
~~~

#### 3.web.xml最新

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0"
         metadata-complete="true">

</web-app>
~~~



## 1.基本概念

### 1.1静态web



- *. htm，这些都是网页的后缀，如果服务器上一直存在这些东西，我们就可以一直读取他。
- 逻辑图：![image-20220118135241917](D:\code\MarkdownTypora\notes\image-20220118135241917.png)
- 静态web的缺点：
  * web页面无法更新，所有用户看到的都是统一界面1
  * 通过JavaScript或者vbscript实现的动画是伪动态
  * 无法与数据库交互（数据无法次就画1，用户无法交互！）

### 1.2静态web

页面会动态展示，“web页面展示效果因人而异”。

![image-20220118140743120](D:\code\MarkdownTypora\notes\image-20220118140743120.png)

缺点：

- 假如服务的动态web资源程序出现错误，我们需要重新编写我们的后台程序，重新发布

优点:

- web页面可以更新，所有用户看到的都不是统一界面
- 可以实现数据库交互（数据持久化）

## 2.web服务器

### 2.1技术讲解

ASP

- 微软，国内最早流行的就是ASP
- 在html中其嵌入vb脚本，ASP+COM
- 在ASP中，基本上就只有一个页面，页面混乱，维护成本高
- 主要用于c#中    

php

- PHP开发速度快，跨平台，代码简单（中小型网站开发）
- 无法承载大访问量的情况（局限性）

JSP/Servlet

- sun公式主推B/S架构（B/S：浏览器和服务器，C/S：客户端和服务器）
- 基于 java语言开发
- 可以承载三高问题带来的影响（高并发，高性能，高可用）
- 语法类似ASP,提高了竞争力

......



### 2.2Tomcat

服务器是一种被动操作，用来处理用户的请求和给用户响应一些信息

**Tomcat**

![image-20220118145214454](D:\code\MarkdownTypora\notes\image-20220118145214454.png)

Tomcat是Apache 软件基金会（Apache Software Foundation）的Jakarta 项目中的一个核心项目，由[Apache](https://baike.baidu.com/item/Apache/6265)、Sun 和其他一些公司及个人共同开发而成。由于有了Sun 的参与和支持，最新的Servlet 和JSP 规范总是能在Tomcat 中得到体现，Tomcat 5支持最新的Servlet 2.4 和JSP 2.0 规范。因为Tomcat 技术先进、性能稳定，而且**免费**，因而深受Java 爱好者的喜爱并得到了部分软件开发商的认可，成为比较流行的Web 应用服务器。

Tomcat 服务器是一个免费的开放源代码的Web 应用服务器，属于轻量级应用[服务器](https://baike.baidu.com/item/服务器)，在中小型系统和并发访问用户不是很多的场合下被普遍使用，是开发和调试JSP 程序的首选。对于一个初学者来说，他是最合适的选择。

Tomcat 实际上运行JSP 页面和Servlet。Tomcat最新版本为10.0.14**。**

**Tomcat环境配置**

1. 新建两个环境变量：CATALINA_BASE和CATALINA_HOME，变量值都是安装Tomcat的位置
2. ![image-20220118213314151](D:\code\MarkdownTypora\notes\image-20220118213314151.png)
3. 在CLASSPATH中添加路径，注意要在原来的路径后面加上英文的 “  ;%CATALINA_HOME%\lib\servlet-api.jar;   "
4. ![image-20220118213518665](D:\code\MarkdownTypora\notes\image-20220118213518665.png)
5. 最后在Path环境变量中添加%CATALINA_HOME%\bin和%CATALINA_HOME%\lib即可
6. ![image-20220118213649616](D:\code\MarkdownTypora\notes\image-20220118213649616.png)







**$\textcolor{Red} {注意使用Tomcat10以上版本可能导致，Tomcat中的servlet-api和maven导入的servlet-api版本不一样，} $**

**$\textcolor{Red}{导致出现异常实例化Servlet类异常，可采用降级回到Tomcat8.5.73版本解决，或者手动导入jar包 } $**

[解决Tomcat乱码的问题](https://blog.csdn.net/amingccc/article/details/86503180)

**面试题**

谈一谈网站是如何进行访问的

1. 输入域名，回车

2. 检查本地C:\Windows\System32\drivers\etc\hosts配置文件有没有这个域名映射

   1. 有：直接返回对应的ip地址，在这个地址中有我们需要访问的web程序，可以直接访问

      ~~~xml
      127.0.0.1       localhost
      ~~~

   2. 没有：去DNS服务器找，找到就返回，找不到就返回没找到

      ![image-20220118151941499](D:\code\MarkdownTypora\notes\image-20220118151941499.png)

**网站结构**

~~~java
--webapps ：Tomcat服务器的web目录
    -ROOT
    - name  ：网站的目录名
    	-WEB-INF
    		-classes ：java程序
    		-lib: web应用所依赖的jar包
            -web.xml :网站的配置文件
        -index.xml  默认的首页
        -static
            -css
            -js
            -img  
            -...
~~~

### 2.3Maven配置

idea已经集成了了maven，因此只需要简单的配置一些本地环境

- 本地配置，如果使用Maven远程下载有些jar包下载很慢！

- [Maven官网下载压缩包](https://maven.apache.org/download.cgi)

- 解压，然后到mave文件中的conf中有一个setting.xml文件，复制到你想要下载的本地仓库位置！

- ![image-20220118191103558](D:\code\MarkdownTypora\notes\image-20220118191103558.png)

- 这个位置就是你的本地仓库位置

- 找到mirror，添加阿里镜像[阿里文档](https://help.aliyun.com/document_detail/102512.html)

- ![image-20220118191242480](D:\code\MarkdownTypora\notes\image-20220118191242480.png)

- 创建Maven项目的时候，选择本地仓库

- ![image-20220118191542790](D:\code\MarkdownTypora\notes\image-20220118191542790.png)

   **在maven项目中，可能会遇到项目资源无法识别，无法被导出的情况，因此需要手动添加配置，以确保能被识别到**
  
  ~~~xml
  <build>
          <resources>
              <resource>
                  <directory>src/main/resources</directory>
                  <includes>
                      <include>**/*.properties</include>
                      <include>**/*.xml</include>
                  </includes>
                  <filtering>true</filtering>
              </resource>
              <resource>
                  <directory>src/main/java</directory>
                  <includes>
                      <include>**/*.properties</include>
                      <include>**/*.xml</include>
                  </includes>
                  <filtering>true</filtering>
              </resource>
          </resources>
      </build>
  ~~~
  
  
  
  

## 3.Servlet

### 3.1Servlet简介

Servlet接口Sun公司有两个默认的实现类：HttpServlet ，GenericServlet

- Servlet是sun公司开发动态web的一门技术

- sun公司在这些API中提供一个接口叫做;Servlet，如果想要开发一个Servlet程序只需要完成两个小步骤

  - 编写一个类，实现Servlet接口
  - 把开发好的java类部署到web服务器中

  **把实现了Servlet接口的陈或许叫做:Servlet**

### 3.2hello Servlet

1. 构建一个普通都Maven项目，删除里面的src目录，后续学习都在这个项目里面见Model，这个空的项目就是主项目

2. 关于Maven父子工程的理解

   父项目中会有：

   ~~~xml
      <modules>
           <module>Servlet01</module>
       </modules>
   ~~~

   子项目会有：

   ~~~xml
      <parent>
           <artifactId>Servlet-01</artifactId>
           <groupId>org.example</groupId>
           <version>1.0-SNAPSHOT</version>
       </parent>
   ~~~

   父项目中的java子项目可以直接使用

   ~~~ java
   son extends father
   ~~~

   

3. Maven环境优化

   1. 修改web.xml为最新的

      ~~~xml
      <?xml version="1.0" encoding="UTF-8"?>
      <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
               xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
               version="4.0"
               metadata-complete="true">
      
      </web-app>
      ~~~

      

   2. 将maven结构搭建完整

4. 编写一个Servlet程序

   1. 编写一个普通类

   1. 实现Servlet接口，这里我们直接继承HttpServlet 

![image-20220118163751846](D:\code\MarkdownTypora\notes\image-20220118163751846.png)

例子：

~~~java
public class HelloServlet  extends HttpServlet {
    //由于get或者post只是请求方式不同，可以互相调用，业务逻辑都一样
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter printWriter=resp.getWriter();//响应流
        printWriter.println("hello world");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
~~~

5 编写Servlet的映射

​	为什么要写映射？因为我们写的是java程序，但是要通过浏览器访问，而浏览器需要连接web服务器，所以我们需要在web服务器中注册我们的写的Servlet，还需要给他一个浏览器能够访问的路径。

~~~xml
 <!--注册servlet-->
  <servlet>
    <servlet-name>helloWorld</servlet-name>
    <servlet-class>com.yang.servlet.HelloWorld</servlet-class>
  </servlet>
  <servlet-mapping>
    <!--Servlet请求路径-->
    <servlet-name>helloWorld</servlet-name>
    <url-pattern>helloworld</url-pattern><!--表示请求-->
  </servlet-mapping>
~~~

6. 配置Tomcat

   ![image-20220118204030725](D:\code\MarkdownTypora\notes\image-20220118204030725.png)

   还需要配置发布路径：为Artifact既可

   ![image-20220118204130707](D:\code\MarkdownTypora\notes\image-20220118204130707.png)

   ![image-20220118204225196](D:\code\MarkdownTypora\notes\image-20220118204225196.png)

   

7. 测试项目：运行之后，会生成target文件夹，文件夹下面包含着这个项目的所有东西，第一次实例化类，需要时间。

8. 有时候部署项目到Tomcat中，无法识别，这个时候需要maven拉取刷新一下

### 3.2Servlet原理

Servlet是web服务器调用，web服务器在收到浏览器请求后，会：

![image-20220119153237394](D:\code\MarkdownTypora\notes\image-20220119153237394.png)

查看博客[浅析Servlet原理](https://www.cnblogs.com/wangjiming/p/10360327.html)

### 3.3Mapping问题

1. 一个Servlet可以指定一个映射路径

   ~~~xml
   <servlet-mapping>
               <!--Servlet请求路径-->
               <servlet-name>hello</servlet-name>
               <url-pattern>/hello</url-pattern><!--请求路径-->
           </servlet-mapping>
   ~~~

   

2. 一个Servlet可以指定多个映射路径

      <servlet-mapping>

   ```xml
           <servlet-name>hello</servlet-name>
           <url-pattern>/hello</url-pattern>
       </servlet-mapping>
   <servlet-mapping>
   
       <servlet-name>hello</servlet-name>
       <url-pattern>/hello1</url-pattern>
   </servlet-mapping>
   <servlet-mapping>
       <servlet-name>hello</servlet-name>
       <url-pattern>/hello2</url-pattern>
   </servlet-mapping>
    <!--多个路径指向同一个类-->
   ```

3. 一个Servlet可以指定通用映射路径

   ~~~xml
   <servlet-mapping>
               <servlet-name>hello</servlet-name>
               <url-pattern>/hello/*</url-pattern>
           </servlet-mapping>
   ~~~

   

4. 默认请求路径

   ~~~xml
    <servlet-mapping>
           <servlet-name>hello</servlet-name>
           <url-pattern>/*</url-pattern>
       </servlet-mapping>
   <!--表示默认请求，什么都不做，会直接到这个页面，不建议这样写-->
   ~~~

   

5. 指定一些后缀或者前缀等等...

   ~~~xml
     <servlet-mapping>
           <servlet-name>hello</servlet-name>
           <url-pattern>.do</url-pattern>
       </servlet-mapping>
   <!--只要以.do结尾或者自定义的名称结尾的都可以访问到，注意在这个之前不能有“/”映射路径-->
   ~~~

   

6. 优先级问题：

   指定了固有的映射优先级最高，如果找不到就会走默认的处理请求

### 3.4ServletContext

web容器在启动的时候，他会为每一个web程序创建一个对应的ServletContext对象，他代表了当前的web应用。

类似于一个**共享**空间，所有的Servlet都可以进行存取。

生命周期：服务器**启动**时**生成**，服务器**关闭**时**结束**

原理：![image-20220119174107290](D:\code\MarkdownTypora\notes\image-20220119174107290.png)

#### 1.共享数据

在当前Servlet设置的数据可以在另外一个Servlet中拿到

设置数据

~~~java
public class SetServletContext extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext= this.getServletContext();
        /*ServletContext类似共享中心，能够实现不同页面的请求流转*/
        String username="张三";
        servletContext.setAttribute("username",username);
        /*使用键值对的方式把内容存储进去*/
    }   

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
~~~

取得数据

~~~java
public class GetServletContext extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
        String usernaem=(String) servletContext.getAttribute("username");
        resp.setContentType("text/html;charset=utf-8");//设置编码，避免乱码
        resp.getWriter().println("用户姓名是"+usernaem);//这里没有去实例化PrintWriter响应流，采用的直接使用！
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
~~~

#### 2.获取初始化数据

在web.xml中对数据进行初始化之后，可以通过getInitParameter方法获取数据。

例如：

~~~xml
  <context-param>
    <param-name>url</param-name>
    <param-value>jdbc:mysql://localhost:3306/mybatis</param-value>
  </context-param>
~~~

~~~java
   @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = this.getServletContext();
      String url= servletContext.getInitParameter("url");//获得初始化数据
        resp.getWriter().println(url);
    }
~~~



#### 3.请求转发

通过调用getRequestDispatcher()方法和forward()实现转发。

**转发和重定向的类比：转发，我去拿你要的东西给你，有中间商。重定向：你找我要，我没有，我告诉你哪里有，让你直接去找厂家。**

~~~java
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.printf("进入了GetRequestDispatcher类");
        ServletContext servletContext = this.getServletContext();
        RequestDispatcher requestDispatcher=servletContext.getRequestDispatcher("/mysql");//转发的请求路径，/ 表示当前项目
        requestDispatcher.forward(req,resp);//调用forward实现请求转发，参数就是请求和响应
        //简写
        /*RequestDispatcher requestDispatcher=servletContext.getRequestDispatcher("/mysql").forward(req,resp);*/
    }
~~~

#### 4.资源文件的读取

Properties

- 在java目录下新建properties（为了防止Maven无法读取到，需要手动配置，详情查看2.3Maven配置）
- 在resource目录下新建properties

发现：都会被打包到项目的同一个路径下面：classes，这个路径就是在服务器上的相对位置，俗称classespath，使用properties读取资源的时候采用这个相对定位。

案例思路：

现在rescource下面创建资源文件，然后设置读取流

~~~properties
username=root
password=123456
~~~

读取，注意：如果在java目录下面创建的资源文件，那么路径也需要更换。

~~~java
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context=this.getServletContext();
        InputStream is=context.getResourceAsStream("/WEB-INF/classes/db.properties.properties");
            /*需要读取资源在当前项目中相对位置的路径，然后返回一个文件流*/
        Properties prop=new Properties();
        prop.load(is);//拿到文件流
     String user=prop.getProperty("username");
     String pwd=prop.getProperty("password");
     resp.getWriter().println(user+":"+pwd);

    }
~~~

## 4.HttpServletRequest

HttpServletRequest表示请求，用户通过http协议访问服务器，http请求中的所有信息都会被封装到HttpServletRequest，，通过这个方法可以获得所有的客户端信息

重点掌握两个方法

~~~java
    String getParameter(String var1);  //获取单个信息，返回字符 
	String[] getParameterValues(String var1);//获取多个信息，返回数组
~~~

案例：

~~~jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>登录</h1>
<div>
    <form action="${pageContext.request.contextPath}/login" method="post"><%--这里采用post请求，实际都可以--%>
        用户名：<input type="text" name="username" ><br>
        密码：<input type="password" name="password"><br>
        爱好：
        <input type="checkbox" name="hobbys" value="女孩">女孩
        <input type="checkbox" name="hobbys" value="code">code
        <input type="checkbox" name="hobbys" value="hobby意思：爱好">hobby意思：爱好
        <input type="checkbox" name="hobbys" value="游戏">游戏
        <input type="submit"><br>


    </form>
</div>

</body>
</html>

~~~

~~~java
public class RequestTestTwo  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setCharacterEncoding("utf-8");
        req.setCharacterEncoding("utf-8");

        String username=req.getParameter("username");
        String pwd=req.getParameter("password");
        String[] hobbys = req.getParameterValues("hobbys");
        System.out.println("============================");
        System.out.println("账户名："+username);
        System.out.println("密码："+pwd);
        System.out.println("爱好："+ Arrays.toString(hobbys));//Arrays.toString能够直接对数组进行转换输出，不需要使用循环
        System.out.println("============================");
        req.getRequestDispatcher("/successTwo.jsp").forward(req,resp);//使用req来实现转发。
        //转发这里：/ 表示了当前项目，不需要再重新输入根项目位置
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

~~~



逻辑分析：启动服务，使用post提交，提交到 action="${pageContext.request.contextPath}/login"当前项目的login下面，login获得请求，寻找对应的类服务（web.xml里的请求，没贴），post请求调用get请求的方法，后台展示数据，然后转发，跳转页面



重定向和转发的区别：

相同点

- 页面都会实现跳转

不同点

- 请求转发的时候，url不会发生改变   307
- 重定向的时候，url会发生改变  302

## 5.HttpServletResponse

web服务器接收到客户端的http请求，针对这个请求，分别创建一个代表请求的HttpServletRequest对象，代表响应一个HttpServletResponse.

- 如果要获取客户端发送过来的请求去找：HttpServletRequest（请求）
- 如果要给客户端响应一些信息去找：HttpServletResponse（响应）

#### 1.常见的应用

1. 向浏览器输出信息

   ~~~java
       ServletOutputStream getOutputStream() throws IOException;//文件流
   
       PrintWriter getWriter() throws IOException;//字符流
   ~~~

   

2. 下载文件

   案例：设置头[查看博客](https://blog.csdn.net/weixin_33747129/article/details/93745005)
   
   ~~~java
     @Override
       protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
   //        1. 获取下载文件的路径
           String realPath="D:\\code\\JavaIdea\\demo\\javaWebTest\\response\\src\\main\\resources\\你好world.png";
   //        2. 获取下载文件名
           String fileName=realPath.substring(realPath.lastIndexOf("\\")+1);//读取路径的最后一个\\后面的文件，他一定是我们想下载的文件
   //        3. 让浏览器支持下载文件(Content-Disposition)
           resp.setHeader("Content-Disposition", "attachment; " +
                   "filename="+ URLEncoder.encode(fileName,"UTF-8"));//为了避免文件下载时中文文件，编码问题这里提前做一个转换
   //        4. 获取下载文件的输入流
           FileInputStream in=new FileInputStream(realPath);//参数是文件的路径
   //        5. 创建缓冲区
           int len=0;
           byte [] buffer=new byte[1024];//固定写法
   //        6. 获取OutputStream对象
           ServletOutputStream sos=resp.getOutputStream();
   //        7. 将FileOutputStream写到buffer缓冲区,使用OutputStream将缓冲区中的数据输出到客户端
           while ((len=in.read(buffer))>0){  //如果读取的缓冲是大于0的就继续读取
               sos.write(buffer,0,len);
           }
           in.close();//关闭文件流
           sos.close();
       }
   
   ~~~
   
   ~~~xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0"
            metadata-complete="true">
       <servlet>
           <servlet-name>fileDownload</servlet-name>
           <servlet-class>com.yang.servlet03.FileServlet</servlet-class>
       </servlet>
       <servlet-mapping>
           <servlet-name>fileDownload</servlet-name>
           <url-pattern>/down</url-pattern>
       </servlet-mapping>
   
   </web-app>
   ~~~
   
   

#### 2.重定向

一个web资源，收到请求之后，通知他去访问另外一个web资源，这个过程叫做重定向

~~~java
    void sendRedirect(String var1) throws IOException;
~~~

~~~java
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.sendRedirect("/Servlet03_war/say");
            //这里需要跟上项目的位置，即Servlet03_war，不然重定向无法找到需要定向的项目
    }
~~~

重定向和转发的区别：

相同点

- 页面都会实现跳转

不同点

- 请求转发的时候，url不会发生改变  307
- 重定向的时候，url会发生改变  302

案例：

~~~jsp
<html>
<body>
<h2>Hello World!</h2>
<%--method请求方式--%>
<%--${pageContext.request.contextPath}表示当前下项目固定写法q--%>
<%--<%@ page contentType="text/html; charset=UTF-8"  %>让页面编码为utf-8--%>
<%--action表示请求位置--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<form action="${pageContext.request.contextPath}/login" method="get">
    用户名：<input type="text" name="username">
    密码：<input type="password" name="password">
    <input type="submit">
</form>
</body>
</html>

~~~

注意：

- 这里的需要使用<%@ page contentType="text/html; charset=UTF-8" %>让页面编码改为utf-8。

-  action="${pageContext.request.contextPath}表示请求的位置，后面一串表示当前目录的，需要导入jsp-api的jar包。

  ~~~xml
       <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/javax.servlet.jsp-api -->
          <dependency>
              <groupId>javax.servlet.jsp</groupId>
              <artifactId>javax.servlet.jsp-api</artifactId>
              <version>2.3.3</version>
              <scope>provided</scope>
          </dependency>
  ~~~

- method="get"为请求方式，这里有两种，因此后台需要写两种方式，避免前端不知道用什么请求方式。

- 直接请求jsp网页注意位置需要是相对于项目的根路径，例如案例中请求的success.jsp页面是与index.jsp同级的

  ```java
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      String username=req.getParameter("username");
      String pwd=req.getParameter("password");
      System.out.println("账号密码是"+username+":"+pwd);
      resp.sendRedirect("/Servlet03_war/success.jsp");
  }
  ```


## 6. 编码问题

为了避免数据传输过程中的乱码，如果出现乱码，可以对内容进行编码设置，先编码，再解码

~~~java
  URLEncoder.encode("内容","utf-8");//编码
  URLDecoder.decode("内容","utf-8");//解码
~~~

##  7.session(重点)

- 服务器会给每一个用户（浏览器）创建一个session对象
- 一个session独占一个浏览器，只要浏览器没有关闭，就会一直存在，也可以设置Session存在的时间
-  一个Session整个网站都有可以使用 



Session和cookie的区别

- Cookie是把用户·数据留给用户的浏览器，浏览器保存（可以保存多个）
- Session是把用户数据写到用户独占的Sessio中，服务器端保存（保存重要信息，减少服务器资源浪费）
- Sessino对象是由服务器创建

Cookie

类似于服务器给你发一张身份证，下一次你去请求的时候带上身份证，保存在客户端

![image-20220122154741025](D:\code\MarkdownTypora\notes\image-20220122154741025.png)

Session

类似于官方给你一把钥匙，你能通过这个钥匙（id）来获取资源，存放在服务器。

![image-20220122155112014](D:\code\MarkdownTypora\notes\image-20220122155112014.png)

案例：

创建Session并写入内容，可以是键值对形式的字符。也可以是键值对形式的对象

~~~java
public class Session  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决乱码
       req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
        //得到Session
        HttpSession session   = req.getSession();
        //给Session存入东西
        session.setAttribute("name","杨杰");//Session可以有两种存储方式，键值对字符，键值对对象
        session.setAttribute("Entity", new Person("杨杰","19"));
        //获得Session的id
        String id = session.getId();
        //判断Session是不是新的
        if (session.isNew()){
            resp.getWriter().println("Session创建成功，id："+id);
        }else {
            resp.getWriter().println("Session已经创建，id："+id);
        }
            //Session在创建的时候做了什么？
/*        Cookie cookie=new Cookie("JSESSIONID",id);//把session作为键值对，存到cokie里面，然后响应回去
        resp.addCookie(cookie);*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
~~~

获取Session中的数据

~~~java

public class GetSession  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf-8");
//        //得到Session
        HttpSession session   = req.getSession();
        //给Session存入东西
        Object name = session.getAttribute("name");
        Person entity = (Person) session.getAttribute("Entity");//Entity表示实体类，这里是输出实体类
        System.out.println(name);
        System.out.println(entity.toString());//转换为字符串
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
~~~

删除Session的两种方式：

手动注销

~~~java
public class DeleteSession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("name");
        session.removeAttribute("Entity");//删除某个Session节点
        session.invalidate();//手动注销Session

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
~~~

自动注销

~~~xml
  <!--设置Session默认失效时间-->
  <session-config>
    <!--1分钟后失效，单位是分钟-->
    <!--同时设置手动和自动，能够便于用户主动注销和长时间未使用，自动注销-->
    <session-timeout>1</session-timeout>
  </session-config>
~~~

## 6.jsp

### 6.1 什么是jsp

java Server Pages：java服务器端页面，和Servlet是一样的，用于动态web技术

特点：

- 语法和html语法差不多
- 区别：
  - html只提供静态数据
  - Jsp页面中可以嵌套Java代码，为用户提供数据

###  6.2Jsp原理

 服务器内部工作：Tomcat中有一个work目录表示tomcat的工作目录，在IDEA中使用Tomcat会在IDEA的Tomcat中生成一个work目录。

~~~xml
C:\Users\Hello world\AppData\Local\JetBrains\IntelliJIdea2021.3\tomcat\724c00c7-a0e6-4a4b-ba0a-0ef626ea2395\work\Catalina\localhost\Servlet04_war\org\apache\jsp
~~~

发现页面转变为java程序！只有当我们访问它对应的服务页面才会生成对应的java文件

![image-20220122165940246](D:\code\MarkdownTypora\notes\image-20220122165940246.png)

**浏览器向服务器发送请求，不管访问什么页面，实质上都是在访问Servlet！**

jsp最终也会被转换为一个java类

通过源码分析，发现这些java类继承了HttpJspBase，而不是继承的的HttpServlet，但是继续查看源码，发现：HttpJspBase又继承了HttpServlet，实质上还是继承的HttpServlet

![image-20220122170515315](D:\code\MarkdownTypora\notes\image-20220122170515315.png)

![image-20220122170928754](D:\code\MarkdownTypora\notes\image-20220122170928754.png)

注意这里使用HttpJspBase需要额外导入依赖

~~~xml
<!-- https://mvnrepository.com/artifact/tomcat/jasper-runtime -->
<dependency>
    <groupId>tomcat</groupId>
    <artifactId>jasper-runtime</artifactId>
    <version>5.5.23</version>
</dependency>
~~~

所以：**本质上来将jsp就是Servlet！**

~~~java
//初始化  
public void _jspInit() {
  }
//销毁
  public void _jspDestroy() {
  }
//JspServlet
  public void _jspService(HttpServletRequest request, HttpServletResponse response)
~~~

这个类做的一些事情

* 请求判断

  ![image-20220122171647290](D:\code\MarkdownTypora\notes\image-20220122171647290.png)

* 内置一些对象

  ~~~java
      final javax.servlet.jsp.PageContext pageContext;    //页面上下文
      javax.servlet.http.HttpSession session = null;      //Session
      final javax.servlet.ServletContext application;		//applicationContext
      final javax.servlet.ServletConfig config;			//config
      javax.servlet.jsp.JspWriter out = null;				//out(等价于PrintWriter out = resp.getWriter();)
      final java.lang.Object page = this;					//page：当前
  	HttpServletRequest request;							//请求
   	 HttpServletResponse response;						//响应
  ~~~

  

* 在输出页面之前做的事情

~~~java
  response.setContentType("text/html;charset=UTF-8");		//设置响应的页面类型
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);					//初始化界面
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
~~~

这些对象我们在jsp页面中都可以直接时使用！

经过这些设置之后，我们在jsp页面直接输出的文字，就会被转换htm格式的页面

![image-20220122173036466](D:\code\MarkdownTypora\notes\image-20220122173036466.png)

效果类似

```java
resp.setCharacterEncoding("UTF-8");
resp.setContentType("text/html;charset=utf-8");
resp.getWriter().println("<h1>注销成功</h1>");
```

证明了jsp就是Servlet，只是jsp把很多东西简化了

![image-20220122201513525](D:\code\MarkdownTypora\notes\image-20220122201513525.png)

### 6.3Jsp基础语法

#### Jsp表达式

~~~jsp
<%--jsp表达式
作用：用来将程序输出到客户端
<%=变量或者表达式 %>
--%>
<%= new java.util.Date()%>
~~~

#### jsp脚本段

~~~jsp
<%--jsp脚本片段--%>
<%         int sum=0;
        for(int i=0;i<100;i++){
            sum+=i;
                }
out.println("<h1>sum="+sum+"</h1>");
%>
~~~

~~~jsp
<%
int i=10;
out.println(i);
%>
<p>这是一个jsp文档</p>
<%
int j=20;
out.println(j);
%>
<hr>
<%--在代码中嵌套Html元素--%>

<%
    for (int k = 0; k < 5; k++) {

%>
<h1>hello world     <%=k%></h1>
<%
    }
    //实现循环输出html内容
%>
~~~

#### Jsp声明

~~~jsp
<%!
    /*jsp声明*/
static  {
    System.out.println("loading Servlet");
}
private  String a="张三";
public void  test(){
    System.out.println(a);
}
%>
~~~

jsp声明：会被编译到jsp生成的类中，其他的会被生成到jsp的JspServet方法中！

jsp的注释不会显示，但是html的注释会显示

#### Jsp指令

**导包指令：**

~~~jsp
<%@ page import="java.util.*" %>
导包之后，不需要在去重新new具体哪个包了
<%
      out.println(new Date());
%>
~~~

**错误处理指定页面指令**

~~~jsp
<%@ page errorPage="error/500.jsp" %>
~~~

出现错误之后跳转到不同的界面

**页面处理**

~~~jsp
<%--实现页面拼接，把三个页面拼接成一个，因此在其他页面定义的参数，在本页面无法重复定义--%>
<%@ include file="common/header.jsp"%>
<h1>这是中间部分</h1>
<%@ include file="common/footer.jsp"%>

<%--实现页面拼接，三个页面仍然是独立存在--%>
<jsp:include page="common/header.jsp"></jsp:include>
<h1>这是中间部分2</h1>
<jsp:include page="common/footer.jsp"></jsp:include>
~~~

**页面跳转**

~~~jsp

<%--进行页面跳转，可以携带参数，注意，里面不能有注释--%>
<%--
等价于：http://localhost:8080/jsp1_war/jsp04.jsp？name=yj&age=19
--%>
<jsp:forward page="jsp04.jsp">
    <jsp:param name="name" value="yj"/>
    <jsp:param name="age" value="19"/>
</jsp:forward>
~~~



### 6.4Jsp内置对象

jsp内置九大对象

- PageContext                                      存东西

- Request                                              存东西                   

- Response

- Session                                                存东西

- Application  (等价于:ServletContext)  存东西

- Config   (ServeletConfig)

- out   

- page

- exception

  值域范围

~~~jsp
<%
pageContext.setAttribute("key","value");//保存的数据只在一个页面有效
request.setAttribute("key","value");//保存的数据只在一次请求中有效，请求转发会携带数据
session.setAttribute("key","value");//保存的数据只在一次会话中有效，从打开服务器到关闭服务器。
application.setAttribute("key","value");//保存的数据只在服务器中有效，打开服务器到关闭服务器
%>


<%
//取值，同样可以用对应的的getAttribute()方法取值
    Object key = session.getAttribute("key");
    //取值也可以用pageContext.findAttribute("key");的方法来取值，针对所有的都有效
    Object key1 = pageContext.findAttribute("key");
%>

<%
//注意，如果取到的数据是不存在的，使用"<%=key%>"会报出现为null
使用El表达式${key}数据如果不存在，不会显示
%>
~~~

#### 6.5 Jsp标签，Jstl标签，El表达式

**EL表达式：${ }**

- 获取数据
- 执行运算
- 获取javaweb开发常用的对象

**Jstl标签**

jstl标签库就是为了弥补html标签的不足，它可以自定义许多标签，供我们使用，标签的功能和java代码一样，有很多标签（掌握部分），核心标签，xml标签，sql标签

使用步骤：

- 导入对应的taglib

  ~~~jsp
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>类似这样
  ~~~

  

- 使用其中的方法，可能会遇到导入需要的包之后，无法运行500错误，需要将jstl包和standard手动导入到Tomcat的jar中

  **if判断***

~~~jsp
<%--
   El表达式获取表单中的数据
   ${param.参数}
   --%>
<form action="jstl.jsp" method="get">

    <input type="text"  name="username" value="${param.username}">
<input type="submit" value="登录">
</form>
<%--判断，如果提交的用户是管理员就登录成功--%>
<%--
test是必须有的参数，进行判断的，需要定义一个变量来接收结果
--%>
<c:if test="${param.username=='admin'}" var="test">
    <c:out value="登录成功"></c:out>
</c:if>
~~~

**多分枝或[if  else](https://blog.csdn.net/u014070729/article/details/52126108)**

~~~jsp
<form action="jstl.jsp" method="get">

    请输入成绩<input type="text"  name="score" value="${param.score}">
    <input type="submit" value="提交">
</form>

<c:choose>
    <c:when test="${param.score>90}">
        成绩优秀
    </c:when>
    <c:when test="${param.score>80}">
        还行
    </c:when>
    <c:when test="${param.score>60}">
        及格了
    </c:when>
</c:choose>
~~~

**for each**

~~~jsp
<%

    ArrayList<String> people = new ArrayList<>();
/*列表自定义的时候可以加上索引位置*/
    people.add(0,"张三");
    people.add(1,"李四");
    people.add(2,"王五");
    people.add(3,"老六");
    request.setAttribute("list",people);//存入数据
%>

<%--类似于：定义了一个ppl的变量，去便利list这个列表，list来自于前面存入数据时候的变量名称--%>
<c:forEach var="ppl" items="${list}">
    <c:out value="${ppl}"></c:out>  <br>
</c:forEach>

<hr>
<%--类似于：定义了一个ppl2的变量，去遍历list这个列表，从索引为1的地方开始，结束索引为2，每两个为一次--%>
<c:forEach var="ppl2" items="${list}" begin="1" end="2" step="2" >
    <c:out value="${ppl2}"></c:out>
</c:forEach>
~~~

## 7. MVC三层架构

![image-20220124204734423](D:\code\MarkdownTypora\notes\image-20220124204734423.png)

**Model**

- 业务处理：业务逻辑（Service）
- 数据持久层 ：增删改查（dao层）

**View**

- 展示数据
- 提供链接发起Servlet请求（前端控件，链接，表单，图片等等）

**Controller（Servlet）**

- 接收用户请求：（	Request：请求参数，Session信息...）
- 交给业务层处理对应的代码
- 控制视图跳转

## 8.Filter过滤器

Filter使用方法和Servlet差不多，继承接口 javax.servlet.Filter

重写对应的方法：

~~~java
public class Filter   implements javax.servlet.Filter {
    //初始化：web服务器启动就初始化，随时等待过滤对象出现
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    // filterChain 链
    /*
    * 1. 过滤中所有代码，在过滤特定的请求的时候都会去执行
    * 2. 必须让过滤器继续执行，将请求和响应转发到下一个
    *  filterChain.doFilter(servletRequest,servletResponse);必写
    * */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;UTF-8");

        filterChain.doFilter(servletRequest,servletResponse);//让请求继续执行

    }
//销毁，web服务器关闭就销毁
    @Override
    public void destroy() {

    }
}
~~~

配置web.xml

~~~xml
 <filter>
    <filter-name>hello</filter-name>
    <filter-class>com.yang.Test.Filter</filter-class>
  </filter>
  <filter-mapping>
    <filter-name>hello</filter-name>
    <url-pattern>/hello/*</url-pattern>
<!--    <url-pattern>/*</url-pattern>  表示对所有的请求和转发都去执行，不建议-->
  </filter-mapping>
~~~

##### 过滤器权限demo

案例：当用户没有登录的时候，无法前往主页。

~~~jsp
  <form action="/servlet/Login" method="get">   //用户登录之后请求到这个页面
请输入账号<input type="text" name="username">
    <input type="submit">
  </form>
~~~

**配置对应的请求xml**

~~~xml

    <servlet>
        <servlet-name>login</servlet-name>
        <servlet-class>com.yang.Servlet.Login</servlet-class>//请求去往对应的页面
    </servlet>
    <servlet-mapping>
        <servlet-name>login</servlet-name>
        <url-pattern>/servlet/Login</url-pattern>
    </servlet-mapping>

~~~

**获得登录的时候的参数，进行比对，数据因该来自于数据库，这里是手动的**

注意这里的Contant.USER_SESSION，属于一个常量类中的数据，这里存入Session是键值对，用常量做为键，方便后面修改

~~~java
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");//获得参数
        System.out.println(username);
        if ("admin".equals(username)){//进行比对
            req.getSession().setAttribute(Contant.USER_SESSION,req.getSession().getId());
            //把数据存入到Session，方便后面进行取值或者进行其他操作
            System.out.println("登录成功了");
            resp.sendRedirect("/sys/suc.jsp");//页面重定向
        }else {
            resp.sendRedirect("/error/er.jsp");
        }

    }
~~~

**重定向到登录成功后的主页：并提供注销方式**

```java
<h1>主页</h1>
<a href="/servlet/Loginout">注销</a> 
//注销方式可以是很多，图片，超链接，按钮等等...发起
//注销发起请求
```

**接收请求，删除Session中的数据，或者直接删除Session（不建议）**

~~~java
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user_session = req.getSession().getAttribute(Contant.USER_SESSION);
        if (user_session!=null){
            req.getSession().removeAttribute(Contant.USER_SESSION);//删除数据
            resp.sendRedirect("/index.jsp");//重定向
        }

    }
~~~



**登录失败后跳转到登录失败的页面，并提供重新登录的方法**

~~~jsp
<h1>登录失败，账号无权限或账号错误</h1>
<a href="/index.jsp">点击重新登录</a>
~~~

**权限拦截（重点），注意需要将请求和响应转发下去**

~~~java
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       //需要对类型转换，我们需要的是HttpServletRequest类型的去获取参数
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp= (HttpServletResponse) servletResponse;
       if (req.getSession().getAttribute(Contant.USER_SESSION)==null){
           //判断，如果取到的数据为空，就是没有登录就直接访问，重定向到错误界面
           resp.sendRedirect("/error/er.jsp");
       }

        filterChain.doFilter(servletRequest,servletResponse);
    }
~~~

## 9.JDBC

#### 1.JDBC的简单使用配置demo

1. 导包：根据自己的mysql版本导包

~~~xml
  <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.27</version>   
        </dependency>
~~~

2. 主要配置：这里使用的是导包之后直接映射链接，更快一些

   ~~~java
     //配置信息
           String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
           String userName="root";
           String password="password";
           //1.加载驱动！这里采用的直接映射jar里面的配置文件
           Class.forName("com.mysql.cj.jdbc.Driver");
           //2. 链接数据库
           Connection connection = DriverManager.getConnection(url, userName, password);
   ~~~

3. 查询和修改信息:查询信息使用executeQuery(),修改信息executeUpdate();这里推荐使用preparedStatement来对sql语句执行操作，避免sql注入

   ~~~java
    public  static  void update() throws ClassNotFoundException, SQLException {
           //配置信息
           String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
           String userName="root";
           String password="password";
           //1.加载驱动！这里采用的直接映射jar里面的配置文件
           Class.forName("com.mysql.cj.jdbc.Driver");
           //2. 链接数据库
           Connection connection = DriverManager.getConnection(url, userName, password);
           //3. 编写sql语句
           String sql="insert into people(id, name, password, email, birthday) VALUES (?,?,?,?,?) ";
           //4.预编译
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1,5);//设置第一个问号的值是什么，注意主键id不能够重复
           preparedStatement.setString(2,"测试");
           preparedStatement.setString(3,"测试");
           preparedStatement.setString(4,"测试");
           preparedStatement.setDate(5,new Date(new java.util.Date().getTime()));
           //5.执行sql
           int i = preparedStatement.executeUpdate();
           if(i>0){
               System.out.println("新增加成功");
           }
        	//查询信息
           preparedStatement=connection.prepareStatement("select * from people");
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()){
               System.out.println("id="+resultSet.getObject("id"));
               System.out.println("name="+resultSet.getObject("name"));
               System.out.println("password="+resultSet.getObject("password"));
               System.out.println("email="+resultSet.getObject("email"));
               System.out.println("birthday="+resultSet.getObject("birthday"));
           }
   
           //6.关闭链接，释放资源，先开后关
           preparedStatement.close();
           connection.close();
   
       }
   ~~~

   其他方法

   ~~~java
   public  static  void find() throws SQLException, ClassNotFoundException {
           //配置信息
           String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
           String userName="root";
           String password="password";
           //1.加载驱动！
           Class.forName("com.mysql.cj.jdbc.Driver");
           //2. 链接数据库
           Connection connection = DriverManager.getConnection(url, userName, password);
           //3. 向数据库发送sql对象：CRUD
           Statement statement = connection.createStatement();
           // 4. 编写sql
           String sql="select * from people";
   
           //5.执行sql语句
           ResultSet resultSet = statement.executeQuery(sql);
           while (resultSet.next()){
               System.out.println("id="+resultSet.getObject("id"));
               System.out.println("name="+resultSet.getObject("name"));
               System.out.println("password="+resultSet.getObject("password"));
               System.out.println("email="+resultSet.getObject("email"));
               System.out.println("birthday="+resultSet.getObject("birthday"));
           }
           statement.close();
           connection.close();
           //6.关闭链接，释放资源，先开后关
       }
   
   ~~~

#### 2.junit单元测试	

导包

~~~xml
  <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
~~~

导包之后能够对一些方法直接进行测试，就不需要再去写main方法，在需要测试的方法上机上注解@Test

~~~java
 @Test
    public  void  client(){
        System.out.println("hello");
    }
~~~

![image-20220125224632244](D:\code\MarkdownTypora\notes\image-20220125224632244.png)

#### 3.事务

要么都成功，要么都失败。

ACID原则：保证数据安全性

~~~xml
开启事务   setAutoCommit
事务提交
事务回滚   rollback
关闭事务
~~~

~~~java
lic class clientTest {
    //这个类用于测试事务回滚，和junit方法
    @Test
    public  void  client(){

            //配置信息
            String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
            String userName="root";
            String password="password";
        Connection connection=null;
        try {
            //1.加载驱动！这里采用的直接映射jar里面的配置文件
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 链接数据库
            connection = DriverManager.getConnection(url, userName, password);

            //3. 通知数据库开启事务，false开启
            connection.setAutoCommit(false);
            String sql1="update  client set  money=money-100 where name='A'";
            //执行sql
            connection.prepareStatement(sql1).executeUpdate();
            //出现错误
            int i=1/0;

            String sql2="update  client set  money=money+100 where name='B'";
            //执行sql
            connection.prepareStatement(sql2).executeUpdate();

            //提交事务
            connection.commit();
        }catch (Exception e){
            //如果出现异常就回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }finally {
            try {
                connection.close();//关掉数据库链接
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
~~~

