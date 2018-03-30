package com.github.servb.kotlin.forest

import io.kotlintest.properties.*
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec

class ForestTests : ShouldSpec() {
    init {
        "animals" {
            should("match treir children") {
		        var trees = ArrayList<AbstractTree>()
		        val tree = Fir(trees)
		        trees.add(tree)
                
                Squirrel(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Chipmunk::class
                Chipmunk(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Chipmunk::class
                Badger(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Badger::class
                FlyingSquirrel(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe FlyingSquirrel::class
                Woodpecker(tree, Gender.MALE).create(tree, Gender.MALE)::class shouldBe Woodpecker::class
            }
        }
        
        "predator count" {
            should("work properly ;)") {
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
