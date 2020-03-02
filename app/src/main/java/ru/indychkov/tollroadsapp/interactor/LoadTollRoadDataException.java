package ru.indychkov.tollroadsapp.interactor;

public class LoadTollRoadDataException extends Exception {
    LoadTollRoadDataException(String message, Throwable cause) {

        super(message, cause);
        System.out.println("Ошибка " + message+cause);
    }
}
