package edu.nps.noodles

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

@RestController
class NoodleBasicController
{
  @RequestMapping("/api/noodle")
  def supplyNoodlesAsJson(Principal principal)
  {
    NoodleDAO noodle = new NoodleDAO(
        id: 1,
        name: "macaroni",
        comment: "good with cheese"
    )

    return [
        user  : principal.name,
        noodle: noodle
    ]
  }
}
