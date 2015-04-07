package de.conterra.ecodp.gazetteer;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;


public class SolrCoreContainerFactory
        extends AutowiringBeanFactory<CoreContainer>
        implements ServletContextAware {

    private static final Logger LOG = LoggerFactory
            .getLogger(SolrCoreContainerFactory.class);
    private static final String PATH = "/WEB-INF/solr.home";

    private ServletContext servletContext;

    public SolrCoreContainerFactory() {
        LOG.info("Init");
    }

    @Override
    protected CoreContainer create() {
        String home = getSolrHome();
        SolrResourceLoader loader = new SolrResourceLoader(home);
        CoreContainer coreContainer = new CoreContainer(loader);
        return coreContainer;
    }

    private String getSolrHome() {
        String path = servletContext.getRealPath(PATH);
        if (path != null) {
            File home = new File(path).getAbsoluteFile();
            if (home.exists()  && home.isDirectory()) {
                return home.getAbsolutePath();
            }
        }
        return SolrResourceLoader.locateSolrHome();
    }


    @Override
    protected CoreContainer postLoad(CoreContainer container) {
        container.load();
        return container;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        LOG.info("Setting servlet context: {}", servletContext);
        this.servletContext = servletContext;
    }

}
