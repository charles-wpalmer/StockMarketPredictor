package chp38.Factory.WekaHandler;

import chp38.Factory.AbstractFactory;
import chp38.Handler.IHandler;
import chp38.ML.IWeka;
import chp38.ML.WekaHandler;

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
                factory = new WekaHandler();
                break;
            case "":
                factory = null;
                break;
        }

        return factory;

    }
}
