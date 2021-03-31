package main.repository;

import main.model.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSetting, Integer> {
    GlobalSetting findByCode(@Param("code") String code);
}
