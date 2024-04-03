package com.microservicesdemo.userservice.service;


import com.microservicesdemo.userservice.VO.Department;
import com.microservicesdemo.userservice.VO.ResponseTemplateVO;
import com.microservicesdemo.userservice.entity.User;
import com.microservicesdemo.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate; //using this rest template we get call dept and get dept object

    public User saveUser(User user) {
        log.info("Inside saveUser of UserService");
        return userRepository.save(user);
    }

    public ResponseTemplateVO getUserWithDepartment(Long userId) {
        log.info("Inside getUserWithDepartment of UserService");
        ResponseTemplateVO vo= new ResponseTemplateVO();
        User user= userRepository.findByUserId(userId);
        log.info("Rj check "+ user.getDepartmentId());
        Department department=
                restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments/" +user.getDepartmentId(),
                        Department.class);

        vo.setUser(user);
        vo.setDepartment(department);

        return vo;
    }
}
