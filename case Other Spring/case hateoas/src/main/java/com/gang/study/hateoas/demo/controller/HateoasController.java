package com.gang.study.hateoas.demo.controller;

import com.gang.study.hateoas.demo.entity.Greeting;

/**
 * @Classname HateoasController
 * @Description TODO
 * @Date 2020/8/4 17:55
 * @Created by zengzg
 */
@RestController
public class HateoasController {

    private static final String TEMPLATE = "Hello, %s!";

    @RequestMapping("/greeting")
    public HttpEntity<Greeting> greeting(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name) {

        Greeting greeting = new Greeting(String.format(TEMPLATE, name));
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());

        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
