package at.fhv.sportsclub.ejb;

/*
      Created: 11.12.2018
      Author: Moritz W.
      Co-Authors: 
*/
public class SpringContextBeanFactory {
    private static SpringContextBean contextBean;

    public static SpringContextBean getInstance(){
        if (contextBean == null) {
            return new SpringContextBean();
        }
        return contextBean;
    }
}
