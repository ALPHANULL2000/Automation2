package com.example.automation2.controller;

import com.example.automation2.model.User;
import com.example.automation2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/*** Created: 8/18/2021 --- 12:34 AM --- ALPHANULL ***/

@Controller
public class MainController {

    private final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private final ApplicationContext context = new ClassPathXmlApplicationContext
            ("contexts/ApplicationContext.xml");
    private final UserService service = context.getBean("userService",UserService.class);

    private void loggerByResult(int result, String func) {
        final String classN = this.getClass().getSimpleName();
        if(result == 1) LOGGER.info(func+" -> "+classN+" -> SUCCESSFUL \n");
        else if(result == -1) LOGGER.error(func+" -> "+classN+" -> EXCEPTION \n");
        else LOGGER.warn(func+" -> "+classN+" -> UNHANDLED \n");
    }


    // Application Starter page and shows current system time
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showServerTime(@NonNull Model model) {
        Date date = new Date();
        model.addAttribute("serverTime",date.toString());
        LOGGER.info(" --- INFO --- starter -> starting \n");
        return "starter";
    }


    // Navigation to Starter page
    @RequestMapping(value = "/gotoHomePage")
    public ModelAndView gotoStarter() {
        return new ModelAndView("redirect:/");
    }


    // Navigation to main listView page of app
    @RequestMapping(value = "/gotoUserListView")
    public ModelAndView showListView() {
        List<User> userList = service.userList();
        return new ModelAndView("listview","userListServlet",userList);
    }


    // Navigation to selected user editor page
    @RequestMapping(value = "/gotoModifyUser/{uuid}")
    public ModelAndView gotoUserEditor(@PathVariable String uuid, @NonNull Model model) {
        Optional<User> user = service.getUser(UUID.fromString(uuid));
        if(user.isPresent()) {
            model.addAttribute("genderListJSP",User.getGenderList());
            model.addAttribute("activeListJSP",User.getActiveList());
            return new ModelAndView("editor","command",user.get());
        } else {
            return new ModelAndView("redirect:/gotoUserListView");
        }
    }


    // Commit and apply selected user changes
    @RequestMapping(value = "/modifyUserCommitForm", method = RequestMethod.POST)
    public ModelAndView commitModifiedUser(@ModelAttribute("user") User user) {
        int result = service.modify(user);
        loggerByResult(result,"service.modify");
        return new ModelAndView("redirect:/gotoUserListView");
    }


    // Removes selected user from database
    @RequestMapping(value = "/gotoRemoveUser/{uuid}", method = RequestMethod.GET)
    public ModelAndView removeUser(@PathVariable String uuid) {
        int result = service.delete(UUID.fromString(uuid));
        loggerByResult(result,"service.delete");
        return new ModelAndView("redirect:/gotoUserListView");
    }


    // Navigation to user append page
    @RequestMapping(value = "/gotoAppendUser")
    public ModelAndView addUser(@NonNull Model model) {
        model.addAttribute("genderListJSP",User.getGenderList());
        model.addAttribute("activeListJSP",User.getActiveList());
        return new ModelAndView("appender","command",new User());
    }


    // Commit appending defined user in append page
    @RequestMapping(value = "/addUserCommitForm", method = RequestMethod.POST)
    public ModelAndView commitAddedUser(@ModelAttribute(" ") User user) {
        service.add(UUID.randomUUID(),user);
        return new ModelAndView("redirect:/gotoUserListView");
    }
}


