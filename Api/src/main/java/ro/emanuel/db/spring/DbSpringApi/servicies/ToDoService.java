package ro.emanuel.db.spring.DbSpringApi.servicies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.emanuel.db.spring.DbSpringApi.dtos.ToDo;
import ro.emanuel.db.spring.DbSpringApi.entities.ToDoEntity;
import ro.emanuel.db.spring.DbSpringApi.exceptions.NotFoundException;
import ro.emanuel.db.spring.DbSpringApi.repositories.ToDoRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ToDoService {
    private final ToDoRepository toDoRepository;

    public ToDo createToDo(ToDo toDo){

        ToDoEntity toDoEntity = ConvertToEntity(toDo);
        ToDoEntity saveEntity = toDoRepository.save(toDoEntity);

        return ConvertToDto(saveEntity);
    }

    private ToDoEntity ConvertToEntity(ToDo toDo) {
        ToDoEntity toDoEntity = new ToDoEntity();
        toDoEntity.setId(toDo.getId());
        toDoEntity.setTitle(toDo.getTitle());
        toDoEntity.setCompleted(toDo.getCompleted());

        return toDoEntity;
    }

    private ToDo ConvertToDto(ToDoEntity toDoEntity){
        ToDo toDo = new ToDo();
        toDo.setId(toDoEntity.getId());
        toDo.setTitle(toDoEntity.getTitle());
        toDo.setCompleted(toDoEntity.getCompleted());

        return toDo;
    }

    public List<ToDo> shorterMethod(){
        // shorter method to get all toDos
        return toDoRepository.findAll().stream().map(this::ConvertToDto).collect(Collectors.toList());
    }
    public List<ToDo> getAllToDos(){
        List<ToDoEntity> allToDos = toDoRepository.findAll();
        List<ToDo> toDoList = new ArrayList<>();

        for(ToDoEntity entity : allToDos){
            ToDo toDo = ConvertToDto(entity);
            toDoList.add(toDo);
        }

        return toDoList;
    }

    public ToDo getToDoById(Long id) {
        try {
            ToDoEntity toDoEntity = toDoRepository.getById(id);
            return ConvertToDto(toDoEntity);
        } catch (Exception e){
            throw new NotFoundException("No item found with id: " + id);
        }
    }

    public ToDo deleteToDo(Long id){
        ToDo toDo = getToDoById(id);
        toDoRepository.delete(ConvertToEntity(toDo));
        return toDo;
    }

    public ToDo updateToDo(Long id, ToDo updatedToDo){
        ToDo toDo = getToDoById(id);

        toDo.setId(id);
        toDo.setTitle(updatedToDo.getTitle());
        toDo.setCompleted(updatedToDo.getCompleted());

        ToDoEntity toDoEntity = toDoRepository.save(ConvertToEntity(toDo));
        return ConvertToDto(toDoEntity);
    }

/***************************************************************************************************/


}
