package chp38;

import chp38.APIHandler.ServerAPI;
import chp38.Core.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //AppHandler han = new AppHandler();

        //han.run();
        ServerAPI server = new ServerAPI();

        server.sendMarketPrediction();
    }
}
