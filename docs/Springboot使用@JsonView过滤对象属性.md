# Spring Boot 使用 @JsonView 过滤对象属性

## 简介

@JsonView是Jackson的一个注解，可以用来过滤序列化对象的字段属性，是你可以选择序列化对象哪些属性，哪些过滤掉。

## 使用步骤

1. 使用接口来声明多个视图

2. 在值对象的get方法上指定视图

3. 在Controller方法上指定视图

## 步骤 1：使用接口来声明多个视图

使用同一个对象，面对不同的场景，去声明多个视图。

例如：

有一个 User 对象，里面有id、username、password、birthday等属性

- 场景1：获得对象的用户名、密码

- 场景2：获得对象的全部属性

为了测试，创建一个User实体对象，加入两个接口 UserSimpleView，UserDetailView

```java
public class User {

    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView {};

    private String id;

    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

## 步骤 2：在值对象的get方法上指定视图

在实体类 User 里的 get 方法上面加上 @JsonView 注解，并将它绑定到一个指定接口

分两类

- @JsonView(UserSimpleView.class)：绑定 id、username、birthday属性

- @JsonView(UserDetailView.class)：绑定 password 属性，继承 UserSimpleView 接口（相当于绑了 UserSimpleView 绑定的属性）

```java
public class User {

    public interface UserSimpleView {};

    public interface UserDetailView extends UserSimpleView {};

    private String id;

    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Past(message = "生日必须是过去的时间")
    private Date birthday;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonView(UserSimpleView.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

## 步骤 3：在Controller方法上指定视图

在controller中俩个方法分别加上@JsonView注解，在分配上不同场景的接口

- user1：输出视图1

- user2：输出视图2

```java
@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition) {
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    /**
     * {id:\d+}:正则表示只接受数字
     *
     * @param id
     * @return
     */
    @GetMapping("{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable String id) {
        User user = new User();
        user.setId(id);
        user.setUsername("tom");
        user.setPassword("tom");
        return user;
    }
}
```

## 测试

```http request
GET http://localhost:8080/user

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 26 Jun 2019 00:41:03 GMT

[
  {
    "id": null,
    "username": null,
    "birthday": null
  },
  {
    "id": null,
    "username": null,
    "birthday": null
  },
  {
    "id": null,
    "username": null,
    "birthday": null
  }
]
```

返回结果中没有密码属性

```http request
GET http://localhost:8080/user/1

HTTP/1.1 200 
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Wed, 26 Jun 2019 00:41:56 GMT

{
  "id": "1",
  "username": "tom",
  "password": "tom",
  "birthday": null
}
```

返回结果中包含了密码字段。

## 总结

说明 @OneView 只会序列化 username、password 属性，TwoView 会序列化username、password、realName、sex属性。

因此想设置不同接口的不同场景,可以用 @JsonView 达到某些目的。