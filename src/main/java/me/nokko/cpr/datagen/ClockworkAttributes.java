package me.nokko.cpr.datagen;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ClockworkAttributes {
    int quality();

    int speed();

    int memory();
}
