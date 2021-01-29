package main.model.repository;

import main.model.GlobalSetting;
import org.springframework.data.repository.CrudRepository;

public interface GlobalSettingsRepository extends CrudRepository<GlobalSetting,Integer> {
}
