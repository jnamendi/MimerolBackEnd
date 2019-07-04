package bmbsoft.orderfoodonline.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bmbsoft.orderfoodonline.dao.UserRestaurantDAO;
import bmbsoft.orderfoodonline.entities.UserRestaurant;
import bmbsoft.orderfoodonline.model.shared.RestaurantLiteResponse3;

@Service
public class UserRestaurantService {

	@Autowired
	private UserRestaurantDAO urd;

	@Transactional
	public List<UserRestaurant> getByUser(long userId) {
		return urd.getByUser(userId);
	}

	@Transactional
	public List<RestaurantLiteResponse3> getByUserRestaurant(long userId) {
		List<UserRestaurant> ur = urd.getByUser(userId);
		List<RestaurantLiteResponse3> r = new ArrayList<>();
		if (ur != null && ur.size() > 0) {
			ur.forEach(u -> {
				RestaurantLiteResponse3 r3 = new RestaurantLiteResponse3();
				r3.setRestaurantId(u.getRestaurant().getRestaurantId());
				r3.setRestaurantName(u.getRestaurant().getName());
				r.add(r3);
			});
		}

		return r;
	} 
	
}
