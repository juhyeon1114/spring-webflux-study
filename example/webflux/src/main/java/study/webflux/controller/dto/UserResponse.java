package study.webflux.controller.dto;

public record UserResponse(
        String id,
        String name,
        int age,
        Long followCount
) {

}
