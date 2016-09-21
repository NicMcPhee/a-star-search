(ns problems.t-towers
  (:use [midje.sweet])
  (:require [problems.towers :refer :all]))



(facts
  ((:vec-of-pegs (->State [[] [1] [5]])) 0) => []
  ((:vec-of-pegs (->State [[] [1] [5]])) 1) => [1]
  ((:vec-of-pegs (->State [[] [1] [5]])) 2) => [5]
  ((:vec-of-pegs (->State [[] [] []])) 0) => [])

(facts
  (legal? (->State [[] [] []])) => true
  (legal? (->State [[1] [] []])) => true
  (legal? (->State [[] [1] []])) => true
  (legal? (->State [[] [] [1]])) => true
  (legal? (->State [[1] [2] []])) => true
  (legal? (->State [[1] [2] [3]])) => true
  (legal? (->State [[1] [2] [4 3]])) => true
  (legal? (->State [[1] [2] [5 4 3]])) => true
  (legal? (->State [[1] [2] [4 4 3]])) => false
  (legal? (->State [[] [2 1] [5 4 3]])) => true
  (legal? (->State [[1] [] [2 3]])) => false
  (legal? (->State [[1 2 3 4 5] [] []])) => false
  (legal? (->State [[1 2 3 4 5] [1] []])) => false
  (legal? (->State [[1 2 3 4 5 5] [] [55]])) => false
  )

(facts
  (children (->State [[1 2 3] [] []])) => ((->State [[1 2 3] [] []]) (->State [[2 3] [1] []]) (->State [[2 3] [] [1]]))
)
