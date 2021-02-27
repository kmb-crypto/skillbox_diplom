package main.api.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;


@Getter
@Setter
public class CalendarResponse {
    private TreeSet<Integer> years;

    private HashMap<String,Integer> posts;

}
