package ro.emanuel.db.spring.DbSpringApi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.emanuel.db.spring.DbSpringApi.entities.ToDoEntity;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {

}
