package Helper;

import java.util.zip.DataFormatException;

public class DownloadCalculator {
    private double size;
    private double stream;
    private SizeSuffix suffix;
    private static double bbwStream = 10000;

    public DownloadCalculator(double size, double stream, SizeSuffix suffix)   {
        this.size = size;
        this.stream = stream; //Stream in Mbit
        this.suffix = suffix;
        convertToMBit();
    }

    public DownloadCalculator() {
    }

    private void convertToMBit()   {
        switch (suffix){
            case TB: size = size * 8000000.0;
                break;
            case GB: size = size * 8000.0;
                break;
            case MB: size = size * 8.0;
                break;
        }
    }

    public double getDownloadtimeSec(){
        return size/stream;
    }

    public double getBBWdownloadtimeSec(){
        return size/bbwStream;
    }


    public void setSize(double size, SizeSuffix sizeSuffix){
        this.size = size;
        this.suffix= sizeSuffix;
        convertToMBit();
    }

    public void setStream(double stream) {
        this.stream = stream;
    }

    public double getSize() {
        return size;
    }
}
