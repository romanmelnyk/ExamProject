package sharecare.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sharecare.config.RuntimeProperties;

@RestController
@RequestMapping("config")
public class ConfigController {

    private final RuntimeProperties runtimeProperties;

    @Autowired
    public ConfigController(RuntimeProperties runtimeProperties) {
        this.runtimeProperties = runtimeProperties;
    }

    @GetMapping
    public RuntimeProperties getConfig() {
        return runtimeProperties;
    }

    @PostMapping
    public RuntimeProperties updateConfig(@RequestBody RuntimeProperties config) {
        BeanUtils.copyProperties(config, runtimeProperties);
        return runtimeProperties;
    }
}
