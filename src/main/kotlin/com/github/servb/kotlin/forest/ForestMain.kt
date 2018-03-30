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

import java.util.Random

val random = Random()

fun main(args : Array<String>) {
    var trees = ArrayList<AbstractTree>()
    for (i in 1..5) {
        trees.add(Fir(trees))
        trees.add(Pine(trees))
        trees.add(Oak(trees))
        trees.add(Birch(trees))
        trees.add(Maple(trees))
        trees.add(Walnut(trees))
    }
    
    var animals = ArrayList<AbstractAnimal>()
    
    for (i in 1..10) {
        animals.add(Squirrel(trees[0], Gender.MALE))
        animals.add(Squirrel(trees[1], Gender.FEMALE))
        
        animals.add(Chipmunk(trees[0], Gender.MALE))
        animals.add(Chipmunk(trees[1], Gender.FEMALE))
        
        animals.add(Badger(trees[0], Gender.MALE))
        animals.add(Badger(trees[1], Gender.FEMALE))
        
        animals.add(FlyingSquirrel(trees[0], Gender.MALE))
        animals.add(FlyingSquirrel(trees[1], Gender.FEMALE))
        
        animals.add(Woodpecker(trees[0], Gender.MALE))
        animals.add(Woodpecker(trees[1], Gender.FEMALE))
    }
    
    var predators = ArrayList<Predator>()
    for (i in 1..5) {
        predators.add(Kite(trees[0], Gender.MALE))
        predators.add(Kite(trees[1], Gender.FEMALE))
        predators.add(Wolf(trees[0], Gender.MALE))
        predators.add(Wolf(trees[1], Gender.FEMALE))
    }

    var newAnimals = ArrayList<AbstractAnimal>()
    var newPredators = ArrayList<Predator>()
    
    for (i in 1..100) {
        for (tree in trees) {
            tree.update()
        }
        
        var j = 0
        while (j < animals.size) {
            animals[j].update(animals, newAnimals)
            if (animals[j].foodLevel <= FOOD_DIE) {
                animals.removeAt(j)
            } else {
                ++j
            }
        }
        
        animals.addAll(newAnimals)
        newAnimals.clear()
        
        j = 0
        while (j < predators.size) {
            predators[j].update(animals, predators, newPredators)
            if (predators[j].foodLevel <= FOOD_DIE) {
                predators.removeAt(j)
            } else {
                ++j
            }
        }
        
        predators.addAll(newPredators)
        newPredators.clear()
        
        println("Step: $i, animals: ${animals.size}, kites: ${countKites(predators)}, volves: ${countWolves(predators)}")
    }
}
