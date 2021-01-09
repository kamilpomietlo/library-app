package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.converters.UserToUserCommand;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {

    private final UserCommandToUser userCommandToUser;
    private final UserToUserCommand userToUserCommand;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSenderService emailSenderService;

    public UserServiceImpl(UserRepository repository, UserCommandToUser userCommandToUser,
                           UserToUserCommand userToUserCommand, ConfirmationTokenService confirmationTokenService,
                           EmailSenderService emailSenderService) {
        super(repository);
        this.userCommandToUser = userCommandToUser;
        this.userToUserCommand = userToUserCommand;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public UserCommand saveUserCommand(UserCommand userCommand) {
        User detachedUser = userCommandToUser.convert(userCommand);
        User savedUser = repository.save(detachedUser);

        return userToUserCommand.convert(savedUser);
    }

    @Override
    public UserCommand findCommandById(Long id) {
        return userToUserCommand.convert(findById(id));
    }

    @Override
    public void deleteById(Long id) {
        User userToDelete = findById(id);

        if ((userToDelete.getBooks().isEmpty()) && (userToDelete.getUserRole() != UserRole.ADMIN)) {
            repository.deleteById(id);
        }
    }

    @Override
    public void registerUser(UserCommand userCommand) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String encryptedPassword = passwordEncoder.encode(userCommand.getPassword());

        userCommand.setPassword(encryptedPassword);
        User savedUser = repository.save(userCommandToUser.convert(userCommand));

        final ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendConfirmationMail(savedUser.getEmail(), confirmationToken.getConfirmationToken());
    }

    @Override
    public void sendConfirmationMail(String userMail, String confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("BestLib registration confirmation link");
        mailMessage.setFrom("bestlib@interia.pl");
        mailMessage.setText("Thank you for registering. Please click on the link below to activate your account.\n\n"
                + "http://localhost:8080/register/confirm?token=" + confirmationToken);

        emailSenderService.sendEmail(mailMessage);
    }

    @Override
    public void confirmUser(ConfirmationToken confirmationToken) {
        User user = confirmationToken.getUser();
        user.setEnabled(true);

        repository.save(user);

        confirmationTokenService.deleteById(confirmationToken.getId());
    }

    @Override
    public User findUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }

        return userOptional.get();
    }

    @Override
    public UserCommand editRemainingFields(UserCommand userCommand) {
        UserCommand dbUserCommand = findCommandById(userCommand.getId());

        userCommand.setBooks(dbUserCommand.getBooks());
        userCommand.setEmail(dbUserCommand.getEmail());
        userCommand.setPassword(dbUserCommand.getPassword());
        userCommand.setUserRole(dbUserCommand.getUserRole());
        userCommand.setLocked(dbUserCommand.getLocked());
        userCommand.setEnabled(dbUserCommand.getEnabled());

        return userCommand;
    }
}
