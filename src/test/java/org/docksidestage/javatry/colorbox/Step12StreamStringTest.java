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

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.sun.xml.internal.bind.v2.TODO;
import org.docksidestage.bizfw.colorbox.ColorBox;
import org.docksidestage.bizfw.colorbox.space.BoxSpace;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.DevilBox;
import org.docksidestage.bizfw.colorbox.yours.YourPrivateRoom.DevilBoxTextNotFoundException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of String with color-box, using Stream API you can. <br>
 * Show answer by log() for question of javadoc.
 * <pre>
 * addition:
 * o e.g. "string in color-boxes" means String-type content in space of color-box
 * o don't fix the YourPrivateRoom class and color-box classes
 * </pre>
 * @author jflute
 * @author your_name_here
 */
public class Step12StreamStringTest extends PlainTestCase {

    // ===================================================================================
    //                                                                            length()
    //                                                                            ========
    /**
     * What is color name length of first color-box? <br>
     * (最初のカラーボックスの色の名前の文字数は？)
     */
    public void test_length_basic() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        String answer = colorBoxList.stream()
                .findFirst()
                .map(colorBox -> colorBox.getColor().getColorName())
                .map(colorName -> colorName.length() + " (" + colorName + ")")
                .orElse("*not found");
        log(answer);
        assertEquals(answer, "5 (green)");
    }

    /**
     * Which string has max length in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長い文字列は？)
     */
    public void test_length_findMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Object[] contents = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .toArray(Object[]::new);
        String maxLenString = null;
        for (Object content : contents) {
            if (maxLenString == null) {
                maxLenString = content.toString();
            } else if (content.toString().length() > maxLenString.length()) {
                maxLenString = content.toString();
            }
        }
        if (maxLenString != null)
            log(maxLenString);
        else
            log("No strings in color-boxes");
    }

    /**
     * How many characters are difference between max and min length of string in color-boxes? <br>
     * (カラーボックスに入ってる文字列の中で、一番長いものと短いものの差は何文字？)
     */
    public void test_length_findMaxMinDiff() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Object[] contents = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .toArray(Object[]::new);
        String maxLenString = null;
        String minLenString = null;
        for (Object content : contents) {
            if (maxLenString == null) {
                maxLenString = content.toString();
            }
            if (minLenString == null) {
                minLenString = content.toString();
            }
            if (content.toString().length() > maxLenString.length()) {
                maxLenString = content.toString();
            }
            if (content.toString().length() < minLenString.length()) {
                minLenString = content.toString();
            }
        }
        if (maxLenString != null) {
            log(maxLenString.length() - minLenString.length());
        } else {
            log("No strings in color-boxes.");
        }
    }

    // has small #adjustmemts from ClassicStringTest
    //  o sort allowed in Stream
    /**
     * Which value (toString() if non-string) has second-max length in color-boxes? (sort allowed in Stream)<br>
     * (カラーボックスに入ってる値 (文字列以外はtoString()) の中で、二番目に長い文字列は？ (Streamでのソートありで))
     */
    public void test_length_findSecondMax() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> contents = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .map(content -> String.valueOf(content))
                .sorted((s1, s2) -> s2.length() - s1.length())
                .findFirst();
        log(contents.orElse("Empty contents of color-boxes."));
    }

    /**
     * How many total lengths of strings in color-boxes? <br>
     * (カラーボックスに入ってる文字列の長さの合計は？)
     */
    public void test_length_calculateLengthSum() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> totalLength = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> String.valueOf(content))
                .map(content -> content.length())
                .reduce((s1, s2) -> s1 + s2);
        log(totalLength.orElseThrow(() -> new RuntimeException("Not enough strings in color-boxes")));
    }

    /**
     * Which color name has max length in color-boxes? <br>
     * (カラーボックスの中で、色の名前が一番長いものは？)
     */
    public void test_length_findMaxColorSize() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> longestColour = colorBoxList.stream()
                .map(colorBox -> colorBox.getColor().getColorName())
                .sorted((c1, c2) -> c2.length() - c1.length())
                .findFirst();
        log(longestColour.orElseThrow(() -> new RuntimeException("Not enough strings in color-boxes")));
    }

    // ===================================================================================
    //                                                            startsWith(), endsWith()
    //                                                            ========================
    /**
     * What is color in the color-box that has string starting with "Water"? <br>
     * ("Water" で始まる文字列をしまっているカラーボックスの色は？)
     */
    public void test_startsWith_findFirstWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> longestColour = colorBoxList.stream()
                .filter(box -> box.getSpaceList()
                        .stream()
                        .map(BoxSpace::getContent)
                        .filter(content -> content instanceof String)
                        .anyMatch(content -> String.valueOf(content).startsWith("Water")))
                .map(colorBox -> colorBox.getColor().getColorName())
                .findFirst();
        log(longestColour.orElseThrow(() -> new RuntimeException("Not enough strings in color-boxes")));
    }

    /**
     * What is color in the color-box that has string ending with "front"? <br>
     * ("front" で終わる文字列をしまっているカラーボックスの色は？)
     */
    public void test_endsWith_findLastWord() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<String> longestColour = colorBoxList.stream()
                .filter(box -> box.getSpaceList()
                        .stream()
                        .map(BoxSpace::getContent)
                        .filter(content -> content instanceof String)
                        .anyMatch(content -> String.valueOf(content).endsWith("front")))
                .map(colorBox -> colorBox.getColor().getColorName())
                .findFirst();
        log(longestColour.orElseThrow(() -> new RuntimeException("Not enough strings in color-boxes")));
    }

    // ===================================================================================
    //                                                            indexOf(), lastIndexOf()
    //                                                            ========================
    /**
     * What number character is starting with first "front" of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列で、最初の "front" は何文字目から始まる？)
     */
    public void test_indexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> idx = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList()
                        .stream()
                        .map(BoxSpace::getContent)
                        .filter(content -> content instanceof String)
                        .filter(content -> String.valueOf(content).endsWith("front"))
                        .map(content -> ((String) content).indexOf("front")))
                .filter(index -> index > -1)
                .findFirst();
        log(idx.orElseThrow(() -> new RuntimeException("Not enough strings in color-boxes")));
    }

    /**
     * What number character is starting with the late "ど" of string containing plural "ど"s in color-boxes? (e.g. "どんどん" => 3) <br>
     * (カラーボックスに入ってる「ど」を二つ以上含む文字列で、最後の「ど」は何文字目から始まる？ (e.g. "どんどん" => 3))
     */
    public void test_lastIndexOf_findIndex() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> idx = colorBoxList.stream()
                .flatMap(box -> box.getSpaceList()
                        .stream()
                        .map(BoxSpace::getContent)
                        .filter(content -> content instanceof String)
                        .map(content -> String.valueOf(content))
                        .filter(content -> (content.length() - content.replace("ど", "").length()) > 1)
                        .map(content -> ((String) content).lastIndexOf("ど")))
                .filter(index -> index > -1)
                .findFirst();
        log(idx.orElseThrow(() -> new RuntimeException("Not enough strings in color-boxes")));
    }

    // ===================================================================================
    //                                                                         substring()
    //                                                                         ===========
    /**
     * What character is first of string ending with "front" in color-boxes? <br>
     * (カラーボックスに入ってる "front" で終わる文字列の最初の一文字は？)
     */
    public void test_substring_findFirstChar() {
        List<ColorBox> colorBoxList = new YourPrivateRoom().getColorBoxList();
        Character[] characters = colorBoxList.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(boxSpace -> boxSpace.getContent())
                .filter(content -> content instanceof String)
                .map(content -> content.toString())
                .filter(content -> content.endsWith("front"))
                .map(content -> content.charAt(0))
                .toArray(Character[]::new);
        log(characters);
    }

    /**
     * What character is last of string starting with "Water" in color-boxes? <br>
     * (カラーボックスに入ってる "Water" で始まる文字列の最後の一文字は？)
     */
    public void test_substring_findLastChar() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Character[] characters = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof String)
                .map(Object::toString)
                .filter(content -> content.startsWith("Water"))
                .map(content -> content.charAt(content.length() - 1))
                .toArray(Character[]::new);
        log(characters);
    }

    // ===================================================================================
    //                                                                           replace()
    //                                                                           =========
    /**
     * How many characters does string that contains "o" in color-boxes and removing "o" have? <br>
     * (カラーボックスに入ってる "o" (おー) を含んだ文字列から "o" を全て除去したら何文字？)
     */
    public void test_replace_remove_o() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Integer[] numberOfCharacters = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof String)
                .map(Object::toString)
                .filter(content -> content.contains("o"))
                .map(content -> content.replace("o", "").length())
                .toArray(Integer[]::new);
        log(numberOfCharacters);
    }

    /**
     * What string is path string of java.io.File in color-boxes, which is replaced with "/" to Windows file separator? <br>
     * カラーボックスに入ってる java.io.File のパス文字列のファイルセパレーターの "/" を、Windowsのファイルセパレーターに置き換えた文字列は？
     */
    public void test_replace_fileseparator() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        String[] paths = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof File)
                .map(file -> ((File) file).getPath())
                .map(path -> path.replace("/", "\\"))
                .toArray(String[]::new);
        log(paths);
    }

    // ===================================================================================
    //                                                                    Welcome to Devil
    //                                                                    ================
    /**
     * What is total length of text of DevilBox class in color-boxes? <br>
     * (カラーボックスの中に入っているDevilBoxクラスのtextの長さの合計は？)
     */
    public void test_welcomeToDevil() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        Optional<Integer> totalLength = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof DevilBox)
                .map(devilbox -> {
                    ((DevilBox) devilbox).wakeUp();
                    ((DevilBox) devilbox).allowMe();
                    ((DevilBox) devilbox).open();
                    try {
                        return ((DevilBox) devilbox).getText();
                    } catch (DevilBoxTextNotFoundException e) {
                        return "";
                    }
                })
                .map(text -> text.length())
                .reduce((l1, l2) -> l1 + l2);
        log(totalLength);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What string is converted to style "map:{ key = value ; key = value ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = value ; ... }" という形式で表示すると？)
     */
    public void test_showMap_flat() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        String[] simpleMaps = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof YourPrivateRoom.SecretBox)
                .map(secretBox -> ((YourPrivateRoom.SecretBox) secretBox).getText())
                .filter(text -> (text.length() - text.replace("map:{", "").length()) == 5)
                .toArray(String[]::new);
        for (String map : simpleMaps)
            log(map);
    }

    /**
     * What string is converted to style "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" from java.util.Map in color-boxes? <br>
     * (カラーボックスの中に入っている java.util.Map を "map:{ key = value ; key = map:{ key = value ; ... } ; ... }" という形式で表示すると？)
     */
    public void test_showMap_nested() {
        List<ColorBox> colorBoxes = new YourPrivateRoom().getColorBoxList();
        String[] simpleMaps = colorBoxes.stream()
                .flatMap(colorBox -> colorBox.getSpaceList().stream())
                .map(BoxSpace::getContent)
                .filter(content -> content instanceof YourPrivateRoom.SecretBox)
                .map(secretBox -> ((YourPrivateRoom.SecretBox) secretBox).getText())
                .filter(text -> (text.length() - text.replace("map:{", "").length()) > 5)
                .toArray(String[]::new);
        for (String map : simpleMaps)
            log(map);
    }

    // TODO: Try this after figuring out the meaning of question
    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    // has small #adjustmemts from ClassicStringTest
    //  o comment out because of too difficult to be stream?
    ///**
    // * What string of toString() is converted from text of SecretBox class in upper space on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのupperスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_flat() {
    //}
    //
    ///**
    // * What string of toString() is converted from text of SecretBox class in both middle and lower spaces on the "white" color-box to java.util.Map? <br>
    // * (whiteのカラーボックスのmiddleおよびlowerスペースに入っているSecretBoxクラスのtextをMapに変換してtoString()すると？)
    // */
    //public void test_parseMap_nested() {
    //}
}
