package chp38.Factory;

import chp38.Factory.AppHandler.AppHandlerFactory;
import chp38.Factory.WekaHandler.WekaHandlerFactory;

public class FactoryProducer {

    /**
     * Get AppHandlerFactory instance
     * @return AbstractFactory
     */
    public static AbstractFactory getAppHandlerFactory(){
        return new AppHandlerFactory();
    }

    /**
     * Get WekaHandlerFactory instance
     * @return AbstractFactory
     */
    public static AbstractFactory getWekaHandler(){
        return new WekaHandlerFactory();
    }
}
