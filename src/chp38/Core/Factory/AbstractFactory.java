package chp38.Core.Factory;

import chp38.Core.Handler.IHandler;
import chp38.ML.Weka.IWeka;

public abstract class AbstractFactory {

    /**
     * Get an IHandler instance (Fundamental|Statistical)
     * @param type String
     * @param commodity String
     * @param filesFolder String
     * @return IHandler|null
     */
    public abstract IHandler getAppHandler(String type, String commodity, String filesFolder);

    /**
     * Get an IHandler instance (NB|J48)
     * @param type String
     * @return IWeka|null
     */
    public abstract IWeka getWekaHandler(String type);

}
