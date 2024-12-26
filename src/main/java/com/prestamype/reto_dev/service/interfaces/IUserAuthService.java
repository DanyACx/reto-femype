package com.prestamype.reto_dev.service.interfaces;

import java.util.List;

import com.prestamype.reto_dev.persistence.entity.UserAuth;
import com.prestamype.reto_dev.presentation.dto.UserAuthDTO;

public interface IUserAuthService {

	public UserAuth insertUser(UserAuthDTO userAuthDTO);
	
	public List<UserAuth> getUsuarios();
}
