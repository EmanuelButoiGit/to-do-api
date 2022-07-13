package ro.emanuel.db.spring.DbSpringApi.controllers;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import ro.emanuel.db.spring.DbSpringApi.dtos.ToDo;
import ro.emanuel.db.spring.DbSpringApi.entities.UserEntity;
import ro.emanuel.db.spring.DbSpringApi.repositories.UserRepository;
import ro.emanuel.db.spring.DbSpringApi.servicies.ToDoService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToDoControllerTest {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private ToDoService service;
    @Autowired
    private UserRepository repo;
    private static Long idTest;
    private HttpHeaders httpHeaders;

    @BeforeEach
    void init(){
        if(repo.count() == 0){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("super");
            userEntity.setPassword("$2a$12$KlTFu9FiAm8FBgaO9wPleuv9c5EaHXLVwhL1SwrLO6SWqz68nlPBa");
            userEntity.setRole("admin");
            repo.save(userEntity);
        }

        httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth("super", "super");

    }

    @Test
    @Order(1)
     void createToDoTest() {
        // assume
        final ToDo toCreate = new ToDo();
        toCreate.setTitle("Test");
        toCreate.setCompleted(true);

        final ToDo serviceToDo = service.createToDo(toCreate);
        idTest = serviceToDo.getId();
        toCreate.setId(serviceToDo.getId());

        // act
        HttpEntity<ToDo> requestEntity = new HttpEntity<>(toCreate, httpHeaders);
        ResponseEntity<Object> result = rest.exchange("/todos/admin", HttpMethod.POST, requestEntity, Object.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody()).isNotNull();

        assertEquals(toCreate.getId(), serviceToDo.getId()); //assertThat().isEqual
        assertEquals(toCreate.getTitle(), serviceToDo.getTitle());
        assertEquals(toCreate.getCompleted(), serviceToDo.getCompleted());
    }

    @Test
    @Order(2)
    void getToDoByIdTest(){
        // assume
        final ToDo serviceToDo = service.getToDoById(idTest);

        // act
        HttpEntity<ToDo> requestEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<Object> result = rest.exchange("/todos/" + idTest.toString(), HttpMethod.GET, requestEntity, Object.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();

        assertEquals(idTest, serviceToDo.getId());
        assertEquals("Test", serviceToDo.getTitle());
        assertEquals(true, serviceToDo.getCompleted());
    }

    @Test
    @Order(3)
    void updateToDoTest(){
        // assume
        final ToDo toUpdate = new ToDo();
        toUpdate.setId(idTest);
        toUpdate.setTitle("Test");
        toUpdate.setCompleted(false);

        final ToDo serviceToDo = service.createToDo(toUpdate);

        // act
        HttpEntity<ToDo> requestEntity = new HttpEntity<>(toUpdate, httpHeaders);
        ResponseEntity<Object> result = rest.exchange("/todos/admin/" + idTest.toString(), HttpMethod.PUT, requestEntity, Object.class);

        // assert
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();

        assertEquals(toUpdate.getId(), serviceToDo.getId());
        assertEquals(toUpdate.getTitle(), serviceToDo.getTitle());
        assertEquals(toUpdate.getCompleted(), serviceToDo.getCompleted());
    }

    @Test
    @Order(4)
    void deleteToDoTest(){
        // assume
        final ToDo toDelete = new ToDo();
        toDelete.setId(idTest);
        toDelete.setTitle("Test");
        toDelete.setCompleted(false);

        // act
        HttpEntity<ToDo> requestEntity = new HttpEntity<>(toDelete, httpHeaders);
        ResponseEntity<Object> response = rest.exchange("/todos/admin/" + idTest.toString(), HttpMethod.DELETE, requestEntity, Object.class);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @Order(5)
    void deleteToDoServiceTest(){
        // assume
        final ToDo toCreate = new ToDo();
        toCreate.setId(idTest + 1);
        toCreate.setTitle("Test");
        toCreate.setCompleted(true);

        //act
        service.createToDo(toCreate);
        final ToDo serviceToDo = service.deleteToDo(toCreate.getId());

        //asserts
        assertEquals(toCreate.getId(), serviceToDo.getId());
        assertEquals(toCreate.getTitle(), serviceToDo.getTitle());
        assertEquals(toCreate.getCompleted(), serviceToDo.getCompleted());
    }

}