package edu.nps.noodles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.env.Environment

@SpringBootApplication
class Application
{

  @Autowired
  Environment env

  public static void main(String[] args)
  {
    Object[] sources = [
        Application.class
    ]
    SpringApplication.run(sources, args);
  }
}
