(ns problems.t-towers
  (:use [midje.sweet])
  (:require [problems.towers :refer :all]))




(facts
  (:peg1 (->State [] [1] [5])) => []
  (:peg2 (->State [] [1] [5])) => [1]
  (:peg3 (->State [] [1] [5])) => [5]
  (:peg1 (->State [] [] [])) => [])

(facts
  (legal? (->State [] [] [])) => true
  (legal? (->State [1] [] [])) => true
  (legal? (->State [] [1] [])) => true
  (legal? (->State [] [] [1])) => true
  (legal? (->State [1] [2] [])) => true
  (legal? (->State [1] [2] [3])) => true
  (legal? (->State [1] [2] [4 3])) => true
  (legal? (->State [1] [2] [5 4 3])) => true
  (legal? (->State [1] [2] [4 4 3])) => false
  (legal? (->State [] [2 1] [5 4 3])) => true
  (legal? (->State [1] [] [2 3])) => false
  (legal? (->State [1 2 3 4 5] [] [])) => false
  (legal? (->State [1 2 3 4 5] [1] [])) => false
  (legal? (->State [1 2 3 4 5 5] [] [55])) => false
  )
