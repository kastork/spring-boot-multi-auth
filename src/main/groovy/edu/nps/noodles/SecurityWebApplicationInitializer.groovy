package edu.nps.noodles

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer

class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer
{
  protected Class<?>[] getRootConfigClasses()
  {
    return [
        SecurityConfig.class
    ]
  }
}
