package computers;

public class InitialConstant implements SurfaceComputer {
    private double currentStorage;
    private final double maxStorage;
    private final double constantRate;
    private static final double CONVERSION = 60 * 60;

    public InitialConstant(double initialStorage, double maxStorage, double constantRate) {
        this.currentStorage = initialStorage; //in mm
        this.maxStorage = maxStorage; //in mm
        this.constantRate = constantRate; //in mm/hr
    }

    public double freeStorage() {
        //free storage in mm
        return maxStorage - currentStorage;
    }

    public double saturatedRunoff(double rainfallRate, double timestepSeconds) {
        //depth of runoff in mm
        //rainfall rate in mm/s
        //timestepSeconds in s
        double netRate = rainfallRate - (constantRate / CONVERSION);
        if(netRate < 0) {
            return 0;
        } else {
            return netRate * timestepSeconds;
        }
    }

    public double unsaturatedRunoff(double precipitationDepth, double timestepSeconds) {
        /*
        If depth < free storage, then runoff = 0 and current storage += depth
        If depth >= free storage, then net depth = depth - free storage and runoff = net depth - (constant rate / conv) * timestep
         */
        if(precipitationDepth < freeStorage()) {
            currentStorage += precipitationDepth;
            return 0;
        } else {
            double netDepth = precipitationDepth - freeStorage();
            currentStorage = maxStorage;
            double computedRunoff = netDepth - ((constantRate / CONVERSION) * timestepSeconds);
            return Math.max(0d, computedRunoff);
        }

    }

    @Override
    public double computeRunoffTimestep(double precipitationDepth, double timestepSeconds) {
        //precipitationDepth in mm
        //timestepSeconds in seconds
        double rate = precipitationDepth / timestepSeconds;
        if(freeStorage() == 0) {
            return saturatedRunoff(rate, timestepSeconds);
        } else {
            return unsaturatedRunoff(precipitationDepth, timestepSeconds);
        }
    }
}
