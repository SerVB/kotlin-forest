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

enum class Food {
    NO,
    NUT,
    CONE,
    LEAF,
    WORM,
    ROOT;
}

enum class TreePlace {
    CROWN,
    TRUNK,
    ROOTS;
}

data class TreePart(val food: Food, var count: Int)

const val COUNT = 1
const val UPDATE_RATE = 4
const val INCREMENT = 20

abstract class Tree(val nearTrees: MutableList<Tree>,
                    val crown: TreePart,
                    val trunk: TreePart,
                    val roots: TreePart) {

    fun update() {
        if (random.nextInt(UPDATE_RATE) == 0) {
            crown.count += INCREMENT
            trunk.count += INCREMENT
            roots.count += INCREMENT
        }
    }

}

class Fir(nearTrees: MutableList<Tree>)
    : Tree(nearTrees, TreePart(Food.CONE, COUNT), TreePart(Food.WORM, COUNT), TreePart(Food.CONE, COUNT))

class Pine(nearTrees: MutableList<Tree>)
    : Tree(nearTrees, TreePart(Food.CONE, COUNT), TreePart(Food.WORM, COUNT), TreePart(Food.CONE, COUNT))

class Oak(nearTrees: MutableList<Tree>)
    : Tree(nearTrees, TreePart(Food.LEAF, COUNT), TreePart(Food.WORM, COUNT), TreePart(Food.ROOT, COUNT))

class Birch(nearTrees: MutableList<Tree>)
    : Tree(nearTrees, TreePart(Food.LEAF, COUNT), TreePart(Food.WORM, COUNT), TreePart(Food.ROOT, COUNT))

class Maple(nearTrees: MutableList<Tree>)
    : Tree(nearTrees, TreePart(Food.LEAF, COUNT), TreePart(Food.WORM, COUNT), TreePart(Food.ROOT, COUNT))

class Walnut(nearTrees: MutableList<Tree>)
    : Tree(nearTrees, TreePart(Food.NUT, COUNT), TreePart(Food.WORM, COUNT), TreePart(Food.NUT, COUNT))
