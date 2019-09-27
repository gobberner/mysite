package kr.co.itcen.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


public class ContextLoadListener implements ServletContextListener {

  
    public ContextLoadListener() {
    
    }

    public void contextInitialized(ServletContextEvent servletContextEvent)  { 
    	String contextconfigLocation = servletContextEvent.getServletContext().getInitParameter("contextConfigLocation");
    	System.out.println("Mysite2 Application Starts..."+contextconfigLocation);
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    
    }
}
