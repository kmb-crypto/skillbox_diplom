package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.TreeSet;


@Getter
@Setter
@NoArgsConstructor
public class CalendarResponse {
    private TreeSet<Integer> years;
    private HashMap<String, Integer> posts;

}
