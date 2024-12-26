package com.prestamype.reto_dev.service.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prestamype.reto_dev.persistence.entity.Role;
import com.prestamype.reto_dev.persistence.entity.UserAuth;
import com.prestamype.reto_dev.presentation.dto.UserAuthDTO;
import com.prestamype.reto_dev.service.interfaces.IEmailService;
import com.prestamype.reto_dev.service.interfaces.IUserAuthService;

@Service
public class UserAuthServiceImpl implements IUserAuthService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Autowired
	private IEmailService emailService;
	
	public Optional<UserAuth> findByEmailWithRoles(String email) {
        Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("email").is(email)),
            Aggregation.lookup("role", "roles._id", "_id", "roles_info")
        );

        AggregationResults<UserAuth> results = mongoTemplate.aggregate(aggregation, "user_auth", UserAuth.class);
        return Optional.ofNullable(results.getUniqueMappedResult());
    }
	
	public UserAuth insertUser(UserAuthDTO userAuthDTO) {
		
		Role aux = new Role();
		Set<Role> roles = new HashSet<>();
	
		for(Role rol : userAuthDTO.getRoles()) {
			aux.setId(rol.getId());
			aux.setRolenombre(rol.getRolenombre());
			roles.add(aux);
		}
		
		UserAuth userAuth = UserAuth.builder()
				.email(userAuthDTO.getEmail())
				.password(passwordEncoder.encode(userAuthDTO.getPassword()))
				.fecharegistro(userAuthDTO.getFecharegistro())
				.isEnabled(userAuthDTO.isIsenabled())
				.accountNoExpired(userAuthDTO.isAccountNoExpired())
				.accountNoLocked(userAuthDTO.isAccountNoLocked())
				.credentialNoExpired(userAuthDTO.isCredentialNoExpired())
				.roles(roles)
				.build();
		
        mongoTemplate.insert(userAuth);
        
        if(!userAuth.getId().isEmpty()) {
        	emailService.sendEmail(userAuthDTO.getEmail(), "Hola, bienvenido al team  :)", "Feliz navidad y Año Nuevo 2025");
        }
        
        return userAuth;
    }
	
	public List<UserAuth> getUsuarios(){
		return mongoTemplate.findAll(UserAuth.class);
	}
	
}
