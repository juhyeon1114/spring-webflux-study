package study.sse.entity.common.repository;

import lombok.Data;

@Data
public class UserEntity {

    private final String id;
    private final String name;
    private final int age;
    private final String profileImageId;

}