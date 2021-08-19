package com.example.automation2.controller;

import com.example.automation2.model.User;
import com.example.automation2.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/*** Created: 8/19/2021 --- 1:34 AM --- ALPHANULL ***/

@Controller
public class FilterController {

    private final ApplicationContext context = new ClassPathXmlApplicationContext
            ("contexts/ApplicationContext.xml");
    private final UserService service = context.getBean("userService",UserService.class);
    private final String nameTable = "tabusers";

    private boolean orderModeCycle = true;
    private boolean isFilterGenderEnabled = false;
    private boolean isFilterActiveEnabled = false;

    private int stateOfGenderMode = 1;
    private Boolean stateOfActiveMode = false;
    private String stateOfGenderCurr;

    private String getGenderByState() {
        switch(stateOfGenderMode) {
            case 2: return User.Gender.Male.toString();
            case 3: return User.Gender.Female.toString();
            case 1: return User.Gender.Other.toString();
            default: return "usergender";
        }
    }

    @NonNull
    private Integer boolToInt(Boolean bVar) {return (bVar) ? 1 : 0;}


    // Sort users by Age
    @RequestMapping(value = "/sortByUserAge")
    public ModelAndView sortByAge() {
        List<User> list;
        if(!isFilterActiveEnabled && !isFilterGenderEnabled) {
            if(orderModeCycle) {
                list = service.userListOrder("userage",true);
                orderModeCycle = false;
            } else {
                list = service.userListOrder("userage",false);
                orderModeCycle = true;
            }
        } else if(!isFilterActiveEnabled) {
            String orderModeTemp = (orderModeCycle) ? "ASC" : "DESC";
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE usergender='"+stateOfGenderCurr
                    +"' ORDER BY userage "+orderModeTemp;
            list = service.userListComplex(sqlTemp);
            orderModeCycle = !orderModeCycle;
        } else if(!isFilterGenderEnabled) {
            String orderModeTemp = (orderModeCycle) ? "ASC" : "DESC";
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE isactive='"+
                    boolToInt(!stateOfActiveMode) +"' ORDER BY userage "+orderModeTemp;
            list = service.userListComplex(sqlTemp);
            orderModeCycle = !orderModeCycle;
        } else {
            String orderModeTemp = (orderModeCycle) ? "ASC" : "DESC";
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE isactive='"+
                    boolToInt(!stateOfActiveMode) +"' AND usergender='"+
                    stateOfGenderCurr+"' ORDER BY userage "+orderModeTemp;
            list = service.userListComplex(sqlTemp);
            orderModeCycle = !orderModeCycle;
        }
        return new ModelAndView("listview","userListServlet",list);
    }


    // Sort users by Name
    @RequestMapping(value = "/sortByUserName")
    public ModelAndView sortByName() {
        List<User> list;
        if(!isFilterActiveEnabled && !isFilterGenderEnabled) {
            if(orderModeCycle) {
                list = service.userListOrder("username",true);
                orderModeCycle = false;
            } else {
                list = service.userListOrder("username",false);
                orderModeCycle = true;
            }
        } else if(!isFilterActiveEnabled) {
            String orderModeTemp = (orderModeCycle) ? "ASC" : "DESC";
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE usergender='"+
                    stateOfGenderCurr +"' ORDER BY username "+orderModeTemp;
            list = service.userListComplex(sqlTemp);
            orderModeCycle = !orderModeCycle;
        } else if(!isFilterGenderEnabled) {
            String orderModeTemp = (orderModeCycle) ? "ASC" : "DESC";
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE isactive='"+
                    boolToInt(!stateOfActiveMode)+"' ORDER BY username "+orderModeTemp;
            list = service.userListComplex(sqlTemp);
            orderModeCycle = !orderModeCycle;
        } else {
            String orderModeTemp = (orderModeCycle) ? "ASC" : "DESC";
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE isactive='"+
                    boolToInt(!stateOfActiveMode)+"' AND usergender='"+
                    stateOfGenderCurr+"' ORDER BY username "+orderModeTemp;
            list = service.userListComplex(sqlTemp);
            orderModeCycle = !orderModeCycle;
        }
        return new ModelAndView("listview","userListServlet",list);
    }


    // Filter users by activation status
    @RequestMapping(value = "/sortByUserActive")
    public ModelAndView filterByActive() {
        List<User> list;
        if(!isFilterGenderEnabled) {
            if(orderModeCycle) {
                list = service.userListFilter("isactive","0");
                orderModeCycle = false;
            } else {
                list = service.userListFilter("isactive","1");
                orderModeCycle = true;
            }
        } else {
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE isactive='"+
                    boolToInt(!stateOfActiveMode)+"' AND usergender='"+stateOfGenderCurr+"'";
            list = service.userListComplex(sqlTemp);
        }
        stateOfActiveMode = !stateOfActiveMode;
        isFilterActiveEnabled = true;
        return new ModelAndView("listview","userListServlet",list);
    }


    // Filter users by Gender
    @RequestMapping(value = "/sortByUserGender")
    public ModelAndView filterByGender() {
        List<User> list;
        if(!isFilterActiveEnabled) {
            switch(stateOfGenderMode) {
                case 1:
                    list = service.userListFilter("usergender",
                            User.Gender.Male.toString());
                    stateOfGenderCurr = User.Gender.Male.toString();
                    stateOfGenderMode = 2; break;
                case 2:
                    list = service.userListFilter("usergender",
                            User.Gender.Female.toString());
                    stateOfGenderCurr = User.Gender.Female.toString();
                    stateOfGenderMode = 3; break;
                case 3:
                    list = service.userListFilter("usergender",
                            User.Gender.Other.toString());
                    stateOfGenderCurr = User.Gender.Other.toString();
                    stateOfGenderMode = 1; break;
                default: list = new ArrayList<>(); break;
            }
        } else {
            String sqlTemp = "SELECT * FROM "+nameTable+" WHERE usergender='"+
                    getGenderByState()+"' AND isactive='"+boolToInt(!stateOfActiveMode)+"'";
            list = service.userListComplex(sqlTemp);
            switch(stateOfGenderMode) {
                case 1:
                    stateOfGenderMode = 2;
                    stateOfGenderCurr = User.Gender.Other.toString();
                    break;
                case 2:
                    stateOfGenderMode = 3;
                    stateOfGenderCurr = User.Gender.Male.toString();
                    break;
                case 3:
                    stateOfGenderMode = 1;
                    stateOfGenderCurr = User.Gender.Female.toString();
                    break;
                default: break;
            }
        }
        isFilterGenderEnabled = true;
        return new ModelAndView("listview","userListServlet",list);
    }


    // Clear and refresh all enabled filter and sorting options
    @RequestMapping(value = "/clearFilter")
    public ModelAndView clearListFilters() {
        List<User> userList = service.userList();
        isFilterActiveEnabled = false;
        isFilterGenderEnabled = false;
        stateOfActiveMode = false;
        stateOfGenderMode = 1;
        orderModeCycle = true;
        stateOfGenderCurr = null;
        return new ModelAndView("listview","userListServlet",userList);
    }
}


