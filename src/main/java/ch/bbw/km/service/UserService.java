package ch.bbw.km.service;

import ch.bbw.km.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class UserService {

    public List<User> getAllUsers() {
        return User.listAll();
    }

    public User getUserById(Long id) {
        return User.findById(id);
    }

    public List<User> getUserByUsername(String username) {
        List<User> users = User.listAll();
        users.removeIf((user) -> !user.username.toLowerCase().contains(username.toLowerCase()));
        return users;
    }

    @Transactional
    public User createUser(User user) {
        user.persist();
        return user;
    }

    @Transactional
    public User updateUser(Long id, User user) {
        User userToUpdate = getUserById(id);
        userToUpdate.lastName = user.lastName;
        userToUpdate.firstName = user.firstName;
        userToUpdate.age = user.age;
        userToUpdate.username = user.username;
        userToUpdate.password = user.password;
        userToUpdate.email = user.email;
        userToUpdate.role = user.role;
        userToUpdate.image = user.image;
        userToUpdate.profession = user.profession;
        userToUpdate.bookingReason = user.bookingReason;
        userToUpdate.persist();
        return userToUpdate;
    }

    @Transactional
    public void deleteUser(Long id) {
        User userToDelete = getUserById(id);
        userToDelete.delete();
    }

}
