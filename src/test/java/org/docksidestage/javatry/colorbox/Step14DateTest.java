/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.colorbox;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.Temporal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.tomcat.jni.Local;
import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.impl.DoorColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.space.DoorBoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Date with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step14DateTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is date in color-boxes formatted as plus-separated (e.g. 2019+04+24)? <br>
     * (カラーボックスに入っている日付をプラス記号区切り (e.g. 2019+04+24) のフォーマットしたら？)
     */
    public void test_formatDate() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        String[] contents = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof LocalDate || content instanceof LocalDateTime)
                .map(date -> {
                    if (date instanceof LocalDate) {
                        return date;
                    } else {
                        return LocalDate.of(((LocalDateTime) date).getYear(), ((LocalDateTime) date).getMonthValue(),
                                ((LocalDateTime) date).getDayOfMonth());
                    }
                })
                .map(date -> (LocalDate) date)
                .map(date -> {
                    String builder = date.getYear() + "+" + date.getMonthValue() + "+" + date.getDayOfMonth();
                    return builder;
                })
                .toArray(String[]::new);
        log(contents);
    }

    /**
     * How is it going to be if the slash-separated date string in yellow color-box is converted to LocaDate and toString() is used? <br>
     * (yellowのカラーボックスに入っているSetの中のスラッシュ区切り (e.g. 2019/04/24) の日付文字列をLocalDateに変換してtoString()したら？)
     */
    public void test_parseDate() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        String[] contents = colorBoxes.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("yellow"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Set)
                .flatMap(content -> ((Set<?>) content).stream())
                .map(content -> content.toString())
                .filter(content -> content.matches("\\d{4}/\\d{2}/\\d{2}"))
                .map(content -> {
                    Pattern pattern = Pattern.compile("(\\d{4})/(\\d{2})/(\\d{2})"); // Year/Month/Day
                    Matcher matcher = pattern.matcher(content);
                    if (matcher.find()) {
                        int year = Integer.parseInt(matcher.group(1));
                        int month = Integer.parseInt(matcher.group(2));
                        int day = Integer.parseInt(matcher.group(3));

                        return LocalDate.of(year, month, day);
                    } else {
                        return null;
                    }
                })
                .filter(date -> date != null)
                .map(LocalDate::toString)
                .toArray(String[]::new);
        log(contents);
    }

    /**
     * What is total of month numbers of date in color-boxes? <br>
     * (カラーボックスに入っている日付の月を全て足したら？)
     */
    public void test_sumMonth() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> monthSum = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof LocalDate || content instanceof LocalDateTime || content instanceof Set)
                .flatMap(content -> {
                    if (content instanceof LocalDate) {
                        return Stream.of(content);
                    } else if (content instanceof LocalDateTime) {
                        return Stream.of(LocalDate.of(((LocalDateTime) content).getYear(), ((LocalDateTime) content).getMonthValue(),
                                ((LocalDateTime) content).getDayOfMonth()));
                    } else {
                        LocalDate[] date =
                                ((Set<?>) content).stream().map(Object::toString).filter(c -> c.matches("\\d{4}/\\d{2}/\\d{2}")).map(c -> {
                                    Pattern pattern = Pattern.compile("(\\d{4})/(\\d{2})/(\\d{2})"); // Year/Month/Day
                                    Matcher matcher = pattern.matcher(c);
                                    if (matcher.find()) {
                                        int year = Integer.parseInt(matcher.group(1));
                                        int month = Integer.parseInt(matcher.group(2));
                                        int day = Integer.parseInt(matcher.group(3));

                                        return LocalDate.of(year, month, day);
                                    } else {
                                        return null;
                                    }
                                }).toArray(LocalDate[]::new);
                        return Arrays.stream(date).sequential();
                    }
                })
                .filter(date -> date != null)
                .map(date -> ((LocalDate) date).getMonthValue())
                .reduce((m1, m2) -> m1 + m2);

        log(monthSum);
    }

    /**
     * Add 3 days to second-found date in color-boxes, what day of week is it? <br>
     * (カラーボックスに入っている二番目に見つかる日付に3日進めると何曜日？)
     */
    public void test_plusDays_weekOfDay() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<LocalDate> secondDateModified = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof LocalDate || content instanceof LocalDateTime || content instanceof Set)
                .flatMap(content -> {
                    if (content instanceof LocalDate) {
                        return Stream.of(content);
                    } else if (content instanceof LocalDateTime) {
                        return Stream.of(LocalDate.of(((LocalDateTime) content).getYear(), ((LocalDateTime) content).getMonthValue(),
                                ((LocalDateTime) content).getDayOfMonth()));
                    } else {
                        LocalDate[] date =
                                ((Set<?>) content).stream().map(Object::toString).filter(c -> c.matches("\\d{4}/\\d{2}/\\d{2}")).map(c -> {
                                    Pattern pattern = Pattern.compile("(\\d{4})/(\\d{2})/(\\d{2})"); // Year/Month/Day
                                    Matcher matcher = pattern.matcher(c);
                                    if (matcher.find()) {
                                        int year = Integer.parseInt(matcher.group(1));
                                        int month = Integer.parseInt(matcher.group(2));
                                        int day = Integer.parseInt(matcher.group(3));

                                        return LocalDate.of(year, month, day);
                                    } else {
                                        return null;
                                    }
                                }).toArray(LocalDate[]::new);
                        return Arrays.stream(date).sequential();
                    }
                })
                .filter(date -> date != null)
                .skip(1)
                .findFirst()
                .map(date -> {
                    return ((LocalDate) date).plusDays(3);
                });
        log(secondDateModified);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * How many days (number of day) are between two dates in yellow color-boxes? <br>
     * (yellowのカラーボックスに入っている二つの日付は何日離れている？)
     */
    public void test_diffDay() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<Long> contents = colorBoxes.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("yellow"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Set || content instanceof LocalDate)
                .flatMap(content -> {
                    if (content instanceof Set) {
                        return ((Set<?>) content).stream().map(Object::toString).filter(c -> c.matches("\\d{4}/\\d{2}/\\d{2}")).map(c -> {
                            Pattern pattern = Pattern.compile("(\\d{4})/(\\d{2})/(\\d{2})"); // Year/Month/Day
                            Matcher matcher = pattern.matcher(c);
                            if (matcher.find()) {
                                int year = Integer.parseInt(matcher.group(1));
                                int month = Integer.parseInt(matcher.group(2));
                                int day = Integer.parseInt(matcher.group(3));

                                return LocalDate.of(year, month, day);
                            } else {
                                return null;
                            }
                        });
                    } else {
                        return Stream.of(content);
                    }

                })
                .map(date -> ((LocalDate) date).toEpochDay())
                .reduce((d1, d2) -> Math.abs(d1 - d2));
        log(contents);
    }

    /**
     * Find LocalDate in yellow color-box,
     * and add same color-box's LocalDateTime's seconds as number of months to it,
     * and add red color-box's Long number as days to it,
     * and subtract the first decimal place of BigDecimal that has three(3) as integer in List in color-boxes from it,
     * What date is it? <br>
     * (yellowのカラーボックスに入っているLocalDateに、同じカラーボックスに入っているLocalDateTimeの秒数を月数として足して、
     * redのカラーボックスに入っているLong型を日数として足して、カラーボックスに入っているリストの中のBigDecimalの整数値が3の小数点第一位の数を日数として引いた日付は？)
     */
    public void test_birthdate() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        LocalDate yellowBoxLocalDate = colorBoxes.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("yellow"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof LocalDate)
                .map(date -> (LocalDate) date)
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        LocalDateTime yellowBoxLocalDateTime = colorBoxes.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("yellow"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof LocalDateTime)
                .map(date -> (LocalDateTime) date)
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        Long redBoxLong = colorBoxes.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("red"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Long)
                .map(number -> (Long) number)
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        Integer decimalPlace =
                colorBoxes.stream()
                        .flatMap(colorBox -> colorBox.getSpaceList().stream())
                        .map(BoxSpace::getContent)
                        .flatMap(content -> {
                            if (content instanceof Iterable) {
                                return StreamSupport.stream(((Iterable<?>) content).spliterator(), false);
                            } else {
                                return Stream.of(content);
                            }
                        })
                        .filter(content -> content instanceof BigDecimal)
                        .map(content -> (BigDecimal) content)
                        .filter(bigDecimal1 -> bigDecimal1.intValue() == 3)
                        .map(bigDecimal1 -> bigDecimal1.movePointRight(1).intValue() % 30)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException());

        LocalDate newDate = null;
        newDate = yellowBoxLocalDate.plusMonths(yellowBoxLocalDateTime.getSecond());
        newDate = newDate.plusDays(redBoxLong);
        newDate = newDate.minusDays(decimalPlace);
        log(newDate);
    }

    /**
     * What second is LocalTime in color-boxes? <br>
     * (カラーボックスに入っているLocalTimeの秒は？)
     */
    public void test_beReader() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        LocalTime localTime = colorBoxes.stream()
                .flatMap(colorBox -> {
                    if (colorBox instanceof DoorColorBox) {
                        return ((DoorColorBox) colorBox).getDoorSpaceList().stream().peek(DoorBoxSpace::openTheDoor);
                    } else {
                        return colorBox.getSpaceList().stream();
                    }
                })
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof LocalTime)
                .map(date -> (LocalTime) date)
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        log(localTime.getSecond());
    }
}
