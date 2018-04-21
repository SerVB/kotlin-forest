package com.github.servb.kotlin.forest

import io.kotlintest.matchers.*
import io.kotlintest.properties.forAll
import io.kotlintest.specs.ShouldSpec

class ForestTests : ShouldSpec() {
    init {
        "animals" {
            should("match their children") {
		        var trees = ArrayList<AbstractTree>()
		        val tree = Fir(trees)
		        trees.add(tree)
                
                Squirrel(tree, Gender.MALE).create(tree, Gender.MALE) shouldBe beOfType<Squirrel>()
                Chipmunk(tree, Gender.MALE).create(tree, Gender.MALE) shouldBe beOfType<Chipmunk>()
                Badger(tree, Gender.MALE).create(tree, Gender.MALE) shouldBe beOfType<Badger>()
                FlyingSquirrel(tree, Gender.MALE).create(tree, Gender.MALE) shouldBe beOfType<FlyingSquirrel>()
                Woodpecker(tree, Gender.MALE).create(tree, Gender.MALE) shouldBe beOfType<Woodpecker>()
            }
        }
        
        "predator count" {
            should("count the same count") {
                forAll({ kites: Int, wolves: Int ->
	                var trees = ArrayList<AbstractTree>()
	                val tree = Fir(trees)
	                trees.add(tree)
                    
                    val COUNT_LIMIT = 107
                    
					val kitesCount = kites % COUNT_LIMIT
                    val wolvesCount = wolves % COUNT_LIMIT
                    
                    var predators = ArrayList<Predator>()
				    for (i in 1..kitesCount) {
				        predators.add(Kite(trees[0], Gender.MALE))
                    }
                    
                    for (i in 1..wolvesCount) {
                        predators.add(Wolf(trees[0], Gender.MALE))
                    }
                        
                    countKites(predators) == kitesCount && countWolves(predators) == wolvesCount
				})
            }
        }
        
        "trees" {
            // Nothing to test ):
        }
    }
}
