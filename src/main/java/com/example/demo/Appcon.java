package com.example.demo;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class Appcon {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private AdminRepository rep;
	
	@GetMapping("")
	public String home() {
		return "index";
	}
	
	@GetMapping("userLogin")
	public ModelAndView loginpage(ModelAndView mav, User user){
		mav.addObject("user", user);
		mav.setViewName("userLogin");
		return mav;
	}
	@PostMapping("/process_login")
	public ModelAndView processLogin(ModelAndView mav, User user) {
		User existUser=repo.findByEmail(user.getEmail());
		if(existUser == null) {
			mav.addObject("messages", "Invalid Email or Password");
			mav.setViewName("userLogin");
		}
		else {
			String dbPass=existUser.getPassword();
			String pass=user.getPassword();
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			if(encoder.matches(pass, dbPass)) {
					mav.setViewName("userIndex");
			}
			else {
				mav.addObject("messages", "Invalid Email or Password");
				mav.setViewName("userLogin");
			}
		}
		return mav;
	}
	@GetMapping("newuser")
	public ModelAndView newUser(ModelAndView mav, User user) {
		mav.addObject("user",user);
		mav.setViewName("newuser");
		return mav;
	}
	@PostMapping("/process_register")
	public ModelAndView processRegistration(ModelAndView mav, User user) {
		User existUser=repo.findByEmail(user.getEmail());
		if(existUser!=null) {
			mav.addObject("message","This Email alreay exists!");
			mav.setViewName("newuser");
		}
		else {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPassword=encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		repo.save(user);
		mav.setViewName("redirect:/userLogin?success");
		}
		return mav;
	}
	
	@GetMapping("adminLogin")
	public ModelAndView adminlogin(ModelAndView mav, Admin admin) {
		mav.addObject("admin", admin);
		mav.setViewName("adminLogin");
		return mav;
	}
	@PostMapping("/process_admin")
	public ModelAndView adminpage(ModelAndView mav, Admin admin) {
		Admin existAdmin=rep.findByemailadmin(admin.getEmail());
		if(existAdmin == null) {
			mav.addObject("msg", "Invalid Email or Password");
			mav.setViewName("adminLogin");
		}
		else {
			String dbPass=existAdmin.getPassword();
			String pass=admin.getPassword();
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			if(encoder.matches(pass, dbPass)) {
					mav.setViewName("adminIndex");
			}
			else {
				mav.addObject("msg", "Invalid Email or Password");
				mav.setViewName("adminLogin");
			}
		}
		return mav;

	}
	@GetMapping("/userIndex")
	public String userIndexes() {
		return "userIndex";
	}
	@GetMapping("/adminIndex")
	public String adminIndexes() {
		return "adminIndex";
	}
	
	@GetMapping("forgot")
	public ModelAndView forgotpage(ModelAndView mav, User user) {
		mav.addObject("user", user);
		mav.setViewName("forgot");
		return mav;
	}
	@PostMapping("/process_validate")
	public ModelAndView forgotvalidate(ModelAndView mav, User user) {
		User existUser=repo.findByEmail(user.getEmail());
		if(existUser == null) {
			mav.addObject("msgs", "Invalid Email or Keyword");
			mav.setViewName("forgot");
		}
		else {
			String exUser=new String(existUser.getKeyword());
			String us=new String(user.getKeyword());
			if(exUser.equals(us)) {
				mav.setViewName("newpassword");
			}
			else {
				mav.addObject("msgs", "Invalid Email or Keyword");
				mav.setViewName("forgot");
			}
		}
		return mav;
	}
	
	@GetMapping("forgotAdmin")
	public ModelAndView forgotpages(ModelAndView mav, Admin admin) {
		mav.addObject("admin", admin);
		mav.setViewName("forgotAdmin");
		return mav;
	}
	@PostMapping("/process_validate_admin")
	public ModelAndView forgotvalidates(ModelAndView mav, Admin admin) {
		Admin existAdmin=rep.findByemailadmin(admin.getEmail());
		if(existAdmin == null) {
			mav.addObject("ms", "Invalid Email or Employee ID");
			mav.setViewName("forgotAdmin");
		}
		else {
			String exAdmin=new String(existAdmin.getEmpId());
			String ad=new String(admin.getEmpId());
			if(exAdmin.equals(ad)) {
				mav.setViewName("newPasswordAdmin");
			}
			else {
				mav.addObject("ms", "Invalid Email or Employee ID");
				mav.setViewName("forgotAdmin");
			}
		}
		return mav;
	}
	
	@GetMapping("newpassword")
	public ModelAndView newpass(ModelAndView mav, User user) {
		mav.addObject("user", user);
		mav.setViewName("newpassword");
		return mav;
	}
	@PostMapping("/process_password")
	@Transactional
	public ModelAndView newpasspage(ModelAndView mav, User user) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPassword=encoder.encode(user.getPassword());
		repo.setUserinfo(encodedPassword);
		mav.setViewName("redirect:/userLogin?successpass");
		return mav;

	}
	
	@GetMapping("newPasswordAdmin")
	public ModelAndView newpassad(ModelAndView mav, Admin admin) {
		mav.addObject("admin", admin);
		mav.setViewName("newPasswordAdmin");
		return mav;
	}
	@PostMapping("/process_password_admin")
	@Transactional
	public ModelAndView newpasspagead(ModelAndView mav, Admin admin) {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPassword=encoder.encode(admin.getPassword());
		rep.setAdmininfo(encodedPassword);
		mav.setViewName("redirect:/adminLogin?successpass");
		return mav;
	}
	
	
}
