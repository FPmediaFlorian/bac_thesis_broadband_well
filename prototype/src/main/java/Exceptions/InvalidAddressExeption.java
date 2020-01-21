package Exceptions;

public class InvalidAddressExeption extends Exception{
    public InvalidAddressExeption(){}
    public InvalidAddressExeption(String message){
        super(message);
    }
}
