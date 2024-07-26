package User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserManager {
    Map<Integer, User> userMap;
    private static volatile UserManager instance;
    private static final AtomicInteger userIdCounter = new AtomicInteger(0);

    private UserManager(){
        this.userMap = new HashMap<Integer, User>();
    }

    public static UserManager getUserManager(){
        if(instance == null){
            synchronized (UserManager.class){
                if(instance == null)
                    instance = new UserManager();
                return instance;
            }
        }
        return instance;
    }


    public User createUser(String userName){
        User user = new User(userIdCounter.incrementAndGet(), userName);
        userMap.put(user.userId, user);
        return  user;
    }

    public String getUserName(int id){
        return userMap.get(id).name;
    }
}
