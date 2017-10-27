package com.sciencom.osd.service;

import com.sciencom.osd.entity.Role;
import com.sciencom.osd.enums.RoleEnum;
import com.sciencom.osd.helper.CodeGenerator;
import com.sciencom.osd.helper.JwtUtil;
import com.sciencom.osd.helper.PasswordUtil;
import com.sciencom.osd.entity.Response;
import com.sciencom.osd.entity.User;
import com.sciencom.osd.repository.RoleRepository;
import com.sciencom.osd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private JwtUtil jwtUtil;

    public Response register(User user){
        if (user.getEmail() == null || user.getEmail().isEmpty()){
            return new Response("Email harus diisi");
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()){
            return new Response("Password harus diisi");
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()){
            return new Response("First name harus diisi");
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()){
            return new Response("Last name harus diisi");
        }

        String id = UUID.randomUUID().toString();
        String encoded = PasswordUtil.encode(user.getPassword());
        List<Role> roles = new ArrayList<Role>();
        Role role = roleRepository.findOne(RoleEnum.MANAGER.getValue());
        roles.add(role);

        user.setId(id);
        user.setPassword(encoded);
        user.setRegiserDate(new Date());
        user.setStatus(0);
        user.setActivationCode(CodeGenerator.generate(6));
        user.setRoles(roles);
        userRepository.save(user);

        // kirim email ke user
        String subject = "Aktifasi Akun OSD";
        String content = "Terimakasih sudah mendaftar di sisem OSD.\n " +
                "Tuntaskan pendaftaran Anda dengan memasukan informasi berikut:\n" +
                "Email: " + user.getEmail() + "\n" +
                "Kode Aktifasi: " + user.getActivationCode();
        mailService.sendEmail(user.getEmail(), subject, content);

        return new Response(user);
    }

    public Response activation(Map map){
        String email = (String) map.get("email");
        String activationCode = (String) map.get("code");

        User user = userRepository.findByEmail(email);
        if (user == null)
            return new Response("Email tidak ditemukan");

        if (!user.getActivationCode().equals(activationCode)){
            return new Response("Kode aktifasi tidak valid");
        }

        user.setStatus(1);
        userRepository.save(user);
        return new Response(true);
    }

    public Response login(User user){
        if (user.getEmail() == null || user.getEmail().isEmpty()){
            return new Response("Email harus diisi");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            return new Response("Password harus diisi");
        }

        User o = userRepository.findByEmail(user.getEmail());
        if (o == null) {
            return new Response("Email tidak ditemukan");
        }

        if (o.getStatus() == 0){
            return new Response("Akun anda belum aktif");
        }

        boolean isMatch = PasswordUtil.match(user.getPassword(), o.getPassword());
        if (isMatch){
            String token = jwtUtil.createToken(o);
            Map map = new HashMap();
            map.put("id", o.getId());
            map.put("firstName", o.getFirstName());
            map.put("lastName", o.getLastName());
            map.put("email", o.getEmail());
            map.put("token", token);
            return new Response(map);
        }else{
            return new Response("Password salah");
        }
    }
}
