package main.repository;

import main.model.CaptchaCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CaptchaRepository extends CrudRepository<CaptchaCode, Integer> {

    @Query(value = "DELETE " +
            "FROM captcha_codes " +
            "WHERE captcha_codes.id > 0 " +
            "AND (unix_timestamp(now())-unix_timestamp(captcha_codes.time)) / 60 > :limit",
            nativeQuery = true)
    void removeOldCaptcha(@Param("limit") int limitInMinutes);
}
