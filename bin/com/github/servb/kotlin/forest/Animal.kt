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

val FOOD_LEVEL = 3
val CHILD_RATE = 2
val CHILD_COUNT = 6
val FOOD_DIE = 0
val MAX_POPULATION = 5000

enum class Gender {
    FEMALE,
    MALE;
}

/* Place: 1 -- top, 2 -- middle, otherwise -- bottom */
data class LoveFood(val food : Food, val place : Int)

abstract class AbstractAnimal(var nowTree : AbstractTree,
						  	  val gender : Gender,
						  	  val loveFood : LoveFood,
						      var foodLevel : Int = FOOD_LEVEL) {
	
	fun update(animals : ArrayList<AbstractAnimal>, newAnimals : ArrayList<AbstractAnimal>) {
		--foodLevel
		
		nowTree = nowTree.nearTrees[random.nextInt(nowTree.nearTrees.size)]
		
		val part = when (loveFood.place) {
			1 -> nowTree.top
			2 -> nowTree.middle
			else -> nowTree.bottom
		}
		if (part.food == loveFood.food && part.count > 0) {
			--part.count
			foodLevel = FOOD_LEVEL
		}
		
		if (animals.size + newAnimals.size < MAX_POPULATION &&
				FOOD_DIE < foodLevel && random.nextInt(CHILD_RATE) == 0) {
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
	
	abstract fun create(nowTree : AbstractTree, gender : Gender) : AbstractAnimal
	
}

class Squirrel(nowTree : AbstractTree, gender : Gender)
	: AbstractAnimal(nowTree, gender, LoveFood(Food.CONE, 1)) {
	
	override fun create(nowTree : AbstractTree, gender : Gender) : AbstractAnimal {
		return Squirrel(nowTree, gender)
	}
	
}

class Chipmunk(nowTree : AbstractTree, gender : Gender)
	: AbstractAnimal(nowTree, gender, LoveFood(Food.CONE, 3)) {
	
	override fun create(nowTree : AbstractTree, gender : Gender) : AbstractAnimal {
		return Chipmunk(nowTree, gender)
	}
	
}

class Badger(nowTree : AbstractTree, gender : Gender)
	: AbstractAnimal(nowTree, gender, LoveFood(Food.ROOT, 3)) {
	
	override fun create(nowTree : AbstractTree, gender : Gender) : AbstractAnimal {
		return Badger(nowTree, gender)
	}
	
}

class FlyingSquirrel(nowTree : AbstractTree, gender : Gender)
	: AbstractAnimal(nowTree, gender, LoveFood(Food.LEAVE, 1)) {
	
	override fun create(nowTree : AbstractTree, gender : Gender) : AbstractAnimal {
		return FlyingSquirrel(nowTree, gender)
	}
	
}

class Woodpecker(nowTree : AbstractTree, gender : Gender)
	: AbstractAnimal(nowTree, gender, LoveFood(Food.WORM, 2)) {
	
	override fun create(nowTree : AbstractTree, gender : Gender) : AbstractAnimal {
		return Woodpecker(nowTree, gender)
	}
	
}

abstract class Predator(var nowTree : AbstractTree,
	  	   val gender : Gender,
	       var foodLevel : Int = FOOD_LEVEL) {
	
	abstract fun update(animals : ArrayList<AbstractAnimal>,
			            predators : ArrayList<Predator>, newPredators : ArrayList<Predator>)
	
}

val KITE_CAN_EAT = 1
val MAX_KITES = 30

fun countKites(predators : ArrayList<Predator>) : Int {
	var i = 0
	for (predator in predators) {
		if (predator::class == Kite::class) {
			++i
		}
	}
	return i
}

class Kite(nowTree : AbstractTree, gender : Gender) : Predator(nowTree, gender) {
	
	override fun update(animals : ArrayList<AbstractAnimal>,
			            predators : ArrayList<Predator>, newPredators : ArrayList<Predator>) {
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
				FOOD_DIE < foodLevel && random.nextInt(CHILD_RATE) == 0) {
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

val WOLF_CAN_EAT = 1
val MAX_VOLVES = 30

fun countWolves(predators : ArrayList<Predator>) : Int {
	var i = 0
	for (predator in predators) {
		if (predator::class == Wolf::class) {
			++i
		}
	}
	return i
}

class Wolf(nowTree : AbstractTree, gender : Gender) : Predator(nowTree, gender) {
	
	override fun update(animals : ArrayList<AbstractAnimal>,
			            predators : ArrayList<Predator>, newPredators : ArrayList<Predator>) {
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
		
		if (countWolves(predators) + countWolves(newPredators) < MAX_VOLVES &&
				FOOD_DIE < foodLevel && random.nextInt(CHILD_RATE) == 0) {
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
