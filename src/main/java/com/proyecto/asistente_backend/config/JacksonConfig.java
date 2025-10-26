package com.proyecto.asistente_backend.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JacksonConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();

        // ✅ Soporte para entidades con Lazy Loading
        Hibernate6Module hibernate6Module = new Hibernate6Module();
        hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false);
        mapper.registerModule(hibernate6Module);

        // ✅ Soporte para LocalDate, LocalDateTime, etc.
        mapper.registerModule(new JavaTimeModule());

        // ✅ Evita que las fechas salgan como timestamps numéricos
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        converter.setObjectMapper(mapper);
        return converter;
    }
}
