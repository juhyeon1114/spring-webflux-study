package study.r2dbcmysql.controller.dto;

public record SignupUserRequest(
        String name,
        Integer age,
        String password,
        String profileImageId
) {

}
