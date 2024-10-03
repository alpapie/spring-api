package zone01.com.lets_play.user;

public interface UserServiceInterface {
    
    public void registerUser(User user) throws Exception;
    public User getUserByEmail(String email) throws Exception;
}
