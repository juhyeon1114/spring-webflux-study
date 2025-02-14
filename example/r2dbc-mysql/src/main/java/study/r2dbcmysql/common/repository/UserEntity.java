package study.r2dbcmysql.common.repository;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Table("USER")
@Data
public class UserEntity {

    @Id
    private Long id;
    private String name;
    private Integer age;
    private String profileImageId;
    private String password;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @CreatedDate
    private LocalDateTime createdAt;

    @PersistenceCreator
    public UserEntity(
            Long id,
            String name,
            Integer age,
            String profileImageId,
            String password
    ) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.profileImageId = profileImageId;
        this.password = password;
    }

    public UserEntity(
            String name,
            Integer age,
            String profileImageId,
            String password
    ) {
        this(null, name, age, profileImageId, password);
    }

}