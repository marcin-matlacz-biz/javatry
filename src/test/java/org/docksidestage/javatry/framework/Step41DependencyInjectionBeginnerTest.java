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
package org.docksidestage.javatry.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.objanimal.Dog;
import org.docksidestage.bizfw.basic.supercar.SupercarDealer;
import org.docksidestage.bizfw.di.cast.TooLazyDog;
import org.docksidestage.bizfw.di.container.SimpleDiContainer;
import org.docksidestage.bizfw.di.container.component.DiContainerModule;
import org.docksidestage.bizfw.di.usingdi.*;
import org.docksidestage.bizfw.di.usingdi.settings.UsingDiModule;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Dependency Injection (DI) as beginner level. <br>
 * Show answer by log() or write answer on comment for question of javadoc.
 * @author jflute
 * @author Marcin Matlacz
 */
public class Step41DependencyInjectionBeginnerTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        Precondition
    //                                                                        ============
    /**
     * Search "Dependency Injection" by internet and learn it in thirty minutes. (study only) <br>
     * ("Dependency Injection" をインターネットで検索して、30分ほど学んでみましょう。(勉強のみ))
     */
    public void test_whatis_DependencyInjection() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // What is Dependency Injection?
        // - - - - - (your answer?)
        // ``Dependency Injection" is a 25-dollar term for a 5-cent concept.`` - James Shore
        // It means providing objects that the object needs (dependencies) as parameters of a
        // constructor or through the setters instead of having the object create them
        // on it's own.
        //
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Non DI Code Reading
    //                                                                 ===================
    /**
     * What is the difference between NonDiDirectFirstAction and NonDiDirectSecondAction? <br>
     * (NonDiDirectFirstAction と NonDiDirectSecondAction の違いは？)
     */
    public void test_nondi_difference_between_first_and_second() {
        // your answer? =>
        // NonDiDirectSecondAction:
        // callFriend and wakeUp are as bad as in NonDiDirectFirstAction
        // goToOffice and sendGift try to extract some parts of code to make
        // the methods easier to test.
        // Overall they are all hard to test, the process happening inside depends
        // on exact implementations of objects created inside.
        // and your confirmation code here freely
    }

    /**
     * What is the difference between NonDiDirectSecondAction and NonDiFactoryMethodAction? <br>
     * (NonDiDirectSecondAction と NonDiFactoryMethodAction の違いは？)
     */
    public void test_nondi_difference_between_second_and_FactoryMethod()
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException,
            InstantiationException {
        // your answer? =>
        // The good things:
        // - the repeating parts of code are extracted into new methods
        // - now it's possible to better test the code
        // - although we will still get errors because the dog is lazy and screw spec is bad
        // - it would be great to mock the dog and the car dealer
        // - send gift should be able to send other things than sport cars
        // and your confirmation code here freely
        Class<?> nonDiFactoryMethodActionClass = Class.forName("org.docksidestage.bizfw.di.nondi.NonDiFactoryMethodAction");
        Method createAnimal = nonDiFactoryMethodActionClass.getDeclaredMethod("createAnimal");
        createAnimal.setAccessible(true);
        Object lazyDog = createAnimal.invoke(nonDiFactoryMethodActionClass.newInstance());
        assertTrue(lazyDog instanceof TooLazyDog);

        Method createSupercarDealer = nonDiFactoryMethodActionClass.getDeclaredMethod("createSupercarDealer");
        createSupercarDealer.setAccessible(true);
        Object superCarDealer = createSupercarDealer.invoke(nonDiFactoryMethodActionClass.newInstance());
        assertTrue(superCarDealer instanceof SupercarDealer);
    }

    /**
     * What is the difference between NonDiFactoryMethodAction and NonDiIndividualFactoryAction? <br>
     * (NonDiFactoryMethodAction と NonDiIndividualFactoryAction の違いは？)
     */
    public void test_nondi_difference_between_FactoryMethod_and_IndividualFactory() {
        // your answer? =>
        // now all parts of the methods which are not a direct
        // part of them, have been abstracted into factories.
        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                               Using DI Code Reading
    //                                                               =====================
    /**
     * What is the difference between UsingDiAccessorAction and UsingDiAnnotationAction? <br>
     * (UsingDiAccessorAction と UsingDiAnnotationAction の違いは？)
     */
    public void test_usingdi_difference_between_Accessor_and_Annotation() {
        // your answer? => 
        // and your confirmation code here freely
        UsingDiAccessorAction usingDiAccessorAction = new UsingDiAccessorAction();
        UsingDiAnnotationAction usingDiAnnotationAction = new UsingDiAnnotationAction();

        usingDiAccessorAction.setAnimal(new Dog());
        usingDiAccessorAction.setSupercarDealer(new SupercarDealer());

        usingDiAccessorAction.callFriend();
        usingDiAccessorAction.wakeupMe();

        usingDiAccessorAction.goToOffice();
        usingDiAccessorAction.sendGift();
    }

    /**
     * What is the difference between UsingDiAnnotationAction and UsingDiDelegatingAction? <br>
     * (UsingDiAnnotationAction と UsingDiDelegatingAction の違いは？)
     */
    public void test_usingdi_difference_between_Annotation_and_Delegating() {
        // your answer? => 
        // and your confirmation code here freely
    }

    // ===================================================================================
    //                                                           Execute like WebFramework
    //                                                           =========================
    /**
     * Execute callFriend() of accessor action by UsingDiWebFrameworkProcess. (Animal as TooLazyDog) <br>
     * (accessor の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう (Animal は TooLazyDog として))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_accessor() {
        // execution code here
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                doBindAnimal(componentMap);
                doBindAccessorAction(componentMap);
            }

            private void doBindAnimal(Map<Class<?>, Object> componentMap) {
                TooLazyDog dog = new TooLazyDog("tofu");
                dog.petMe();
                dog.playWith(new Cat());
                componentMap.put(Animal.class, dog);
            }

            private void doBindAccessorAction(Map<Class<?>, Object> componentMap) {
                UsingDiAccessorAction action = new UsingDiAccessorAction();
                componentMap.put(UsingDiAccessorAction.class, action);
            }
        });
        simpleDiContainer.resolveDependency();

        UsingDiWebFrameworkProcess usingDiWebFrameworkProcess = new UsingDiWebFrameworkProcess();
        usingDiWebFrameworkProcess.requestAccessorCallFriend();
    }

    /**
     * Execute callFriend() of annotation and delegating actions by UsingDiWebFrameworkProcess. <br>
     *  (Animal as TooLazyDog...so you can increase hit-points of sleepy cat in this method) <br>
     * <br>
     * (annotation, delegating の Action の callFriend() を UsingDiWebFrameworkProcess 経由で実行してみましょう <br>
     *  (Animal は TooLazyDog として...ということで眠い猫のヒットポイントをこのメソッド内で増やしてもOK))
     */
    public void test_usingdi_UsingDiWebFrameworkProcess_callfriend_annotation_delegating() {
        // execution code here
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                doBindAnimal(componentMap);
                doBindAnnotationAction(componentMap);
                doBindDelegatingAction(componentMap);
                doBindDelegatingLogic(componentMap);
            }

            private void doBindAnimal(Map<Class<?>, Object> componentMap) {
                Dog dog = new Dog();
                componentMap.put(Animal.class, dog);
            }

            private void doBindAnnotationAction(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiAnnotationAction.class, new UsingDiAnnotationAction());
            }

            private void doBindDelegatingAction(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiDelegatingAction.class, new UsingDiDelegatingAction());
            }

            private void doBindDelegatingLogic(Map<Class<?>, Object> componentMap) {
                componentMap.put(UsingDiDelegatingLogic.class, new UsingDiDelegatingLogic());
            }
        });
        simpleDiContainer.resolveDependency();

        UsingDiWebFrameworkProcess usingDiWebFrameworkProcess = new UsingDiWebFrameworkProcess();
        usingDiWebFrameworkProcess.requestAnnotationCallFriend();
        usingDiWebFrameworkProcess.requestDelegatingCallFriend();
    }

    /**
     * What is concrete class of instance variable "animal" of UsingDiAnnotationAction? (when registering UsingDiModule) <br>
     * (UsingDiAnnotationAction のインスタンス変数 "animal" の実体クラスは？ (UsingDiModuleを登録した時))
     */
    public void test_usingdi_whatis_animal() {
        // your answer? => TooLazyDog
        // and your confirmation code here freely
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new UsingDiModule());
        simpleDiContainer.resolveDependency();

        UsingDiWebFrameworkProcess usingDiWebFrameworkProcess = new UsingDiWebFrameworkProcess();
        usingDiWebFrameworkProcess.requestAnnotationCallFriend();
    }

    // ===================================================================================
    //                                                                        DI Container
    //                                                                        ============
    /**
     * What is DI container? <br>
     * (DIコンテナとは？)
     */
    public void test_whatis_DIContainer() {
        // your answer? => It's a class responsible for storing and injecting dependencies.
        // and your confirmation code here freely
        SimpleDiContainer simpleDiContainer = SimpleDiContainer.getInstance();
        simpleDiContainer.registerModule(new DiContainerModule() {
            @Override
            public void bind(Map<Class<?>, Object> componentMap) {
                System.out.println("Binding stuff...");
                doBindAnimal(componentMap);
            }

            private void doBindAnimal(Map<Class<?>, Object> componentMap) {
                Cat cat = new Cat();
                componentMap.put(Animal.class, cat);
                System.out.println(componentMap);
            }
        });
        simpleDiContainer.resolveDependency();
        System.out.println(simpleDiContainer.getComponent(Animal.class));
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Lasta Di application? <br>
     * (以下のLasta DiアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     *
     * https://github.com/lastaflute/lastaflute-example-harbor
     */
    public void test_zone_search_component_on_LastaDi() {
        // your answer? => dbflute.xml ???
    }

    /**
     * What is class or file of DI settings that defines MemberBhv class as DI component in the following Spring application? <br>
     * (以下のSpringアプリケーションでMemberBhvクラスをDIコンポーネントとして定義しているDI設定クラスもしくはファイルは？) <br>
     *
     * https://github.com/dbflute-example/dbflute-example-on-springboot
     */
    public void test_zone_search_component_on_Spring() {
        // your answer? => org.docksidestage.dbflute.exbhv.MemberBhv
    }
}
