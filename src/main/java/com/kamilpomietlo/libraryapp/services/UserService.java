package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Provides operations related to {@code User} objects.
 */
public interface UserService extends BaseService<User> {

    /**
     * Saves object.
     *
     * @param userCommand object to be saved
     * @return saved object
     */
    UserCommand saveUserCommand(UserCommand userCommand);

    /**
     * Finds the object by provided id.
     *
     * @param id object id
     * @return found object
     */
    UserCommand findCommandById(Long id);

    /**
     * Registers {@code User} account.
     *
     * @param userCommand object to be saved
     */
    void registerUser(UserCommand userCommand);

    /**
     * Sends confirmation mail to provided e-mail address.
     *
     * @param userMail user e-mail address
     * @param confirmationToken confirmation token
     */
    void sendConfirmationMail(String userMail, String confirmationToken);

    /**
     * Enables {@code User} account if confirmation token is correct.
     *
     * @param confirmationToken confirmation token
     */
    void confirmUser(ConfirmationToken confirmationToken);

    /**
     * Finds {@code User} object by e-mail address.
     *
     * @param email user e-mail address
     * @return user object
     * @throws UsernameNotFoundException exception
     */
    User findUserByEmail(String email) throws UsernameNotFoundException;

    /**
     * Updates all fields which were not sent via form.
     *
     * @param userCommand object to be edited
     * @return edited object
     */
    UserCommand editRemainingFields(UserCommand userCommand);
}
