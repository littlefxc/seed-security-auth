# 开发QQ登录

## QQ 互联

- [API列表](https://wiki.connect.qq.com/api%E5%88%97%E8%A1%A8)

    - [get_user_info](https://wiki.connect.qq.com/get_user_info)
    - [openapi调用说明_oauth2-0](https://wiki.connect.qq.com/openapi%E8%B0%83%E7%94%A8%E8%AF%B4%E6%98%8E_oauth2-0)
    
## 建表语句

sql 建表语句在 {@link JdbcUsersConnectionRepository}同包下，名为 JdbcUsersConnectionRepository.sql

可以为表 `UserConnection` 添加前缀名，但是 `UserConnection` 本身是不能变的。

```sql
create table seed_UserConnection
(
    userId         varchar(255) not null,
    providerId     varchar(255) not null,
    providerUserId varchar(255),
    `rank`         int          not null,
    displayName    varchar(255),
    profileUrl     varchar(512),
    imageUrl       varchar(512),
    accessToken    varchar(512) not null,
    secret         varchar(512),
    refreshToken   varchar(512),
    expireTime     bigint,
    primary key (userId, providerId, providerUserId)
);
create unique index UserConnectionRank on seed_UserConnection (userId, providerId, `rank`);
```

## 业务系统用户服务

```java
/**
 * @author fengxuechao
 * @date 2019-09-10
 */
@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查找用户信息
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("表单登录用户名:{}", username);
        String encodePassword = passwordEncoder.encode("123456");
        log.debug("表单登录密码:{}", encodePassword);
        return new User(username, encodePassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    /**
     * 根据用户ID查询用户信息
     *
     * @param userId the user ID used to lookup the user details
     * @return the SocialUserDetails requested
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        log.info("社交登录用户名:{}", userId);
        String encodePassword = passwordEncoder.encode("123456");
        log.debug("社交登录密码:{}", encodePassword);
        return new SocialUser(userId, encodePassword, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
```

## 定义返回用户信息接口

```java
public interface QQ {

    /**
     * 获取qq用户信息
     *
     * @return
     */
    QQUserInfo getUserInfo();

}
```

## 实现返回用户信息接口

```java
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    /**
     * 申请QQ登录成功后，分配给应用的appid。
     */
    private String appId;

    /**
     * 用户的ID，与QQ号码一一对应
     */
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);

        this.appId = appId;

        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);

        log.info("获取 QQ 的 OPENID 结果: {}", result);

        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {

        String url = String.format(URL_GET_USERINFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);

        log.info("获取 QQ 的 UserInfo 结果: {}", result);

        if (result == null) {
            throw new RuntimeException("获取用户信息失败");
        }

        try {
            QQUserInfo userInfo = objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {
            throw new RuntimeException("获取用户信息失败", e);
        }
    }

}
```

## QQ 用户信息

```java
/**
 * qq 用户信息
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
@Data
public class QQUserInfo {
	
	/**
	 * 	返回码
	 */
	private String ret;
	/**
	 * 如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
	 */
	private String msg;
	/**
	 * 
	 */
	private String openId;
	/**
	 * 不知道什么东西，文档上没写，但是实际api返回里有。
	 */
	private String is_lost;
	/**
	 * 省(直辖市)
	 */
	private String province;
	/**
	 * 市(直辖市区)
	 */
	private String city;
	/**
	 * 出生年月
	 */
	private String year;
	/**
	 * 	用户在QQ空间的昵称。
	 */
	private String nickname;
	/**
	 * 	大小为30×30像素的QQ空间头像URL。
	 */
	private String figureurl;
	/**
	 * 	大小为50×50像素的QQ空间头像URL。
	 */
	private String figureurl_1;
	/**
	 * 	大小为100×100像素的QQ空间头像URL。
	 */
	private String figureurl_2;
	/**
	 * 	大小为40×40像素的QQ头像URL。
	 */
	private String figureurl_qq_1;
	/**
	 * 	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100×100的头像，但40×40像素则是一定会有。
	 */
	private String figureurl_qq_2;
	/**
	 * 	性别。 如果获取不到则默认返回”男”
	 */
	private String gender;
	/**
	 * 	标识用户是否为黄钻用户（0：不是；1：是）。
	 */
	private String is_yellow_vip;
	/**
	 * 	标识用户是否为黄钻用户（0：不是；1：是）
	 */
	private String vip;
	/**
	 * 	黄钻等级
	 */
	private String yellow_vip_level;
	/**
	 * 	黄钻等级
	 */
	private String level;
	/**
	 * 标识是否为年费黄钻用户（0：不是； 1：是）
	 */
	private String is_yellow_year_vip;

}
```

## 基于 QQ 的 OAuth2Template 实现

```java
/**
 * @author fengxuechao
 * @date 2020-02-04
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }

    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr = getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);

        log.info("获取accessToke的响应: {}", responseStr);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");

        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken, null, refreshToken, expiresIn);
    }

    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

}
```

## QQ 服务提供商的实现

```java
/**
 * QQ 服务提供商的实现
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

    private static final String URL_AUTHORIZE = "https://graph.qq.com/oauth2.0/authorize";
    private static final String URL_ACCESS_TOKEN = "https://graph.qq.com/oauth2.0/token";
    private String appId;

    public QQServiceProvider(String appId, String appSecret) {
        super(new QQOAuth2Template(appId, appSecret, URL_AUTHORIZE, URL_ACCESS_TOKEN));
        this.appId = appId;
    }

    /**
     * 因为 AbstractOAuth2ApiBinding 中的 accessToken 属性是全局变量，所以 QQImpl 在系统中不能是单例，防止引起线程安全问题。
     *
     * @param accessToken
     * @return
     */
    @Override
    public QQ getApi(String accessToken) {
        return new QQImpl(accessToken, appId);
    }

}
```

## QQ 服务提供商的连接工厂类

```java
/**
 * QQ 服务提供商的连接工厂类
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    /**
     * @param providerId 服务提供商的唯一标识
     * @param appId
     * @param appSecret
     */
    public QQConnectionFactory(String providerId, String appId, String appSecret) {
        super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
    }

}
```

## QQ 用户信息 和 Spring Social 标准用户信息的适配

```java
/**
 * QQ 用户信息 和 Spring Social 标准用户信息的适配
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
public class QQAdapter implements ApiAdapter<QQ> {

    /**
     * TODO 用于测试当前Api是否可用，QQ服务是否可用。
     *
     * @param api
     * @return
     */
    @Override
    public boolean test(QQ api) {
        return true;
    }

    /**
	 * 适配创建 {@link org.springframework.social.connect.Connection} 需要服务提供商QQ需要的信息
	 *
     * @param api
     * @param values
     */
    @Override
    public void setConnectionValues(QQ api, ConnectionValues values) {
        QQUserInfo userInfo = api.getUserInfo();

        values.setDisplayName(userInfo.getNickname());
        values.setImageUrl(userInfo.getFigureurl_qq_1());
        // qq 没有个人主页
        values.setProfileUrl(null);
        values.setProviderUserId(userInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ api) {
        return null;
    }

	/**
	 * 在某些服务提供商才会有这个功能与 profileUrl 属性有关，例如新浪微博（发送消息更新微博）。
	 * QQ 没有。
	 *
	 * @param api
	 * @param message
	 */
	@Override
    public void updateStatus(QQ api, String message) {
        //do noting
    }

}
```

## QQ登录配置项

```java
/**
 * QQ登录配置项
 *
 * @author fengxuechao
 * @date 2019-09-11
 */
@Data
public class QQProperties {

    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;

    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 qq。
     */
    private String providerId = "qq";
}
```

## QQAutoConfig 创建 QQ 连接工厂

```java
/**
 * @author fengxuechao
 * @date 2020-02-04
 */
@Configuration
@ConditionalOnProperty(prefix = "imooc.security.social.qq", name = "app-id")
public class QQAutoConfig extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqConfig = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
    }

    /**
     * 在 {@link com.fengxuechao.seed.security.social.SocialConfig} 中已有实现，此处无需再次实现。
     *
     * @param connectionFactoryLocator
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        return null;
    }
}
```

## 社交登录配置主类

```java
/**
 * 社交登录配置主类
 *
 * @author fengxuechao
 * @date 2020-02-04
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    /**
     * sql 建表语句在 {@link JdbcUsersConnectionRepository}同包下，名为 JdbcUsersConnectionRepository.sql
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(
                dataSource, connectionFactoryLocator, Encryptors.noOpText());
        repository.setTablePrefix("seed_");
        if (connectionSignUp != null) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }

    /**
     * 社交登录配置类，供浏览器或app模块引入设计登录配置用。
     *
     * @return
     */
    @Bean
    public SpringSocialConfigurer seedSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        SeedSpringSocialConfigurer configurer = new SeedSpringSocialConfigurer(filterProcessesUrl);
        configurer.signupUrl(securityProperties.getBrowser().getSignUpUrl());
        configurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return configurer;
    }

    /**
     * 用来处理注册流程的工具类
     *
     * @param connectionFactoryLocator
     * @return
     */
    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator,
                getUsersConnectionRepository(connectionFactoryLocator)) {
        };
    }
}
```

## 配置 HttpSecurity

```
http.apply(seedSocialSecurityConfig)
```