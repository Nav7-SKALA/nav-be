package com.skala.nav7.api.rolemodel.controller;

import com.skala.nav7.api.rolemodel.service.RoleModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class RoleModelController {
    private final RoleModelService roleModelService;
}
