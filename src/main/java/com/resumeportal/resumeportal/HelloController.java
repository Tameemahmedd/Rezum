package com.resumeportal.resumeportal;

import com.resumeportal.resumeportal.models.Education;
import com.resumeportal.resumeportal.models.Job;
import com.resumeportal.resumeportal.models.User;
import com.resumeportal.resumeportal.models.UserProfile;
import com.resumeportal.resumeportal.repository.UserProfileRepository;
import com.resumeportal.resumeportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HelloController {
    @Autowired
    UserProfileRepository userProfileRepository;
@GetMapping("/")
    public String Hello(){
        return "index";

    }


    @GetMapping("/edit")
    public String sayEdit(Model model,Principal principal,@RequestParam(required = false)String add){
        String userId=principal.getName();
        model.addAttribute("userId",userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(()->new RuntimeException("Not Found: "+ userId));
        UserProfile userProfile=userProfileOptional.get();
        if("job".equals(add)){
            userProfile.getJobs().add(new Job());
        } else if ("education".equals(add)) {
            userProfile.getEducations().add(new Education());
        }else if ("skill".equals(add)) {
            userProfile.getSkills().add("");
        }else if ("tskill".equals(add)) {
            userProfile.getTechnicalskills().add("");
        }

        model.addAttribute("userProfile",userProfile);
        return "profile-edit";
    }

    @GetMapping("/delete")
    public String delete(Model model,Principal principal,@RequestParam String type, @RequestParam int index) {
        String userId=principal.getName();
        model.addAttribute("userId",userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(()->new RuntimeException("Not Found: "+ userId));
        UserProfile userProfile=userProfileOptional.get();
        if("job".equals(type)){
            userProfile.getJobs().remove(index);
        } else if ("education".equals(type)) {
            userProfile.getEducations().remove(index);
        }else if ("skill".equals(type)) {
            userProfile.getSkills().remove(index);
        }else if ("tskill".equals(type)) {
            userProfile.getTechnicalskills().remove(index);
        }
userProfileRepository.save(userProfile);
        return "redirect:/edit";
    }
    @PostMapping("/edit")
    public String postEdit(Model model, Principal principal, @ModelAttribute UserProfile userProfile){
        String userName= principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userName);
        userProfileOptional.orElseThrow(()->new RuntimeException("Not Found: "+ userName));
        UserProfile savedUserProfile=userProfileOptional.get();
        userProfile.setId(savedUserProfile.getId());
        userProfile.setUserName(userName);
        userProfileRepository.save(userProfile);
        //model.addAttribute("userName",principal.getName());
        return "redirect:/view/" +userName;
    }


    @GetMapping("/view/{userId}")
    public String view(Principal principal,@PathVariable("userId")String userId, Model model){
        if(principal !=null && principal.getName()!=""){
            boolean currentUsersProfile=principal.getName().equals(userId);
            model.addAttribute("currentUsersProfile",currentUsersProfile);
        }
        String userName= principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(()->new RuntimeException("Not Found: "+ userId));


        model.addAttribute("userId",userId);
        UserProfile userProfile=userProfileOptional.get();
        model.addAttribute("userProfile",userProfile);
        System.out.print(userProfile.getJobs());

        return "profile-templates/"+ userProfile.getTheme()+"/index";
    }
}
