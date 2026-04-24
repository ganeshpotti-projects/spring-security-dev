package com.learning.employee_service.handlers;

import com.learning.employee_service.dao.EmployeeDao;
import com.learning.employee_service.dto.EmployeeDetailsDto;
import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.enums.EmployeeRole;
import com.learning.employee_service.enums.EmployeeStatus;
import com.learning.employee_service.mappers.EmployeeMapper;
import com.learning.employee_service.repositories.EmployeeRepository;
import com.learning.employee_service.services.EmployeeService;
import com.learning.employee_service.services.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final EmployeeDao employeeDao;

    private final EmployeeService employeeService;

    private final JwtService jwtService;

   @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
           throws IOException, ServletException
   {
       OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
       DefaultOAuth2User oAuth2User = (DefaultOAuth2User) token.getPrincipal();
       String email = oAuth2User.getAttribute("email");
       Employee employee = employeeService.findByUsername(email);
       if(employee == null){
           EmployeeDetailsDto creationDetails = EmployeeDetailsDto.builder()
                   .name(oAuth2User.getAttribute("name"))
                   .email(oAuth2User.getAttribute("email"))
                   .status(EmployeeStatus.ACTIVE)
                   .roles(Set.of(EmployeeRole.GUEST))
                   .build();
           employeeDao.createOrUpdateEmployee(creationDetails);
       }
       Employee updatedEmployee = employeeService.findByUsername(email);
       String accessToken = jwtService.generateAccessToken(updatedEmployee);
       String refreshToken = jwtService.generateRefreshToken(updatedEmployee);

       Cookie cookie = new Cookie("refreshToken", refreshToken);
       cookie.setHttpOnly(true);
       response.addCookie(cookie);
       String frontEndUrl = "http://localhost:8080/home.html?token="+accessToken;
       getRedirectStrategy().sendRedirect(request, response, frontEndUrl);
   }
}
