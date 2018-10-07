package chp38.Core.DailyInformation;

public class StatisticalInformation extends DailyInformation {

    /**
     * Double SMA
     */
    private Double SMA;

    /**
     * Get the SMA
     * @return Double
     */
    public Double getSMA() {
        return SMA;
    }

    /**
     * Set the SMA
     * @param SMA Double
     */
    public void setSMA(Double SMA) {
        this.SMA = SMA;
    }
}
