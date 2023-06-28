package com.nrslib.workshop.service.http.controllers.root

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class RootController {
    @RequestMapping("/")
    fun index(): String {
        return "redirect:/swagger-ui/index.html"
    }
}