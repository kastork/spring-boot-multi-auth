package edu.nps.noodles

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

import java.security.Principal

@Controller
class NoodleCasController
{
  @RequestMapping("/noodle")
  String supplyNoodlePage(Principal principal, Model model)
  {
    // Spring doesn't like groovy strings ("") here
    // so use java strings ('')

    model.addAttribute('user', principal.name.toString())
    model.addAttribute('id', '2')
    model.addAttribute('name', 'Gnocci')
    model.addAttribute('comment', 'Good with clams')
    return 'noodle'
  }
}
