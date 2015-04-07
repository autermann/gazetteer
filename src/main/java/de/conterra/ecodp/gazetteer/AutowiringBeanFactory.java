package de.conterra.ecodp.gazetteer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * TODO JavaDoc
 *
 * @author Christian Autermann
 */
public abstract class AutowiringBeanFactory<T>
        implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory
            .getLogger(SolrCoreContainerFactory.class);
    private ApplicationContext applicationContext;

    public AutowiringBeanFactory() {
    }

    protected abstract T create();

    protected abstract T postLoad(T t)
            throws Exception;

    public T createInstance()
            throws Exception {
        T instance = create();
        if (instance != null) {
            getApplicationContext()
                    .getAutowireCapableBeanFactory()
                    .autowireBean(instance);
        }
        return postLoad(instance);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        LOG.info("Setting application context: {}", applicationContext);
        this.applicationContext = applicationContext;
    }

    protected ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
