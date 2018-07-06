package ext.test.service;

import java.util.HashMap;
import java.util.Map;

import ext.spring.bean.Service;

@Service
public class UserService {

	public UserService() {

		Map<String, String> user = new HashMap<>();
		user.put("id", "1");
		user.put("name", "xyz");
		users.put("1", user);

		user = new HashMap<>();
		user.put("id", "2");
		user.put("name", "xyy");
		users.put("2", user);

		user = new HashMap<>();
		user.put("id", "3");
		user.put("name", "yyy");
		users.put("3", user);

		user = new HashMap<>();
		user.put("id", "4");
		user.put("name", "xxx");
		users.put("4", user);
	}

	Map<String, Map<String, String>> users = new HashMap<>();

	public Map<String, Map<String, String>> userList() {
		return users;
	}

	public Map<String, String> getUser(String key) {
		return users.get(key);

	}

}
