package spring.mvc.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.mvc.model.User;

/**
 * @author An Nguyen
 *
 */
@RestController
public class UserRestController {

	public static final HttpHeaders HTTP_HEADERS() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
		return httpHeaders;
	};

	public static HashMap<Integer, User> map = new HashMap<Integer, User>();
	static {
		map.put(1, new User(1, "ANNNKJ", "adsg2@gmail.com", "HCM-VN"));
		map.put(2, new User(2, "BBBBSB", "saud3@gmail.com", "HN_VN"));
		map.put(3, new User(3, "CCCCCC", "saio23@gmail.com", "HNN_CNC"));
		map.put(4, new User(4, "DDDDDD", "hsao3@gmail.com", "DN VN"));
	}

	/**
	 * @return All User
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUser() {
		List<User> listUsers = new ArrayList<User>(map.values());
		return new ResponseEntity<List<User>>(listUsers, HttpStatus.OK);
	}

	/**
	 * @param id: id User
	 * @return User and status
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") int id) {
		User user = map.get(id);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("Not Found User  không hợp lệ", HTTP_HEADERS(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Create User
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody User user) {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

		if (map.containsKey(user.getId())) {
			return new ResponseEntity<String>("User Already Exist không hợp lệ", responseHeaders, HttpStatus.CONFLICT);
		}
		map.put(user.getId(), user);
		return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
	}

	/**
	 * Delete User By Id
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserById(@PathVariable(value = "id") int id) {
		User user = map.get(id);
		if (user == null) {
			return new ResponseEntity<String>("Not Found User", HTTP_HEADERS(), HttpStatus.NOT_FOUND);
		}
		map.remove(id);
		return new ResponseEntity<String>("Deleted!", HTTP_HEADERS(), HttpStatus.OK);
	}

	/**
	 * Update User
	 * 
	 * @param user
	 * @return status
	 */
	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<String> updateUser(@RequestBody User user) {
		User oldUser = map.get(user.getId());
		if (oldUser == null) {
			return new ResponseEntity<String>("Not Found User", HttpStatus.NO_CONTENT);
		}
		map.put(user.getId(), user);
		return new ResponseEntity<String>("Updated!", HttpStatus.OK);
	}
}
