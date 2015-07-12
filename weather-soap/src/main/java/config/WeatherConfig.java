package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

/**
 * Created by lutay.d on 12.07.2015.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:weather.properties")
public class WeatherConfig {

    @Value("${serviceUri}")
    private String serviceUri = "111";

    @Bean
    public WebServiceTemplate weatherTemplate(){
        System.out.println(serviceUri);
        WebServiceTemplate templ = new WebServiceTemplate();
        templ.setMessageFactory(messageFactory());
        templ.setDefaultUri(serviceUri);
        templ.setMarshaller(marshaller());
        templ.setUnmarshaller(marshaller());
        return templ;
    }

    @Bean
    public SaajSoapMessageFactory messageFactory(){
        return new SaajSoapMessageFactory();
    }

    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("model.soap");
        return marshaller;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
