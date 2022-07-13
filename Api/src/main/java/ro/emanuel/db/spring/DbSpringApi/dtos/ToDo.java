package ro.emanuel.db.spring.DbSpringApi.dtos;

import lombok.Data;

@Data
public class ToDo {
    private Long id;
    private String title = "";
    private Boolean completed = false;
}
