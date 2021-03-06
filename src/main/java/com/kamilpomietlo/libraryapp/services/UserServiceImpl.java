package com.kamilpomietlo.libraryapp.services;

import com.kamilpomietlo.libraryapp.commands.UserCommand;
import com.kamilpomietlo.libraryapp.converters.UserCommandToUser;
import com.kamilpomietlo.libraryapp.converters.UserToUserCommand;
import com.kamilpomietlo.libraryapp.model.ConfirmationToken;
import com.kamilpomietlo.libraryapp.model.User;
import com.kamilpomietlo.libraryapp.model.UserRole;
import com.kamilpomietlo.libraryapp.repositories.UserRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCommand saveUserCommand(UserCommand userCommand) {
        User detachedUser = userCommandToUser.convert(userCommand);
        User savedUser = repository.save(detachedUser);

        return userToUserCommand.convert(savedUser);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCommand findCommandById(Long id) {
        return userToUserCommand.convert(findById(id));
    }

    /**
     * Deletes the {@code User} object only if there are no books assigned and it's role is not Admin.
     *
     * @param id object id
     */
    @Override
    public void deleteById(Long id) {
        User userToDelete = findById(id);

        if ((userToDelete.getBooks().isEmpty()) && (userToDelete.getUserRole() != UserRole.ADMIN)) {
            repository.deleteById(id);
        }
    }

    /**
     * Encrypts password before saving user account. Generates {@code ConfirmationToken}
     * and calls {@code sendConfirmationMail} method.
     *
     * @param userCommand object to be saved
     */
    @Override
    public void registerUser(UserCommand userCommand) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        final String encryptedPassword = passwordEncoder.encode(userCommand.getPassword());

        userCommand.setPassword(encryptedPassword);

        // delete line below when mailing activation is enabled
        userCommand.setEnabled(true);
        User savedUser = repository.save(userCommandToUser.convert(userCommand));

        // uncomment to enable mailing activation
        /*final ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        sendConfirmationMail(savedUser.getEmail(), confirmationToken.getConfirmationToken());*/
    }

    /**
     * Prepares confirmation mail to be sent to the user e-mail address. The message is internationalized.
     *
     * @param userMail user e-mail address
     * @param confirmationToken confirmation token
     */
    @Override
    public void sendConfirmationMail(String userMail, String confirmationToken) {
        String confirmationLink = "https://bestlib-app.herokuapp.com/user/register/confirm?token=";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setFrom("BestLib");

        if (LocaleContextHolder.getLocale() == Locale.forLanguageTag("pl")) {
            mailMessage.setSubject("Link aktywacyjny BestLib");
            mailMessage.setText("Dziękujemy za rejestrację. Kliknij w poniższy link w celu aktywacji konta.\n\n"
                    + confirmationLink + confirmationToken);
        } else {
            mailMessage.setSubject("BestLib registration confirmation link");
            mailMessage.setText("Thank you for registering. Please click on the link below to activate your account.\n\n"
                    + confirmationLink + confirmationToken);
        }

        emailSenderService.sendEmail(mailMessage);
    }

    /**
     * Enables user account and deletes related redundant {@code ConfirmationToken} object.
     *
     * @param token confirmation token
     */
    @Override
    public void confirmUser(String token) {
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        if (optionalConfirmationToken.isPresent()) {
            User user = optionalConfirmationToken.get().getUser();
            user.setEnabled(true);

            repository.save(user);

            confirmationTokenService.deleteById(optionalConfirmationToken.get().getId());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found.", email));
        }

        return userOptional.get();
    }

    /**
     * {@inheritDoc}
     */
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
