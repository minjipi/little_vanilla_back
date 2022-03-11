package com.minji.idusbackend.admin;

import com.minji.idusbackend.admin.model.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @GetMapping("/write")
    public void write_get() {

    }

    @PostMapping("/write")
    public String write_post(ProductDTO productDTO) {

        adminService.saveService(productDTO);
        return "redirect:/";
    }



}
