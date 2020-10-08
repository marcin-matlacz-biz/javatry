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
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.size.BoxSize;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Number with color-box. <br>
 * Show answer by log() for question of javadoc.
 * @author jflute
 * @author your_name_here
 */
public class Step13NumberTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * How many integer-type values in color-boxes are between 0 and 54 (includes)? <br>
     * (カラーボックの中に入っているInteger型で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_IntegerOnly() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        long numValuesInRange = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Integer)
                .map(content -> (Integer) content)
                .filter(value -> value >= 0 && value <= 54)
                .count();
        log(numValuesInRange);
    }

    /**
     * How many number values in color-boxes are between 0 and 54? <br>
     * (カラーボックの中に入っている数値で、0から54までの値は何個ある？)
     */
    public void test_countZeroToFiftyFour_Number() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        BigDecimal low = new BigDecimal(0);
        BigDecimal high = new BigDecimal(54);
        long numValuesInRange = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof Number)
                .map(value -> new BigDecimal(value.toString()))
                .filter(value -> value.compareTo(low) > 0 && value.compareTo(high) < 0)
                .count();
        log(numValuesInRange);
    }

    /**
     * What color name is used by color-box that has integer-type content and the biggest width in them? <br>
     * (カラーボックスの中で、Integer型の Content を持っていてBoxSizeの幅が一番大きいカラーボックスの色は？)
     */
    public void test_findColorBigWidthHasInteger() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<String> colorName = colorBoxes.stream()
                .filter(colorBox -> colorBox.getSpaceList().stream().anyMatch(boxSpace -> boxSpace.getContent() instanceof Integer))
                .max(Comparator.comparingInt(b -> b.getSize().getWidth()))
                .map(ColorBox::getColor)
                .map(boxColor -> boxColor.getColorName());
        log(colorName.orElse(""));
    }

    /**
     * What is total of BigDecimal values in List in color-boxes? <br>
     * (カラーボックスの中に入ってる List の中の BigDecimal を全て足し合わせると？)
     */
    public void test_sumBigDecimalInList() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<BigDecimal> sum = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof List)
                .flatMap(content -> ((List<?>) content).stream())
                .filter(content -> content instanceof BigDecimal)
                .map(content -> new BigDecimal(content.toString()))
                .reduce((a, b) -> a.add(b));

        log(sum.orElse(new BigDecimal(0)));
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What key is related to value that is max number in Map that has only number in color-boxes? <br>
     * (カラーボックスに入ってる、valueが数値のみの Map の中で一番大きいvalueのkeyは？)
     */
    public void test_findMaxMapNumberValue() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<? extends Map.Entry<?, ?>> entry = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Map)
                .filter(content -> ((Map<?, ?>) content).values().stream().allMatch(value -> value instanceof Number))
                .flatMap(map -> ((Map<?, ?>) map).entrySet().stream())
                .max(Comparator.comparing(e -> new BigDecimal(e.getValue().toString())));
        log(entry);
    }

    /**
     * What is total of number or number-character values in Map in purple color-box? <br> 
     * (purpleのカラーボックスに入ってる Map の中のvalueの数値・数字の合計は？)
     */
    public void test_sumMapNumberValue() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<BigDecimal> objects = colorBoxes.stream()
                .filter(colorBox -> colorBox.getColor().getColorName().equals("purple"))
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof Map)
                .flatMap(content -> ((Map<?, ?>) content).values().stream())
                .map(value -> {
                    try {
                        return new BigDecimal(value.toString());
                    } catch (NumberFormatException e) {
                        return new BigDecimal(0);
                    }
                })
                .reduce((n1, n2) -> n1.add(n2));
        log(objects);
    }
}
