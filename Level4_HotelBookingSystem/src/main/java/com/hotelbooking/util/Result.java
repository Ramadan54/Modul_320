package com.hotelbooking.util;

/**
 * Generic wrapper class for operation results
 * GENERICS: Demonstrates use of type parameter <T> with Diamond Operator
 * This class can hold any data type and success/error information
 *
 * @param <T> The type of data this result contains
 * @author Ramadan Asani
 * @version 1.0
 */
public class Result<T> {
    private boolean success;
    private T data;
    private String message;

    /**
     * Private constructor - use static factory methods instead
     */
    private Result(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    /**
     * Creates a successful result with data
     * GENERICS: Type T is inferred from the data parameter (Diamond Operator)
     *
     * @param <T> The type of data
     * @param data The result data
     * @return Success result containing the data
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(true, data, null);
    }

    /**
     * Creates a successful result with data and message
     *
     * @param <T> The type of data
     * @param data The result data
     * @param message Success message
     * @return Success result
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, data, message);
    }

    /**
     * Creates a failed result with error message
     * GENERICS: Type T is explicitly specified, data is null
     *
     * @param <T> The type of data (not used in failure)
     * @param message Error message
     * @return Failure result
     */
    public static <T> Result<T> failure(String message) {
        return new Result<>(false, null, message);
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        if (success) {
            return "Result[Success]: " + (message != null ? message : "OK") +
                    (data != null ? " | Data: " + data : "");
        } else {
            return "Result[Failure]: " + message;
        }
    }
}