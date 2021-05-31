package com.shubh.rest.webServices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class userDAO {
    private static List<User> users= new ArrayList<>();

    static {
        users.add(new User(1, "Shubham", new Date(19111996)));
        users.add(new User(2, "Dhananjay", new Date(1111964)));
        users.add(new User(3, "Shubhangi", new Date(7111968)));
    }

    public List<User> findAll(){
        return users;
    }


    public User findUserWithId(int id){
        for(User user:users){
            if(user.getId() == id){
                return user;
            }
        }
        return null;
    }
    public User save(User user){
        users.add(user);
        return user;
    }

    public User remove(int id){
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext())
        {
            User user = iterator.next();
            if(user.getId() == id){
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}
