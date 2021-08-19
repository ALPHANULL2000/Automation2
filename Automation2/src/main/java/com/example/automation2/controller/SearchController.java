package com.example.automation2.controller;

import com.example.automation2.model.User;
import com.example.automation2.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/*** Created: 8/19/2021 --- 12:34 AM --- ALPHANULL ***/

@Controller
public class SearchController {

    private final ApplicationContext context = new ClassPathXmlApplicationContext
            ("contexts/ApplicationContext.xml");
    private final UserService service = context.getBean("userService",UserService.class);


    // Navigation to starter search page
    @RequestMapping(value = "/gotoSearchUser")
    public ModelAndView gotoSearchUser() {
        return new ModelAndView("finder","command",new User());
    }


    // Finds users in database by Id
    @RequestMapping(value = "/searchUserById", method = RequestMethod.GET)
    public ModelAndView commitSearchUserId(@ModelAttribute(" ") @NonNull User user) {
        List<User> list;
        if(user.getUuid().toString().isEmpty())
            list = service.userListFilter("uuid","uuid");
        else
            list = service.userListFilter("uuid",user.getUuid().toString());
        return new ModelAndView("listview","userListServlet",list);
    }


    // Finds users in database by Age
    @RequestMapping(value = "/searchUserByAge", method = RequestMethod.GET)
    public ModelAndView commitSearchUserAge(@ModelAttribute(" ") @NonNull User user) {
        List<User> list;
        if(user.getUserAge() == null)
            list = service.userListFilterNoCot("userage","userage");
        else
            list = service.userListFilter("userage",user.getUserAge().toString());
        return new ModelAndView("listview","userListServlet",list);
    }


    // Finds users in database by Name
    @RequestMapping(value = "/searchUserByName", method = RequestMethod.GET)
    public ModelAndView commitSearchUserName(@ModelAttribute(" ") @NonNull User user) {
        List<User> list;
        if(user.getUserName().isEmpty())
            list = service.userListFilterNoCot("username","username");
        else
            list = service.userListFilter("username",user.getUserName());
        return new ModelAndView("listview","userListServlet",list);
    }


    // Finds users in database by Email
    @RequestMapping(value = "/searchUserByEmail", method = RequestMethod.GET)
    public ModelAndView commitSearchUserEmail(@ModelAttribute(" ") @NonNull User user) {
        List<User> list;
        if(user.getUserEmail().isEmpty())
            list = service.userListFilterNoCot("useremail","useremail");
        else
            list = service.userListFilter("useremail",user.getUserEmail());
        return new ModelAndView("listview","userListServlet",list);
    }


    // Surrounds column state with (')s
    // Helpful distinguishing null and non-Null column states
    @NonNull
    private String Cotter(@NonNull String s) { return "'"+s+"'"; }


    // Navigation to Advanced search page
    @RequestMapping(value = "/gotoSearchUserAdvanced")
    public ModelAndView gotoSearchUserAdvance(@NonNull Model model) {
        model.addAttribute("genderListJSP",User.getGenderList());
        return new ModelAndView("finderadvanced","command",new User());
    }


    // Finds user by one or multiple attributes
    @RequestMapping(value = "/searchUserByAttrs", method = RequestMethod.GET)
    public ModelAndView commitSearchUserAttr(@ModelAttribute(" ") @NonNull User user) {
        List<User> userList;
        String sqlName   = "username";
        String sqlAge    = "userage";
        String sqlMail   = "useremail";
        boolean isNameNull,isAgeNull,isMailNull;
        if(!(user.getUserName().isEmpty()))
        { sqlName = user.getUserName().trim();isNameNull = false; }
        else isNameNull = true;
        if(!(user.getUserAge() == null))
        { sqlAge = user.getUserAge().toString().trim();isAgeNull = false; }
        else isAgeNull = true;
        if(!(user.getUserEmail().isEmpty()))
        { sqlMail = user.getUserEmail().trim();isMailNull = false; }
        else isMailNull = true;
        String nameTable = "tabusers";
        String sql = "SELECT * FROM "+ nameTable +" WHERE "
                + "username=" + ((isNameNull) ? sqlName : Cotter(sqlName))
                + " AND userage=" + ((isAgeNull) ? sqlAge : sqlAge)
                + " AND useremail=" + ((isMailNull) ? sqlMail : Cotter(sqlMail))
                + " AND usergender=" + Cotter(user.getUserGenderText());
        userList = service.userListComplex(sql);
        return new ModelAndView("listview","userListServlet",userList);
    }
}


