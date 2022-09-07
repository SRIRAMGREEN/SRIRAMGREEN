package com.timesheet.entity.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
    //modelMapper sometimes may not able to match properties from the reference type in order to resolve we use this config

//              modelMapper.getConfiguration().setMactchingStrategy(MatchingStrategies.LOOSE);
