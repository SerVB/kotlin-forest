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

fun main(args: Array<String>) {
    val trees = createForest()

    val animals = createAnimalsOnTrees(trees)

    val predators = createPredatorsOnTrees(trees)

    val newAnimals = ArrayList<Animal>()
    val newPredators = ArrayList<Predator>()

    for (i in 1..100) { // Main cycle
        animals.shuffle()
        predators.shuffle()

        for (tree in trees) {
            tree.update()
        }

        var j = 0
        while (j < animals.size) {
            animals[j].update(animals, newAnimals)
            if (animals[j].isDead) {
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
            if (predators[j].isDead) {
                predators.removeAt(j)
            } else {
                ++j
            }
        }

        predators.addAll(newPredators)
        newPredators.clear()

        println("Step: $i, animals: ${animals.size}, " +
                "kites: ${countKites(predators)}, wolves: ${countWolves(predators)}")
    }
}

fun createForest(): MutableList<Tree> {
    val trees = ArrayList<Tree>()

    for (i in 1..5) {
        trees.add(Fir(ArrayList()))
        trees.add(Pine(ArrayList()))
        trees.add(Oak(ArrayList()))
        trees.add(Birch(ArrayList()))
        trees.add(Maple(ArrayList()))
        trees.add(Walnut(ArrayList()))
    }

    for (i in trees.indices) {
        for (edgeDelta in 1..5) {
            val nextTreeIdx = (i + edgeDelta) % trees.size
            val nextTree = trees[nextTreeIdx]
            trees[i].nearTrees.add(nextTree)
        }
    }

    return trees
}

fun createAnimalsOnTrees(trees: List<Tree>): MutableList<Animal> {
    val animals = ArrayList<Animal>()

    for (i in 1..10) {
        animals.add(Squirrel(trees[(i + 1) % trees.size], Gender.MALE))
        animals.add(Squirrel(trees[(i + 2) % trees.size], Gender.FEMALE))

        animals.add(Chipmunk(trees[(i + 1) % trees.size], Gender.MALE))
        animals.add(Chipmunk(trees[(i + 2) % trees.size], Gender.FEMALE))

        animals.add(Badger(trees[(i + 1) % trees.size], Gender.MALE))
        animals.add(Badger(trees[(i + 2) % trees.size], Gender.FEMALE))

        animals.add(FlyingSquirrel(trees[(i + 1) % trees.size], Gender.MALE))
        animals.add(FlyingSquirrel(trees[(i + 2) % trees.size], Gender.FEMALE))

        animals.add(Woodpecker(trees[(i + 1) % trees.size], Gender.MALE))
        animals.add(Woodpecker(trees[(i + 2) % trees.size], Gender.FEMALE))
    }
    return animals
}

fun createPredatorsOnTrees(trees: List<Tree>): MutableList<Predator> {
    val predators = ArrayList<Predator>()

    for (i in 1..4) {
        predators.add(Kite(trees[(i + 1) % trees.size], Gender.MALE))
        predators.add(Kite(trees[(i + 2) % trees.size], Gender.FEMALE))
        predators.add(Wolf(trees[(i + 1) % trees.size], Gender.MALE))
        predators.add(Wolf(trees[(i + 2) % trees.size], Gender.FEMALE))
    }

    return predators
}
