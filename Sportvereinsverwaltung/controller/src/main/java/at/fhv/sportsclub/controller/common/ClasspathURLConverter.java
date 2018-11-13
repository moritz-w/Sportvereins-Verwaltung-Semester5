package at.fhv.sportsclub.controller.common;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/*
      Created: 08.11.2018
      Author: m_std
      Co-Authors:
*/
@Component
public class ClasspathURLConverter implements Converter<ClassPathResource, String> {

    @Override
    public String convert(ClassPathResource classPathResource) {
        try {
            System.out.println(classPathResource.getURL().getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classPathResource.getPath();
    }
}
