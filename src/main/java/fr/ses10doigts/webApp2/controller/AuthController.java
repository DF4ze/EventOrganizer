package fr.ses10doigts.webApp2.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import fr.ses10doigts.webApp2.security.jwt.JwtUtils;
import fr.ses10doigts.webApp2.security.model.ERole;
import fr.ses10doigts.webApp2.security.model.Role;
import fr.ses10doigts.webApp2.security.model.User;
import fr.ses10doigts.webApp2.security.model.payload.request.LoginRequest;
import fr.ses10doigts.webApp2.security.model.payload.request.SignupRequest;
import fr.ses10doigts.webApp2.security.model.payload.response.MessageResponse;
import fr.ses10doigts.webApp2.security.model.payload.response.UserInfoResponse;
import fr.ses10doigts.webApp2.security.repository.RoleRepository;
import fr.ses10doigts.webApp2.security.repository.UserRepository;
import fr.ses10doigts.webApp2.security.service.impl.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager	authenticationManager;

    @Autowired
    UserRepository		userRepository;

    @Autowired
    RoleRepository		roleRepository;

    @Autowired
    PasswordEncoder		encoder;

    @Autowired
    JwtUtils			jwtUtils;

    @Autowired
    TestApiController		cont;

    private static final Logger	logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/initializeApp")
    public String registerRolesAndUsers() {

	Long nb = roleRepository.count();
	if (nb < 3) {
	    Role u = new Role(ERole.ROLE_USER);
	    Role m = new Role(ERole.ROLE_MODERATOR);
	    Role a = new Role(ERole.ROLE_ADMIN);

	    roleRepository.save(u);
	    roleRepository.save(m);
	    roleRepository.save(a);
	}

	long count = userRepository.count();
	if (count < 3) {
	    List<Role> rolesList = roleRepository.findAll();
	    Set<Role> roles = new HashSet<>(rolesList);

	    User u1 = new User();
	    u1.setUsername("clement");
	    u1.setEmail("email1@email.fr");
	    u1.setPassword(encoder.encode("Banzai31"));
	    u1.setRoles(roles);

	    User u2 = new User();
	    u2.setUsername("ghis");
	    u2.setEmail("email2@email.fr");
	    u2.setPassword(encoder.encode("ghis"));
	    u2.setRoles(roles);

	    User u3 = new User();
	    u3.setUsername("aude");
	    u3.setEmail("email3@email.fr");
	    u3.setPassword(encoder.encode("aude"));
	    u3.setRoles(roles);

	    userRepository.save(u1);
	    userRepository.save(u2);
	    userRepository.save(u3);

	    cont.registerCeremonies();

	    return "Done.";
	}else {
	    return "Allready Done.";
	}
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

	Authentication authentication = authenticationManager
		.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

	SecurityContextHolder.getContext().setAuthentication(authentication);

	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	ResponseCookie jwtCookie = jwtUtils.generateJwtResponseCookie(userDetails);

	List<String> roles = userDetails.getAuthorities().stream()
		.map(item -> item.getAuthority())
		.collect(Collectors.toList());

	return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
		.body(new UserInfoResponse(userDetails.getId(),
			userDetails.getUsername(),
			userDetails.getEmail(),
			roles));
    }

    @PostMapping("/signinForm")
    public ModelAndView authenticateUserForm(
	    @Valid @ModelAttribute LoginRequest loginRequest, HttpServletResponse response
	    ) {

	try {
	    Authentication authentication = authenticationManager.authenticate(
		    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

	    Cookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

	    response.addCookie(jwtCookie);

	    logger.info("User '" + loginRequest.getUserName() + "' signs in");

	} catch (RuntimeException e) {
	    logger.error("Bad credentials");

	    ModelAndView mav = new ModelAndView();
	    mav.addObject("error", true);
	    mav.setViewName("login");
	    return mav;
	}

	ModelAndView mav = new ModelAndView("redirect:/home");
	mav.addObject("loggued", true);
	mav.addObject("username", loginRequest.getUserName());
	return mav;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
	logger.info("Entering SIGNUP");
	if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	    logger.info("Bad request : Username is already taken!");
	    return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
	}

	if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	    logger.info("Bad request : Email is already in use!");
	    return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	}


	// Create new user's account
	User user = new User(signUpRequest.getUsername(),
		signUpRequest.getEmail(),
		encoder.encode(signUpRequest.getPassword()));

	List<String> strRoles = signUpRequest.getRole();
	Set<Role> roles = new HashSet<>();



	if (strRoles == null) {
	    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		    .orElseThrow(() -> new RuntimeException("Error: Role is null."));
	    roles.add(userRole);
	} else {

	    for (String role : strRoles) {
		switch (role) {
		case "admin":
		    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
		    .orElseThrow(
			    () -> new RuntimeException("Error: Role " + ERole.ROLE_ADMIN + " is not found in DB."));
		    roles.add(adminRole);

		    break;
		case "mod":
		    Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
		    .orElseThrow(
			    () -> new RuntimeException(
				    "Error: Role " + ERole.ROLE_MODERATOR + " is not found in DB."));

		    roles.add(modRole);

		    break;
		default:
		    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
		    .orElseThrow(
			    () -> new RuntimeException("Error: Role " + ERole.ROLE_USER + " is not found in DB."));
		    roles.add(userRole);
		}
	    }
	}

	user.setRoles(roles);
	userRepository.save(user);

	logger.info("User : " + user);

	return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signupForm")
    public ModelAndView registerUserForm(@Valid @ModelAttribute SignupRequest signUpRequest) {
	logger.info("Entering SIGNUP");

	ModelAndView mav = new ModelAndView();
	mav.setViewName("register");
	if (userRepository.existsByUsername(signUpRequest.getUsername())) {
	    logger.info("Bad request : Username '" + signUpRequest.getUsername() + "' is already taken!");

	    mav.addObject("exception", "Username is already taken!");
	    mav.addObject("signUpRequest", signUpRequest);
	    return mav;
	    //	    return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
	}

	if (userRepository.existsByEmail(signUpRequest.getEmail())) {
	    logger.info("Bad request : Email '" + signUpRequest.getEmail() + "' is already in use!");

	    mav.addObject("exception", "Email is already in use!");
	    mav.addObject("signUpRequest", signUpRequest);
	    return mav;
	    //	    return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
	}

	// Create new user's account
	User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
		encoder.encode(signUpRequest.getPassword()));

	Set<Role> roles = signUpRequest.getRoles();
	user.setRoles(roles);
	userRepository.save(user);

	logger.info("User Created: " + user);

	mav.addObject("ok", true);
	mav.addObject("signUpRequest", signUpRequest);
	mav.addObject("allRoles", signUpRequest.getRoles());
	return mav;
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
	ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
		.body(new MessageResponse("You've been signed out!"));
    }

    @GetMapping("/signoutForm")
    public ResponseEntity<?> logoutUserForm() {
	ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
	return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
		.body(new MessageResponse("You've been signed out!"));
    }




    @ExceptionHandler(BindException.class)
    public ModelAndView handleBindError(HttpServletRequest req, BindException ex) {
	logger.info("Request: " + req.getRequestURL() + " raised BindException: " + ex);

	List<String> errors = new ArrayList<>();
	List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
	for (ObjectError objectError : allErrors) {
	    errors.add(objectError.getDefaultMessage());
	}

	ModelAndView mav = new ModelAndView();
	mav.addObject("errors", errors);
	mav.addObject("url", req.getRequestURL());
	mav.addObject("signUpRequest", new SignupRequest());
	mav.setViewName("register");
	return mav;
    }
}