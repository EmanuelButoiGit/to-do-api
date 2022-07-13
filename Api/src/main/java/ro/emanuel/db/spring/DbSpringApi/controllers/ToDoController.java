package ro.emanuel.db.spring.DbSpringApi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ro.emanuel.db.spring.DbSpringApi.servicies.ToDoService;
import ro.emanuel.db.spring.DbSpringApi.dtos.ToDo;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoService toDoService;

    @GetMapping("/{id}")
    public ToDo getToDoById(@PathVariable("id") Long id){
//        try {
            return toDoService.getToDoById(id);
//        }
//        catch (Exception e){
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "No item found with id: " + id.toString(), e);
//        }
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public ToDo createToDo(@RequestBody ToDo toDo){


        return toDoService.createToDo(toDo);
    }

    @PutMapping("/admin/{id}")
    public ToDo updateToDo(@PathVariable("id") Long id,@RequestBody ToDo toDo){
        return  toDoService.updateToDo(id,toDo);
    }

    @DeleteMapping("/admin/{id}")
    public ToDo deleteToDo(@PathVariable Long id){
        return toDoService.deleteToDo(id);
    }

    @GetMapping()
    public List<ToDo> getAllToDos(){
        return toDoService.getAllToDos();
    }

}
