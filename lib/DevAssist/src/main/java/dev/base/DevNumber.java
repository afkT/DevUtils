package dev.base;

import dev.base.number.INumberListener;
import dev.base.number.INumberOperate;

/**
 * detail: 数量实体类
 * @author Ttt
 */
public class DevNumber<T>
        extends DevObject<T>
        implements INumberOperate<DevNumber<T>> {

    public DevNumber() {
    }

    public DevNumber(final T value) {
        super(value);
    }

    public DevNumber(
            final T value,
            final Object tag
    ) {
        super(value, tag);
    }

    // ==================
    // = INumberOperate =
    // ==================

    @Override
    public boolean isMinNumber() {
        return false;
    }

    @Override
    public boolean isMinNumber(int number) {
        return false;
    }

    @Override
    public boolean isLessThanMinNumber(int number) {
        return false;
    }

    @Override
    public boolean isGreaterThanMinNumber(int number) {
        return false;
    }

    @Override
    public boolean isMaxNumber() {
        return false;
    }

    @Override
    public boolean isMaxNumber(int number) {
        return false;
    }

    @Override
    public boolean isLessThanMaxNumber(int number) {
        return false;
    }

    @Override
    public boolean isGreaterThanMaxNumber(int number) {
        return false;
    }

    @Override
    public <CTO> CTO getObject() {
        return null;
    }

    @Override
    public DevNumber<T> setObject(Object object) {
        return null;
    }

    @Override
    public int getMinNumber() {
        return 0;
    }

    @Override
    public DevNumber<T> setMinNumber(int minNumber) {
        return null;
    }

    @Override
    public int getMaxNumber() {
        return 0;
    }

    @Override
    public DevNumber<T> setMaxNumber(int maxNumber) {
        return null;
    }

    @Override
    public DevNumber<T> setMinMaxNumber(
            int minNumber,
            int maxNumber
    ) {
        return null;
    }

    @Override
    public int getCurrentNumber() {
        return 0;
    }

    @Override
    public DevNumber<T> setCurrentNumber(int currentNumber) {
        return null;
    }

    @Override
    public DevNumber<T> setCurrentNumber(
            int currentNumber,
            boolean isTriggerListener
    ) {
        return null;
    }

    @Override
    public int getResetNumber() {
        return 0;
    }

    @Override
    public DevNumber<T> setResetNumber(int resetNumber) {
        return null;
    }

    @Override
    public boolean isAllowNegative() {
        return false;
    }

    @Override
    public DevNumber<T> setAllowNegative(boolean allowNegative) {
        return null;
    }

    @Override
    public DevNumber<T> numberChange(int number) {
        return null;
    }

    @Override
    public DevNumber<T> addNumber() {
        return null;
    }

    @Override
    public DevNumber<T> subtractionNumber() {
        return null;
    }

    @Override
    public INumberListener getNumberListener() {
        return null;
    }

    @Override
    public DevNumber<T> setNumberListener(INumberListener iNumberListener) {
        return null;
    }
}