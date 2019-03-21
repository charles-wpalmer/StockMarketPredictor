package chp38.Core.Factory.WekaHandler;

import chp38.Core.Factory.AbstractFactory;
import chp38.Core.Handler.IHandler;
import chp38.ML.Weka.IWeka;
import chp38.ML.Weka.J48Handler;
import chp38.ML.Weka.NBHandler;

public class WekaHandlerFactory extends AbstractFactory {

    /**
     * @param type String
     * @return null
     */
    public IHandler getAppHandler(String type, String commodity, String folder) {
        return null;
    }

    /**
     * Get IWeka Instance
     * @param type String
     * @return IWeka|null
     */
    public IWeka getWekaHandler(String type) {
        IWeka factory = null;

        switch(type){
            case "NB":
                factory = new NBHandler();
                break;
            case "J48":
                factory = new J48Handler();
                break;
        }

        return factory;

    }
}
