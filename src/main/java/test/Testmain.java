package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.dto.Board;

public class Testmain {
	public static void main(String[] args) {
		
		
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("board", new Board());
		map.put("cnt", 123);
		
		
		Map<String, Object> map1 = new HashMap<>();
		
		map1.put("board", new Board());
		map1.put("cnt", 234);
		
		
		Map<String, Object> map2 = new HashMap<>();
		
		map2.put("board", new Board());
		map2.put("cnt", 345);
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		list.add(map);
		list.add(map1);
		list.add(map2);
		
		
		
		System.out.println(list.get(2).get("cnt"));
		
	}
}
