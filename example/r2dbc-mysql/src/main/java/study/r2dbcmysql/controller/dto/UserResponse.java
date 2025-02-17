package study.r2dbcmysql.controller.dto;

import java.util.Optional;

public record UserResponse(
        String id,
        String name,
        int age,
        Long followCount,
        Optional<ProfileImageResponse> profileImage
) {

}
