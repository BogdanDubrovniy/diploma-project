package com.dubrovnyi.bohdan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import static com.dubrovnyi.bohdan.constants.ConstantClass.*;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages =
        {
                REPOSITORIES_PACKAGE,
                SERVICES_PACKAGE,
                "net.sf.oval.integration.spring"
        }
)
@Import(HibernateConfig.class)
@EnableTransactionManagement
public class RootConfiguration {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix(PAGES_PATH);
        viewResolver.setSuffix(PAGES_EXTENSION);

        return viewResolver;
    }


}