package io.github.vino42.support;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import static io.github.vino42.support.ServiceResponseCodeEnum.*;


/**
 * The class Wrap mapper.
 */
public final class ResultMapper {

    /**
     * Instantiates a new wrap mapper.
     */
    private ResultMapper() {
        // no arg constructor
    }


    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status, String message) {

        return wrap(status, message, null);
    }

    /**
     * Wrap.
     *
     * @param <E>     the element type
     * @param status  the status
     * @param message the message
     * @param o       the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status, String message, E o) {

        return create(status, message, o);
    }

    /**
     * Wrap.
     *
     * @param <E>    the element type
     * @param status the status
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(int status) {

        return wrap(status, null);
    }

    /**
     * Wrap.
     *
     * @param <E> the element type
     * @param e   the e
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> wrap(Exception e) {

        return new ServiceResponseResult<>(SYSTEM_INTERNAL_ERROR.getStatus(), e.getMessage());
    }

    /**
     * Un wrapper.
     *
     * @param <E>     the element type
     * @param wrapper the wrapper
     * @return the e
     */
    public static <E> E unWrap(ServiceResponseResult<E> wrapper) {

        return wrapper.getData();
    }

    /**
     * Wrap ERROR. status=100
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> illegalArgument() {

        return wrap(BAD_REQUEST.status, BAD_REQUEST.message);
    }

    /**
     * Wrap ERROR. status=401001
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> pleaseLogin() {

        return wrap(AUTH_401_NEED_AUTH.status, AUTH_401_NEED_AUTH.message);
    }

    /**
     * Wrap ERROR. status=100
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> illegalArgument(String msg) {

        return wrap(BAD_REQUEST.status, msg);
    }

    /**
     * Wrap ERROR. status=500
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error() {

        return error(SYSTEM_INTERNAL_ERROR.getStatus(), SYSTEM_INTERNAL_ERROR.getMessage(), null);

    }

    /**
     * Wrap ERROR. status=500
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(ServiceResponseCodeEnum responseEnum) {
        return error(responseEnum.getStatus(), responseEnum.message, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(String message, String attache) {
        return error(SYSTEM_INTERNAL_ERROR.getStatus(), message, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(String message) {
        return error(SYSTEM_INTERNAL_ERROR.getStatus(), message, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(int status, String message) {

        return error(status, message, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(int status, String message, String errorDetail) {
        return error(status, message, null);
    }

    /**
     * Error wrapper.
     *
     * @param <E>     the type parameter
     * @param message the message
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> error(int status, String message, E data) {

        return create(status, message, data);
    }

    public static <E> ServiceResponseResult<E> error(Exception e) {
        return error(SYSTEM_INTERNAL_ERROR.status, SYSTEM_INTERNAL_ERROR.message, null);
    }

    /**
     * Wrap SUCCESS. status=200
     *
     * @param <E> the element type
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok() {
        return create(SUCCESS.status, SUCCESS.message, null);
    }

    public static <E> ServiceResponseResult<E> failed() {
        return create(OPT_FAILED.status, OPT_FAILED.message, null);
    }

    private static <T> ServiceResponseResult<T> create(int status, String message, T data) {
        ServiceResponseResult<T> result = new ServiceResponseResult<>();
        if (StringUtils.isNotBlank(message)) {
            result.message(message);
        }

        result.status(status);

        if (ObjectUtils.isNotEmpty(data)) {
            result.data(data);
        }

        return result;
    }


    /**
     * Ok wrapper.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok(E o, String msg) {
        return create(SUCCESS.status, msg, o);
    }


    /**
     * Ok wrapper.
     *
     * @param <E> the type parameter
     * @param o   the o
     * @return the wrapper
     */
    public static <E> ServiceResponseResult<E> ok(E o) {
        return new ServiceResponseResult<>(SUCCESS.status, SUCCESS.message, o);
    }


}
