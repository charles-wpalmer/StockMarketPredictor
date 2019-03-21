package chp38.Core.Factory;

import chp38.Core.Factory.AppHandler.AppHandlerFactory;
import chp38.Core.Factory.WekaHandler.WekaHandlerFactory;

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
