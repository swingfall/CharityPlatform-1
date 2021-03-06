package com.seu.beyondtheboundary.charityplatform.service;


import com.seu.beyondtheboundary.charityplatform.domain.User;
import com.seu.beyondtheboundary.charityplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * User 服务.
 * 

 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public void removeUser(Long id) {
		userRepository.delete(id);
	}

	@Transactional
	@Override
	public void removeUsersInBatch(List<User> users) {
		userRepository.deleteInBatch(users);
	}
	
	@Transactional
	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public List<User> listUsers() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> listUsersByNameLike(String name, Pageable pageable) {
		// 模糊查询
		name = "%" + name + "%";
		Page<User> users = userRepository.findByRealnameLike(name, pageable);
		return users;
	}

	public User findMeet(String username, String password){
		User user = userRepository.findByUsernameAndPassword(username,password);

	return user;

//		if (user!= null){
//			return true;
//		}else {
//			return false;
//
//		}
	}

	//将所有待审核的用户筛选出来，并返回需要的信息
	public List<User> userNotVerified() {
		return userRepository.findAllByVerified(2);
	}

	//审核通过用户状态设为1，审核未通过设为0
	public void userCheckPass(Long id, Integer verified) {

		User user = userRepository.findById(id);
		user.setVerified(verified);
		userRepository.save(user);
	}

	//将所有待审核的用户筛选出来，并返回需要的信息
	public List<User> userHasVerified() {
		return userRepository.findAllByVerified(1);
	}

	//将所有管理员筛选出来
	public List<User> findAdmins() {
		return userRepository.findAllByAdmin(true);
	}

}
