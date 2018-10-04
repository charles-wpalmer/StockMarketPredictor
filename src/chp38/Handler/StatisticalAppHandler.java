package chp38.Handler;

public class StatisticalAppHandler implements IHandler{

    /**
     * String commodity
     */
    private String commodity;

    /**
     * String filesFolder
     */
    private String filesFolder;

    /**
     * Constructor
     * @param commodity String
     * @param filesFolder String
     */
    public StatisticalAppHandler(String commodity, String filesFolder) {
        this.commodity = commodity;
        this.filesFolder = filesFolder;
    }

    @Override
    public String run() throws Exception {
        return null;
    }

    @Override
    public void displayMenu() throws Exception {

    }

    @Override
    public void prepareData() throws Exception {

    }

}
