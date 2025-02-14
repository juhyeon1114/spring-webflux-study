package study.r2dbcmysql.controller.dto;

import java.util.Optional;
import study.r2dbcmysql.common.Image;

public record UserResponse(
        String id,
        String name,
        int age,
        Long followCount,
        Optional<Image> profileImage
) {

}
