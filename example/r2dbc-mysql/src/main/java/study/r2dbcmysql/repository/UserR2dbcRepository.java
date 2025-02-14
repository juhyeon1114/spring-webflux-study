package study.r2dbcmysql.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import study.r2dbcmysql.common.repository.UserEntity;

public interface UserR2dbcRepository extends R2dbcRepository<UserEntity, Long> {

}
