package study.webflux.controller.dto;

import java.util.Optional;
import study.webflux.entity.common.Image;

public record UserResponse(
        String id,
        String name,
        int age,
        Long followCount,
        Optional<Image> profileImage
) {

}
