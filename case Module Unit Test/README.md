# Unit Test Module

## 项目说明

Java 单元测试示例集合，演示测试框架和最佳实践。

## 技术栈

- JUnit 5
- Mockito
- Spring Boot Test
- AssertJ

## 测试框架对比

| 框架 | 说明 |
|------|------|
| JUnit 5 | Java 标准测试框架 |
| TestNG | 功能丰富的测试框架 |
| Mockito | Mock 框架 |
| PowerMock | 增强 Mock 功能 |

## JUnit 5 注解

| 注解 | 说明 |
|------|------|
| @Test | 测试方法 |
| @BeforeEach | 每个测试前执行 |
| @AfterEach | 每个测试后执行 |
| @BeforeAll | 所有测试前执行 |
| @AfterAll | 所有测试后执行 |
| @Disabled | 禁用测试 |
| @DisplayName | 显示名称 |
| @ParameterizedTest | 参数化测试 |

## 代码示例

### 基础测试

```java
@Test
void testAdd() {
    Calculator calc = new Calculator();
    assertEquals(5, calc.add(2, 3));
}
```

### Mock 测试

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testFindById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        
        User user = userService.findById(1L);
        
        assertNotNull(user);
        verify(userRepository).findById(1L);
    }
}
```

### Spring Boot 测试

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Alice"));
    }
}
```

## 最佳实践

1. 测试方法命名清晰 (should_xxx_when_yyy)
2. 每个测试只验证一个行为
3. 使用 AAA 模式 (Arrange-Act-Assert)
4. 测试边界条件
5. 保持测试独立性

