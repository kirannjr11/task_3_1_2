package habsida.spring.boot_security.demo.controllers;

import habsida.spring.boot_security.demo.models.User;
import habsida.spring.boot_security.demo.services.RoleService;
import habsida.spring.boot_security.demo.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;


    @GetMapping(value = "/admin/gen")
    public ModelAndView showUsers() {
        ModelAndView mov = new ModelAndView("/gen");
        mov.addObject("users",userService.listUsers());
        return mov;
    }

    @GetMapping("/admin/new")
    public ModelAndView newPerson(@ModelAttribute("user") User user) {
        ModelAndView mov = new ModelAndView("/new");
        mov.addObject("roles", roleService.listRoles());
        return mov;
    }

    @PostMapping("/admin/gen")
    public String create(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin/gen";
    }

    @GetMapping("/admin/edit")
    public ModelAndView edit(@RequestParam long id) {
        ModelAndView mav = new ModelAndView("/edit");
        User user = userService.userById(id).get();
        mav.addObject("user", user);
        mav.addObject("roles", roleService.listRoles());
        return mav;
    }

    @PostMapping("/admin/edit")
    public String update(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin/gen";
    }

    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam long id) {
        userService.remove(id);
        return "redirect:/admin/gen";
    }

    @GetMapping("/index")
    public String index() {
        return "/index";
    }

    @GetMapping("/user")
    public ModelAndView user(Principal principal) {
        ModelAndView mov = new ModelAndView("/user");
        mov.addObject("user", userService.findByName(principal.getName()));
        return mov;
    }
}
