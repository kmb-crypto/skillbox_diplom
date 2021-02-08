package main.api.response;

import lombok.Getter;
import lombok.Setter;

public class TagResponseObject {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private float weight;
}
