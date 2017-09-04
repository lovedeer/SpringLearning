package com.smart.processor;

import com.smart.domain.Leader;
import com.smart.domain.Leaderdelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String str : beanFactory.getBeanDefinitionNames()) {
            if (str.equals("leader2")) {
                Leader bean = (Leader) beanFactory.getBean("leader2");
                Leaderdelegate leaderdelegate = new Leaderdelegate(bean);
                leaderdelegate.service();
            }
        }

    }
}
