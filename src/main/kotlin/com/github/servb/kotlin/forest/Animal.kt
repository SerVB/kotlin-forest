/*
 * The MIT License
 *
 * Copyright 2018 SerVB.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.servb.kotlin.forest

const val FOOD_LEVEL = 3
const val CHILD_RATE = 2
const val CHILD_COUNT = 6
const val FOOD_DIE_LEVEL = 0
const val MAX_POPULATION = 5000

enum class Gender {
    FEMALE,
    MALE;
}

data class LoveFood(val food: Food, val place: TreePlace)

abstract class Animal(var nowTree: Tree,
                      val gender: Gender,
                      val loveFood: LoveFood,
                      private var foodLevel: Int = FOOD_LEVEL) {

    val isDead get() = foodLevel <= FOOD_DIE_LEVEL

    fun update(animals: MutableList<Animal>, newAnimals: MutableList<Animal>) {
        --foodLevel

        nowTree = nowTree.nearTrees[random.nextInt(nowTree.nearTrees.size)]

        val part = when (loveFood.place) {
            TreePlace.CROWN -> nowTree.crown
            TreePlace.TRUNK -> nowTree.trunk
            TreePlace.ROOTS -> nowTree.roots
        }
        if (part.food == loveFood.food && part.count > 0) {
            --part.count
            foodLevel = FOOD_LEVEL
        }

        if (animals.size + newAnimals.size < MAX_POPULATION &&
                FOOD_DIE_LEVEL < foodLevel && random.nextInt(CHILD_RATE) == 0) {
            for (animal in animals) {
                if (animal.gender != this.gender && animal.nowTree === this.nowTree
                        && animal::class == this::class) {
                    for (i in 1..CHILD_COUNT) {
                        newAnimals.add(create(nowTree,
                                if (this.gender == Gender.MALE) Gender.FEMALE else Gender.MALE))
                    }
                }
            }
        }
    }

    abstract fun create(nowTree: Tree, gender: Gender): Animal

}

class Squirrel(nowTree: Tree, gender: Gender)
    : Animal(nowTree, gender, LoveFood(Food.CONE, TreePlace.CROWN)) {

    override fun create(nowTree: Tree, gender: Gender): Animal {
        return Squirrel(nowTree, gender)
    }

}

class Chipmunk(nowTree: Tree, gender: Gender)
    : Animal(nowTree, gender, LoveFood(Food.CONE, TreePlace.ROOTS)) {

    override fun create(nowTree: Tree, gender: Gender): Animal {
        return Chipmunk(nowTree, gender)
    }

}

class Badger(nowTree: Tree, gender: Gender)
    : Animal(nowTree, gender, LoveFood(Food.ROOT, TreePlace.ROOTS)) {

    override fun create(nowTree: Tree, gender: Gender): Animal {
        return Badger(nowTree, gender)
    }

}

class FlyingSquirrel(nowTree: Tree, gender: Gender)
    : Animal(nowTree, gender, LoveFood(Food.LEAF, TreePlace.CROWN)) {

    override fun create(nowTree: Tree, gender: Gender): Animal {
        return FlyingSquirrel(nowTree, gender)
    }

}

class Woodpecker(nowTree: Tree, gender: Gender)
    : Animal(nowTree, gender, LoveFood(Food.WORM, TreePlace.TRUNK)) {

    override fun create(nowTree: Tree, gender: Gender): Animal {
        return Woodpecker(nowTree, gender)
    }

}

abstract class Predator(var nowTree: Tree,
                        val gender: Gender,
                        protected var foodLevel: Int = FOOD_LEVEL) {

    val isDead get() = foodLevel <= FOOD_DIE_LEVEL

    abstract fun update(animals: MutableList<Animal>,
                        predators: MutableList<Predator>, newPredators: MutableList<Predator>)

}

const val KITE_CAN_EAT = 1
const val MAX_KITES = 30

fun countKites(predators: MutableList<Predator>) = predators.filter { it::class == Kite::class }.size

class Kite(nowTree: Tree, gender: Gender) : Predator(nowTree, gender) {

    override fun update(animals: MutableList<Animal>,
                        predators: MutableList<Predator>, newPredators: MutableList<Predator>) {
        --foodLevel

        nowTree = nowTree.nearTrees[random.nextInt(nowTree.nearTrees.size)]

        var eaten = 0
        var i = 0
        while (i < animals.size) {
            if (animals[i].nowTree == this.nowTree && animals[i]::class != Badger::class) {
                animals.removeAt(i)
                foodLevel = FOOD_LEVEL
                ++eaten
            } else {
                ++i
            }
            if (eaten >= KITE_CAN_EAT) {
                break
            }
        }

        if (countKites(predators) + countKites(newPredators) < MAX_KITES &&
                FOOD_DIE_LEVEL < foodLevel && random.nextInt(CHILD_RATE) == 0) {
            for (kite in predators) {
                if (kite::class == Kite::class &&
                        kite.gender != this.gender && kite.nowTree === this.nowTree) {
                    for (_i in 1..CHILD_COUNT) {
                        newPredators.add(Kite(nowTree,
                                if (this.gender == Gender.MALE) Gender.FEMALE else Gender.MALE))
                    }
                }
            }
        }
    }

}

const val WOLF_CAN_EAT = 1
const val MAX_WOLVES = 30

fun countWolves(predators: MutableList<Predator>) = predators.filter { it::class == Wolf::class }.size

class Wolf(nowTree: Tree, gender: Gender) : Predator(nowTree, gender) {

    override fun update(animals: MutableList<Animal>,
                        predators: MutableList<Predator>, newPredators: MutableList<Predator>) {
        --foodLevel

        nowTree = nowTree.nearTrees[random.nextInt(nowTree.nearTrees.size)]

        var eaten = 0
        var i = 0
        while (i < animals.size) {
            if (animals[i].nowTree == this.nowTree && animals[i]::class != Woodpecker::class) {
                animals.removeAt(i)
                foodLevel = FOOD_LEVEL
                ++eaten
            } else {
                ++i
            }
            if (eaten >= WOLF_CAN_EAT) {
                break
            }
        }

        if (countWolves(predators) + countWolves(newPredators) < MAX_WOLVES &&
                FOOD_DIE_LEVEL < foodLevel && random.nextInt(CHILD_RATE) == 0) {
            for (wolf in predators) {
                if (wolf::class == Wolf::class &&
                        wolf.gender != this.gender && wolf.nowTree === this.nowTree) {
                    for (_i in 1..CHILD_COUNT) {
                        newPredators.add(Wolf(nowTree,
                                if (this.gender == Gender.MALE) Gender.FEMALE else Gender.MALE))
                    }
                }
            }
        }
    }

}
