package chp38.Core.Factory.AppHandler;

import chp38.Core.Factory.AbstractFactory;
import chp38.Core.Handler.FundamentalAppHandler;
import chp38.Core.Handler.IHandler;
import chp38.Core.Handler.StatisticalAppHandler;
import chp38.ML.Weka.IWeka;

public class AppHandlerFactory extends AbstractFactory {

    /**
     * Get an IHandler instance (Fundamental|Statistical)
     * @param type String
     * @param commodity String
     * @param filesFolder String
     * @return IHandler|null
     */
    public IHandler getAppHandler(String type, String commodity, String filesFolder) {
        IHandler factory = null;

        switch(type){
            case "fundamental":
                factory = new FundamentalAppHandler(commodity, filesFolder);
                break;
            case "statistical":
                factory = new StatisticalAppHandler(commodity, filesFolder);
                break;
        }

        return factory;
    }

    /**
     * @param type String
     * @return null
     */
    public IWeka getWekaHandler(String type) {
        return null;
    }
}
