package com.sciencom.osd;

import com.sciencom.osd.entity.Role;
import com.sciencom.osd.entity.User;
import com.sciencom.osd.enums.RoleEnum;
import com.sciencom.osd.helper.PasswordUtil;
import com.sciencom.osd.repository.RoleRepository;
import com.sciencom.osd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initData(){
        // create or update role
        for (RoleEnum item: RoleEnum.values()){
            Role db = roleRepository.findOne(item.getValue());
            if (db == null){
                Role role = new Role();
                role.setId(item.getValue());
                role.setName(item.name());
                roleRepository.save(role);
            }else{
                db.setName(item.name());
                roleRepository.save(db);
            }
        }

        // create user ADMIN
        String email = "admin@osd.com";
        User user = userRepository.findByEmail(email);
        if (user == null){
            User u = new User();
            u.setId(UUID.randomUUID().toString());
            u.setFirstName("Admin");
            u.setLastName("Osd");
            u.setStatus(1);
            u.setRegiserDate(new Date());
            u.setEmail(email);
            u.setId(UUID.randomUUID().toString());
            u.setPassword(PasswordUtil.encode("abcdef"));
            List<Role> roles = new ArrayList<Role>();
            roles.add(new Role(RoleEnum.ADMIN));
            u.setRoles(roles);
            userRepository.save(u);
        }
    }

}
