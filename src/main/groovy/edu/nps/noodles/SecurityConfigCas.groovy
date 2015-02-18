package edu.nps.noodles

import org.jasig.cas.client.validation.Cas20ServiceTicketValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.cas.ServiceProperties
import org.springframework.security.cas.authentication.CasAuthenticationProvider
import org.springframework.security.cas.web.CasAuthenticationEntryPoint
import org.springframework.security.cas.web.CasAuthenticationFilter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class TestCasAuthenticationUserDetailsService implements AuthenticationUserDetailsService
{

  UserDetails loadUserDetails(Authentication token)
      throws UsernameNotFoundException
  {
    List<GrantedAuthority> authorities = new ArrayList<>()
    authorities.add(new SimpleGrantedAuthority("ROLE_USER"))
    token.principal
    return new User(token.principal, "eoRCvxFHjXwj43FnQn4yW7", authorities)
  }
}

@EnableGlobalAuthentication
class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
  {
    auth
        .authenticationProvider(casAuthenticationProvider());
  }

  @Bean
  public ServiceProperties serviceProperties()
  {
    ServiceProperties serviceProperties = new ServiceProperties();
    serviceProperties.setService("http://localhost:8080/login/cas");
    serviceProperties.setSendRenew(false);
    return serviceProperties;
  }

  @Bean
  public CasAuthenticationProvider casAuthenticationProvider()
  {
    CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
    casAuthenticationProvider.setAuthenticationUserDetailsService(
        authenticationUserDetailsService()
    );
    casAuthenticationProvider.setServiceProperties(serviceProperties());
    casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
    casAuthenticationProvider.setKey("nps_ecco");
    return casAuthenticationProvider;
  }

  @Bean
  public AuthenticationUserDetailsService authenticationUserDetailsService()
  {
    return new TestCasAuthenticationUserDetailsService();
  }

  @Bean
  public Cas20ServiceTicketValidator cas20ServiceTicketValidator()
  {
    return new Cas20ServiceTicketValidator("https://cas.nps.edu/ecco")
  }

  @Bean
  public CasAuthenticationFilter casAuthenticationFilter() throws Exception
  {
    CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
    casAuthenticationFilter.setAuthenticationManager(authenticationManager());
    return casAuthenticationFilter;
  }

  @Bean
  public CasAuthenticationEntryPoint casAuthenticationEntryPoint()
  {
    CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
    casAuthenticationEntryPoint.setLoginUrl("https://cas.nps.edu/ecco/client");
    casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
    return casAuthenticationEntryPoint;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http
        .addFilter(casAuthenticationFilter())
    http
        .exceptionHandling()
        .authenticationEntryPoint(casAuthenticationEntryPoint())
    http
        .authorizeRequests()
        .antMatchers("/resources/**", "/beans").permitAll()
        .anyRequest().authenticated()
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth
        .authenticationProvider(casAuthenticationProvider());
  }
}
