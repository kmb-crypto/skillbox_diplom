package main.api.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.TreeSet;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarResponse {
    private TreeSet<Integer> years;
    private HashMap<String, Integer> posts;

}
