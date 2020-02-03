package Helper;

public class DownloadCalculator {
    private static double bbwStream = 10000;
    private double size;
    private double stream;
    private SizeSuffix suffix;

    /**
     * Constructor for a Downloadcalculator
     *
     * @param size   Filesize
     * @param stream Streamspeed (in MBit/s)
     * @param suffix Size suffix for size of File
     */
    public DownloadCalculator(double size, double stream, SizeSuffix suffix) {
        this.size = size;
        this.stream = stream; //Stream in Mbit
        this.suffix = suffix;
        convertToMBit();
    }

    /**
     * Default constructor
     */
    public DownloadCalculator() {
    }

    /**
     * converts the Filesize from MB,GB or TB to MBit
     */
    private void convertToMBit() {
        switch (suffix) {
            case TB:
                size = size * 8000000.0;
                break;
            case GB:
                size = size * 8000.0;
                break;
            case MB:
                size = size * 8.0;
                break;
        }
    }

    /**
     * calculates downloadtime @ current location in seconds
     *
     * @return downloadtime @ current location in seconds
     */
    public double getDownloadtimeSec() {
        return size / stream;
    }

    /**
     * calculates downloadtime @ BBW in seconds
     *
     * @return downloadtime @ BBW in seconds
     */
    public double getBBWdownloadtimeSec() {
        return size / bbwStream;
    }

    /**
     * sets filesize and size suffix
     *
     * @param size       size of file
     * @param sizeSuffix size Suffix
     */
    public void setSize(double size, SizeSuffix sizeSuffix) {
        this.size = size;
        this.suffix = sizeSuffix;
        convertToMBit();
    }

    /**
     * sets streamspeed
     *
     * @param stream streamspeed (MBit/s)
     */
    public void setStream(double stream) {
        this.stream = stream;
    }

    /**
     * returns filesize in Mbit
     *
     * @return filesize in Mbit
     */
    public double getSize() {
        return size;
    }
}
