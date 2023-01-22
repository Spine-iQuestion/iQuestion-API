package org.spine.iquestionapi;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spine.iquestionapi.model.EmailDomain;
import org.spine.iquestionapi.model.User;
import org.spine.iquestionapi.model.User.Role;
import org.spine.iquestionapi.repository.EmailDomainRepo;
import org.spine.iquestionapi.repository.UserRepo;
import org.spine.iquestionapi.security.JWTUtil;
import org.spine.iquestionapi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class ShellCommands implements Quit.Command {

    @Autowired private EmailDomainRepo emailDomainRepo;
    @Autowired private UserRepo userRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTUtil jwtUtil;
    @Autowired
    private ApplicationContext appContext;
    @Value("${root_email}")
    private String rootEmail;
    @Value("${root_password}")
    private String rootPassword;
    Logger logger = LoggerFactory.getLogger(ShellCommands.class);

    @ShellMethod("Add a user")
    public String addUser(String name, String email, String organization, String role, String password) {
        // check if all the fields are filled
        if (name.isEmpty() || email.isEmpty() || organization.isEmpty() || role == null || password.isEmpty()) {
            return "Please fill all the fields";
        }
        
        // check if role is a valid role
        if (!EnumUtils.isValidEnum(Role.class, role.toUpperCase())) {
            return "Role is not valid, use one of the following: " + Role.values().toString();
        }

        // check if the email domain is allowed, if not return
        List<EmailDomain> emailDomains = emailDomainRepo.findAll();

        // Check if email is of a valid type
        if (!StringUtil.isValidEmail(email, emailDomains)) {
            return "Email is not valid or email domain is not allowed (you can add it via the addEmailDomain command)";
        }

        // Check if the email already exists
        if (userRepo.findByEmail(email).isPresent()) {
            return "Email already exists";
        }

        // create new user
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setOrganization(organization);
        user.setRole(Role.valueOf(role.toUpperCase()));
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setPasswordChangeTime(System.currentTimeMillis());
        userRepo.save(user);

        return jwtUtil.generateToken(email);
    }

    @ShellMethod("Add an email domain")
    public String addEmailDomain(String domain) {
        // check if the domain is empty
        if (domain.isEmpty()) {
            return "Please enter a domain";
        }

        // check if domain is valid
        if (!StringUtil.isValidDomain(domain)) {
            return "Domain is not valid";
        }

        // check if the domain already exists
        if (emailDomainRepo.findByDomain(domain).isPresent()) {
            return "Domain already exists";
        }

        // add the domain
        EmailDomain emailDomain = new EmailDomain();
        emailDomain.setDomain(domain);
        emailDomainRepo.save(emailDomain);

        return "Domain added successfully";
    }

    @ShellMethod("Show example commands")
    public String examples() {
        return "Example commands:\r\n\r\n" +
                "add-user --name Hulpverlener --email test2@live.nl --organization hsleiden --role CAREGIVER --password 12345678\r\n" +
                "add-email-domain --domain live.nl";
    }

    @ShellMethod(value= "Exit", key = {"quit", "exit", "terminate"})
    public String quit() {
        SpringApplication.exit(appContext, () -> 0);
        return "Exiting...";
    }

    @PostConstruct
    public void init() {
        		// Check if the database is empty
		if (userRepo.count() > 0) {
			logger.info("Root user already exists. Skipping root user creation.");
			return;
		}

		// Create the root user
		User user = new User();
        user.setName("Root");
        user.setEmail(rootEmail);
        user.setOrganization("Spine");
        user.setRole(User.Role.SPINE_ADMIN);
		user.setPassword(passwordEncoder.encode(rootPassword));
        user.setEnabled(true);
        user.setPasswordChangeTime(System.currentTimeMillis());
        userRepo.save(user);

        logger.info("Root user created with email: " + rootEmail + " and password: " + rootPassword);
    }
}
