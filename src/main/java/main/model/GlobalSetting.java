package main.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "global_settings")
@Component
@Getter
@Setter
@NoArgsConstructor
public class GlobalSetting extends BaseEntity {

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

}
