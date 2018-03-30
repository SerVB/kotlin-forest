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

import java.util.ArrayList;

enum class Food {
	NO,
	NUT,
	CONE,
	LEAVE,
	WORM,
	ROOT;
}

data class Part(var food : Food, var count : Int)

val COUNT = 1
val UPDATE_RATE = 4
val INCREMENT = 20

open class AbstractTree(var nearTrees : ArrayList<AbstractTree>,
						var top : Part,
						var middle : Part,
						var bottom : Part) {

	fun update() {
		if (random.nextInt(UPDATE_RATE) == 0) {
			top.count += INCREMENT
			middle.count += INCREMENT
			bottom.count += INCREMENT
		}
	}
	
}

class Fir(nearTrees : ArrayList<AbstractTree>)
	: AbstractTree(nearTrees, Part(Food.CONE, COUNT), Part(Food.WORM, COUNT), Part(Food.CONE, COUNT))

class Pine(nearTrees : ArrayList<AbstractTree>)
	: AbstractTree(nearTrees, Part(Food.CONE, COUNT), Part(Food.WORM, COUNT), Part(Food.CONE, COUNT))

class Oak(nearTrees : ArrayList<AbstractTree>)
	: AbstractTree(nearTrees, Part(Food.LEAVE, COUNT), Part(Food.WORM, COUNT), Part(Food.ROOT, COUNT))

class Birch(nearTrees : ArrayList<AbstractTree>)
	: AbstractTree(nearTrees, Part(Food.LEAVE, COUNT), Part(Food.WORM, COUNT), Part(Food.ROOT, COUNT))

class Maple(nearTrees : ArrayList<AbstractTree>)
	: AbstractTree(nearTrees, Part(Food.LEAVE, COUNT), Part(Food.WORM, COUNT), Part(Food.ROOT, COUNT))

class Walnut(nearTrees : ArrayList<AbstractTree>)
	: AbstractTree(nearTrees, Part(Food.NUT, COUNT), Part(Food.WORM, COUNT), Part(Food.NUT, COUNT))
