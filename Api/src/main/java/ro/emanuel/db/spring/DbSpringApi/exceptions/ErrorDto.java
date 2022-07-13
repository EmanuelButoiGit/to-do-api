package ro.emanuel.db.spring.DbSpringApi.exceptions;

import lombok.Data;

@Data
public class ErrorDto {
    private String message;

   public ErrorDto(String message) {
        this.message = message;
    }
}
